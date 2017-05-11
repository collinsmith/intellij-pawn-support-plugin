package net.alliedmods.lang.amxxpawn.oldpsi2;

import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.ApLanguage;

import org.jetbrains.annotations.NonNls;

public class IApElementType extends IElementType {
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
