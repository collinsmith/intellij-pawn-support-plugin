package net.alliedmods.lang.amxxpawn.oldpsi2;

/**
 * Represents an AMXX Pawn {#include ""} preprocessor directive.
 */
public interface PsiRelativeIncludeStatement extends PsiIncludeStatementBase {
  /**
   * An array of PSI relative include statements which can be reused to avoid any unnecessary allocations.
   */
  PsiRelativeIncludeStatement[] EMPTY_ARRAY = new PsiRelativeIncludeStatement[0];
}
