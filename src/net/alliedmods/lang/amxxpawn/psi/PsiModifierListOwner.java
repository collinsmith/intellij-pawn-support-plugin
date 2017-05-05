package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a PSI element which has a list of modifiers (public/static/stock) associated with it.
 */
public interface PsiModifierListOwner extends PsiElement {
  /**
   * Returns the list of modifiers for the element, or {@code null} if the element does not have one.
   */
  @Nullable PsiModifierList getModifierList();

  /**
   * Indicates whether or not the modifier list contains the specified modifier, either set by an
   * explicit keyword or implicitly (for example, identifiers starting with {@code @} are implicitly
   * public).
   *
   * @see PsiModifierList#hasExplicitModifier(String)
   */
  boolean hasModifierProperty(@PsiModifier.ModifierConstant @NotNull @NonNls String name);
}
