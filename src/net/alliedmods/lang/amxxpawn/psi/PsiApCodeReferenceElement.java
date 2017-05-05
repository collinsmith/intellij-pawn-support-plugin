package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a reference found in AMXX Pawn code (an identifier).
 */
public interface PsiApCodeReferenceElement extends PsiApReference {
  /**
   * Returns the element representing the name of the referenced element, or {@code null} if the
   * referenced element is not physical (for example exists in compiled code).
   */
  @Nullable
  PsiElement getReferenceNameElement();

  /**
   * Returns the text of the reference, including its qualifier (e.g., file name).
   */
  String getQualifiedName();
}
