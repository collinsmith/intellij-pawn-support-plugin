package net.alliedmods.lang.amxxpawn.oldpsi2;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ApPsiUtil {

  private ApPsiUtil() {}

  @Nullable
  public static PsiApFile getApFile(@Nullable PsiFile file) {
    if (!(file instanceof PsiApFile)) {
      return null;
    }

    return (PsiApFile) file;
  }

  @Nullable
  public static PsiApFile getApFile(@Nullable PsiElement element) {
    if (!(element instanceof PsiFile)) {
      return null;
    }

    return getApFile((PsiFile) element);
  }

  @Nullable
  public static PsiApFile getApFile(@NotNull VirtualFile file, @NotNull Project project) {
    return getApFile(PsiManager.getInstance(project).findFile(file));
  }

  public static boolean isApFile(@Nullable PsiFile file) {
    return getApFile(file) != null;
  }

}
