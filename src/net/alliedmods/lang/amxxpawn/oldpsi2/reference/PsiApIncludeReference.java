package net.alliedmods.lang.amxxpawn.oldpsi2.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileReference;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a reference to a file included with the {@code #include} preprocessor directive.
 */
public interface PsiApIncludeReference extends PsiFileReference {
  /**
   * Returns the element representing the name of the referenced element, or {@code null} if there
   * is none.
   */
  @Nullable
  PsiElement getReferenceNameElement();
}
