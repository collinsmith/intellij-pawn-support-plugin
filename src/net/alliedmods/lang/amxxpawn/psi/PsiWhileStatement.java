package net.alliedmods.lang.amxxpawn.psi;

import org.jetbrains.annotations.Nullable;

/**
 * Represents an AMXX Pawn {@code while} statement.
 */
public interface PsiWhileStatement extends PsiLoopStatement {
  /**
   * Returns the expression representing the exit condition of the loop.
   */
  @Nullable PsiExpression getCondition();

  /**
   * Returns the opening parenthesis enclosing the statement, or {@code null} if the statement is incomplete.
   */
  @Nullable PsiApToken getLParenth();

  /**
   * Returns the closing parenthesis enclosing the statement, or {@code null} if the statement is incomplete.
   */
  @Nullable PsiApToken getRParenth();
}
