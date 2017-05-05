package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.util.IncorrectOperationException;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public interface ApElementFactory {
  /**
   * Creates a field with the specified name and tag.
   *
   * @throws IncorrectOperationException if {@code name} is not a valid AMXX Pawn identifier.
   */
  @NotNull
  PsiField createField(@NotNull @NonNls String name, @NotNull PsiTag tag)
      throws IncorrectOperationException;

  /**
   * Creates an empty method with the specified name and return type.
   *
   * @throws IncorrectOperationException if {@code name} is not a valid AMXX Pawn identifier.
   */
  @NotNull
  PsiMethod createMethod(@NotNull @NonNls String name, PsiTag returnType)
      throws IncorrectOperationException;

  /**
   * @see #createMethod(String, PsiTag)
   */
  @NotNull
  PsiMethod createMethod(@NotNull @NonNls String name, PsiTag returnType, PsiElement context)
      throws IncorrectOperationException;

  /**
   * Creates a parameter with the specified name and tag.
   *
   * @throws IncorrectOperationException if some of the parameter names or types are invalid.
   */
  @NotNull
  PsiParameter createParameter(@NotNull @NonNls String name, PsiTag tag)
      throws IncorrectOperationException;

  /**
   * @see #createParameter(String, PsiTag)
   */
  @NotNull
  PsiParameter createParameter(@NotNull @NonNls String name, PsiTag tag, PsiElement context)
      throws IncorrectOperationException;

  /**
   * Creates doc comment from text.
   */
  @NotNull
  PsiDocComment createDocCommentFromText(@NotNull String text);

  /**
   * Indicates whether or not the specified value is a valid method name for AMXX Pawn.
   */
  boolean isValidMethodName(@NotNull String name);

  /**
   * Indicates whether or not the specified value is a valid parameter name for AMXX Pawn.
   */
  boolean isValidParameterName(@NotNull String name);

  /**
   * Indicates whether or not the specified value is a valid field name for AMXX Pawn.
   */
  boolean isValidFieldName(@NotNull String name);

  /**
   * Indicates whether or not the specified value is a valid local variable name for AMXX Pawn.
   */
  boolean isValidLocalVariableName(@NotNull String name);
}
