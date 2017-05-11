package net.alliedmods.lang.amxxpawn.oldpsi2.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;

import net.alliedmods.lang.amxxpawn.oldpsi2.ElementTypes;
import net.alliedmods.lang.amxxpawn.oldpsi2.PsiApFile;
import net.alliedmods.lang.amxxpawn.oldpsi2.PsiIncludeStatementBase;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.PsiIncludeStatementStub;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class PsiIncludeStatementBaseImpl extends ApStubPsiElement<PsiIncludeStatementStub> implements PsiIncludeStatementBase {
  public static final PsiIncludeStatementBaseImpl[] EMPTY_ARRAY = new PsiIncludeStatementBaseImpl[0];

  protected PsiIncludeStatementBaseImpl(PsiIncludeStatementStub stub, IStubElementType type) {
    super(stub, type);
  }

  protected PsiIncludeStatementBaseImpl(ASTNode node) {
    super(node);
  }

  @Override
  public PsiReference getReference() {
    PsiApFile file = getReferencedFile();
    if (file == null) {
      return null;
    }

    System.out.println("file=" + file);
    PsiReference ref = new PsiApFileReferenceImpl(findChildByType(ElementTypes.AMXX_INCLUDE));
    System.out.println("file.ref=" + ref);
    return ref;
  }

  @NotNull
  @Override
  public PsiReference[] getReferences() {
    PsiReference ref = getReference();
    if (ref == null) {
      return PsiReference.EMPTY_ARRAY;
    }

    return new PsiReference[] { ref };
  }

  @Nullable
  @Override
  public PsiElement resolve() {
    return getReferencedFile();
  }

}
