package net.sourcemod.sourcepawn.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static net.sourcemod.sourcepawn.lexer.SpTokenTypes.*;
import net.alliedmods.intellij.sourcepawn.SourcePawnUtils;

import java.io.Reader;
import java.util.function.IntConsumer;

%%

%public
%class _SpLexer
%implements FlexLexer
%debug

%unicode

%function advance
%type IElementType

%eof{
  return;
%eof}

%init{
  resetState();
%init}

%{
  private static final boolean DEBUG = true;

  private static final int DEFAULT_ESCAPE_CHARACTER = '\\';

  private static String zzToPrintable(CharSequence str) {
    StringBuilder builder = new StringBuilder();
    str.codePoints().iterator().forEachRemaining(new IntConsumer() {
      @Override
      public void accept(int ch) {
        int charCount = Character.charCount(ch);
        if (ch > 31 && ch < 127) {
          builder.append((char) ch);
        } else if (charCount == 1) {
          builder.append(String.format("\\u%04X", ch));
        } else {
          builder.append(String.format("\\U%06X", ch));
        }
      }
    });

    return builder.toString();
  }

  private final StringBuilder string = new StringBuilder(32);

  private int escapeCharacter;
  private Object value;

  public _SpLexer() {
    this((Reader)null);
  }

  public <E> E value() {
    return (E)value;
  }

  public void resetState() {
    resetEscapeCharacter();
  }

  public int getEscapeCharacter() {
    return escapeCharacter;
  }

  public void setEscapeCharacter(int codePoint) {
    if (getEscapeCharacter() != codePoint) {
      this.escapeCharacter = codePoint;
      if (DEBUG) {
        System.out.printf("Escape sequence character changed to '%c'%n", codePoint);
      }
    }
  }

  public void resetEscapeCharacter() {
    setEscapeCharacter(DEFAULT_ESCAPE_CHARACTER);
  }

  public boolean isEscapeCharacter(int codePoint) {
    return codePoint == getEscapeCharacter();
  }
%}

w                   = [ \t]+
now                 = [^ \t]
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

%xstate IN_PREPROCESSOR
%xstate IN_PREPROCESSOR_INCLUDE_PRE
%xstate IN_PREPROCESSOR_INCLUDE
%xstate IN_PREPROCESSOR_INCLUDE_SYSTEMPATH_PRE
%xstate IN_PREPROCESSOR_INCLUDE_SYSTEMPATH
%xstate IN_PREPROCESSOR_INCLUDE_RELATIVEPATH_PRE
%xstate IN_PREPROCESSOR_INCLUDE_RELATIVEPATH

%%

// Single character symbols
"&"                     { return AMPERSAND; }
"="                     { return ASSIGN; }
"*"                     { return ASTERISK; }
"^"                     { return CARET; }
":"                     { return COLON; }
","                     { return COMMA; }
"!"                     { return EXCLAMATION; }
"#"                     { yybegin(IN_PREPROCESSOR); return HASH; }
"-"                     { return MINUS; }
"%"                     { return PERCENT; }
"."                     { return PERIOD; }
"+"                     { return PLUS; }
";"                     { return SEMICOLON; }
"/"                     { return SLASH; }
"~"                     { return TILDE; }
"|"                     { return VERTICAL_BAR; }

// Multiple character symbols
"+="                    { return ADDEQ; }
"&&"                    { return AND; }
"&="                    { return ANDEQ; }
"--"                    { return DECREMENT; }
"/="                    { return DIVEQ; }
"..."                   { return ELLIPSIS; }
"=="                    { return EQUALTO; }
">="                    { return GTEQ; }
"++"                    { return INCREMENT; }
"<="                    { return LTEQ; }
"%="                    { return MODEQ; }
"*="                    { return MULEQ; }
"!="                    { return NEQUALTO; }
"||"                    { return OR; }
"|="                    { return OREQ; }
".."                    { return RANGE; }
"::"                    { return SCOPE_RESOLUTION; }
"<<"                    { return SL; }
"<<="                   { return SLEQ; }
">>"                    { return SRA; }
">>="                   { return SRAEQ; }
">>>"                   { return SRL; }
">>>="                  { return SRLEQ; }
"-="                    { return SUBEQ; }
"^="                    { return XOREQ; }

