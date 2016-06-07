package net.alliedmods.intellij.sourcepawn.lexer;

import org.jetbrains.annotations.NotNull;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static net.alliedmods.intellij.sourcepawn.lexer.SourcePawnTokenTypes.*;

%%

%class SourcePawnLexer
%implements FlexLexer
//%debug

%unicode

%function advance
%type IElementType

%eof{ return;
%eof}

%init{
  resetState();
%init}

%{
  private static final boolean DEBUG = true;

  private static final char DEFAULT_ESCAPE_CHARACTER = '\\';
  private static final boolean REQUIRE_SEMICOLONS = false;

  //ExtendedSyntaxStrCommentHandler longCommentOrStringHandler
  //    = new ExtendedSyntaxStrCommentHandler();

  private char escapeCharacter;
  private boolean requireSemicolons;

  private StringBuilder string = new StringBuilder(32);
  private char character;

  public void resetState() {
    setEscapeCharacter(DEFAULT_ESCAPE_CHARACTER);
    setSemicolonsRequired(REQUIRE_SEMICOLONS);
  }

  public char getEscapeCharacter() {
    return escapeCharacter;
  }

  public void setEscapeCharacter(char escapeCharacter) {
    if (getEscapeCharacter() != escapeCharacter) {
      this.escapeCharacter = escapeCharacter;
      if (DEBUG) {
        System.out.printf("Escape sequence character changed to '%c'%n", escapeCharacter);
      }
    }
  }

  public boolean isEscapeCharacter(char ch) {
    return ch == getEscapeCharacter();
  }

  public boolean areSemicolonsRequired() {
    return requireSemicolons;
  }

  public void setSemicolonsRequired(boolean requireSemicolons) {
    if (areSemicolonsRequired() != requireSemicolons) {
      this.requireSemicolons = requireSemicolons;
      if (DEBUG) {
        if (requireSemicolons) {
          System.out.println("Semicolons are required");
        } else {
          System.out.println("Semicolons are no longer required");
        }
      }
    }
  }

%}

w                   = [ \t]+
wnl                 = [ \r\n\t]+
nl                  = \r|\n|\r\n
nonl                = [^\r\n]
nobrknl             = [^\[\r\n]

identifier          = ([_@][_@a-zA-Z0-9]+) | ([a-zA-Z][_@a-zA-Z0-9]*)

binary_digit        = [01]
octal_digit         = [0-7]
decimal_digit       = [0-9]
hexadecimal_digit   = [0-9a-fA-F]

binary_prefix       = 0b
octal_prefix        = 0o
decimal_prefix      = {decimal_digit}
hexadecimal_prefix  = 0x

unicode_escape      = ({hexadecimal_digit}){0,2};?
decimal_escape      = {decimal_digit}*;?

binary_literal      = {binary_prefix}       ( _ | {binary_digit} )*
octal_literal       = {octal_prefix}        ( _ | {octal_digit} )*
decimal_literal     = {decimal_prefix}      ( _ | {decimal_digit} )*
hexadecimal_literal = {hexadecimal_prefix}  ( _ | {hexadecimal_digit} )*
number              = {binary_literal} | {octal_literal} | {decimal_literal} | {hexadecimal_literal}

rational_literal    = {decimal_digit} \. {decimal_digit} {exponent}?
exponent            = e -? {decimal_digit}+

control_character   = [abefnrtvx]

%x IN_CHARACTER_LITERAL
%x IN_STRING_LITERAL

%x IN_CHARACTER_LITERAL_ESCAPE_SEQUENCE
%x IN_CHARACTER_LITERAL_DECIMAL_ESCAPE
%x IN_CHARACTER_LITERAL_UNICODE_ESCAPE

%%

{identifier}    { return IDENTIFIER; }

"@"   { return AT_SIGN; }
"_"   { return UNDERSCORE; }
{nl}  { return NEW_LINE; }
{w}   { return WHITESPACE; }

"\'"  { string.setLength(0); yybegin(IN_CHARACTER_LITERAL); }
"\""  { string.setLength(0); yybegin(IN_STRING_LITERAL); }

[^]   { return BAD_CHARACTER; }

<IN_CHARACTER_LITERAL> {
  <<EOF>>               { yybegin(YYINITIAL); return BAD_CHARACTER; }
  \'                    { String text = Character.toString(character);
                          if (DEBUG) {
                            System.out.printf("yytext = \"%s\"%n", text);
                          }

                          yybegin(YYINITIAL);
                          return CHARACTER_LITERAL; }
  .                     { character = yycharat(0);
                          if (isEscapeCharacter(character)) {
                            yybegin(IN_CHARACTER_LITERAL_ESCAPE_SEQUENCE);
                          }
                        }
  [^]                   { yybegin(YYINITIAL); return BAD_CHARACTER; }
}

