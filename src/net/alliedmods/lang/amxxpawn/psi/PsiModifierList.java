package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the list of modifiers and annotations on an AMXX Pawn element (variable, function,
 * etc). Possible modifiers are declared as constants in {@link PsiModifier}.
 */
public interface PsiModifierList extends PsiElement {
  /**
   * Indicates whether or not the modifier list contains the specified modifier, either set by an
   * explicit keyword or implicitly (for example, identifiers starting with {@code @} are implicitly
   * public).
   *
   * @see #hasExplicitModifier(String)
   */
  boolean hasModifierProperty(@PsiModifier.ModifierConstant @NotNull @NonNls String name);

  /**
   * Indicates whether or not the modifier list contains the specified modifier set by an explicit
   * keyword.
   *
   * @see #hasModifierProperty(String)
   */
  boolean hasExplicitModifier(@PsiModifier.ModifierConstant @NotNull @NonNls String name);

  /**
   * Adds or removes the specified modifier from the modifier list depending on whether or not
   * {@code value} is {@code true} (add) or {@code false} (remove).
   *
   * @throws IncorrectOperationException if the modification fails for some reason.
   */
  void setModifierProperty(@PsiModifier.ModifierConstant @NotNull @NonNls String name, boolean add)
      throws IncorrectOperationException;

  /**
   * Indicates whether or not it is possible to add or remove the specified modifier to the modifier
   * list. This method does not change the state of the modifier list.
   *
   * @throws IncorrectOperationException if the operation is not possible.
   */
  void checkSetModifierProperty(@PsiModifier.ModifierConstant @NotNull @NonNls String name, boolean add)
      throws IncorrectOperationException;
}
