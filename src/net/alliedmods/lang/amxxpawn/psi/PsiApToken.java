package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;

/**
 * Represents a single token in an AMXX Pawn file (the lowest-level element in the AMXX Pawn PSI tree.
 */
public interface PsiApToken extends PsiElement {
  /**
   * Returns the type of the token.
   */
  IElementType getTokenType();
}
