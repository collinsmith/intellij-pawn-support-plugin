package net.alliedmods.lang.amxxpawn.oldpsi2.stubs;

import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.IStubFileElementType;

import net.alliedmods.lang.amxxpawn.oldpsi2.impl.tree.IncludeStatementElement;
import net.alliedmods.lang.amxxpawn.oldpsi2.impl.tree.RelativeIncludeStatementElement;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.impl.ApFileElementType;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.impl.ApIncludeStatementElementType;

import org.jetbrains.annotations.NotNull;

public interface ApStubElementTypes {
  IStubFileElementType AP_FILE = new ApFileElementType();

  ApIncludeStatementElementType INCLUDE_STATEMENT = new ApIncludeStatementElementType("INCLUDE_STATEMENT") {
    @NotNull
    @Override
    public ASTNode createCompositeNode() {
      return new IncludeStatementElement();
    }
  };
  ApIncludeStatementElementType RELATIVE_INCLUDE_STATEMENT = new ApIncludeStatementElementType
      ("RELATIVE_INCLUDE_STATEMENT") {
    @NotNull
    @Override
    public ASTNode createCompositeNode() {
      return new RelativeIncludeStatementElement();
    }
  };
}
