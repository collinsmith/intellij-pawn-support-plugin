package net.alliedmods.lang.amxxpawn.oldpsi2.reference.impl;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.PsiManagerEx;
import com.intellij.psi.impl.file.impl.FileManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;

import net.alliedmods.lang.amxxpawn.ApSupport;
import net.alliedmods.lang.amxxpawn.file.ApIncludeFileType;
import net.alliedmods.lang.amxxpawn.oldpsi2.ApPsiUtil;
import net.alliedmods.lang.amxxpawn.oldpsi2.PsiApFile;
import net.alliedmods.lang.amxxpawn.oldpsi2.reference.ApFileManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Nullable;

public class ApFileManagerImpl implements ApFileManager {
  private static final String[] EXTENSIONS = {ApSupport.INC, ApSupport.P, ApSupport.PAWN};

  private final PsiManagerEx psiManager;
  private final ProjectRootManager projectRootManager;
  private final FileManager fileManager;

  public static ApFileManager getInstance(Project project) {
    return ServiceManager.getService(project, ApFileManager.class);
  }

  public ApFileManagerImpl(PsiManagerEx psiManager, ProjectRootManager projectRootManager) {
    this.psiManager = psiManager;
    this.projectRootManager = projectRootManager;
    this.fileManager = psiManager.getFileManager();
  }

  @Nullable
  @Override
  public PsiApFile findInclude(@NotNull String name, @NotNull GlobalSearchScope scope) {
    PsiApFile[] files = findIncludes(name, scope);
    return files.length == 1 ? files[0] : null;
  }

  @NotNull
  @Override
  public PsiApFile[] findIncludes(@NotNull String name, @NotNull GlobalSearchScope scope) {
    ArrayList<PsiApFile> results = new ArrayList<>();
    Collection<VirtualFile> files = FileTypeIndex.getFiles(ApIncludeFileType.INSTANCE, scope);
    for (VirtualFile file : files) {
      String fileName = file.getNameWithoutExtension();
      if (name.equals(fileName)) {
        for (String extension : EXTENSIONS) {
          if (extension.equals(file.getExtension())) {
            PsiFile psiFile = fileManager.findFile(file);
            PsiApFile apFile = ApPsiUtil.getApFile(psiFile);
            assert apFile != null;
            results.add(apFile);
            break;
          }
        }
      }
    }

    return results.toArray(new PsiApFile[results.size()]);
  }
}
