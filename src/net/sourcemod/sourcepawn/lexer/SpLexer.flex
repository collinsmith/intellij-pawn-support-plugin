package net.sourcemod.sourcepawn.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static net.sourcemod.sourcepawn.lexer.SpTokenTypes.*;

import java.io.Reader;
import java.util.function.IntConsumer;
import java.util.NoSuchElementException;

import java.util.Map;
import java.util.HashMap;
import java.util.PrimitiveIterator;

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
  SUBSTITUTIONS.put("__BINARY_PATH__", "path");
  SUBSTITUTIONS.put("__BINARY_NAME__", "name");
  SUBSTITUTIONS.put("__DATE__", "date");
  SUBSTITUTIONS.put("__TIME__", "time");

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

  final Map<CharSequence, CharSequence> SUBSTITUTIONS = new HashMap<>();

  private final StringBuilder string = new StringBuilder(32);
  private final StringBuilder escapeSequence = new StringBuilder(8);
  private int escapedCodePoint;

  private IElementType BAD_WORD_REASON;
  private IElementType BAD_LINE_REASON;

  private int GOTO_AFTER_ESCAPE_SEQUENCE;

  private boolean isPreprocessorUndef;

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

  private void prepareInlineEscapeSequence(int GOTO_AFTER_ESCAPE_SEQUENCE) {
    this.GOTO_AFTER_ESCAPE_SEQUENCE = GOTO_AFTER_ESCAPE_SEQUENCE;
    escapedCodePoint = 0;
    escapeSequence.setLength(0);
    escapeSequence.appendCodePoint(getEscapeCharacter());
  }

  private void gotoBadWord(IElementType BAD_WORD_REASON) {
    this.BAD_WORD_REASON = BAD_WORD_REASON;
    string.setLength(0);
    yybegin(IN_BAD_WORD);
  }

  /*private void gotoBadLine(int BAD_LINE_REASON) {
    this.BAD_LINE_REASON = BAD_LINE_REASON;
    string.setLength(0);
    yybegin(IN_BAD_LINE);
  }*/

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
nownl               = [^ \r\n\t]+
nl                  = \r|\n|\r\n
nonl                = [^\r\n]
nobrknl             = [^\[\r\n]
brknl               = \\{w}?{nl}{w}?
whitespace          = ({w}|{brknl})+

binary_digit        = [01]
octal_digit         = [0-7]
decimal_digit       = [0-9]
hexadecimal_digit   = [0-9a-fA-F]

binary_prefix       = 0b
octal_prefix        = 0o
decimal_prefix      = {decimal_digit}
hexadecimal_prefix  = 0x

alpha               = [_@a-zA-Z]
alphanum            = [_@a-zA-Z0-9]

identifier          = ([_@]{alphanum}+) | ([a-zA-Z]{alphanum}*)

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

pattern_arg         = % {decimal_digit}

doc_pre             = {w} "*" {w}

%xstate IN_BAD_WORD

%xstate LOOKING_FOR_PATTERN
%xstate INITIAL
%xstate IN_PREPROCESSOR

%xstate IN_PREPROCESSOR_STRING_PRE
%xstate IN_PREPROCESSOR_STRING

%%

[^]                     { yypushback(yylength()); yybegin(LOOKING_FOR_PATTERN); }

<LOOKING_FOR_PATTERN> {
  {nownl}               { int codePoint = codePointAt(0);
                          if (!SpUtils.isAlpha(codePoint)) {
                            yypushback(yylength()); yybegin(INITIAL);
                          } else {
                            string.setLength(0);
                            int offset = 0;
                            CharSequence pattern = yytext();
                            PrimitiveIterator.OfInt iterator = pattern.codePoints().iterator();
                            while (iterator.hasNext()) {
                              codePoint = iterator.nextInt();
                              if (SpUtils.isAlphaNumeric(codePoint)) {
                                string.appendCodePoint(codePoint);
                              } else {
                                break;
                              }
                            }

                            if (SUBSTITUTIONS.containsKey(string)) {
                              if (DEBUG) {
                                System.out.printf("pattern prefix = %s%n", string);
                              }

                              return DEFINED_PATTERN_PREFIX;
                            } else {
                              yypushback(yylength()); yybegin(INITIAL);
                            }
                          }
                        }
  [^]                   { yypushback(yylength()); yybegin(INITIAL); }
}

<INITIAL> {
  "#"                     { yybegin(IN_PREPROCESSOR); return HASH; }

  // KEYWORDS
  "acquire"               { yybegin(LOOKING_FOR_PATTERN); return ACQUIRE; }
  "as"                    { yybegin(LOOKING_FOR_PATTERN); return AS; }
  "assert"                { yybegin(LOOKING_FOR_PATTERN); return ASSERT; }
  //"*begin"              { yybegin(LOOKING_FOR_PATTERN); return BEGIN; }
  "break"                 { yybegin(LOOKING_FOR_PATTERN); return BREAK; }
  "builtin"               { yybegin(LOOKING_FOR_PATTERN); return BUILTIN; }
  "case"                  { yybegin(LOOKING_FOR_PATTERN); return CASE; }
  "cast_to"               { yybegin(LOOKING_FOR_PATTERN); return CAST_TO; }
  "catch"                 { yybegin(LOOKING_FOR_PATTERN); return CATCH; }
  "cellsof"               { yybegin(LOOKING_FOR_PATTERN); return CELLSOF; }
  "char"                  { yybegin(LOOKING_FOR_PATTERN); return CHAR; }
  "const"                 { yybegin(LOOKING_FOR_PATTERN); return CONST; }
  "continue"              { yybegin(LOOKING_FOR_PATTERN); return CONTINUE; }
  "decl"                  { yybegin(LOOKING_FOR_PATTERN); return DECL; }
  "default"               { yybegin(LOOKING_FOR_PATTERN); return DEFAULT; }
  "defined"               { yybegin(LOOKING_FOR_PATTERN); return DEFINED; }
  "delete"                { yybegin(LOOKING_FOR_PATTERN); return DELETE; }
  "do"                    { yybegin(LOOKING_FOR_PATTERN); return DO; }
  "double"                { yybegin(LOOKING_FOR_PATTERN); return DOUBLE; }
  "else"                  { yybegin(LOOKING_FOR_PATTERN); return ELSE; }
  //"*end"                { yybegin(LOOKING_FOR_PATTERN); return END; }
  "enum"                  { yybegin(LOOKING_FOR_PATTERN); return ENUM; }
  "exit"                  { yybegin(LOOKING_FOR_PATTERN); return EXIT; }
  "explicit"              { yybegin(LOOKING_FOR_PATTERN); return EXPLICIT; }
  "finally"               { yybegin(LOOKING_FOR_PATTERN); return FINALLY; }
  "for"                   { yybegin(LOOKING_FOR_PATTERN); return FOR; }
  "foreach"               { yybegin(LOOKING_FOR_PATTERN); return FOREACH; }
  "forward"               { yybegin(LOOKING_FOR_PATTERN); return FORWARD; }
  "funcenum"              { yybegin(LOOKING_FOR_PATTERN); return FUNCENUM; }
  "functag"               { yybegin(LOOKING_FOR_PATTERN); return FUNCTAG; }
  "function"              { yybegin(LOOKING_FOR_PATTERN); return FUNCTION; }
  "goto"                  { yybegin(LOOKING_FOR_PATTERN); return GOTO; }
  "if"                    { yybegin(LOOKING_FOR_PATTERN); return IF; }
  "implicit"              { yybegin(LOOKING_FOR_PATTERN); return IMPLICIT; }
  "import"                { yybegin(LOOKING_FOR_PATTERN); return IMPORT; }
  "in"                    { yybegin(LOOKING_FOR_PATTERN); return IN; }
  "int"                   { yybegin(LOOKING_FOR_PATTERN); return INT; }
  "int8"                  { yybegin(LOOKING_FOR_PATTERN); return INT8; }
  "int16"                 { yybegin(LOOKING_FOR_PATTERN); return INT16; }
  "int32"                 { yybegin(LOOKING_FOR_PATTERN); return INT32; }
  "int64"                 { yybegin(LOOKING_FOR_PATTERN); return INT64; }
  "interface"             { yybegin(LOOKING_FOR_PATTERN); return INTERFACE; }
  "intn"                  { yybegin(LOOKING_FOR_PATTERN); return INTN; }
  "let"                   { yybegin(LOOKING_FOR_PATTERN); return LET; }
  "methodmap"             { yybegin(LOOKING_FOR_PATTERN); return METHODMAP; }
  "namespace"             { yybegin(LOOKING_FOR_PATTERN); return NAMESPACE; }
  "native"                { yybegin(LOOKING_FOR_PATTERN); return NATIVE; }
  "new"                   { yybegin(LOOKING_FOR_PATTERN); return NEW; }
  "null"                  { yybegin(LOOKING_FOR_PATTERN); return NULL; }
  "__nullable__"          { yybegin(LOOKING_FOR_PATTERN); return NULLABLE; }
  "object"                { yybegin(LOOKING_FOR_PATTERN); return OBJECT; }
  "operator"              { yybegin(LOOKING_FOR_PATTERN); return OPERATOR; }
  "package"               { yybegin(LOOKING_FOR_PATTERN); return PACKAGE; }
  "private"               { yybegin(LOOKING_FOR_PATTERN); return PRIVATE; }
  "protected"             { yybegin(LOOKING_FOR_PATTERN); return PROTECTED; }
  "public"                { yybegin(LOOKING_FOR_PATTERN); return PUBLIC; }
  "readonly"              { yybegin(LOOKING_FOR_PATTERN); return READONLY; }
  "return"                { yybegin(LOOKING_FOR_PATTERN); return RETURN; }
  "sealed"                { yybegin(LOOKING_FOR_PATTERN); return SEALED; }
  "sizeof"                { yybegin(LOOKING_FOR_PATTERN); return SIZEOF; }
  "sleep"                 { yybegin(LOOKING_FOR_PATTERN); return SLEEP; }
  "static"                { yybegin(LOOKING_FOR_PATTERN); return STATIC; }
  "stock"                 { yybegin(LOOKING_FOR_PATTERN); return STOCK; }
  "struct"                { yybegin(LOOKING_FOR_PATTERN); return STRUCT; }
  "switch"                { yybegin(LOOKING_FOR_PATTERN); return SWITCH; }
  "tagof"                 { yybegin(LOOKING_FOR_PATTERN); return TAGOF; }
  //"*then"               { yybegin(LOOKING_FOR_PATTERN); return THEN; }
  "this"                  { yybegin(LOOKING_FOR_PATTERN); return THIS; }
  "throw"                 { yybegin(LOOKING_FOR_PATTERN); return THROW; }
  "try"                   { yybegin(LOOKING_FOR_PATTERN); return TRY; }
  "typedef"               { yybegin(LOOKING_FOR_PATTERN); return TYPEDEF; }
  "typeof"                { yybegin(LOOKING_FOR_PATTERN); return TYPEOF; }
  "typeset"               { yybegin(LOOKING_FOR_PATTERN); return TYPESET; }
  "uint8"                 { yybegin(LOOKING_FOR_PATTERN); return UINT8; }
  "uint16"                { yybegin(LOOKING_FOR_PATTERN); return UINT16; }
  "uint32"                { yybegin(LOOKING_FOR_PATTERN); return UINT32; }
  "uint64"                { yybegin(LOOKING_FOR_PATTERN); return UINT64; }
  "uintn"                 { yybegin(LOOKING_FOR_PATTERN); return UINTN; }
  "union"                 { yybegin(LOOKING_FOR_PATTERN); return UNION; }
  "using"                 { yybegin(LOOKING_FOR_PATTERN); return USING; }
  "var"                   { yybegin(LOOKING_FOR_PATTERN); return VAR; }
  "variant"               { yybegin(LOOKING_FOR_PATTERN); return VARIANT; }
  "view_as"               { yybegin(LOOKING_FOR_PATTERN); return VIEW_AS; }
  "virtual"               { yybegin(LOOKING_FOR_PATTERN); return VIRTUAL; }
  "void"                  { yybegin(LOOKING_FOR_PATTERN); return VOID; }
  "volatile"              { yybegin(LOOKING_FOR_PATTERN); return VOLATILE; }
  "while"                 { yybegin(LOOKING_FOR_PATTERN); return WHILE; }
  "with"                  { yybegin(LOOKING_FOR_PATTERN); return WITH; }

  // WHITE SPACE
  {whitespace}            { yybegin(LOOKING_FOR_PATTERN); return WHITE_SPACE; }
  {nl}                    { yybegin(LOOKING_FOR_PATTERN); return NEW_LINE; }

  [^]                     { yybegin(LOOKING_FOR_PATTERN); return BAD_CHARACTER; }
}

<IN_PREPROCESSOR> {
   "assert"              { yybegin(INITIAL); return PREPROCESSOR_ASSERT; }
   "define"              { yybegin(INITIAL); return PREPROCESSOR_DEFINE; }
   "else"                { yybegin(INITIAL); return PREPROCESSOR_ELSE; }
   "elseif"              { yybegin(INITIAL); return PREPROCESSOR_ELSEIF; }
   "endif"               { yybegin(INITIAL); return PREPROCESSOR_ENDIF; }
   "endinput"            { yybegin(YYINITIAL); return PREPROCESSOR_ENDINPUT; }
   "endscript"           { yybegin(YYINITIAL); return PREPROCESSOR_ENDSCRIPT; }
   "error"               { yybegin(IN_PREPROCESSOR_STRING_PRE); return PREPROCESSOR_ERROR; }
   "file"                { yybegin(YYINITIAL); return PREPROCESSOR_FILE; }
   "if"                  { yybegin(YYINITIAL); return PREPROCESSOR_IF; }
   "include"             { yybegin(YYINITIAL); return PREPROCESSOR_INCLUDE; }
   "line"                { yybegin(YYINITIAL); return PREPROCESSOR_LINE; }
   "pragma"              { yybegin(YYINITIAL); return PREPROCESSOR_PRAGMA; }
   "tryinclude"          { yybegin(YYINITIAL); return PREPROCESSOR_TRYINCLUDE; }
   "undef"               { yybegin(YYINITIAL); return PREPROCESSOR_UNDEF; }
   // Not a valid preprocessor directive
   [^]                   |
   <<EOF>>               { yypushback(yylength()); gotoBadWord(INVALID_PREPROCESSOR_DIRECTIVE); }
 }

 /**
  * Invalidates the current characters until EOF or a white space/new line is reached for the reason
  * given in {@link #BAD_WORD_REASON}.
  *
  * @note This state will not consume the white space sequence/new line, and will push them back onto
  *       the stream
  */
 <IN_BAD_WORD> {
   {wnl}                 |
   <<EOF>>               { value = string.toString();
                           if (DEBUG) {
                             System.out.printf("%s = %s%n", BAD_WORD_REASON, value);
                           }

                           yypushback(yylength()); yybegin(YYINITIAL);
                           return BAD_WORD_REASON; }
   [^]                   { string.append(yytext()); }
 }

 /**
  * @precedes IN_PREPROCESSOR_STRING
  *
  * Consumes and returns next WHITE_SPACE before transitioning to IN_PREPROCESSOR_STRING. Any other
  * character should push back and return to YYINITIAL.
  */
 <IN_PREPROCESSOR_STRING_PRE> {
   {whitespace}          { string.setLength(0); yybegin(IN_PREPROCESSOR_STRING);
                           return WHITE_SPACE; }
   [^]                   |
   <<EOF>>               { yypushback(yylength()); yybegin(YYINITIAL); }
 }

 /**
  * Consumes the remainder of the line (honoring line continuation), and returns it as
  * {@link SpTokenTypes#PREPROCESSOR_STRING}.
  *
  * @note This state will not consume the final new line sequence, and will push them back onto the
  *       stream
  */
 <IN_PREPROCESSOR_STRING> {
   {w} .                 { string.append(yytext()); }
   . {w} / {brknl}       { string.append(yytext()); }
   {brknl}               { /* ignore whitespace */ }
   {w}? {nl}             |
   <<EOF>>               { value = string.toString().trim();
                           if (DEBUG && !((String)value).isEmpty()) {
                             System.out.printf("message = %s%n", value);
                           }

                           yypushback(yylength()); yybegin(YYINITIAL);
                           if (!((String)value).isEmpty()) {
                             return PREPROCESSOR_STRING;
                           }
                         }
   [^]                   { string.append(yytext()); }
 }