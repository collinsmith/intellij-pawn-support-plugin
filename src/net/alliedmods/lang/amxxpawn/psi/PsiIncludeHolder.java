package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiFile;

import org.jetbrains.annotations.Nullable;

/**
 * Represents a file or code fragment to which include statements can be added.
 */
public interface PsiIncludeHolder extends PsiFile {
  /**
   * Adds an include statement for the specified file.
   */
  boolean include(@Nullable PsiApFile file);
}
