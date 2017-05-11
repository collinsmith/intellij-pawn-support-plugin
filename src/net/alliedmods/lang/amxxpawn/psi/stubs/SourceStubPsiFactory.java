package net.alliedmods.lang.amxxpawn.psi.stubs;

import net.alliedmods.lang.amxxpawn.psi.preprocessor.PsiIncludeStatement;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.impl.PsiIncludeStatementImpl;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.stubs.PsiIncludeStatementStub;

public class SourceStubPsiFactory implements StubPsiFactory {
  @Override
  public PsiIncludeStatement createIncludeStatement(PsiIncludeStatementStub stub) {
    return new PsiIncludeStatementImpl(stub);
  }
}
