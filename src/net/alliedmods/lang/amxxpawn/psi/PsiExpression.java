package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.util.Function;
import com.intellij.util.NullableFunction;

import org.jetbrains.annotations.Nullable;

/**
 * Represents an AMXX Pawn expression.
 */
public interface PsiExpression {
  /**
   * Function used to map a {@code PsiExpression} to a {@code PsiTag}.
   */
  Function<PsiExpression, PsiTag> EXPRESSION_TO_TAG = (NullableFunction<PsiExpression, PsiTag>) PsiExpression::getTag;

  /**
   * Returns the tag of the expression, or {@code null} if the tag is not known.
   */
  @Nullable
  PsiTag getTag();
}
