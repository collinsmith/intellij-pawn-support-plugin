package net.alliedmods.lang.amxxpawn.editor;

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;

import net.alliedmods.lang.amxxpawn.psi.ApTokenType;

public class ApQuoteHandler extends SimpleTokenSetQuoteHandler {

  public ApQuoteHandler() {
    super(ApTokenType.STRING_LITERAL, ApTokenType.CHARACTER_LITERAL);
  }

}
