package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.pom.PomRenameableTarget;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.PsiTarget;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PsiEnum
    extends PsiNameIdentifierOwner, PsiTarget, PomRenameableTarget<PsiElement> {

  /**
   * Returns the tag of the enum.
   */
  @NotNull PsiTag getTag();

  /**
   * Returns the name of the enum, also serves as the tag if no explicit tag was set.
   */
  @Override @Nullable PsiElement getNameIdentifier();

  /**
   *
   * @return
   */
  @Nullable PsiExpression getIncrement();

  /**
   * Returns the list of enum constants.
   */
  @NotNull PsiEnumConstant[] getConstants();

}
