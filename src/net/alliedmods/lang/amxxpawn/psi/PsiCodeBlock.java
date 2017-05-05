package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an AMXX Pawn code block, usually surrounded by curly braces.
 */
public interface PsiCodeBlock extends PsiElement, PsiModifiableCodeBlock {
  /**
   * Returns the array of statements contained in the block.
   *
   * <p>Note: Comments are not returned, as they are not part of statements.
   */
  @NotNull PsiStatement[] getStatements();

  /**
   * Returns the first PSI element contained in the block, or {@code null} if the block is empty.
   */
  @Nullable PsiElement getFirstBodyElement();

  /**
   * Returns the last PSI element contained in the block, or {@code null} if the block is empty.
   */
  @Nullable PsiElement getLastBodyElement();

  /**
   * Returns the opening curly brace of the block, or {@code null} if the block does not have one.
   */
  @Nullable PsiApToken getLBrace();

  /**
   * Returns the closing curly brace of the block, or {@code null} if the block does not have one.
   */
  @Nullable PsiApToken getRBrace();

}
