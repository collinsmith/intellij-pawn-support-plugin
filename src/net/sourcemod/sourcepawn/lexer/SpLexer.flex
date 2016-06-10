package net.sourcemod.sourcepawn.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static net.sourcemod.sourcepawn.lexer.SpTokenTypes.*;

import java.io.Reader;
import java.util.function.IntConsumer;
import java.util.NoSuchElementException;

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

  private IElementType BAD_LITERAL_REASON;
  private int GOTO_AFTER_ESCAPE_SEQUENCE;
  private int GOTO_AFTER_ESCAPE_SEQUENCE_FAIL;

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

  private int codePointAt(int index) {
    final int length = yylength();
    if (index >= length) {
        throw new NoSuchElementException();
    }

    char c1 = zzBuffer.charAt(zzStartRead + index);
    index++;
    if (Character.isHighSurrogate(c1) && index < length) {
        char c2 = zzBuffer.charAt(zzStartRead + index);
        if (Character.isLowSurrogate(c2)) {
            return Character.toCodePoint(c1, c2);
        }
    }

    return c1;
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

control_character   = [abefnrtvx]

doc_pre             = {w} "*" {w}

%xstate IN_LINE_COMMENT
%xstate IN_BLOCK_COMMENT
%xstate IN_DOC_COMMENT_PRE
%xstate IN_DOC_COMMENT
%xstate IN_DOC_COMMENT_POST

%xstate IN_PREPROCESSOR
%xstate IN_PREPROCESSOR_INCLUDE_PRE
%xstate IN_PREPROCESSOR_INCLUDE
%xstate IN_PREPROCESSOR_INCLUDE_SYSTEMPATH_PRE
%xstate IN_PREPROCESSOR_INCLUDE_SYSTEMPATH
%xstate IN_PREPROCESSOR_INCLUDE_RELATIVEPATH_PRE
%xstate IN_PREPROCESSOR_INCLUDE_RELATIVEPATH

%xstate IN_CASE

%xstate IN_CHARACTER_LITERAL
%xstate IN_CHARACTER_LITERAL_FINISH

%xstate IN_STRING_LITERAL

%xstate IN_BAD_LITERAL
%xstate IN_ESCAPE_SEQUENCE
%xstate IN_DECIMAL_ESCAPE_SEQUENCE
%xstate IN_UNICODE_ESCAPE_SEQUENCE

%%

// PUNCTUATION
":"                     { return COLON; }
","                     { return COMMA; }
"#"                     { yybegin(IN_PREPROCESSOR); return HASH; }
"."                     { return PERIOD; }
";"                     { return SEMICOLON; }
"..."                   { return ELLIPSIS; }

// OPERATORS
"&"                     { return AMPERSAND; }
"="                     { return ASSIGN; }
"*"                     { return ASTERISK; }
"^"                     { return CARET; }
"!"                     { return EXCLAMATION; }
"-"                     { return MINUS; }
"%"                     { return PERCENT; }
"+"                     { return PLUS; }
"/"                     { return SLASH; }
"~"                     { return TILDE; }
"|"                     { return VERTICAL_BAR; }
"+="                    { return ADDEQ; }
"&&"                    { return AND; }
"&="                    { return ANDEQ; }
"--"                    { return DECREMENT; }
"/="                    { return DIVEQ; }
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

// MATCHED PAIRS
"{"                     { return LBRACE; }
"}"                     { return RBRACE; }
"["                     { return LBRACKET; }
"]"                     { return RBRACKET; }
"("                     { return LPAREN; }
")"                     { return RPAREN; }
"<"                     { return LT; }
">"                     { return GT; }

// KEYWORDS
"acquire"               { return ACQUIRE; }
"as"                    { return AS; }
"assert"                { return ASSERT; }
//"*begin"              { return BEGIN; }
"break"                 { return BREAK; }
"builtin"               { return BUILTIN; }
"case"                  { yybegin(IN_CASE); return CASE; }
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

// CHARACTER LITERALS
"\'"                    { string.setLength(0); string.append('\'');
                          GOTO_AFTER_ESCAPE_SEQUENCE = IN_CHARACTER_LITERAL_FINISH;
                          GOTO_AFTER_ESCAPE_SEQUENCE_FAIL = IN_BAD_LITERAL;
                          BAD_LITERAL_REASON = INCOMPLETE_CHARACTER_LITERAL;
                          yybegin(IN_CHARACTER_LITERAL); }

// STRING LITERALS
"\""                    { string.setLength(0); string.append('\"');
                          GOTO_AFTER_ESCAPE_SEQUENCE = IN_STRING_LITERAL;
                          GOTO_AFTER_ESCAPE_SEQUENCE_FAIL = IN_BAD_LITERAL;
                          BAD_LITERAL_REASON = BAD_STRING_LITERAL;
                          yybegin(IN_STRING_LITERAL); }

// COMMENTS
"//" {w}?               { string.setLength(0); yybegin(IN_LINE_COMMENT); }
"/**" {w}?              { string.setLength(0); yybegin(IN_DOC_COMMENT_PRE); }
"/*" {w}?               { string.setLength(0); yybegin(IN_BLOCK_COMMENT); }

// WHITE SPACE
{whitespace}            { return WHITE_SPACE; }
{nl}                    { return NEW_LINE; }

// IDENTIFIERS
{identifier}            { return IDENTIFIER; }
{identifier} / "::"     { return IDENTIFIER; }

"_"                     { return UNDERSCORE; }
"_" / "::"              { return UNDERSCORE; }

"@"                     { return AT_SIGN; }

// TAGS
{identifier} / ":"      { return TAG; }
"_" / ":"               { return TAG; }

// NUMBER LITERALS
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

// RATIONAL LITERALS
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

<IN_CHARACTER_LITERAL> {
  \'                    { string.append('\'');
                          value = string.toString();
                          if (DEBUG) {
                            System.out.printf("character = %s%n", value);
                          }

                          yybegin(YYINITIAL); return EMPTY_CHARACTER_LITERAL; }
  . / \'                { int codePoint = codePointAt(0);
                          string.appendCodePoint(codePoint);
                          yybegin(IN_CHARACTER_LITERAL_FINISH);
                        }
  .                     { int codePoint = codePointAt(0);
                          if (isEscapeCharacter(codePoint)) {
                            string.appendCodePoint(codePoint);
                            yybegin(IN_ESCAPE_SEQUENCE);
                          } else {
                            yypushback(yylength()); yybegin(IN_BAD_LITERAL);
                          }
                        }
  <<EOF>>               { BAD_LITERAL_REASON = INCOMPLETE_CHARACTER_LITERAL;
                          yybegin(IN_BAD_LITERAL); }
  [^]                   { BAD_LITERAL_REASON = BAD_CHARACTER_LITERAL;
                          yypushback(yylength()); yybegin(IN_BAD_LITERAL); }
}

