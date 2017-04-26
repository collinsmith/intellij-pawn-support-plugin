package net.alliedmods.lang.amxxpawn.lexer;

import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.ApLanguage;

import org.jetbrains.annotations.NonNls;

public class ApTokenType extends IElementType {

  public ApTokenType(@NonNls String debugName) {
    super(debugName, ApLanguage.INSTANCE);
  }

}
