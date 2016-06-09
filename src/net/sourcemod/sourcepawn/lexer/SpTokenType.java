package net.sourcemod.sourcepawn.lexer;

import com.intellij.psi.tree.IElementType;

import net.sourcemod.sourcepawn.SpLanguage;

public class SpTokenType extends IElementType {

  public SpTokenType(String debugName) {
    super(debugName, SpLanguage.INSTANCE);
  }

}
