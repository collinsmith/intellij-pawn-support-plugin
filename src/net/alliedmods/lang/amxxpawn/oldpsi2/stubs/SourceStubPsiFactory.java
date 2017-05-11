package net.alliedmods.lang.amxxpawn.oldpsi2.stubs;

import net.alliedmods.lang.amxxpawn.oldpsi2.PsiIncludeStatementBase;
import net.alliedmods.lang.amxxpawn.oldpsi2.impl.PsiIncludeStatementImpl;
import net.alliedmods.lang.amxxpawn.oldpsi2.impl.PsiRelativeIncludeStatementImpl;

public class SourceStubPsiFactory implements StubPsiFactory {
  @Override
  public PsiIncludeStatementBase createIncludeStatement(PsiIncludeStatementStub stub) {
    if (stub.isRelative()) {
      return new PsiRelativeIncludeStatementImpl(stub);
    } else {
      return new PsiIncludeStatementImpl(stub);
    }
  }
}
