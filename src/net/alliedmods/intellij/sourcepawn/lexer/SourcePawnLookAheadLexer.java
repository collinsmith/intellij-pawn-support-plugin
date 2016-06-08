package net.alliedmods.intellij.sourcepawn.lexer;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;
import com.intellij.lexer.Lexer;
import com.intellij.lexer.LookAheadLexer;
import com.intellij.psi.tree.IElementType;

import static net.alliedmods.intellij.sourcepawn.lexer.SourcePawnTokenTypes.*;

public class SourcePawnLookAheadLexer extends LookAheadLexer {

  private final SourcePawnLexer sourcePawnLexer;

  public static Lexer createSourcePawnLexer() {
    Lexer adapter = new FlexAdapter(new SourcePawnLexer());
    return new SourcePawnLookAheadLexer(adapter);
  }

  public SourcePawnLookAheadLexer(Lexer baseLexer) {
    super(baseLexer);
    if (!(baseLexer instanceof FlexAdapter)) {
      throw new IllegalArgumentException(
          "This implementation requires that baseLexer be a subclass of FlexAdapter");
    }

    FlexAdapter flexAdapter = (FlexAdapter) baseLexer;
    FlexLexer flexLexer = flexAdapter.getFlex();
    if (!(baseLexer instanceof FlexAdapter)) {
      throw new IllegalArgumentException(
          "This implementation requires that FlexLexer of " +
              "FlexAdapter be a subclass of SourcePawnLexer");
    }

    sourcePawnLexer = (SourcePawnLexer) flexAdapter.getFlex();
  }

  @Override
  protected void lookAhead(Lexer baseLexer) {
    final IElementType type = baseLexer.getTokenType();
    if (type == PRAGMA_CTRLCHAR) {
      addToken(type);
      baseLexer.advance();
      while (baseLexer.getTokenType() == WHITESPACE
          || baseLexer.getTokenType() == LINE_COMMENT
          || baseLexer.getTokenType() == BLOCK_COMMENT
          || baseLexer.getTokenType() == DOC_COMMENT) {
        addToken(WHITESPACE);
        baseLexer.advance();
      }

      final IElementType token = baseLexer.getTokenType();
      addToken(token);
      baseLexer.advance();
      if (token == CHARACTER_LITERAL) {
        sourcePawnLexer.setEscapeCharacter(sourcePawnLexer.value());
      } else if (token == NUMBER_LITERAL) {
        Number number = sourcePawnLexer.value();
        int codePoint = number.intValue();
        sourcePawnLexer.setEscapeCharacter((char)codePoint);
      } else if (token == NEW_LINE) {
        sourcePawnLexer.resetEscapeCharacter();
      } else if (token == null) {
        sourcePawnLexer.resetEscapeCharacter();
      }
    } else {
      super.lookAhead(baseLexer);
    }
  }

}
