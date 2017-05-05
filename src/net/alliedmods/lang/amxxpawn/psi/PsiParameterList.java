package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the list of parameters of an AMXX Pawn method.
 *
 * @see PsiMethod#getParameterList()
 */
public interface PsiParameterList extends PsiElement {
  /**
   * Returns the array of parameters in the list.
   */
  @NotNull PsiParameter[] getParameters();

  /**
   * Returns the index of the specified parameter in the list. The parameter must belong to this
   * parameter list.
   */
  int getParameterIndex(@NotNull PsiParameter parameter);

  /**
   * Returns the number of parameters.
   */
  int getParametersCount();
}
