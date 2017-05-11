package net.alliedmods.lang.amxxpawn.oldpsi2.stubs.impl;

import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;

import net.alliedmods.lang.amxxpawn.oldpsi2.PsiIncludeStatementBase;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.ApStubElementTypes;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.PsiIncludeStatementStub;
import net.alliedmods.lang.amxxpawn.oldpsi2.PsiApFile;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class PsiIncludeStatementStubImpl extends StubBase<PsiIncludeStatementBase>
    implements PsiIncludeStatementStub {
  private static final byte PFLAG_RELATIVE = 0x1;

  public static byte packFlags(boolean isRelative) {
    return (isRelative ? PFLAG_RELATIVE : 0);
  }

  private final byte flags;
  private final StringRef text;

  public PsiIncludeStatementStubImpl(StubElement parent, String text, byte flags) {
    this(parent, StringRef.fromString(text), flags);
  }

  public PsiIncludeStatementStubImpl(StubElement parent, StringRef text, byte flags) {
    super(parent, isRelative(flags)
        ? ApStubElementTypes.RELATIVE_INCLUDE_STATEMENT
        : ApStubElementTypes.INCLUDE_STATEMENT);
    this.text = text;
    this.flags = flags;
  }

  private static boolean isRelative(byte flags) {
    return (flags & PFLAG_RELATIVE) == PFLAG_RELATIVE;
  }

  @Override
  public boolean isRelative() {
    return isRelative(flags);
  }

  public byte getFlags() {
    return flags;
  }

  @Nullable
  @Override
  public PsiApFile getReferencedFile() {
    return null;
  }

  @NotNull
  @Override
  public String getIncludeReferenceText() {
    return StringRef.toString(text);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("PsiImportStatementStub[");

    if (isRelative()) {
      builder.append("relative ");
    }

    builder.append(getIncludeReferenceText());

    builder.append("]");
    return builder.toString();
  }
}
