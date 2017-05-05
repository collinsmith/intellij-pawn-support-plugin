package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.javadoc.PsiDocComment;

import org.jetbrains.annotations.Nullable;

/**
 * A valid target element for a JavaDoc comment (variable, function, etc).
 */
public interface PsiDocumentedElement extends PsiElement {
  /**
   * Returns the JavaDoc comment for the element, or {@code null} when the element has no JavaDoc
   * comment.
   */
  @Nullable PsiDocComment getDocComment();
}
