package net.alliedmods.lang.amxxpawn.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LighterASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.LightStubBuilder;
import com.intellij.psi.stubs.PsiFileStub;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.psi.tree.ILightStubFileElementType;
import com.intellij.util.diff.FlyweightCapableTreeStructure;
import com.intellij.util.io.StringRef;

import net.alliedmods.lang.amxxpawn.ApLanguage;
import net.alliedmods.lang.amxxpawn.parser.ApParser;
import net.alliedmods.lang.amxxpawn.parser.ApParserUtils;
import net.alliedmods.lang.amxxpawn.psi.stubs.ApLightStubBuilder;
import net.alliedmods.lang.amxxpawn.psi.stubs.PsiApFileStub;
import net.alliedmods.lang.amxxpawn.psi.stubs.impl.PsiApFileStubImpl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class ApFileElementType extends ILightStubFileElementType<PsiApFileStub> {
  public static final int STUB_VERSION = 17;

  public ApFileElementType() {
    super("amxxpawn.FILE", ApLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getExternalId() {
    return "amxxpawn.FILE";
  }

  @Override
  public int getStubVersion() {
    return STUB_VERSION;
  }

  @Override
  public LightStubBuilder getBuilder() {
    return new ApLightStubBuilder();
  }

  @Override
  public boolean shouldBuildStubFor(VirtualFile file) {
    // TODO: custom?
    return super.shouldBuildStubFor(file);
  }

  @Nullable
  @Override
  public ASTNode createNode(CharSequence text) {
    return new ApFileElement(text);
  }

  @Nullable
  @Override
  public ASTNode parseContents(ASTNode chameleon) {
    PsiBuilder builder = ApParserUtils.createBuilder(chameleon);
    doParse(builder);
    return builder.getTreeBuilt().getFirstChildNode();
  }

  @Override
  public FlyweightCapableTreeStructure<LighterASTNode> parseContentsLight(ASTNode chameleon) {
    PsiBuilder builder = ApParserUtils.createBuilder(chameleon);
    doParse(builder);
    return builder.getLightTree();
  }

  private void doParse(@NotNull PsiBuilder builder) {
    PsiBuilder.Marker root = builder.mark();
    ApParser.INSTANCE.getFileParser().parse(builder);
    root.done(this);
  }

  @Override
  public void serialize(@NotNull PsiApFileStub stub, @NotNull StubOutputStream dataStream)
      throws IOException {
    dataStream.writeName(stub.getPackageName());
  }

  @NotNull
  @Override
  public PsiApFileStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub)
      throws IOException {
    StringRef packageName = dataStream.readName();
    return new PsiApFileStubImpl(null, packageName);
  }

  @Override
  public void indexStub(@NotNull PsiFileStub stub, @NotNull IndexSink sink) {}
}