<IN_CHARACTER_LITERAL_ESCAPE_SEQUENCE> {
  {control_character}   { character = yycharat(0);
                          if (character == 'x') {
                            yybegin(IN_CHARACTER_LITERAL_UNICODE_ESCAPE);
                          } else {
                            switch(yycharat(0)) {
                              case 'a':
                                character = '\u0007';
                                break;
                              case 'b':
                                character = '\b';
                                break;
                              case 'e':
                                character = '\u001B';
                                break;
                              case 'f':
                                character = '\f';
                                break;
                              case 'n':
                                character = '\n';
                                break;
                              case 'r':
                                character = '\r';
                                break;
                              case 't':
                                character = '\t';
                                break;
                              case 'v':
                                character = '\u000B';
                                break;
                              default:
                                throw new AssertionError(
                                    "Unsupported control character: " + yycharat(0));
                            }

                            yybegin(IN_CHARACTER_LITERAL);
                          }
                        }
  [\"'%]                { character = yycharat(0); yybegin(IN_CHARACTER_LITERAL); }
  {decimal_digit}       { yypushback(yylength()); yybegin(IN_CHARACTER_LITERAL_DECIMAL_ESCAPE); }
  [^]                   { yybegin(YYINITIAL); return BAD_CHARACTER; }
}

<IN_CHARACTER_LITERAL_DECIMAL_ESCAPE> {
  {decimal_escape}      { int character = 0;
                          for (int i = 0; i < yylength(); i++) {
                            char ch = yycharat(i);
                            switch (ch) {
                              case '0':case '1':case '2':case '3':case '4':
                              case '5':case '6':case '7':case '8':case '9':
                                character = (character * 10) + (ch - '0');
                                break;
                              case ';':
                                if (i != (yylength()-1)) {
                                  throw new AssertionError(
                                      "semicolon should be the final character in the sequence");
                                }

                                break;
                              default:
                                throw new AssertionError("Unsupported control character: " + ch);
                            }
                          }

                          this.character = (char)character;
                          yybegin(IN_CHARACTER_LITERAL);
                        }
  [^]                   { character = 0; yypushback(yylength()); yybegin(IN_CHARACTER_LITERAL); }
}

<IN_CHARACTER_LITERAL_UNICODE_ESCAPE> {
  {unicode_escape}      { int character = 0;
                          for (int i = 0; i < yylength(); i++) {
                            char ch = yycharat(i);
                            switch (ch) {
                              case '0':case '1':case '2':case '3':case '4':
                              case '5':case '6':case '7':case '8':case '9':
                                character = (character << 4) + (ch - '0');
                                break;
                              case 'a':case 'b':case 'c':case 'd':case 'e':case 'f':
                                character = (character << 4) + (ch - 'a');
                                break;
                              case 'A':case 'B':case 'C':case 'D':case 'E':case 'F':
                                character = (character << 4) + (ch - 'A');
                                break;
                              case ';':
                                if (i != (yylength()-1)) {
                                  throw new AssertionError(
                                      "semicolon should be the final character in the sequence");
                                }

                                break;
                              default:
                                throw new AssertionError("Unsupported control character: " + ch);
                            }
                          }

                          this.character = (char)character;
                          yybegin(IN_CHARACTER_LITERAL);
                        }
  [^]                   { character = 0; yypushback(yylength()); yybegin(IN_CHARACTER_LITERAL); }
}

// IN_STRING_LITERAL is a lot more lax than IN_CHARACTER_LITERAL, because so long as the string
// is delimited correctly, we don't care about the contents, whereas each character literal should
// only represent a single character
<IN_STRING_LITERAL> {
  <<EOF>>               { yybegin(YYINITIAL); return BAD_CHARACTER; }
  \"                    { String text = string.toString();
                          if (DEBUG) {
                            System.out.printf("yytext = \"%s\"%n", text);
                          }

                          yybegin(YYINITIAL);
                          return STRING_LITERAL; }
  \\{w}*{nl}{w}*        { /* continue */ }
  ..                    { if (isEscapeCharacter(yycharat(0))) {
                            char ctrl = yycharat(1);
                            switch (ctrl) {
                              case 'a':case 'b':case 'e':case 'f':
                              case 'n':case 'r':case 't':case 'v':
                              case 'x':
                              case '%':
                              case '"':
                              case '\'':
                              case '0':case '1':case '2':case '3':case '4':
                              case '5':case '6':case '7':case '8':case '9':
                                string.append(yytext());
                                break;
                              default:
                                if (isEscapeCharacter(ctrl)) {
                                  string.append(yytext());
                                  break;
                                }

                                yybegin(YYINITIAL);
                                return BAD_CHARACTER;
                            }
                          } else {
                            string.append(yycharat(0));
                            yypushback(1);
                          }
                        }
  .                     { string.append(yytext()); }
  [^]                   { yybegin(YYINITIAL); return BAD_CHARACTER; }
}