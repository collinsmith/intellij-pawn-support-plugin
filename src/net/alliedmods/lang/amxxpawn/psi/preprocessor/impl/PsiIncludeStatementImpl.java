package net.alliedmods.lang.amxxpawn.psi.preprocessor.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.util.ArrayFactory;

import net.alliedmods.lang.amxxpawn.psi.ApElementVisitor;
import net.alliedmods.lang.amxxpawn.psi.ApStubElementTypes;
import net.alliedmods.lang.amxxpawn.psi.ApStubPsiElement;
import net.alliedmods.lang.amxxpawn.psi.ChildRole;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.PsiApFileReference;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.PsiIncludeStatement;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.stubs.PsiIncludeStatementStub;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PsiIncludeStatementImpl
    extends ApStubPsiElement<PsiIncludeStatementStub>
    implements PsiIncludeStatement {
  public static final PsiIncludeStatementImpl[] EMPTY_ARRAY = new PsiIncludeStatementImpl[0];
  public static final ArrayFactory<PsiIncludeStatementImpl> ARRAY_FACTORY
      = i -> i == 0 ? EMPTY_ARRAY : new PsiIncludeStatementImpl[i];

  public PsiIncludeStatementImpl(PsiIncludeStatementStub stub) {
    super(stub, ApStubElementTypes.INCLUDE_STATEMENT);
  }

  public PsiIncludeStatementImpl(ASTNode node) {
    super(node);
  }

  @Nullable
  @Override
  public PsiApFileReference getIncludeReference() {
    PsiUtilCore.ensureValid(this);
    PsiIncludeStatementStub stub = getStub();
    if (stub != null) {
      return stub.getReference();
    }

    return (PsiApFileReference) calcTreeElement()
        .findChildByRoleAsPsiElement(ChildRole.INCLUDE_REFERENCE);
  }

  @Nullable
  @Override
  public PsiElement resolve() {
    PsiApFileReference ref = getIncludeReference();
    return ref != null ? ref.resolve() : null;
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ApElementVisitor) {
      ((ApElementVisitor) visitor).visitIncludeStatement(this);
    } else {
      super.accept(visitor);
    }
  }

  @Override
  public String toString() {
    return "PsiIncludeStatement";
  }
}
