package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.scope.PsiScopeProcessor;

import net.alliedmods.lang.amxxpawn.psi.util.ApResolveResult;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a reference found in AMXX Pawn code.
 */
public interface PsiApReference extends PsiPolyVariantReference {
  /**
   * Passes all variants to which the reference may resolve to the specifier processor.
   */
  void processVariants(@NotNull PsiScopeProcessor processor);

  /**
   * Resolves the reference and returns the result.
   *
   * @param incompleteCode {@code true} if, the code in the context of which the reference is being
   *                       resolved is considered incomplete, and the method may return an invalid
   *                       result.
   */
  @NotNull
  ApResolveResult advancedResolve(boolean incompleteCode);

  @Override
  @NotNull
  ApResolveResult[] multiResolve(boolean incompleteCode);
}
