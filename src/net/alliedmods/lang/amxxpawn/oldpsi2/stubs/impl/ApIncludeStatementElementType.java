package net.alliedmods.lang.amxxpawn.oldpsi2.stubs.impl;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LighterAST;
import com.intellij.lang.LighterASTNode;
import com.intellij.psi.impl.source.tree.LightTreeUtil;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.util.io.StringRef;

import net.alliedmods.lang.amxxpawn.oldpsi2.ApElementTypes;
import net.alliedmods.lang.amxxpawn.oldpsi2.ElementTypes;
import net.alliedmods.lang.amxxpawn.oldpsi2.PsiIncludeStatementBase;
import net.alliedmods.lang.amxxpawn.oldpsi2.impl.PsiIncludeStatementImpl;
import net.alliedmods.lang.amxxpawn.oldpsi2.impl.PsiRelativeIncludeStatementImpl;
import net.alliedmods.lang.amxxpawn.oldpsi2.impl.tree.RelativeIncludeStatementElement;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.ApStubElementType;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.PsiIncludeStatementStub;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public abstract class ApIncludeStatementElementType extends ApStubElementType<PsiIncludeStatementStub, PsiIncludeStatementBase> {
  public ApIncludeStatementElementType(@NonNls @NotNull String debugName) {
    super(debugName);
  }

  @Override
  public PsiIncludeStatementBase createPsi(@NotNull PsiIncludeStatementStub stub) {
    return getPsiFactory(stub).createIncludeStatement(stub);
  }

  @Override
  public PsiIncludeStatementBase createPsi(@NotNull ASTNode node) {
    if (node instanceof RelativeIncludeStatementElement) {
      return new PsiRelativeIncludeStatementImpl(node);
    } else {
      return new PsiIncludeStatementImpl(node);
    }
  }

  @Override
  public PsiIncludeStatementStub createStub(LighterAST tree, LighterASTNode node, StubElement parent) {
    String refText = LightTreeUtil.toFilteredString(tree, node, ElementTypes.AMXX_COMMENT_OR_WHITESPACE_BIT_SET);
    byte flags = PsiIncludeStatementStubImpl.packFlags(node.getTokenType() == ApElementTypes.RELATIVE_INCLUDE_STATEMENT);
    return new PsiIncludeStatementStubImpl(parent, refText, flags);
  }

  @Override
  public void serialize(@NotNull PsiIncludeStatementStub stub, @NotNull StubOutputStream dataStream) throws IOException {
    dataStream.writeByte(((PsiIncludeStatementStubImpl) stub).getFlags());
    dataStream.writeName(stub.getIncludeReferenceText());
  }

  @NotNull
  @Override
  public PsiIncludeStatementStub deserialize(@NotNull StubInputStream dataStream, StubElement stub) throws IOException {
    byte flags = dataStream.readByte();
    StringRef refText = dataStream.readName();
    return new PsiIncludeStatementStubImpl(stub, refText, flags);
  }

  @Override
  public void indexStub(@NotNull PsiIncludeStatementStub psiIncludeStatementStub,
                        @NotNull IndexSink indexSink) {}
}
