package net.alliedmods.lang.amxxpawn.oldpsi2.reference;

import com.intellij.psi.PsiFile;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ApFileNameEvaluator {
  ApFileNameEvaluator DEFAULT = psiFile -> {
    /*PsiDirectory directory = ReadAction.compute(() -> psiFile.getParent());
    if (directory == null) {
      return null;
    }

    StringBuilder qualifiedName = new StringBuilder(directory.toString());
    qualifiedName.append('/');
    qualifiedName.append(psiFile.)*/
    return psiFile.getVirtualFile().getName();
  };

  @Nullable
  String evaluateName(@NotNull PsiFile psiFile);
}
