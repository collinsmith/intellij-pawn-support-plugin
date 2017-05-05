package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;

/**
 * Represents a PSI element which can be modified without caches reset.
 */
public interface PsiModifiableCodeBlock {
  /**
   * Indicates whether or not specific caches could be saved after the change.
   */
  boolean shouldChangeModificationCount(PsiElement place);
}