<IN_CHARACTER_LITERAL_FINISH> {
  \'                    { string.append('\'');
                          value = string.toString();
                          if (DEBUG) {
                            System.out.printf("character = %s%n", value);
                          }

                          yybegin(YYINITIAL);
                          return CHARACTER_LITERAL; }
  <<EOF>>               { BAD_LITERAL_REASON = BAD_CHARACTER_LITERAL;
                          yybegin(IN_BAD_LITERAL); }
  [^]                   { BAD_LITERAL_REASON = BAD_CHARACTER_LITERAL;
                          yypushback(yylength()); yybegin(IN_BAD_LITERAL); }
}

<IN_STRING_LITERAL> {
  \"                    { string.append('\"');
                          value = string.toString();
                          if (DEBUG) {
                            System.out.printf("string = %s%n", value);
                          }

                          yybegin(YYINITIAL);
                          return STRING_LITERAL; }
  {brknl}               { /* line continuation */ }
  .                     { int codePoint = codePointAt(0);
                          if (isEscapeCharacter(codePoint)) {
                            string.appendCodePoint(codePoint);
                            yybegin(IN_ESCAPE_SEQUENCE);
                          } else {
                            yypushback(yylength()); yybegin(IN_BAD_LITERAL);
                          }
                        }
  <<EOF>>               { BAD_LITERAL_REASON = INCOMPLETE_STRING_LITERAL;
                          yybegin(IN_BAD_LITERAL); }
  [^]                   { BAD_LITERAL_REASON = BAD_STRING_LITERAL;
                          yypushback(yylength()); yybegin(IN_BAD_LITERAL); }
}

<IN_BAD_LITERAL> {
  {nl}                  |
  <<EOF>>               { value = string.toString();
                          if (DEBUG) {
                            System.out.printf("%s = %s%n", BAD_LITERAL_REASON, value);
                          }

                          yybegin(YYINITIAL);
                          return BAD_LITERAL_REASON; }
  [^]                   { string.append(yytext()); }
}

