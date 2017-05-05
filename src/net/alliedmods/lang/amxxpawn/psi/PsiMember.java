package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiFile;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a member of an AMXX Pawn file (for example, a variable or function).
 */
public interface PsiMember extends PsiModifierListOwner, NavigatablePsiElement {
  /**
   * Returns the class containing the member.
   */
  @Nullable
  PsiFile getContainingFile();
}
