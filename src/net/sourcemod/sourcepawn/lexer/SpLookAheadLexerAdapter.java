package net.sourcemod.sourcepawn.lexer;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;
import com.intellij.lexer.LookAheadLexer;

@Deprecated
public class SpLookAheadLexerAdapter extends LookAheadLexer {

  private final _SpLexer spLexer;

  public SpLookAheadLexerAdapter(FlexAdapter flexAdapter) {
    super(flexAdapter);
    FlexLexer flexLexer = flexAdapter.getFlex();
    if (!(flexLexer instanceof _SpLexer)) {
      throw new IllegalArgumentException(
          "This implementation requires that FlexLexer of " +
              "FlexAdapter be a subclass of SourcePawnLexer");
    }

    spLexer = (_SpLexer) flexAdapter.getFlex();
  }

  /*@Override
  protected void lookAhead(Lexer baseLexer) {
    final IElementType type = baseLexer.getTokenType();
    if (type == PRAGMA_CTRLCHAR) {
      addToken(type);
      baseLexer.advance();
      while (baseLexer.getTokenType() == WHITE_SPACE
          || baseLexer.getTokenType() == END_OF_LINE_COMMENT
          || baseLexer.getTokenType() == C_STYLE_COMMENT
          || baseLexer.getTokenType() == DOC_COMMENT) {
        addToken(WHITE_SPACE);
        baseLexer.advance();
      }

      final IElementType token = baseLexer.getTokenType();
      addToken(token);
      baseLexer.advance();
      if (token == CHARACTER_LITERAL) {
        String text = spLexer.value();
        BigInteger codePoint = SpUtils.parseCharacter(text, spLexer.getEscapeCharacter());
        if (codePoint != null) {
          spLexer.setEscapeCharacter(codePoint.intValue());
        }
      } else if (token == NUMBER_LITERAL) {
        BigInteger codePoint = spLexer.value();
        if (codePoint != null) {
          spLexer.setEscapeCharacter(codePoint.intValue());
        }
      } else if (token == NEW_LINE) {
        spLexer.resetEscapeCharacter();
      } else if (token == null) {
        spLexer.resetEscapeCharacter();
      }
    } else {
      super.lookAhead(baseLexer);
    }
  }*/

}
