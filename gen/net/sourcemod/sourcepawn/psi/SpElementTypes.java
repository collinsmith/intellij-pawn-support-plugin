// This is a generated file. Not intended for manual editing.
package net.sourcemod.sourcepawn.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import net.sourcemod.sourcepawn.SpTokenType;
import net.sourcemod.sourcepawn.psi.impl.*;

public interface SpElementTypes {

  IElementType DEFINE_ARGS = new SpElementType("DEFINE_ARGS");
  IElementType DEFINE_SUBSTITUTION = new SpElementType("DEFINE_SUBSTITUTION");
  IElementType PRAGMA = new SpElementType("PRAGMA");
  IElementType PREPROCESSOR = new SpElementType("PREPROCESSOR");
  IElementType PREPROCESSOR_EXPRESSION = new SpElementType("PREPROCESSOR_EXPRESSION");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
       if (type == DEFINE_ARGS) {
        return new SpDefineArgsImpl(node);
      }
      else if (type == DEFINE_SUBSTITUTION) {
        return new SpDefineSubstitutionImpl(node);
      }
      else if (type == PRAGMA) {
        return new SpPragmaImpl(node);
      }
      else if (type == PREPROCESSOR) {
        return new SpPreprocessorImpl(node);
      }
      else if (type == PREPROCESSOR_EXPRESSION) {
        return new SpPreprocessorExpressionImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
