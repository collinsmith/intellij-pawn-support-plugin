package net.alliedmods.lang.amxxpawn.psi.preprocessor.stubs.impl;

import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.reference.SoftReference;
import com.intellij.util.io.StringRef;

import net.alliedmods.lang.amxxpawn.psi.ApStubElementTypes;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.PsiApFileReference;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.PsiIncludeStatement;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.stubs.PsiIncludeStatementStub;

import org.jetbrains.annotations.Nullable;

public class PsiIncludeStatementStubImpl
    extends StubBase<PsiIncludeStatement>
    implements PsiIncludeStatementStub {

  private final StringRef text;

  private SoftReference<PsiApFileReference> reference = null;

  public PsiIncludeStatementStubImpl(StubElement parent, String text) {
    this(parent, StringRef.fromString(text));
  }

  public PsiIncludeStatementStubImpl(StubElement parent, StringRef text) {
    super(parent, ApStubElementTypes.INCLUDE_STATEMENT);
    this.text = text;
  }

  @Override
  public String getIncludeReferenceText() {
    return StringRef.toString(text);
  }

  @Nullable
  @Override
  public PsiApFileReference getReference() {
    if (reference == null) {
      reference = new SoftReference<>(createReference());
    }

    return reference.get();
  }

  @Nullable
  private PsiApFileReference createReference() {
    String refText = getIncludeReferenceText();
    if (refText == null) {
      return null;
    }

    System.out.println("createReference().refText=" + refText);
    // TODO: PsiApParserFacadeImpl.createReferenceFromText
    return null;
  }

  @Override
  public String toString() {
    return "PsiIncludeStatementStub[" + getIncludeReferenceText() + "]";
  }
}
