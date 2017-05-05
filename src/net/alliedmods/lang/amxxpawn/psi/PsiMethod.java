package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.pom.PomRenameableTarget;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.PsiTarget;
import com.intellij.util.IncorrectOperationException;

import net.alliedmods.lang.amxxpawn.psi.util.MethodSignature;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an AMXX Pawn function or prototype.
 */
// TODO: replace javadoc?
public interface PsiMethod
    extends PsiMember, PsiNameIdentifierOwner, PsiModifierListOwner, PsiDocCommentOwner,
            PsiParameterListOwner, PsiTarget, PomRenameableTarget<PsiElement> {
  /**
   * Returns the return tag of the element, or {@code null} if it does not have one.
   */
  @Nullable PsiTag getReturnTag();

  /**
   * Returns the tag element for the return tag of the function, or {@code null} if it does not have one.
   */
  @Nullable PsiTagElement getReturnTagElement();

  /**
   * Returns the parameter list for the method.
   */
  @Override @NotNull PsiParameterList getParameterList();

  /**
   * Returns the body of the method, or {@code null} if it is a compiled function (native/forward).
   */
  @Override @Nullable PsiCodeBlock getBody();

  /**
   * Indicates whether or not the function accepts a variable number of arguments.
   */
  boolean isVarArgs();

  /**
   * Returns the signature of this function.
   */
  @NotNull MethodSignature getSignature();

  /**
   * Returns the name identifier for the method.
   */
  @Override @Nullable PsiIdentifier getNameIdentifier();

  /**
   * Searches the include of the containing file to find methods which this method implements. Can
   * return multiple results of the base class and/or one or more of the implemented interfaces have
   * a method with the same signature.
   */
  @NotNull PsiMethod[] findPrototypes();

  /**
   * Searches the include of the specified file to find methods which this method implements. Can
   * return multiple results of the base class and/or one or more of the implemented interfaces have
   * a method with the same signature.
   */
  @NotNull PsiMethod[] findPrototypes(PsiApFile parentFile);

  //@NotNull List<MethodSignatureBackedByPsiMethod> findPrototypeSignatures(boolean checkAccess);

  /**
   * {@inheritDoc}
   */
  @Override @NotNull PsiModifierList getModifierList();

  /**
   * {@inheritDoc}
   */
  @Override @NotNull @NonNls String getName();

  /**
   * {@inheritDoc}
   */
  @Override PsiElement setName(@NotNull @NonNls String name) throws IncorrectOperationException;

  //@NotNull HierarchicalMethodSignature getHierarchicalMethodSignature();
}
