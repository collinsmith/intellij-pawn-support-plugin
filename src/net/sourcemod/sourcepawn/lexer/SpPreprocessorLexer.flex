package net.sourcemod.sourcepawn.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static net.sourcemod.sourcepawn.lexer.SpPreprocessorTokenTypes.*;

import java.util.function.IntConsumer;

%%

%public
%unicode
%class _SpPreprocessorLexer
%implements FlexLexer
%function advance
%type IElementType
//%debug

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

  public _SpPreprocessorLexer() {
    this((java.io.Reader)null);
  }

  public void goTo(int offset) {
    zzCurrentPos = zzMarkedPos = zzStartRead = offset;
    zzAtEOF = false;
  }
%}

WHITE_SPACE_CHAR    = [\ \t\f]
NEW_LINE_SEQUENCE   = \r|\n|\r\n
LINE_CONTINUATION   = \\ {WHITE_SPACE_CHAR}+ {NEW_LINE_SEQUENCE}

ALPHA               = [_@a-zA-Z]
ALPHA_NUM           = [_@a-zA-Z0-9]
IDENTIFIER          = ([_@]{ALPHA_NUM}+) | ([a-zA-Z]{ALPHA_NUM}*)

%xstate IN_DEFINE
%xstate IN_DEFINE_SUBST

%%

<YYINITIAL> {

  {WHITE_SPACE_CHAR}+   { yybegin(YYINITIAL); return SpTokenTypes.WHITE_SPACE; }
  {NEW_LINE_SEQUENCE}   { yybegin(YYINITIAL); return SpTokenTypes.NEW_LINE; }
  {LINE_CONTINUATION}+  { yybegin(YYINITIAL); return SpTokenTypes.WHITE_SPACE; }

  "#assert"             { yybegin(YYINITIAL); return ASSERT; }
  "#define"             { yybegin(IN_DEFINE); return DEFINE; }
  "#else"               { yybegin(YYINITIAL); return ELSE; }
  "#elseif"             { yybegin(YYINITIAL); return ELSEIF; }
  "#endif"              { yybegin(YYINITIAL); return ENDIF; }
  "#endinput"           { yybegin(YYINITIAL); return ENDINPUT; }
  "#endscript"          { yybegin(YYINITIAL); return ENDSCRIPT; }
  "#error"              { yybegin(YYINITIAL); return ERROR; }
  "#file"               { yybegin(YYINITIAL); return FILE; }
  "#if"                 { yybegin(YYINITIAL); return IF; }
  "#include"            { yybegin(YYINITIAL); return INCLUDE; }
  "#line"               { yybegin(YYINITIAL); return LINE; }
  "#pragma"             { yybegin(YYINITIAL); return PRAGMA; }
  "#tryinclude"         { yybegin(YYINITIAL); return TRYINCLUDE; }
  "#undef"              { yybegin(YYINITIAL); return UNDEF; }

}

<IN_DEFINE> {
  [^\ \t\f\r\n]+        { yybegin(IN_DEFINE_SUBST); return PATTERN_DEFINITION; }
  [^]                   { yypushback(yylength()); yybegin(YYINITIAL); }
}

<IN_DEFINE_SUBST> {
  [^\ \t\f\r\n]+        { yybegin(YYINITIAL); return PATTERN_DEFINITION; }
  [^]                   { yypushback(yylength()); yybegin(YYINITIAL); }
}

[^]                     { yybegin(YYINITIAL); return SpTokenTypes.BAD_CHARACTER; }