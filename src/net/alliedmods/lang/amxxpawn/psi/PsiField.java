package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.util.IncorrectOperationException;

import org.jetbrains.annotations.Nullable;

/**
 * Represents an AMXX Pawn variable or enum constant.
 */
public interface PsiField extends PsiMember, PsiVariable, PsiDocCommentOwner {
  /**
   * Adds an initializer to the field declaration, or if {@code initializer} is {@code null},
   * removes the initializer from the field declaration.
   *
   * @throws IncorrectOperationException if the modification fails for some reason.
   */
  void setInitializer(@Nullable PsiExpression initializer) throws IncorrectOperationException;

  /**
   * {@inheritDoc}
   */
  @Override @Nullable PsiIdentifier getNameIdentifier();
}
