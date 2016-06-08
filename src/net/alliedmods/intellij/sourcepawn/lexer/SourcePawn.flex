package net.alliedmods.intellij.sourcepawn.lexer;

import org.jetbrains.annotations.NotNull;

import java.io.Reader;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static net.alliedmods.intellij.sourcepawn.lexer.SourcePawnTokenTypes.*;
import net.alliedmods.intellij.sourcepawn.SourcePawnUtils;

%%

%class SourcePawnLexer
%implements FlexLexer
//%debug

%unicode

%function advance
%type IElementType

%eof{
  return;
%eof}

%init{
  switch (PAWN_CELL_SIZE) {
    case 16:
    case 32:
    case 64:
      break;
    default:
      throw new AssertionError("Unsupported cell size (" + PAWN_CELL_SIZE + ")");
  }

  resetState();
%init}

%{
  private static final boolean DEBUG = true;

  public static final int PAWN_CELL_SIZE = 32;

  private static final char DEFAULT_ESCAPE_CHARACTER = '\\';
  private static final boolean DEFAULT_REQUIRE_SEMICOLONS = false;
  private static final boolean DEFAULT_REQUIRE_NEWDECLS = false;

  //ExtendedSyntaxStrCommentHandler longCommentOrStringHandler
  //    = new ExtendedSyntaxStrCommentHandler();

  private char escapeCharacter;
  private boolean requireSemicolons;
  private boolean requireNewDecls;

  private StringBuilder string = new StringBuilder(32);
  private char character;
  private Object value;

  public SourcePawnLexer() {
    this((Reader)null);
  }

  public <E> E value() {
    return (E)value;
  }

  public void resetState() {
    resetEscapeCharacter();
    resetSemicolonsRequired();
    resetNewDeclsRequired();
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

  public void resetEscapeCharacter() {
    setEscapeCharacter(DEFAULT_ESCAPE_CHARACTER);
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

  public void resetSemicolonsRequired() {
    setSemicolonsRequired(DEFAULT_REQUIRE_SEMICOLONS);
  }

  public boolean areNewDeclsRequired() {
    return requireNewDecls;
  }

  public void setNewDeclsRequired(boolean requireNewDecls) {
    if (areNewDeclsRequired() != requireNewDecls) {
      this.requireNewDecls = requireNewDecls;
      if (DEBUG) {
        if (requireNewDecls) {
          System.out.println("NewDecls are required");
        } else {
          System.out.println("NewDecls are no longer required");
        }
      }
    }
  }

  public void resetNewDeclsRequired() {
    setNewDeclsRequired(DEFAULT_REQUIRE_NEWDECLS);
  }

%}

w                   = [ \t]+
now                 = [^ \t]+
wnl                 = [ \r\n\t]+
nl                  = \r|\n|\r\n
nonl                = [^\r\n]
nobrknl             = [^\[\r\n]
brknl               = \\{w}?{nl}{w}?
whitespace          = ({w}|{brknl})+

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

boolean_literal     = true | false
binary_literal      = {binary_prefix}       ( _ | {binary_digit} )*
octal_literal       = {octal_prefix}        ( _ | {octal_digit} )*
decimal_literal     = {decimal_prefix}      ( _ | {decimal_digit} )*
hexadecimal_literal = {hexadecimal_prefix}  ( _ | {hexadecimal_digit} )*
number              = {boolean_literal} | {binary_literal} | {octal_literal} | {decimal_literal} | {hexadecimal_literal}

rational_literal    = {decimal_digit} \. {decimal_digit} {exponent}?
exponent            = e -? {decimal_digit}+

control_character   = [abefnrtvx]

%x IN_PREPROCESSOR
%x IN_PREPROCESSOR_PRAGMA_PRE
%x IN_PREPROCESSOR_PRAGMA
%x IN_PRAGMA_DEPRECATED_STRING_PRE
%x IN_PRAGMA_DEPRECATED_STRING
%x IN_PREPROCESSOR_PRAGMA_NEWDECLS_PRE
%x IN_PREPROCESSOR_PRAGMA_NEWDECLS

%x IN_CHARACTER_LITERAL
%x IN_STRING_LITERAL

%x IN_CHARACTER_LITERAL_ESCAPE_SEQUENCE
%x IN_CHARACTER_LITERAL_DECIMAL_ESCAPE
%x IN_CHARACTER_LITERAL_UNICODE_ESCAPE

%x IN_LINE_COMMENT
%x IN_BLOCK_COMMENT
%x IN_DOC_COMMENT_PRE
%x IN_DOC_COMMENT
%x IN_DOC_COMMENT_POST

%%

"&"    { return AMPERSAND; }
"="    { return ASSIGN; }
"*"    { return ASTERISK; }
"@"    { return AT_SIGN; }
"^"    { return CARET; }
","    { return COMMA; }
"!"    { return EXCLAMATION; }
"#"    { yybegin(IN_PREPROCESSOR); return HASH; }
"-"    { return MINUS; }
"%"    { return PERCENT; }
"."    { return PERIOD; }
"+"    { return PLUS; }
";"    { return SEMICOLON; }
"/"    { return SLASH; }
"~"    { return TILDE; }
"_"    { return UNDERSCORE; }
"|"    { return VERTICAL_BAR; }

"+="   { return ADDEQ; }
"&&"   { return AND; }
"&="   { return ANDEQ; }
"--"   { return DECREMENT; }
"/="   { return DIVEQ; }
"..."  { return ELLIPSIS; }
"=="   { return EQUALTO; }
">="   { return GTEQ; }
"++"   { return INCREMENT; }
"<="   { return LTEQ; }
"%="   { return MODEQ; }
"*="   { return MULEQ; }
"!="   { return NEQUALTO; }
"||"   { return OR; }
"|="   { return OREQ; }
".."   { return RANGE; }
"::"   { return SCOPE_RESOLUTION; }
"<<"   { return SL; }
"<<="  { return SLEQ; }
">>"   { return SRA; }
">>="  { return SRAEQ; }
">>>"  { return SRL; }
">>>=" { return SRLEQ; }
"-="   { return SUBEQ; }
"^="   { return XOREQ; }

"{"   { return LBRACE; }
"}"   { return RBRACE; }
"["   { return LBRACKET; }
"]"   { return RBRACKET; }
"("   { return LPAREN; }
")"   { return RPAREN; }
"<"   { return LT; }
">"   { return GT; }

"acquire"           { return ACQUIRE; }
"as"                { return AS; }
"assert"            { return ASSERT; }
//"*begin"          { return BEGIN; }
"break"             { return BREAK; }
"builtin"           { return BUILTIN; }
"case"              { return CASE; }
"cast_to"           { return CAST_TO; }
"catch"             { return CATCH; }
"cellsof"           { return CELLSOF; }
"char"              { return CHAR; }
"const"             { return CONST; }
"continue"          { return CONTINUE; }
"decl"              { return DECL; }
"default"           { return DEFAULT; }
"defined"           { return DEFINED; }
"delete"            { return DELETE; }
"do"                { return DO; }
"double"            { return DOUBLE; }
"else"              { return ELSE; }
//"*end"            { return END; }
"enum"              { return ENUM; }
"exit"              { return EXIT; }
"explicit"          { return EXPLICIT; }
"finally"           { return FINALLY; }
"for"               { return FOR; }
"foreach"           { return FOREACH; }
"forward"           { return FORWARD; }
"funcenum"          { return FUNCENUM; }
"functag"           { return FUNCTAG; }
"function"          { return FUNCTION; }
"goto"              { return GOTO; }
"if"                { return IF; }
"implicit"          { return IMPLICIT; }
"import"            { return IMPORT; }
"in"                { return IN; }
"int"               { return INT; }
"int8"              { return INT8; }
"int16"             { return INT16; }
"int32"             { return INT32; }
"int64"             { return INT64; }
"interface"         { return INTERFACE; }
"intn"              { return INTN; }
"let"               { return LET; }
"methodmap"         { return METHODMAP; }
"namespace"         { return NAMESPACE; }
"native"            { return NATIVE; }
"new"               { return NEW; }
"null"              { return NULL; }
"__nullable__"      { return NULLABLE; }
"object"            { return OBJECT; }
"operator"          { return OPERATOR; }
"package"           { return PACKAGE; }
"private"           { return PRIVATE; }
"protected"         { return PROTECTED; }
"public"            { return PUBLIC; }
"readonly"          { return READONLY; }
"return"            { return RETURN; }
"sealed"            { return SEALED; }
"sizeof"            { return SIZEOF; }
"sleep"             { return SLEEP; }
"static"            { return STATIC; }
"stock"             { return STOCK; }
"struct"            { return STRUCT; }
"switch"            { return SWITCH; }
"tagof"             { return TAGOF; }
//"*then"           { return THEN; }
"this"              { return THIS; }
"throw"             { return THROW; }
"try"               { return TRY; }
"typedef"           { return TYPEDEF; }
"typeof"            { return TYPEOF; }
"typeset"           { return TYPESET; }
"uint8"             { return UINT8; }
"uint16"            { return UINT16; }
"uint32"            { return UINT32; }
"uint64"            { return UINT64; }
"uintn"             { return UINTN; }
"union"             { return UNION; }
"using"             { return USING; }
"var"               { return VAR; }
"variant"           { return VARIANT; }
"view_as"           { return VIEW_AS; }
"virtual"           { return VIRTUAL; }
"void"              { return VOID; }
"volatile"          { return VOLATILE; }
"while"             { return WHILE; }
"with"              { return WITH; }

\'                  { string.setLength(0); yybegin(IN_CHARACTER_LITERAL); }
\"                  { string.setLength(0); yybegin(IN_STRING_LITERAL); }

"//" {w}?           { string.setLength(0); yybegin(IN_LINE_COMMENT); }
"/**" {w}?          { string.setLength(0); yybegin(IN_DOC_COMMENT_PRE); }
"/*" {w}?           { string.setLength(0); yybegin(IN_BLOCK_COMMENT); }

{whitespace}        { return WHITESPACE; }
{nl}                { return NEW_LINE; }

{identifier}        { return IDENTIFIER; }

{number}            { try {
                        switch (PAWN_CELL_SIZE) {
                          case 16: value = (short)SourcePawnUtils.parseNumber(yytext()); break;
                          case 32: value = (int)SourcePawnUtils.parseNumber(yytext()); break;
                          case 64: value = (long)SourcePawnUtils.parseNumber(yytext()); break;
                          default: throw new AssertionError(
                                "Unsupported cell size (" + PAWN_CELL_SIZE + ")");
                        }

                        if (DEBUG) {
                          System.out.printf("number %s = %d%n", yytext(), value);
                        }
                      } catch (NumberFormatException e) {
                        // This should not happen if number was tokenized correctly
                        throw new AssertionError(e.getMessage());
                      }

                      return NUMBER_LITERAL;
                    }
{rational_literal}  { try {
                        switch (PAWN_CELL_SIZE) {
                          case 32: value = (float)SourcePawnUtils.parseRational(yytext()); break;
                          case 64: value = (double)SourcePawnUtils.parseRational(yytext()); break;
                          default: throw new AssertionError(
                                "Unsupported cell size (" + PAWN_CELL_SIZE + ")");
                        }

                        if (DEBUG) {
                          System.out.printf("rational %s = %f%n", yytext(), value);
                        }
                      } catch (NumberFormatException e) {
                        // This should not happen if number was tokenized correctly
                        throw new AssertionError(e.getMessage());
                      }
                    }

[^]                 { return BAD_CHARACTER; }

<IN_LINE_COMMENT> {
  {w} .       { string.append(yytext()); }
  {w}? {nl}   { String text = string.toString().trim();
                value = text;
                if (DEBUG) {
                  System.out.printf("line comment = '%s'%n", text);
                }

                yybegin(YYINITIAL);
                yypushback(yylength());
                return LINE_COMMENT;
              }
  <<EOF>>     { String text = string.toString().trim();
                value = text;
                if (DEBUG) {
                  System.out.printf("line comment = '%s'%n", text);
                }

                yybegin(YYINITIAL);
                return LINE_COMMENT;
              }
  [^]         { string.append(yytext()); }
}

<IN_BLOCK_COMMENT> {
  <<EOF>>                   { return BAD_CHARACTER; }
  {w} .                     { string.append(yytext()); }
  {w}? {nl}+ {w}?           { string.append(' '); }
  {w}? "*/"                 { String text = string.toString().trim();
                              value = text;
                              if (DEBUG) {
                                System.out.printf("block comment = '%s'%n", text);
                              }

                              yybegin(YYINITIAL);
                              return BLOCK_COMMENT;
                            }
  [^]                       { string.append(yytext()); }
}

<IN_DOC_COMMENT_PRE> {
  <<EOF>>                   { return BAD_CHARACTER; }
  "*"+                      { /* ignore leading asterisks */ }
  "*"* {nl} ({w} "*" {w})?  { yybegin(IN_DOC_COMMENT); }
  "*"* "*/"                 { yybegin(IN_DOC_COMMENT_POST); }
  [^]                       { string.append(yytext()); }
}

<IN_DOC_COMMENT> {
  <<EOF>>                   { return BAD_CHARACTER; }
  {w}? {nl} ({w} "*" {w})?  { string.append(' '); }
  "*"* "*/"                 { yypushback(yylength()); yybegin(IN_DOC_COMMENT_POST); }
  [^]                       { string.append(yytext()); }
}

<IN_DOC_COMMENT_POST> {
  [^]                       |
  <<EOF>>                   { String text = string.toString().trim();
                              value = text;
                              if (DEBUG) {
                                System.out.printf("doc comment = '%s'%n", text);
                              }

                              yybegin(YYINITIAL);
                              yypushback(yylength());
                              return DOC_COMMENT;
                            }
}

<IN_PREPROCESSOR> {
  "assert"              { yybegin(YYINITIAL); return PREPROCESSOR_ASSERT; }
  "define"              { yybegin(YYINITIAL); return PREPROCESSOR_DEFINE; }
  "else"                { yybegin(YYINITIAL); return PREPROCESSOR_ELSE; }
  "elseif"              { yybegin(YYINITIAL); return PREPROCESSOR_ELSEIF; }
  "endif"               { yybegin(YYINITIAL); return PREPROCESSOR_ENDIF; }
  "endinput"            { yybegin(YYINITIAL); return PREPROCESSOR_ENDINPUT; }
  "endscript"           { yybegin(YYINITIAL); return PREPROCESSOR_ENDSCRIPT; }
  "error"               { yybegin(YYINITIAL); return PREPROCESSOR_ERROR; }
  "file"                { yybegin(YYINITIAL); return PREPROCESSOR_FILE; }
  "if"                  { yybegin(YYINITIAL); return PREPROCESSOR_IF; }
  "include"             { yybegin(YYINITIAL); return PREPROCESSOR_INCLUDE; }
  "line"                { yybegin(YYINITIAL); return PREPROCESSOR_LINE; }
  "pragma"              { yybegin(IN_PREPROCESSOR_PRAGMA_PRE); return PREPROCESSOR_PRAGMA; }
  "tryinclude"          { yybegin(YYINITIAL); return PREPROCESSOR_TRYINCLUDE; }
  "undef"               { yybegin(YYINITIAL); return PREPROCESSOR_UNDEF; }
  [^]                   { yypushback(yylength()); yybegin(YYINITIAL); }
}

<IN_PREPROCESSOR_PRAGMA_PRE> {
  {whitespace}          { yybegin(IN_PREPROCESSOR_PRAGMA); return WHITESPACE; }
  [^]                   { yypushback(yylength()); yybegin(YYINITIAL); }
}

<IN_PREPROCESSOR_PRAGMA> {
  "codepage"            { yybegin(YYINITIAL); return PRAGMA_CODEPAGE; }
  "ctrlchar"            { yybegin(YYINITIAL); return PRAGMA_CTRLCHAR; }
  "deprecated"          { yybegin(IN_PRAGMA_DEPRECATED_STRING_PRE); return PRAGMA_DEPRECATED; }
  "dynamic"             { yybegin(YYINITIAL); return PRAGMA_DYNAMIC; }
  "rational"            { yybegin(YYINITIAL); return PRAGMA_RATIONAL; }
  "semicolon"           { yybegin(YYINITIAL); return PRAGMA_SEMICOLON; }
  "newdecls"            { yybegin(IN_PREPROCESSOR_PRAGMA_NEWDECLS_PRE); return PRAGMA_NEWDECLS; }
  "tabsize"             { yybegin(YYINITIAL); return PRAGMA_TABSIZE; }
  "unused"              { yybegin(YYINITIAL); return PRAGMA_UNUSED; }
  [^]                   { yypushback(yylength()); yybegin(YYINITIAL); }
}

<IN_PRAGMA_DEPRECATED_STRING_PRE> {
  {whitespace}          { string.setLength(0);
                          yybegin(IN_PRAGMA_DEPRECATED_STRING);
                           /* no return, ignore preceeding whitespace */ }
  [^]                   { yypushback(yylength()); yybegin(YYINITIAL); }
}

<IN_PRAGMA_DEPRECATED_STRING> {
  {w} .                 { string.append(yytext()); }
  . {w} / {brknl}       { string.append(yytext()); }
  {brknl} {w}?          { /* ignore whitespace */ }
  {w}? {nl}             |
  <<EOF>>               { String text = string.toString().trim();
                          value = text;
                          if (DEBUG) {
                            System.out.printf("deprecated message = \"%s\"%n", text);
                          }

                          yybegin(YYINITIAL);
                          yypushback(yylength());
                          if (!text.isEmpty()) {
                            return PRAGMA_DEPRECATED_STRING;
                          }
                        }
  [^]                   { string.append(yytext()); }
}

<IN_PREPROCESSOR_PRAGMA_NEWDECLS_PRE> {
  {whitespace}          { yybegin(IN_PREPROCESSOR_PRAGMA_NEWDECLS); return WHITESPACE; }
  [^]                   { yypushback(yylength()); yybegin(YYINITIAL); }
}

<IN_PREPROCESSOR_PRAGMA_NEWDECLS> {
  {whitespace}          { /* ignore whitespace */ }
  "required"            { yybegin(YYINITIAL); return PRAGMA_NEWDECLS_REQUIRED; }
  "optional"            { yybegin(YYINITIAL); return PRAGMA_NEWDECLS_OPTIONAL; }
  [^]                   |
  <<EOF>>               { yypushback(yylength()); yybegin(YYINITIAL); }
}

<IN_CHARACTER_LITERAL> {
  <<EOF>>               { yybegin(YYINITIAL); return BAD_CHARACTER; }
  \'                    { String text = Character.toString(character);
                          value = character;
                          if (DEBUG) {
                            System.out.printf("character = \'%s\'%n", text);
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
                          value = text;
                          if (DEBUG) {
                            System.out.printf("string = \"%s\"%n", text);
                          }

                          yybegin(YYINITIAL);
                          return STRING_LITERAL; }
  \\{w}?{nl}{w}?        { /* line continuation */ }
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