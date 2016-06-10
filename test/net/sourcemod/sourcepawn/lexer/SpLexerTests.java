package net.sourcemod.sourcepawn.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;

import net.sourcemod.sourcepawn.SpLanguage;

import org.junit.Ignore;
import org.junit.Test;

public class SpLexerTests {

  private final Lexer LEXER;

  public SpLexerTests() {
    LEXER = SpLanguage.createLexer();
  }

  @Test
  @Ignore
  public void testPreprocessor() {
    LEXER.start("#include <file>");

    for (IElementType type = LEXER.getTokenType();
         type != null;
         LEXER.advance(), type = LEXER.getTokenType()) {
      System.out.println(LEXER.getTokenType() + " -> " + LEXER.getTokenText());
    }
  }

  @Test
  public void testCharacterLiterals() {
    LEXER.start("'\\x61'");
    for (IElementType type = LEXER.getTokenType();
         type != null;
         LEXER.advance(), type = LEXER.getTokenType()) {
      System.out.println(LEXER.getTokenType() + " -> " + LEXER.getTokenText());
    }
  }
}
