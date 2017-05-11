package net.alliedmods.lang.amxxpawn.psi.preprocessor;

import com.intellij.lang.ASTNode;

import net.alliedmods.lang.amxxpawn.psi.preprocessor.impl.IncludeStatementElement;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.stubs.ApIncludeStatementElementType;

import org.jetbrains.annotations.NotNull;

public interface ApPreprocessorStubElementTypes {
  ApIncludeStatementElementType INCLUDE_STATEMENT = new ApIncludeStatementElementType("INCLUDE_STATEMENT") {
    @NotNull
    @Override
    public ASTNode createCompositeNode() {
      return new IncludeStatementElement();
    }
  };
}
