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

%xstate IN_PREPROCESSOR
%xstate IN_PREPROCESSOR_INCLUDE_PRE
%xstate IN_PREPROCESSOR_INCLUDE
%xstate IN_PREPROCESSOR_INCLUDE_SYSTEMPATH_PRE
%xstate IN_PREPROCESSOR_INCLUDE_SYSTEMPATH
%xstate IN_PREPROCESSOR_INCLUDE_RELATIVEPATH_PRE
%xstate IN_PREPROCESSOR_INCLUDE_RELATIVEPATH

%%

"#"                     { yybegin(IN_PREPROCESSOR); return HASH; }

{whitespace}            { return WHITE_SPACE; }
{nl}                    { return NEW_LINE; }

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