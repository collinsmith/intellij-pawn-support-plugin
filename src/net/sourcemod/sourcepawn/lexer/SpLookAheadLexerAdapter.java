package net.sourcemod.sourcepawn.lexer;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;
import com.intellij.lexer.Lexer;
import com.intellij.lexer.LookAheadLexer;
import com.intellij.psi.tree.IElementType;

import java.math.BigInteger;

import static net.sourcemod.sourcepawn.lexer.SpTokenTypes.BLOCK_COMMENT;
import static net.sourcemod.sourcepawn.lexer.SpTokenTypes.CHARACTER_LITERAL;
import static net.sourcemod.sourcepawn.lexer.SpTokenTypes.DOC_COMMENT;
import static net.sourcemod.sourcepawn.lexer.SpTokenTypes.LINE_COMMENT;
import static net.sourcemod.sourcepawn.lexer.SpTokenTypes.NEW_LINE;
import static net.sourcemod.sourcepawn.lexer.SpTokenTypes.NUMBER_LITERAL;
import static net.sourcemod.sourcepawn.lexer.SpTokenTypes.PRAGMA_CTRLCHAR;
import static net.sourcemod.sourcepawn.lexer.SpTokenTypes.WHITE_SPACE;

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

  @Override
  protected void lookAhead(Lexer baseLexer) {
    final IElementType type = baseLexer.getTokenType();
    if (type == PRAGMA_CTRLCHAR) {
      addToken(type);
      baseLexer.advance();
      while (baseLexer.getTokenType() == WHITE_SPACE
          || baseLexer.getTokenType() == LINE_COMMENT
          || baseLexer.getTokenType() == BLOCK_COMMENT
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
        spLexer.setEscapeCharacter(codePoint.intValue());
      } else if (token == NUMBER_LITERAL) {
        BigInteger codePoint = spLexer.value();
        spLexer.setEscapeCharacter(codePoint.intValue());
      } else if (token == NEW_LINE) {
        spLexer.resetEscapeCharacter();
      } else if (token == null) {
        spLexer.resetEscapeCharacter();
      }
    } else {
      super.lookAhead(baseLexer);
    }
  }

}
