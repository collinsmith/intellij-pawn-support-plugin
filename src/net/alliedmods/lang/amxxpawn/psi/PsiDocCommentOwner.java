package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiJavaDocumentedElement;

/**
 * Represents a PSI element which can have an attached documentation comment.
 */
public interface PsiDocCommentOwner extends PsiMember, PsiJavaDocumentedElement {
  /**
   * Indicates whether or not the element is marked as deprecated via {@code #pragma deprecated}
   * or a documentation tag.
   */
  boolean isDeprecated();
}
