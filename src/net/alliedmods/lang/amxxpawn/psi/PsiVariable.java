package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.pom.PomRenameableTarget;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.PsiTarget;
import com.intellij.util.IncorrectOperationException;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an AMXX Pawn local variable, function parameter, or global variable.
 */
public interface PsiVariable
    extends PsiModifierListOwner, PsiNameIdentifierOwner, PsiTarget,
            PomRenameableTarget<PsiElement> {
  /**
   * Returns the tag of the variable.
   */
  @NotNull PsiTag getTag();

  /**
   * Returns the tag element declaring the tag of the variable.
   */
  @Nullable PsiTagElement getTagElement();

  /**
   * Returns the initializer for the variable, or {@code null} if it has no initializer.
   */
  @Nullable PsiExpression getInitizlier();

  /**
   * Ensures that the variable declaration is not combined in the same statement with other declarations.
   *
   * @throws IncorrectOperationException if the modification fails for some reason.
   */
  void normalizeDeclaration() throws IncorrectOperationException;

  /**
   * Calculates and returns the constant value of the variable initializer, or {@code null} if the
   * variable has no initializer, or the initializer does not evaluate to a constant.
   */
  @Nullable Object computeConstantValue();

  /**
   * Returns the identifier declaring the name of the variable.
   */
  @Override @Nullable PsiIdentifier getNameIdentifier();

  /**
   * {@inheritDoc}
   */
  @Override PsiElement setName(@NotNull @NonNls String name) throws IncorrectOperationException;
}
