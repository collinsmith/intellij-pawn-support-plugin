package net.alliedmods.lang.amxxpawn.oldpsi2.stubs.impl;

import com.intellij.psi.stubs.LightStubBuilder;
import com.intellij.psi.tree.ILightStubFileElementType;

import net.alliedmods.lang.amxxpawn.ApLanguage;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.PsiApFileStub;

public class ApFileElementType extends ILightStubFileElementType<PsiApFileStub> {
  public static final int STUB_VERSION = 1;

  public ApFileElementType() {
    super("amxxpawn.FILE", ApLanguage.INSTANCE);
  }

  @Override
  public int getStubVersion() {
    return STUB_VERSION;
  }

  @Override
  public LightStubBuilder getBuilder() {
    return super.getBuilder();
  }


}
