package net.alliedmods.lang.amxxpawn.psi.stubs.impl;

import com.intellij.psi.stubs.PsiFileStubImpl;
import com.intellij.psi.tree.IStubFileElementType;
import com.intellij.util.io.StringRef;

import net.alliedmods.lang.amxxpawn.psi.ApStubElementTypes;
import net.alliedmods.lang.amxxpawn.psi.PsiApFile;
import net.alliedmods.lang.amxxpawn.psi.stubs.PsiApFileStub;
import net.alliedmods.lang.amxxpawn.psi.stubs.SourceStubPsiFactory;
import net.alliedmods.lang.amxxpawn.psi.stubs.StubPsiFactory;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PsiApFileStubImpl
    extends PsiFileStubImpl<PsiApFile> implements PsiApFileStub {
  private StringRef packageName;
  private StubPsiFactory factory;

  public PsiApFileStubImpl(@Nullable PsiApFile file, @Nullable StringRef packageName) {
    super(file);
    this.packageName = packageName;
    this.factory = new SourceStubPsiFactory();
  }

  public PsiApFileStubImpl(@Nullable String packageName) {
    this(null, StringRef.fromString(packageName));
  }

  @Override
  public String getPackageName() {
    return StringRef.toString(packageName);
  }

  @Override
  public StubPsiFactory getPsiFactory() {
    return factory;
  }

  @Override
  public void setPsiFactory(StubPsiFactory factory) {
    this.factory = factory;
  }

  @NotNull
  @Override
  public IStubFileElementType getType() {
    return ApStubElementTypes.AP_FILE;
  }

  @Override
  public String toString() {
    return "PsiApFileStub [" + packageName + "]";
  }
}
