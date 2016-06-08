package net.alliedmods.intellij.sourcepawn.lexer;

import com.intellij.lexer.Lexer;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LexerTester {

  public static void main(String[] args) throws IOException {
    if (args.length < 1) {
      System.out.println("Expected file path as first argument!");
      System.exit(0);
    }

    for (String path : args) {
      System.out.println("Lexing " + path + "...");
      lexFile(path);
      System.out.println("<EOF>");
    }
  }

  public static void lexFile(String path) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    Lexer tester = SourcePawnLookAheadLexer.createSourcePawnLexer();
    tester.start(new String(encoded));
    for (IElementType type = tester.getTokenType();
         type != null;
         tester.advance(), type = tester.getTokenType()) {
      if (type != TokenType.WHITE_SPACE && type != TokenType.BAD_CHARACTER) {
        //Disabled until support is added for token texts
        //System.out.printf("%s.text = \"%s\"%n", type, tester.getTokenText());
      }
    }
  }

}
