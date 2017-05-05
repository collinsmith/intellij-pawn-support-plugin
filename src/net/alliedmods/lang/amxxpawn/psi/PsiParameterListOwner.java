package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base interface for functions.
 */
public interface PsiParameterListOwner extends PsiElement {
  @NotNull PsiParameterList getParameterList();
  @Nullable PsiElement getBody();
}
