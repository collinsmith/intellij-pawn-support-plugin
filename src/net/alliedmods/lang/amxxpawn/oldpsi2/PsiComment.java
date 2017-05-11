package net.alliedmods.lang.amxxpawn.oldpsi2;

import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a comment in AMXX Pawn code.
 */
public interface PsiComment extends PsiElement {
  /**
   * Returns the token type of the comment (for example, {@link ApTokenTypes#END_OF_LINE_COMMENT}
   * or {@link ApTokenTypes#C_STYLE_COMMENT}.
   */
  @NotNull
  IElementType getTokenType();
}
