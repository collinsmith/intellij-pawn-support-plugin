package net.alliedmods.lang.amxxpawn.oldpsi2.reference;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import com.intellij.util.containers.ConcurrentFactoryMap;

import net.alliedmods.lang.amxxpawn.ApSupport;
import net.alliedmods.lang.amxxpawn.file.ApIncludeFileType;
import net.alliedmods.lang.amxxpawn.oldpsi2.ApPsiUtil;
import net.alliedmods.lang.amxxpawn.oldpsi2.PsiApFile;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ApFileReferenceManager {
  private final PsiManager psiManager;
  private final DumbService dumbService;

  private static final String[] EXTENSIONS = {ApSupport.INC, ApSupport.P, ApSupport.PAWN};

  public static ApFileReferenceManager getInstance(Project project) {
    return ServiceManager.getService(project, ApFileReferenceManager.class);
  }

  public ApFileReferenceManager(PsiManager psiManager, DumbService dumbService) {
    this.psiManager = psiManager;
    this.dumbService = dumbService;
  }

  @NotNull
  public List<PsiApFile> findApFiles(@NotNull Module module, String name) {
    ConcurrentFactoryMap<String, List<PsiApFile>> map
        = CachedValuesManager.getManager(module.getProject()).getCachedValue(module, () -> {
      ConcurrentFactoryMap<String, List<PsiApFile>> factoryMap
          = new ConcurrentFactoryMap<String, List<PsiApFile>>() {
        @Nullable
        @Override
        protected List<PsiApFile> create(String fileName) {
          GlobalSearchScope scope = GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(module);
          return findApFiles(scope, fileName, ApFileNameEvaluator.DEFAULT);
        }
      };
      return CachedValueProvider.Result.create(factoryMap, PsiModificationTracker.MODIFICATION_COUNT);
    });

    return map.get(name);
  }

  @NotNull
  public List<PsiApFile> findApFiles(@NotNull GlobalSearchScope scope,
                                     String name,
                                     @NotNull ApFileNameEvaluator evaluator) {
    ArrayList<PsiApFile> result = new ArrayList<>();
    processApFiles(scope, (includeFile, file) -> {
      // TODO: this can be improved and sped up instead of comparing the start of the string EXTENSIONS.length times
      for (String extension : EXTENSIONS) {
        if (includeFile.equals(name + "." + extension)) {
          result.add(file);
          break;
        }
      }

      return true;
    }, evaluator);
    return result;
  }

  public boolean processApFiles(@NotNull GlobalSearchScope scope,
                                @NotNull ApFileProcessor processor,
                                @NotNull ApFileNameEvaluator evaluator) {
    for (VirtualFile file : FileTypeIndex.getFiles(ApIncludeFileType.INSTANCE, scope)) {
      if (!processFile(file, evaluator, processor)) {
        return false;
      }
    }

    if (!dumbService.isDumb()) {
      // TODO: implement support?
      //throw new UnsupportedOperationException();
    }

    return true;
  }

  private boolean processFile(@NotNull VirtualFile file,
                              @NotNull ApFileNameEvaluator evaluator,
                              @NotNull ApFileProcessor processor) {
    PsiFile psiFile = psiManager.findFile(file);
    PsiApFile apFile = ApPsiUtil.getApFile(psiFile);
    if (apFile != null) {
      String includeName = evaluator.evaluateName(psiFile);
      if (includeName != null && !processor.process(includeName, apFile)) {
        return false;
      }
    }

    return true;
  }
}
