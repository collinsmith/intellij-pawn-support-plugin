package net.alliedmods.lang.amxxpawn.oldpsi2.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.util.ArrayFactory;

import net.alliedmods.lang.amxxpawn.oldpsi2.ApElementVisitor;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.ApStubElementTypes;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.PsiIncludeStatementStub;
import net.alliedmods.lang.amxxpawn.oldpsi2.PsiApFile;
import net.alliedmods.lang.amxxpawn.oldpsi2.PsiRelativeIncludeStatement;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PsiRelativeIncludeStatementImpl extends PsiIncludeStatementBaseImpl implements PsiRelativeIncludeStatement {
  public static final PsiRelativeIncludeStatementImpl[] EMPTY_ARRAY = new PsiRelativeIncludeStatementImpl[0];
  public static final ArrayFactory<PsiRelativeIncludeStatementImpl> ARRAY_FACTORY = i -> i == 0 ? EMPTY_ARRAY : new PsiRelativeIncludeStatementImpl[i];

  public PsiRelativeIncludeStatementImpl(PsiIncludeStatementStub stub) {
    super(stub, ApStubElementTypes.RELATIVE_INCLUDE_STATEMENT);
  }

  public PsiRelativeIncludeStatementImpl(ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ApElementVisitor) {
      ((ApElementVisitor) visitor).visitRelativeIncludeStatement(this);
    } else {
      visitor.visitElement(this);
    }
  }

  @Override
  public String toString() {
    return "PsiRelativeIncludeStatement";
  }

  @Nullable
  @Override
  public PsiApFile getReferencedFile() {
    PsiIncludeStatementStub stub = getStub();
    if (stub != null) {
      return stub.getReferencedFile();
    }

    return null;
  }
}
