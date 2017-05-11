package net.alliedmods.lang.amxxpawn.psi.stubs;

import net.alliedmods.lang.amxxpawn.psi.preprocessor.PsiIncludeStatement;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.stubs.PsiIncludeStatementStub;

public interface StubPsiFactory {
  PsiIncludeStatement createIncludeStatement(PsiIncludeStatementStub stub);
}