<IN_ESCAPE_SEQUENCE> {
  <<EOF>>               { yybegin(YYINITIAL); return BAD_ESCAPE_SEQUENCE; }
  {control_character}   { int codePoint = codePointAt(0);
                          if (codePoint == 'x') {
                            string.appendCodePoint(codePoint);
                            yybegin(IN_UNICODE_ESCAPE_SEQUENCE);
                          } else {
                            switch(codePoint) {
                              case 'a': string.append('\u0007'); break;
                              case 'b': string.append('\b'); break;
                              case 'e': string.append('\u001B'); break;
                              case 'f': string.append('\f'); break;
                              case 'n': string.append('\n'); break;
                              case 'r': string.append('\r'); break;
                              case 't': string.append('\t'); break;
                              case 'v': string.append('\u000B'); break;
                              default:
                                throw new AssertionError(String.format(
                                    "Unsupported control character: %c (%1$d)", codePoint));
                            }

                            yybegin(GOTO_AFTER_ESCAPE_SEQUENCE);
                          }
                        }
  [\"'%]                { string.append(yytext()); yybegin(GOTO_AFTER_ESCAPE_SEQUENCE); }
  {decimal_digit}       { yypushback(yylength()); yybegin(IN_DECIMAL_ESCAPE_SEQUENCE); }
  .                     { int codePoint = codePointAt(0);
                          if (isEscapeCharacter(codePoint)) {
                            string.appendCodePoint(codePoint);
                            yybegin(GOTO_AFTER_ESCAPE_SEQUENCE);
                          } else {
                            BAD_LITERAL_REASON = BAD_ESCAPE_SEQUENCE;
                            yypushback(yylength()); yybegin(IN_BAD_LITERAL);
                          }
                        }
  [^]                   { BAD_LITERAL_REASON = BAD_ESCAPE_SEQUENCE;
                          yypushback(yylength()); yybegin(IN_BAD_LITERAL); }
}

<IN_DECIMAL_ESCAPE_SEQUENCE> {
  <<EOF>>               { BAD_LITERAL_REASON = BAD_ESCAPE_SEQUENCE; yybegin(IN_BAD_LITERAL); }
  {decimal_escape}      { string.append(yytext()); yybegin(GOTO_AFTER_ESCAPE_SEQUENCE); }
  [^]                   { yypushback(yylength()); yybegin(GOTO_AFTER_ESCAPE_SEQUENCE); }
}

<IN_UNICODE_ESCAPE_SEQUENCE> {
  <<EOF>>               { BAD_LITERAL_REASON = BAD_ESCAPE_SEQUENCE; yybegin(IN_BAD_LITERAL); }
  {unicode_escape}      { string.append(yytext()); yybegin(GOTO_AFTER_ESCAPE_SEQUENCE); }
  [^]                   { yypushback(yylength()); yybegin(GOTO_AFTER_ESCAPE_SEQUENCE); }
}

<IN_LINE_COMMENT> {
  {w} .                 { string.append(yytext()); }
  {w}? {nl}             { value = string.toString();
                          if (DEBUG) {
                            System.out.printf("line comment = %s%n", value);
                          }

                          yybegin(YYINITIAL);
                          yypushback(yylength());
                          return LINE_COMMENT;
                        }
  <<EOF>>               { value = string.toString().trim();
                          if (DEBUG) {
                            System.out.printf("line comment = %s%n", value);
                          }

                          yybegin(YYINITIAL);
                          return LINE_COMMENT;
                        }
  [^]                   { string.append(yytext()); }
}

<IN_BLOCK_COMMENT> {
  <<EOF>>               { return UNTERMINATED_COMMENT; }
  {w} .                 { string.append(yytext()); }
  {w}? {nl}+ {w}?       { string.append(' '); }
  {w}? "*/"             { value = string.toString().trim();
                          if (DEBUG) {
                            System.out.printf("block comment = %s%n", value);
                          }

                          yybegin(YYINITIAL);
                          return BLOCK_COMMENT;
                        }
  [^]                   { string.append(yytext()); }
}

<IN_DOC_COMMENT_PRE> {
  <<EOF>>               { return UNTERMINATED_COMMENT; }
  "*"+                  { /* ignore leading asterisks */ }
  "*"* {nl} {doc_pre}?  { yybegin(IN_DOC_COMMENT); }
  "*"* "*/"             { yypushback(yylength()); yybegin(IN_DOC_COMMENT_POST); }
  [^]                   { string.append(yytext()); }
}

<IN_DOC_COMMENT> {
  <<EOF>>               { return UNTERMINATED_COMMENT; }
  {w}? {nl} {doc_pre}?  { string.append(' '); }
  "*"* "*/"             { yypushback(yylength()); yybegin(IN_DOC_COMMENT_POST); }
  [^]                   { string.append(yytext()); }
}

<IN_DOC_COMMENT_POST> {
  <<EOF>>               { return UNTERMINATED_COMMENT; }
  "*"* "*/"             { value = string.toString().trim();
                          if (DEBUG) {
                            System.out.printf("doc comment = %s%n", value);
                          }

                          yybegin(YYINITIAL);
                          yypushback(yylength());
                          return DOC_COMMENT;
                        }
  [^]                   { throw new AssertionError(
                              "Doc comment terminator should already have been read " +
                              "and pushed back into the stream."); }
}