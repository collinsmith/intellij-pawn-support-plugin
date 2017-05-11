package net.alliedmods.lang.amxxpawn.oldpsi2;

import com.intellij.psi.PsiElement;
import com.intellij.util.ArrayFactory;

import javax.annotation.Nullable;

/**
 * Represents an AMXX Pawn preprocessor {@code #include} directive.
 */
public interface PsiIncludeStatementBase extends PsiElement {
  /**
   * An array of PSI base include statements which can be reused to avoid any unnecessary allocations.
   */
  PsiIncludeStatementBase[] EMPTY_ARRAY = new PsiIncludeStatementBase[0];
  ArrayFactory<PsiIncludeStatementBase> ARRAY_FACTORY = i -> i == 0 ? EMPTY_ARRAY : new PsiIncludeStatementBase[i];

  /**
   * Returns the file referenced by the include statement, or {@code null} if it cannot be found.
   */
  @Nullable
  PsiApFile getReferencedFile();

  /**
   * Resolves the reference to the included class.
   */
  @Nullable
  PsiElement resolve();
}
