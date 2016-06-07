package net.alliedmods.intellij.sourcepawn.lexer;

import org.jetbrains.annotations.NotNull;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static net.alliedmods.intellij.sourcepawn.lexer.SourcePawnTokenTypes.*;

%%

%class SourcePawnLexer
%implements FlexLexer
%debug

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

%x CHECKING_ESCAPE_SEQUENCE

%%

{identifier}    { return IDENTIFIER; }

"@"   { return AT_SIGN; }
"_"   { return UNDERSCORE; }
{nl}  { return NEW_LINE; }
{w}   { return WHITESPACE; }

"\'"  { yybegin(IN_CHARACTER_LITERAL); string.setLength(0); }
"\""  { yybegin(IN_STRING_LITERAL); string.setLength(0); }

[^]   { return BAD_CHARACTER; }

<IN_CHARACTER_LITERAL> {
  <<EOF>>               { yybegin(YYINITIAL); return BAD_CHARACTER; }
  \'                    { String text = string.toString();
                          System.out.printf("yytext = \"%s\"%n", text);
                          yybegin(YYINITIAL);
                          return CHARACTER_LITERAL; }
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
                                System.out.printf("appending \"%s\"%n", yytext());
                                string.append(yytext());
                                break;
                              default:
                                if (isEscapeCharacter(ctrl)) {
                                  System.out.printf("appending \"%s\"%n", yytext());
                                  string.append(yytext());
                                  break;
                                }

                                System.out.printf("invalid escape sequence \"%s\"%n", yytext());
                                yybegin(YYINITIAL);
                                return BAD_CHARACTER;
                            }
                          } else {
                            string.append(yycharat(0));
                            yypushback(1);
                          }
                        }
  .\'                   { string.append(yytext()); }
}

<IN_STRING_LITERAL> {
  <<EOF>>               { yybegin(YYINITIAL); return BAD_CHARACTER; }
  \"                    { String text = string.toString();
                          System.out.printf("yytext = \"%s\"%n", text);
                          yybegin(YYINITIAL);
                          return STRING_LITERAL; }
  \\{w}*{nl}{w}*        {  }
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
                                System.out.printf("appending \"%s\"%n", yytext());
                                string.append(yytext());
                                break;
                              default:
                                if (isEscapeCharacter(ctrl)) {
                                  System.out.printf("appending \"%s\"%n", yytext());
                                  string.append(yytext());
                                  break;
                                }

                                System.out.printf("invalid escape sequence \"%s\"%n", yytext());
                                yybegin(YYINITIAL);
                                return BAD_CHARACTER;
                            }
                          } else {
                            string.append(yycharat(0));
                            yypushback(1);
                          }
                        }
  .                     { string.append(yytext()); }
}