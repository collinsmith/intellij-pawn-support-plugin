package net.sourcemod.sourcepawn.editor;

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;

import net.sourcemod.sourcepawn.lexer.SpTokenTypes;

public class SpQuoteHandler extends SimpleTokenSetQuoteHandler {

  public SpQuoteHandler() {
    super(SpTokenTypes.STRING_LITERAL, SpTokenTypes.CHARACTER_LITERAL);
  }

}
