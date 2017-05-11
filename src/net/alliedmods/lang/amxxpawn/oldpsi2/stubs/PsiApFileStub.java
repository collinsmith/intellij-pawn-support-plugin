package net.alliedmods.lang.amxxpawn.oldpsi2.stubs;

import com.intellij.psi.stubs.PsiFileStub;

import net.alliedmods.lang.amxxpawn.oldpsi2.PsiApFile;

public interface PsiApFileStub extends PsiFileStub<PsiApFile> {
  StubPsiFactory getPsiFactory();
  void setPsiFactory(StubPsiFactory factory);
}
