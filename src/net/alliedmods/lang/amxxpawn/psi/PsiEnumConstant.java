package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.util.IncorrectOperationException;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an enum constant in AMXX Pawn.
 */
public interface PsiEnumConstant extends PsiField {
  /**
   * Returns the tag of the enum constant, or {@code null} if it doesn't have one.
   */
  @Override
  @NotNull
  PsiTag getTag();

  /**
   * Adds an initializer to the enum constant declaration, or if {@code initializer} is
   * {@code null}, removes the initializer from the enum constant declaration.
   *
   * @throws IncorrectOperationException if the modification fails for some reason.
   */
  @Override
  void setInitializer(@org.jetbrains.annotations.Nullable PsiExpression initializer)
      throws IncorrectOperationException;
}
