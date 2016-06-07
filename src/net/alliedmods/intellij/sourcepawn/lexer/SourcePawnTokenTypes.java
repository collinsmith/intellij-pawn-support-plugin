package net.alliedmods.intellij.sourcepawn.lexer;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.intellij.sourcepawn.psi.SourcePawnTokenType;

import org.jetbrains.annotations.NotNull;

public class SourcePawnTokenTypes {

  private SourcePawnTokenTypes() {
  }

  @NotNull static IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
  @NotNull static IElementType WHITESPACE = TokenType.WHITE_SPACE;
  @NotNull static IElementType NEW_LINE = TokenType.WHITE_SPACE;

  @NotNull static IElementType CHARACTER_LITERAL = new SourcePawnTokenType("CHARACTER_LITERAL");
  @NotNull static IElementType STRING_LITERAL = new SourcePawnTokenType("STRING_LITERAL");

  @NotNull static IElementType IDENTIFIER = new SourcePawnTokenType("IDENTIFIER");

  @NotNull static IElementType AT_SIGN = new SourcePawnTokenType("AT_SIGN");
  @NotNull static IElementType UNDERSCORE = new SourcePawnTokenType("UNDERSCORE");

}
