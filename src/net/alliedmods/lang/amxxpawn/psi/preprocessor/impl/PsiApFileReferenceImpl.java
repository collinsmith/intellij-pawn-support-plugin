package net.alliedmods.lang.amxxpawn.psi.preprocessor.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.impl.PsiManagerEx;
import com.intellij.psi.impl.file.impl.FileManager;
import com.intellij.psi.impl.source.SourceTreeToPsiMap;
import com.intellij.psi.impl.source.resolve.ResolveCache;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.psi.impl.source.tree.FileElement;
import com.intellij.psi.impl.source.tree.SharedImplUtil;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.ProjectScope;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.util.IncorrectOperationException;

import net.alliedmods.lang.amxxpawn.ApFileType;
import net.alliedmods.lang.amxxpawn.psi.ApElementVisitor;
import net.alliedmods.lang.amxxpawn.psi.PsiApFile;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.ApPreprocessorElementTypes;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.PsiApFileReference;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class PsiApFileReferenceImpl extends CompositePsiElement implements PsiApFileReference {
  private static final Logger LOG = Logger.getInstance("#net.alliedmods.lang.amxxpawn.psi.preprocessor.impl.PsiApFileReferenceImpl");

  public PsiApFileReferenceImpl() {
    super(ApPreprocessorElementTypes.FILE_REFERENCE);
  }

  public boolean isRelative() {
    PsiUtilCore.ensureValid(this);
    String text = getText();
    return text.charAt(0) == '\"';
  }

  /*@Override
  public int getTextOffset() {
    ASTNode refName = getReferenceNameNode();
    return refName != null ? refName.getStartOffset() : super.getTextOffset();
  }*/

  @Nullable
  @Override
  public PsiElement getReferenceNameElement() {
    return SourceTreeToPsiMap.treeElementToPsi(getReferenceNameNode());
  }

  @Nullable
  private ASTNode getReferenceNameNode() {
    return this;
  }

  @NotNull
  @Override
  public String getCanonicalText() {
    return getNormalizedText();
  }

  @NotNull
  private String getNormalizedText() {
    PsiElement target = resolve();
    if (target instanceof PsiApFile) {
      return ((PsiApFile) target).getVirtualFile().getPath();
    }

    LOG.assertTrue(false);
    return null;
  }

  @Override
  public PsiReference getReference() {
    return this;
  }

  @Nullable
  @Override
  public PsiElement resolve() {
    ResolveResult[] results = multiResolve(false);
    return results.length == 1 ? results[0].getElement() : null;
  }

  @NotNull
  @Override
  public ResolveResult[] multiResolve(boolean incompleteCode) {
    FileElement fileElement = (FileElement) SharedImplUtil.findFileElement(this);
    if (fileElement == null) {
      LOG.error("fileElement == null!");
      return ResolveResult.EMPTY_ARRAY;
    }

    PsiManagerEx manager = fileElement.getManager();
    if (manager == null) {
      LOG.error("fileElement.getManager() == null!");
      return ResolveResult.EMPTY_ARRAY;
    }

    PsiFile file = SharedImplUtil.getContainingFile(fileElement);
    boolean valid = file != null && file.isValid();
    if (!valid) {
      LOG.error("invalid!");
      return ResolveResult.EMPTY_ARRAY;
    }

    Project project = manager.getProject();
    ResolveCache resolveCache = ResolveCache.getInstance(project);
    return resolveCache.resolveWithCaching(
        this, Resolver.INSTANCE, true, incompleteCode, file);
  }

  private ResolveResult[] resolve(boolean relative, PsiFile containingFile) {
    String packageName = getText();
    packageName = packageName.substring(1, packageName.length() - 1).trim();

    FileManager fileManager = getManager().getFileManager();
    if (relative) {
      VirtualFile file = containingFile.getContainingDirectory().getVirtualFile().findFileByRelativePath(packageName);
      if (file == null) {
        return ResolveResult.EMPTY_ARRAY;
      }

      PsiFile psiFile = fileManager.findFile(file);
      return PsiElementResolveResult.createResults(psiFile);
    } else {
      // TODO: This could be narrowed down to the AMXX includes PATH
      GlobalSearchScope libraries = ProjectScope.getLibrariesScope(getProject());
      Collection<VirtualFile> files = FileTypeIndex.getFiles(ApFileType.INSTANCE, libraries);

      Collection<PsiElement> results = new ArrayList<>();
      for (VirtualFile file : files) {
        if (file.getNameWithoutExtension().equals(packageName)) {
          results.add(fileManager.findFile(file));
        }
      }

      return PsiElementResolveResult.createResults(results);
    }
  }

  @NotNull
  @Override
  public Object[] getVariants() {
    return new Object[0];
  }

  @Override
  public boolean isReferenceTo(PsiElement element) {
    if (!(element instanceof PsiApFile)) {
      return false;
    }

    String path = ((PsiApFile) element).getVirtualFile().getPath();
    return path.equals(getNormalizedText());
  }

  @Override
  public PsiElement bindToElement(@NotNull PsiElement psiElement) throws IncorrectOperationException {
    throw new IncorrectOperationException();
  }

  @Override
  public PsiElement handleElementRename(String s) throws IncorrectOperationException {
    throw new IncorrectOperationException();
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ApElementVisitor) {
      ((ApElementVisitor) visitor).visitReferenceElement(this);
    } else {
      super.accept(visitor);
    }
  }

  @Override
  public final TextRange getRangeInElement() {
    return new TextRange(0, getTextLength());
  }

  @Override
  public final PsiElement getElement() {
    return this;
  }

  @Override
  public boolean isSoft() {
    return false;
  }

  private static final class Resolver implements ResolveCache.PolyVariantResolver<PsiApFileReference> {
    static final Resolver INSTANCE = new Resolver();

    @NotNull
    @Override
    public ResolveResult[] resolve(@NotNull PsiApFileReference ref, boolean incompleteCode) {
      PsiApFileReferenceImpl refElement = (PsiApFileReferenceImpl) ref;
      boolean relative = refElement.isRelative();
      PsiFile containingFile = refElement.getContainingFile();
      return refElement.resolve(relative, containingFile);
    }
  }

  @Override
  public String toString() {
    return "PsiApFileReference";
  }
}
