package net.sourcemod.sourcepawn.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;

import net.sourcemod.sourcepawn.SpLanguage;

import org.junit.Test;

public class SpLexerTests {

  private final Lexer LEXER;

  public SpLexerTests() {
    LEXER = SpLanguage.createLexer();
  }

  @Test
  public void testPreprocessor() {
    LEXER.start("#include <file>");

    for (IElementType type = LEXER.getTokenType();
         type != null;
         LEXER.advance(), type = LEXER.getTokenType()) {
      System.out.println(LEXER.getTokenType() + " -> " + LEXER.getTokenText());
    }
  }
}
