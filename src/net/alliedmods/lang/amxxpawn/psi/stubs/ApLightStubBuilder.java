package net.alliedmods.lang.amxxpawn.psi.stubs;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LighterAST;
import com.intellij.lang.LighterASTNode;
import com.intellij.psi.PsiFile;
import com.intellij.psi.stubs.LightStubBuilder;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;

import net.alliedmods.lang.amxxpawn.psi.PsiApFile;
import net.alliedmods.lang.amxxpawn.psi.stubs.impl.PsiApFileStubImpl;

import org.jetbrains.annotations.NotNull;

public class ApLightStubBuilder extends LightStubBuilder {
  @NotNull
  @Override
  protected StubElement createStubForFile(@NotNull PsiFile file, @NotNull LighterAST tree) {
    if (!(file instanceof PsiApFile)) {
      return super.createStubForFile(file, tree);
    }

    String refText = "";
    // TODO: file path to pkg
    return new PsiApFileStubImpl((PsiApFile) file, StringRef.fromString(refText));
  }

  @Override
  public boolean skipChildProcessingWhenBuildingStubs(@NotNull ASTNode parent,
                                                      @NotNull ASTNode node) {
    // TODO: customize this implementation
    return super.skipChildProcessingWhenBuildingStubs(parent, node);
  }

  @Override
  protected boolean skipChildProcessingWhenBuildingStubs(@NotNull LighterAST tree,
                                                         @NotNull LighterASTNode parent,
                                                         @NotNull LighterASTNode node) {
    // TODO: customize this implementation
    return super.skipChildProcessingWhenBuildingStubs(tree, parent, node);
  }
}
