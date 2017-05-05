package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiImportHolder;

import org.jetbrains.annotations.Nullable;

/**
 * Represents an AMXX Pawn file.
 */
public interface PsiApFile extends PsiImportHolder {
  /**
   * Returns a list of the files included within this
   */
  @Nullable PsiIncludeList getIncludeList();
}
