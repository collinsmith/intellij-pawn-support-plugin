package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.ICompositeElementType;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ReflectionUtil;

import net.alliedmods.lang.amxxpawn.ApLanguage;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.ApPreprocessorElementTypes;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

public interface ApElementTypes extends ApPreprocessorElementTypes {
  class IApElementType extends IElementType {
    private final boolean leftBound;

    public IApElementType(@NonNls String debugName) {
      this(debugName, false);
    }

    public IApElementType(@NonNls String debugName, boolean leftBound) {
      super(debugName, ApLanguage.INSTANCE);
      this.leftBound = leftBound;
    }

    @Override
    public boolean isLeftBound() {
      return leftBound;
    }
  }

  class ApCompositeElementType extends IApElementType implements ICompositeElementType {
    private final Constructor<? extends ASTNode> constructor;

    public ApCompositeElementType(@NonNls String debugName, Class<? extends ASTNode> nodeClass) {
      this(debugName, nodeClass, false);
    }

    public ApCompositeElementType(@NonNls String debugName, Class<? extends ASTNode> nodeClass, boolean leftBound) {
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