"{"                     { return LBRACE; }
"}"                     { return RBRACE; }
"["                     { return LBRACKET; }
"]"                     { return RBRACKET; }
"("                     { return LPAREN; }
")"                     { return RPAREN; }
"<"                     { return LT; }
">"                     { return GT; }

// keywords
"acquire"               { return ACQUIRE; }
"as"                    { return AS; }
"assert"                { return ASSERT; }
//"*begin"              { return BEGIN; }
"break"                 { return BREAK; }
"builtin"               { return BUILTIN; }
"case"                  { return CASE; }
"cast_to"               { return CAST_TO; }
"catch"                 { return CATCH; }
"cellsof"               { return CELLSOF; }
"char"                  { return CHAR; }
"const"                 { return CONST; }
"continue"              { return CONTINUE; }
"decl"                  { return DECL; }
"default"               { return DEFAULT; }
"defined"               { return DEFINED; }
"delete"                { return DELETE; }
"do"                    { return DO; }
"double"                { return DOUBLE; }
"else"                  { return ELSE; }
//"*end"                { return END; }
"enum"                  { return ENUM; }
"exit"                  { return EXIT; }
"explicit"              { return EXPLICIT; }
"finally"               { return FINALLY; }
"for"                   { return FOR; }
"foreach"               { return FOREACH; }
"forward"               { return FORWARD; }
"funcenum"              { return FUNCENUM; }
"functag"               { return FUNCTAG; }
"function"              { return FUNCTION; }
"goto"                  { return GOTO; }
"if"                    { return IF; }
"implicit"              { return IMPLICIT; }
"import"                { return IMPORT; }
"in"                    { return IN; }
"int"                   { return INT; }
"int8"                  { return INT8; }
"int16"                 { return INT16; }
"int32"                 { return INT32; }
"int64"                 { return INT64; }
"interface"             { return INTERFACE; }
"intn"                  { return INTN; }
"let"                   { return LET; }
"methodmap"             { return METHODMAP; }
"namespace"             { return NAMESPACE; }
"native"                { return NATIVE; }
"new"                   { return NEW; }
"null"                  { return NULL; }
"__nullable__"          { return NULLABLE; }
"object"                { return OBJECT; }
"operator"              { return OPERATOR; }
"package"               { return PACKAGE; }
"private"               { return PRIVATE; }
"protected"             { return PROTECTED; }
"public"                { return PUBLIC; }
"readonly"              { return READONLY; }
"return"                { return RETURN; }
"sealed"                { return SEALED; }
"sizeof"                { return SIZEOF; }
"sleep"                 { return SLEEP; }
"static"                { return STATIC; }
"stock"                 { return STOCK; }
"struct"                { return STRUCT; }
"switch"                { return SWITCH; }
"tagof"                 { return TAGOF; }
//"*then"               { return THEN; }
"this"                  { return THIS; }
"throw"                 { return THROW; }
"try"                   { return TRY; }
"typedef"               { return TYPEDEF; }
"typeof"                { return TYPEOF; }
"typeset"               { return TYPESET; }
"uint8"                 { return UINT8; }
"uint16"                { return UINT16; }
"uint32"                { return UINT32; }
"uint64"                { return UINT64; }
"uintn"                 { return UINTN; }
"union"                 { return UNION; }
"using"                 { return USING; }
"var"                   { return VAR; }
"variant"               { return VARIANT; }
"view_as"               { return VIEW_AS; }
"virtual"               { return VIRTUAL; }
"void"                  { return VOID; }
"volatile"              { return VOLATILE; }
"while"                 { return WHILE; }
"with"                  { return WITH; }

// White space
{whitespace}            { return WHITE_SPACE; }
{nl}                    { return NEW_LINE; }

// Identifiers
{identifier}            { return IDENTIFIER; }
{identifier} / "::"     { return IDENTIFIER; }

"_"                     { return UNDERSCORE; }
"_" / "::"              { return UNDERSCORE; }

"@"                     { return AT_SIGN; }

