package net.alliedmods.lang.amxxpawn.oldpsi2.stubs.impl;

import com.intellij.psi.stubs.PsiFileStubImpl;

import net.alliedmods.lang.amxxpawn.oldpsi2.PsiApFile;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.PsiApFileStub;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.SourceStubPsiFactory;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.StubPsiFactory;

public class PsiApFileStubImpl extends PsiFileStubImpl<PsiApFile> implements PsiApFileStub {
  private StubPsiFactory factory;

  public PsiApFileStubImpl() {
    super(null);
  }

  public PsiApFileStubImpl(PsiApFile file) {
    super(file);
    this.factory = new SourceStubPsiFactory();
  }

  @Override
  public StubPsiFactory getPsiFactory() {
    return factory;
  }

  @Override
  public void setPsiFactory(StubPsiFactory factory) {
    this.factory = factory;
  }
}
