package net.alliedmods.lang.amxxpawn.lexer;

import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.ApLanguage;

import org.jetbrains.annotations.NonNls;

public class ApTokenType extends IElementType {
  private final boolean leftBound;

  public ApTokenType(@NonNls final String debugName) {
    this(debugName, false);
  }

  public ApTokenType(@NonNls final String debugName, final boolean leftBound) {
    super(debugName, ApLanguage.INSTANCE);
    this.leftBound = leftBound;
  }

  @Override
  public boolean isLeftBound() {
    return leftBound;
  }

}
