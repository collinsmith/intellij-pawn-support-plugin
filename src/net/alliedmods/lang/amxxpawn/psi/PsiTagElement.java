package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the occurence of a tag in AMXX Pawn source code, for example, as a return type of a
 * function, or the type of a function parameter.
 */
public interface PsiTagElement extends PsiElement {
  /**
   * Returns the tag referenced by the tag element.
   */
  @NotNull PsiTag getTag();

  /**
   * Returns the reference element pointing to the referenced tag.
   */
  @Nullable PsiApCodeReferenceElement getInnermostComponentReferenceElement();
}
