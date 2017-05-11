package net.alliedmods.lang.amxxpawn.oldpsi2;

/**
 * Represents an AMXX Pawn {@code #include <>} preprocessor directive.
 */
public interface PsiIncludeStatement extends PsiIncludeStatementBase {
  /**
   * An array of PSI include statements which can be reused to avoid any unnecessary allocations.
   */
  PsiIncludeStatement[] EMPTY_ARRAY = new PsiIncludeStatement[0];
}
