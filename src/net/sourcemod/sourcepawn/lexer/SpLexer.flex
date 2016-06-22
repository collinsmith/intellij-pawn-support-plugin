package net.sourcemod.sourcepawn.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static net.sourcemod.sourcepawn.lexer.SpTokenTypes.*;

import java.util.function.IntConsumer;
import java.util.NoSuchElementException;

import java.util.Map;
import java.util.HashMap;
import java.util.PrimitiveIterator;

%%

%unicode
%class _SpLexer
%implements FlexLexer
%function advance
%type IElementType
%debug

%{
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

  public _SpLexer() {
    this((java.io.Reader)null);
  }

  public void goTo(int offset) {
    zzCurrentPos = zzMarkedPos = zzStartRead = offset;
    zzAtEOF = false;
  }
%}

WHITE_SPACE_CHAR    = [\ \t\f]

ALPHA               = [_@a-zA-Z]
ALPHA_NUM           = [_@a-zA-Z0-9]
IDENTIFIER          = ([_@]{ALPHA_NUM}+) | ([a-zA-Z]{ALPHA_NUM}*)

COMMENT_TAIL        = ([^"*"]*("*"+[^"*""/"])?)*("*"+"/")?
C_STYLE_COMMENT     = ("/*"[^"*"]{COMMENT_TAIL})|"/*"
DOC_COMMENT         = "/*""*"+("/"|([^"/""*"]{COMMENT_TAIL}))?
END_OF_LINE_COMMENT = "/""/"[^\r\n]*

BINARY_DIGIT        = [01]
OCTAL_DIGIT         = [0-7]
DECIMAL_DIGIT       = [0-9]
HEXADECIMAL_DIGIT   = [0-9a-fA-F]

BINARY_LITERAL      = 0b ( _ | {BINARY_DIGIT} )*
OCTAL_LITERAL       = 0o ( _ | {OCTAL_DIGIT} )*
DECIMAL_LITERAL     = {DECIMAL_DIGIT} ( _ | {DECIMAL_DIGIT} )*
HEXADECIMAL_LITERAL = 0x ( _ | {HEXADECIMAL_DIGIT} )*

RATIONAL_LITERAL    = {DECIMAL_DIGIT} "." {DECIMAL_DIGIT} {RATIONAL_EXPONENT}?
RATIONAL_EXPONENT   = e -? {DECIMAL_DIGIT}+

