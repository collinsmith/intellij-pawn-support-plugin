package net.alliedmods.lang.amxxpawn.oldpsi2;

import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.ICompositeElementType;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ReflectionUtil;

import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.ApStubElementTypes;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

public interface ApElementTypes {
  IElementType DUMMY_ELEMENT = TokenType.DUMMY_HOLDER;
  IElementType INCLUDE_STATEMENT = ApStubElementTypes.INCLUDE_STATEMENT;
  IElementType RELATIVE_INCLUDE_STATEMENT = ApStubElementTypes.RELATIVE_INCLUDE_STATEMENT;

  IElementType AP_FILE_REFERENCE = null;//new ApCompositeElementType("AP_FILE_REFERENCE", PsiApFileReferenceImpl.class);

  class ApCompositeElementType extends IApElementType implements ICompositeElementType {
    private final Constructor<? extends ASTNode> constructor;

    private ApCompositeElementType(@NonNls String debugName, Class<? extends ASTNode> nodeClass) {
      this(debugName, nodeClass, false);
    }

    private ApCompositeElementType(@NonNls String debugName, Class<? extends ASTNode> nodeClass, boolean leftBound) {
      super(debugName, leftBound);
      this.constructor = ReflectionUtil.getDefaultConstructor(nodeClass);
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
      return ReflectionUtil.createInstance(constructor);
    }
  }
}
