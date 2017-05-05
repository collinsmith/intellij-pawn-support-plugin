package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a list of include statements contained in an AMXX Pawn file.
 *
 * @see PsiApFile#getIncludeList()
 */
public interface PsiIncludeList extends PsiElement {
  /**
   * Returns the list of import statements included at the top of an AMXX Pawn file.
   */
  @NotNull PsiIncludeStatement[] getImportStatements();

  /**
   * Indicates whether or not this include list is equivalent to the specified one.
   */
  boolean isReplaceEquivalent(@Nullable PsiIncludeList otherList);
}
