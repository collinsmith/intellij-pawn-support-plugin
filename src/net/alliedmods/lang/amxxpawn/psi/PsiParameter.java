package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the parameter of an AMXX Pawn method.
 */
public interface PsiParameter extends PsiVariable {

  /**
   * Returns the element (function, native, forward, etc) in which the parameter is declared.
   */
  @NotNull PsiElement getDeclarationScope();

  /**
   * Indicates whether or not the parameter accepts variable number of arguments.
   */
  boolean isVarArgs();

  /**
   * {@inheritDoc}
   */
  @Override @Nullable PsiTagElement getTagElement();

}
