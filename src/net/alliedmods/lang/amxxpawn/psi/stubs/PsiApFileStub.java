package net.alliedmods.lang.amxxpawn.psi.stubs;

import com.intellij.psi.stubs.PsiFileStub;

import net.alliedmods.lang.amxxpawn.psi.PsiApFile;

public interface PsiApFileStub extends PsiFileStub<PsiApFile> {
  String getPackageName();
  StubPsiFactory getPsiFactory();
  void setPsiFactory(StubPsiFactory factory);
}
