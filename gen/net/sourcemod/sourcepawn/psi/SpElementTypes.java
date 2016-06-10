// This is a generated file. Not intended for manual editing.
package net.sourcemod.sourcepawn.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import net.sourcemod.sourcepawn.SpTokenType;
import net.sourcemod.sourcepawn.psi.impl.*;

public interface SpElementTypes {


  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
