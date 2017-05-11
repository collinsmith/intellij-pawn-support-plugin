package net.alliedmods.lang.amxxpawn.oldpsi2.reference;

import com.intellij.codeInsight.completion.PrioritizedLookupElement;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.ResolvingHint;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Function;
import com.intellij.util.ReflectionUtil;
import com.intellij.util.SmartList;
import com.intellij.util.containers.ContainerUtil;

import net.alliedmods.lang.amxxpawn.ApIcons;
import net.alliedmods.lang.amxxpawn.oldpsi2.PsiApFile;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PsiApFileReference extends PsiReferenceBase<PsiElement>
    implements PsiPolyVariantReference, ResolvingHint, ApFileNameEvaluator {
  private static final Function<PsiApFile, PsiElement> FILE_TO_PSI = PsiApFile::getContainingFile;

  private final String fileReference;

  public PsiApFileReference(PsiElement element) {
    this(element, false);
  }

  public PsiApFileReference(PsiElement element, boolean soft) {
    super(element, soft);
    this.fileReference = getValue();
  }

  @Override
  public boolean canResolveTo(Class<? extends PsiElement> elementClass) {
    return ReflectionUtil.isAssignable(PsiFile.class, elementClass);
  }

  @Nullable
  @Override
  public PsiElement resolve() {
    ResolveResult[] results = multiResolve(false);
    return results.length == 1 ? results[0].getElement() : null;
  }

  @NotNull
  @Override
  public ResolveResult[] multiResolve(boolean b) {
    GlobalSearchScope scope = getSearchScope();
    ApFileReferenceManager manager = ApFileReferenceManager.getInstance(myElement.getProject());
    List<PsiApFile> files = manager.findApFiles(scope, fileReference, this);
    return PsiElementResolveResult.createResults(ContainerUtil.map(files, FILE_TO_PSI));
  }

  @NotNull
  @Override
  public String getCanonicalText() {
    return fileReference;
  }

  public GlobalSearchScope getSearchScope() {
    Module module = ModuleUtilCore.findModuleForPsiElement(myElement);
    if (module == null) {
      return getElement().getResolveScope();
    }

    return GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(module);
  }

  @NotNull
  @Override
  public Object[] getVariants() {
    Project project = getElement().getProject();
    ProjectFileIndex index = ProjectFileIndex.SERVICE.getInstance(project);
    ApFileReferenceManager manager = ApFileReferenceManager.getInstance(project);

    Set<String> fileNames = new HashSet<>();
    List<LookupElement> variants = new SmartList<>();
    ApFileProcessor processor = (file, psiFile) -> {
      if (!fileNames.add(file)) {
        return true;
      }

      LookupElementBuilder builder = LookupElementBuilder.create(file)
          .withIcon(ApIcons.AmxxInclude16);
      boolean isInContent = index.isInContent(psiFile.getVirtualFile());
      variants.add(isInContent
          ? PrioritizedLookupElement.withPriority(builder, Double.MAX_VALUE)
          : builder);
      return true;
    };

    GlobalSearchScope scope = getSearchScope();
    manager.processApFiles(scope, processor, this);
    return variants.toArray(new LookupElement[variants.size()]);
  }

  @Nullable
  @Override
  public String evaluateName(@NotNull PsiFile psiFile) {
    return DEFAULT.evaluateName(psiFile);
  }
}
