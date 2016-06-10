package net.sourcemod.sourcepawn.psi;

import com.intellij.psi.tree.IElementType;

import net.sourcemod.sourcepawn.SpLanguage;

public class SpElementType extends IElementType {

  public SpElementType(String debugName) {
    super(debugName, SpLanguage.INSTANCE);
  }

}
