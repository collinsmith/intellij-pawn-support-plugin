package net.alliedmods.lang.amxxpawn.oldpsi2;

/**
 * Represents an AMXX Pawn identifier.
 */
public interface PsiIdentifier extends PsiApToken {
  /**
   * An empty array of PSI identifiers which can be reused to avoid any unnecessary allocations.
   */
  PsiIdentifier[] EMPTY_ARRAY = new PsiIdentifier[0];
}
