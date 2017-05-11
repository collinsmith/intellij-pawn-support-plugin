package net.alliedmods.lang.amxxpawn.oldpsi2;

import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a single token in an AMXX Pawn file (the lowest-level element in the AMXX Pawn PSI tree.
 */
public interface PsiApToken extends PsiElement {
  /**
   * Returns the type of the token.
   */
  @NotNull
  IElementType getTokenType();
}
