package net.alliedmods.lang.amxxpawn.psi.preprocessor.stubs;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LighterAST;
import com.intellij.lang.LighterASTNode;
import com.intellij.psi.impl.source.tree.LightTreeUtil;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.io.StringRef;

import net.alliedmods.lang.amxxpawn.psi.ApElementTypes;
import net.alliedmods.lang.amxxpawn.psi.ApStubElementType;
import net.alliedmods.lang.amxxpawn.psi.ElementTypes;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.PsiApFileReference;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.PsiIncludeStatement;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.impl.PsiIncludeStatementImpl;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.stubs.impl.PsiIncludeStatementStubImpl;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public abstract class ApIncludeStatementElementType
    extends ApStubElementType<PsiIncludeStatementStub, PsiIncludeStatement> {

  public ApIncludeStatementElementType(@NotNull @NonNls String debugName) {
    super(debugName);
  }

  @Override
  public PsiIncludeStatement createPsi(@NotNull PsiIncludeStatementStub stub) {
    return getPsiFactory(stub).createIncludeStatement(stub);
  }

  @Override
  public PsiIncludeStatement createPsi(@NotNull ASTNode node) {
    return new PsiIncludeStatementImpl(node);
  }

  @NotNull
  @Override
  public PsiIncludeStatementStub createStub(@NotNull PsiIncludeStatement psi,
                                            StubElement parentStub) {
    PsiApFileReference ref = psi.getIncludeReference();
    return new PsiIncludeStatementStubImpl(parentStub, ref != null ? ref.getCanonicalText() : null);
  }

  @Override
  public PsiIncludeStatementStub createStub(LighterAST tree,
                                            LighterASTNode node,
                                            StubElement parentStub) {
    String refText = null;
    for (LighterASTNode child : tree.getChildren(node)) {
      IElementType type = child.getTokenType();
      if (type == ApElementTypes.FILE_REFERENCE) {
        refText = LightTreeUtil.toFilteredString(tree, node, ElementTypes.AMXX_COMMENT_OR_WHITESPACE_BIT_SET);
      }
    }

    return new PsiIncludeStatementStubImpl(parentStub, refText);
  }

  @Override
  public void serialize(@NotNull PsiIncludeStatementStub stub, @NotNull StubOutputStream dataStream)
      throws IOException {
    dataStream.writeName(stub.getIncludeReferenceText());
  }

  @NotNull
  @Override
  public PsiIncludeStatementStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub)
      throws IOException {
    StringRef refText = dataStream.readName();
    return new PsiIncludeStatementStubImpl(parentStub, refText);
  }

  @Override
  public void indexStub(@NotNull PsiIncludeStatementStub stub, @NotNull IndexSink sink) {}
}
