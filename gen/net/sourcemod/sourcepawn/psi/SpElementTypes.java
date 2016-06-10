// This is a generated file. Not intended for manual editing.
package net.sourcemod.sourcepawn.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import net.sourcemod.sourcepawn.SpTokenType;
import net.sourcemod.sourcepawn.psi.impl.*;

public interface SpElementTypes {

  IElementType PRAGMA = new SpElementType("PRAGMA");
  IElementType PREPROCESSOR = new SpElementType("PREPROCESSOR");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == PRAGMA) {
        return new SpPragmaImpl(node);
      }
      else if (type == PREPROCESSOR) {
        return new SpPreprocessorImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
