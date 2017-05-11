package net.alliedmods.lang.amxxpawn.psi.preprocessor;

import com.intellij.psi.PsiElement;
import com.intellij.util.ArrayFactory;

import javax.annotation.Nullable;

public interface PsiIncludeStatement extends PsiElement {
  PsiIncludeStatement[] EMPTY_ARRAY = new PsiIncludeStatement[0];
  ArrayFactory<PsiIncludeStatement> ARRAY_FACTORY =
      i -> i == 0 ? EMPTY_ARRAY : new PsiIncludeStatement[0];

  @Nullable
  PsiApFileReference getIncludeReference();

  @Nullable
  PsiElement resolve();
}
