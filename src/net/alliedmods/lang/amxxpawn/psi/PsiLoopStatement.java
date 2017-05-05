package net.alliedmods.lang.amxxpawn.psi;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a AMXX Pawn statement which loop some statement.
 */
public interface PsiLoopStatement extends PsiStatement {
  /**
   * Returns the body of the statement.
   */
  @Nullable
  PsiStatement getBody();
}