// Tags
{identifier} / ":"      { return TAG; }
"_" / ":"               { return TAG; }

{number}                { value = SpUtils.parseNumber(yytext());
                          if (DEBUG) {
                            System.out.printf("number %s = %d%n", yytext(), value);
                          }

                          if (value == null) {
                            throw new AssertionError(
                                value + " should be a valid number, but it couldn't be parsed");
                          }

                          return NUMBER_LITERAL;
                        }
{rational_literal}      { value = SpUtils.parseRational(yytext());
                          if (DEBUG) {
                            System.out.printf("rational %s = %d%n", yytext(), value);
                          }

                          if (value == null) {
                            throw new AssertionError(
                                value + " should be a valid rational, but it couldn't be parsed");
                          }

                          return RATIONAL_LITERAL;
                        }

[^]                     { return BAD_CHARACTER; }

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
  "include"             { yybegin(IN_PREPROCESSOR_INCLUDE_PRE); return PREPROCESSOR_INCLUDE; }
  "line"                { yybegin(YYINITIAL); return PREPROCESSOR_LINE; }
  "pragma"              { yybegin(YYINITIAL); return PREPROCESSOR_PRAGMA; }
  "tryinclude"          { yybegin(YYINITIAL); return PREPROCESSOR_TRYINCLUDE; }
  "undef"               { yybegin(YYINITIAL); return PREPROCESSOR_UNDEF; }
  [^]                   { yybegin(YYINITIAL); yypushback(yylength()); }
}

<IN_PREPROCESSOR_INCLUDE_PRE> {
  {whitespace}          { yybegin(IN_PREPROCESSOR_INCLUDE); return WHITE_SPACE; }
  [^]                   { yypushback(yylength()); yybegin(YYINITIAL); return BAD_CHARACTER; }
}

<IN_PREPROCESSOR_INCLUDE> {
  "<"                   { string.setLength(0);
                          yybegin(IN_PREPROCESSOR_INCLUDE_SYSTEMPATH_PRE); }
  "\""                  { string.setLength(0);
                          yybegin(IN_PREPROCESSOR_INCLUDE_RELATIVEPATH_PRE); }
  [^]                   { yypushback(yylength()); yybegin(YYINITIAL); }
}

<IN_PREPROCESSOR_INCLUDE_SYSTEMPATH_PRE> {
  {whitespace}          { yybegin(IN_PREPROCESSOR_INCLUDE_SYSTEMPATH); }
  [^]                   { yypushback(yylength()); yybegin(IN_PREPROCESSOR_INCLUDE_SYSTEMPATH); }
}

<IN_PREPROCESSOR_INCLUDE_SYSTEMPATH> {
  {whitespace}? ">"     { value = string.toString();
                          if (DEBUG) {
                            System.out.printf("system file = \"%s\"%n", value);
                          }

                          yybegin(YYINITIAL);
                          return PREPROCESSOR_INCLUDE_SYSTEMPATH;
                        }
  {whitespace} .        |
  [^]                   { string.append(yytext()); }
}

<IN_PREPROCESSOR_INCLUDE_RELATIVEPATH_PRE> {
  {whitespace}          { yybegin(IN_PREPROCESSOR_INCLUDE_RELATIVEPATH); }
  [^]                   { yypushback(yylength()); yybegin(IN_PREPROCESSOR_INCLUDE_RELATIVEPATH); }
}

<IN_PREPROCESSOR_INCLUDE_RELATIVEPATH> {
  {whitespace}? "\""    { value = string.toString();
                          if (DEBUG) {
                            System.out.printf("relative file = \"%s\"%n", value);
                          }

                          yybegin(YYINITIAL);
                          return PREPROCESSOR_INCLUDE_RELATIVEPATH; }
  {whitespace} .        |
  [^]                   { string.append(yytext()); }
}

<IN_CASE> {
  {whitespace}                      { return WHITE_SPACE; }
  {identifier} / {whitespace}? ":"  { yybegin(YYINITIAL); return LABEL; }
  {identifier} / {whitespace}? "::" { yypushback(yylength()); yybegin(YYINITIAL); }
  [^]                               { yypushback(yylength()); yybegin(YYINITIAL); }
}