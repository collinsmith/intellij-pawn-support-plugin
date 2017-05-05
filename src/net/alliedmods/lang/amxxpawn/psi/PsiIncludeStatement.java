package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.Nullable;

/**
 * Represents an AMXX Pawn {@code #include} directive.
 */
public interface PsiIncludeStatement extends PsiElement {
  /**
   * Returns the reference element which specifies the included file.
   */
  @Nullable PsiApCodeReferenceElement getIncludeReference();

  /**
   * Resolves the reference to the included file.
   */
  @Nullable PsiElement resolve();

  /**
   * Returns the fully-qualified name of the included file, or {@code null} if the statement is
   * incomplete.
   */
  @Nullable String getQualifiedName();
}