ESCAPE_SEQUENCE     = \\[^\r\n]
CHARACTER_LITERAL   = "'" ([^\\\'\r\n] | {ESCAPE_SEQUENCE})* ("'"|\\)?
STRING_LITERAL      = \" ([^\\\"\r\n] | {ESCAPE_SEQUENCE})* (\"|\\)?

%state IN_CASE

%%

<IN_CASE> {
  {IDENTIFIER} / {WHITE_SPACE_CHAR}* ":" { yybegin(YYINITIAL); return LABEL; }
}

<YYINITIAL> {

  {WHITE_SPACE_CHAR}+   { yybegin(YYINITIAL); return WHITE_SPACE; }

  {C_STYLE_COMMENT}     { yybegin(YYINITIAL); return C_STYLE_COMMENT; }
  {END_OF_LINE_COMMENT} { yybegin(YYINITIAL); return END_OF_LINE_COMMENT; }
  {DOC_COMMENT}         { yybegin(YYINITIAL); return DOC_COMMENT; }

  {BINARY_LITERAL}      { yybegin(YYINITIAL); return NUMBER_LITERAL; }
  {OCTAL_LITERAL}       { yybegin(YYINITIAL); return NUMBER_LITERAL; }
  {DECIMAL_LITERAL}     { yybegin(YYINITIAL); return NUMBER_LITERAL; }
  {HEXADECIMAL_LITERAL} { yybegin(YYINITIAL); return NUMBER_LITERAL; }
  {RATIONAL_LITERAL}    { yybegin(YYINITIAL); return RATIONAL_LITERAL; }
  {CHARACTER_LITERAL}   { yybegin(YYINITIAL); return CHARACTER_LITERAL; }
  {STRING_LITERAL}      { yybegin(YYINITIAL); return STRING_LITERAL; }

  "true"                { yybegin(YYINITIAL); return TRUE; }
  "false"               { yybegin(YYINITIAL); return FALSE; }
  "null"                { yybegin(YYINITIAL); return NULL; }

  // KEYWORDS
  "acquire"             { yybegin(YYINITIAL); return ACQUIRE; }
  "as"                  { yybegin(YYINITIAL); return AS; }
  "assert"              { yybegin(YYINITIAL); return ASSERT; }
//"*begin"              { yybegin(YYINITIAL); return BEGIN; }
  "break"               { yybegin(YYINITIAL); return BREAK; }
  "builtin"             { yybegin(YYINITIAL); return BUILTIN; }
  "case"                { yybegin(IN_CASE);   return CASE; }
  "cast_to"             { yybegin(YYINITIAL); return CAST_TO; }
  "catch"               { yybegin(YYINITIAL); return CATCH; }
  "cellsof"             { yybegin(YYINITIAL); return CELLSOF; }
  "char"                { yybegin(YYINITIAL); return CHAR; }
  "const"               { yybegin(YYINITIAL); return CONST; }
  "continue"            { yybegin(YYINITIAL); return CONTINUE; }
  "decl"                { yybegin(YYINITIAL); return DECL; }
  "default"             { yybegin(YYINITIAL); return DEFAULT; }
  "defined"             { yybegin(YYINITIAL); return DEFINED; }
  "delete"              { yybegin(YYINITIAL); return DELETE; }
  "do"                  { yybegin(YYINITIAL); return DO; }
  "double"              { yybegin(YYINITIAL); return DOUBLE; }
  "else"                { yybegin(YYINITIAL); return ELSE; }
//"*end"                { yybegin(YYINITIAL); return END; }
  "enum"                { yybegin(YYINITIAL); return ENUM; }
  "exit"                { yybegin(YYINITIAL); return EXIT; }
  "explicit"            { yybegin(YYINITIAL); return EXPLICIT; }
  "finally"             { yybegin(YYINITIAL); return FINALLY; }
  "for"                 { yybegin(YYINITIAL); return FOR; }
  "foreach"             { yybegin(YYINITIAL); return FOREACH; }
  "forward"             { yybegin(YYINITIAL); return FORWARD; }
  "funcenum"            { yybegin(YYINITIAL); return FUNCENUM; }
  "functag"             { yybegin(YYINITIAL); return FUNCTAG; }
  "function"            { yybegin(YYINITIAL); return FUNCTION; }
  "goto"                { yybegin(YYINITIAL); return GOTO; }
  "if"                  { yybegin(YYINITIAL); return IF; }
  "implicit"            { yybegin(YYINITIAL); return IMPLICIT; }
  "import"              { yybegin(YYINITIAL); return IMPORT; }
  "in"                  { yybegin(YYINITIAL); return IN; }
  "int"                 { yybegin(YYINITIAL); return INT; }
  "int8"                { yybegin(YYINITIAL); return INT8; }
  "int16"               { yybegin(YYINITIAL); return INT16; }
  "int32"               { yybegin(YYINITIAL); return INT32; }
  "int64"               { yybegin(YYINITIAL); return INT64; }
  "interface"           { yybegin(YYINITIAL); return INTERFACE; }
  "intn"                { yybegin(YYINITIAL); return INTN; }
  "let"                 { yybegin(YYINITIAL); return LET; }
  "methodmap"           { yybegin(YYINITIAL); return METHODMAP; }
  "namespace"           { yybegin(YYINITIAL); return NAMESPACE; }
  "native"              { yybegin(YYINITIAL); return NATIVE; }
  "new"                 { yybegin(YYINITIAL); return NEW; }
  "__nullable__"        { yybegin(YYINITIAL); return NULLABLE; }
  "object"              { yybegin(YYINITIAL); return OBJECT; }
  "operator"            { yybegin(YYINITIAL); return OPERATOR; }
  "package"             { yybegin(YYINITIAL); return PACKAGE; }
  "private"             { yybegin(YYINITIAL); return PRIVATE; }
  "protected"           { yybegin(YYINITIAL); return PROTECTED; }
  "public"              { yybegin(YYINITIAL); return PUBLIC; }
  "readonly"            { yybegin(YYINITIAL); return READONLY; }
  "return"              { yybegin(YYINITIAL); return RETURN; }
  "sealed"              { yybegin(YYINITIAL); return SEALED; }
  "sizeof"              { yybegin(YYINITIAL); return SIZEOF; }
  "sleep"               { yybegin(YYINITIAL); return SLEEP; }
  "static"              { yybegin(YYINITIAL); return STATIC; }
  "stock"               { yybegin(YYINITIAL); return STOCK; }
  "struct"              { yybegin(YYINITIAL); return STRUCT; }
  "switch"              { yybegin(YYINITIAL); return SWITCH; }
  "tagof"               { yybegin(YYINITIAL); return TAGOF; }
//"*then"               { yybegin(YYINITIAL); return THEN; }
  "this"                { yybegin(YYINITIAL); return THIS; }
  "throw"               { yybegin(YYINITIAL); return THROW; }
  "try"                 { yybegin(YYINITIAL); return TRY; }
  "typedef"             { yybegin(YYINITIAL); return TYPEDEF; }
  "typeof"              { yybegin(YYINITIAL); return TYPEOF; }
  "typeset"             { yybegin(YYINITIAL); return TYPESET; }
  "uint8"               { yybegin(YYINITIAL); return UINT8; }
  "uint16"              { yybegin(YYINITIAL); return UINT16; }
  "uint32"              { yybegin(YYINITIAL); return UINT32; }
  "uint64"              { yybegin(YYINITIAL); return UINT64; }
  "uintn"               { yybegin(YYINITIAL); return UINTN; }
  "union"               { yybegin(YYINITIAL); return UNION; }
  "using"               { yybegin(YYINITIAL); return USING; }
  "var"                 { yybegin(YYINITIAL); return VAR; }
  "variant"             { yybegin(YYINITIAL); return VARIANT; }
  "view_as"             { yybegin(YYINITIAL); return VIEW_AS; }
  "virtual"             { yybegin(YYINITIAL); return VIRTUAL; }
  "void"                { yybegin(YYINITIAL); return VOID; }
  "volatile"            { yybegin(YYINITIAL); return VOLATILE; }
  "while"               { yybegin(YYINITIAL); return WHILE; }
  "with"                { yybegin(YYINITIAL); return WITH; }

  // IDENTIFIERS / TAGS
  {IDENTIFIER}          { yybegin(YYINITIAL); return IDENTIFIER; }
  {IDENTIFIER} / ":"    { yybegin(YYINITIAL); return TAG; }
  {IDENTIFIER} / "::"   { yybegin(YYINITIAL); return IDENTIFIER; }

  "_"                   { yybegin(YYINITIAL); return UNDERSCORE; }
  "_" / ":"             { yybegin(YYINITIAL); return TAG; }
  "_" / "::"            { yybegin(YYINITIAL); return UNDERSCORE; }

  "@"                   { yybegin(YYINITIAL); return AT_SIGN; }

}

[^]                     { yybegin(YYINITIAL); return BAD_CHARACTER; }