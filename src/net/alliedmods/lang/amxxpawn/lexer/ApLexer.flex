package net.alliedmods.lang.amxxpawn.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.parser.ApParserUtils;
import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;

@SuppressWarnings({"ALL"})
%%

%{
    private static final boolean DEBUG = false;

    public int sc_ctrlchar = '^';
    public boolean sc_needsemicolon = false;

    private boolean packed = false;

    public _ApLexer() {
      this((java.io.Reader) null);
    }

    public int getCtrlChar() {
      return sc_ctrlchar;
    }
%}

%unicode
%class _ApLexer
%implements FlexLexer, CtrlProvider
%function advance
%type IElementType
//%debug

WS = [\ \t\f]
NL = \r|\n|\r\n
CONT = "\\" {WS}* {NL} {WS}*

//ALPHA = [_@a-zA-Z] // Embedded into {IDENTIFIER} declaration
ALPHA_NUM = [_@a-zA-Z0-9]
IDENTIFIER = ([_@]{ALPHA_NUM}+) | ([a-zA-Z]{ALPHA_NUM}*)

C_STYLE_COMMENT = ("/*"[^"*"]{COMMENT_TAIL})|"/*"
DOC_COMMENT = "/*""*"+("/"|([^"/""*"]{COMMENT_TAIL}))?
COMMENT_TAIL = ([^"*"]*("*"+[^"*""/"])?)*("*"+"/")?
END_OF_LINE_COMMENT = "/""/"[^\r\n]*

BINARY_DIGIT = [01]
//OCTAL_DIGIT = [0-7] // Not Supported by AMXX Pawn
DECIMAL_DIGIT = [0-9]
HEXADECIMAL_DIGIT = [0-9a-fA-F]

BINARY_LITERAL = 0b ( _ | {BINARY_DIGIT} )*
//OCTAL_LITERAL = 0o ( _ | {OCTAL_DIGIT} )* // Not Supported by AMXX Pawn
DECIMAL_LITERAL = {DECIMAL_DIGIT} ( _ | {DECIMAL_DIGIT} )*
HEXADECIMAL_LITERAL = 0x ( _ | {HEXADECIMAL_DIGIT} )*

RATIONAL_LITERAL = {DECIMAL_DIGIT} "." {DECIMAL_DIGIT} {RATIONAL_EXPONENT}?
RATIONAL_EXPONENT = e -? {DECIMAL_DIGIT}+

SIMPLE_PREPROCESSOR = (else|emit|endif|endinput|endscript|error|file|line|undef|defined)

PATH_REFERENCE = "<" [^\r\n]* ">"?
RELATIVE_REFERENCE = "\"" [^\r\n]* "\""?

%state MAYBE_SEMICOLON

%state PREPROCESSOR
%state DEFINE
%state PRAGMA
%state PRAGMA_CTRLCHAR
%state PRAGMA_SEMICOLON
%xstate PRAGMA_DEPRECATED_PRE
%xstate PRAGMA_DEPRECATED
%state INCLUDE

%xstate STRING
%xstate RAW_STRING
%xstate HARD_STRING

%xstate CHARACTER

%%

{WS}+                 { return ApTokenTypes.WHITE_SPACE; }
{CONT}                { return ApTokenTypes.ESCAPING_SLASH; }
{DOC_COMMENT}         { return ApTokenTypes.DOC_COMMENT; }
{C_STYLE_COMMENT}     { return ApTokenTypes.C_STYLE_COMMENT; }
{END_OF_LINE_COMMENT} { return ApTokenTypes.END_OF_LINE_COMMENT; }

<PREPROCESSOR> {
  "define"        { yybegin(DEFINE); return ApTokenTypes.DEFINE_DIRECTIVE; }
  "elseif"        { yybegin(DEFINE); return ApTokenTypes.ELSEIF_DIRECTIVE; }
  "if"            { yybegin(DEFINE); return ApTokenTypes.IF_DIRECTIVE; }

  "pragma"        { yybegin(PRAGMA); return ApTokenTypes.PRAGMA_DIRECTIVE; }
  "include"       { yybegin(INCLUDE); return ApTokenTypes.INCLUDE_DIRECTIVE; }
  "tryinclude"    { yybegin(INCLUDE); return ApTokenTypes.TRYINCLUDE_DIRECTIVE; }

  "assert"        { return ApTokenTypes.ASSERT_DIRECTIVE; }
  "defined"       { return ApTokenTypes.DEFINED_KEYWORD; }
  "else"          { return ApTokenTypes.ELSE_DIRECTIVE; }
  "emit"          { return ApTokenTypes.EMIT_DIRECTIVE; }
  "endif"         { return ApTokenTypes.ENDIF_DIRECTIVE; }
  "endinput"      { return ApTokenTypes.ENDINPUT_DIRECTIVE; }
  "endscript"     { return ApTokenTypes.ENDSCRIPT_DIRECTIVE; }
  "error"         { return ApTokenTypes.ERROR_DIRECTIVE; }
  "file"          { return ApTokenTypes.FILE_DIRECTIVE; }
  "line"          { return ApTokenTypes.LINE_DIRECTIVE; }
  "undef"         { return ApTokenTypes.UNDEF_DIRECTIVE; }
}

<DEFINE> {
  "defined"    { return ApTokenTypes.DEFINED_KEYWORD; }
}

<PRAGMA> {
  "ctrlchar"   { yybegin(PRAGMA_CTRLCHAR); return ApTokenTypes.PRAGMA_IDENTIFIER; }
  "semicolon"  { yybegin(PRAGMA_SEMICOLON); return ApTokenTypes.PRAGMA_IDENTIFIER; }
  "deprecated" { yybegin(PRAGMA_DEPRECATED_PRE); return ApTokenTypes.PRAGMA_IDENTIFIER; }
  {IDENTIFIER} { yybegin(YYINITIAL); return ApTokenTypes.PRAGMA_IDENTIFIER; }
}

<PRAGMA_CTRLCHAR> {
  // TODO: The re-reading can probably be avoided, but this is pretty rare, and below is easier to write
  {BINARY_LITERAL}      |
  {DECIMAL_LITERAL}     |
  {HEXADECIMAL_LITERAL} { sc_ctrlchar = ApParserUtils.parseCellFast(yytext(), sc_ctrlchar);
                          if (DEBUG) System.out.println("sc_ctrlchar=" + sc_ctrlchar);
                          yybegin(YYINITIAL); yypushback(yylength());
                        }
                        // TODO: support char literals
  [^]                   { yybegin(YYINITIAL); yypushback(yylength()); }
}

<PRAGMA_SEMICOLON> {
  // TODO: The re-reading can probably be avoided, but this is pretty rare, and below is easier to write
  // FIXME: This only covers values that can directly be evaluated as boolean literals, this will need to be updated to preprocess the value
  "true"                |
  "false"               |
  {BINARY_LITERAL}      |
  {DECIMAL_LITERAL}     |
  {HEXADECIMAL_LITERAL} { sc_needsemicolon = ApParserUtils.parseCellFast(yytext(), sc_ctrlchar) > 0;
                          if (DEBUG) System.out.println("sc_needsemicolon=" + sc_needsemicolon);
                          yybegin(YYINITIAL); yypushback(yylength());
                        }
  [^]                   { yybegin(YYINITIAL); yypushback(yylength()); }
}

<PRAGMA_DEPRECATED_PRE> {
  {WS}+ { yybegin(PRAGMA_DEPRECATED); return ApTokenTypes.WHITE_SPACE; }
}

<PRAGMA_DEPRECATED> {
  {CONT} {}
  {NL}   { yybegin(PREPROCESSOR); yypushback(1); return ApTokenTypes.DEPRECATION_REASON; }
  [^]    {}
}

<INCLUDE> {
  {PATH_REFERENCE}     |
  {RELATIVE_REFERENCE} { yybegin(PREPROCESSOR); return ApTokenTypes.INCLUDE_REFERENCE; }
  [^]                  { yybegin(PREPROCESSOR); yypushback(yylength()); }
}

<MAYBE_SEMICOLON> {
  {NL}  { yybegin(YYINITIAL); yypushback(yylength());
          if (!sc_needsemicolon) {
            return ApTokenTypes.SEMICOLON_SYNTHETIC;
          }
        }
  [^]   { yybegin(YYINITIAL); yypushback(yylength()); }
}

<PREPROCESSOR,DEFINE,PRAGMA,PRAGMA_CTRLCHAR,PRAGMA_SEMICOLON,PRAGMA_DEPRECATED> {
  {NL} { yybegin(YYINITIAL); yypushback(yylength()); return ApTokenTypes.SEMICOLON_SYNTHETIC; }
}

{NL} { return ApTokenTypes.WHITE_SPACE; }

<PREPROCESSOR,PRAGMA,PRAGMA_CTRLCHAR,PRAGMA_SEMICOLON,PRAGMA_DEPRECATED_PRE,PRAGMA_DEPRECATED> {
  [^] { yybegin(YYINITIAL); yypushback(yylength()); }
}

{BINARY_LITERAL}      |
{DECIMAL_LITERAL}     |
{HEXADECIMAL_LITERAL} { yybegin(MAYBE_SEMICOLON); return ApTokenTypes.CELL_LITERAL; }
{RATIONAL_LITERAL}    { yybegin(MAYBE_SEMICOLON); return ApTokenTypes.RATIONAL_LITERAL; }
"'"                   { yybegin(CHARACTER); }

"true"  { return ApTokenTypes.TRUE_KEYWORD; }
"false" { return ApTokenTypes.FALSE_KEYWORD; }

"assert"   { return ApTokenTypes.ASSERT_KEYWORD; }
"break"    { yybegin(MAYBE_SEMICOLON); return ApTokenTypes.BREAK_KEYWORD; }
"case"     { return ApTokenTypes.CASE_KEYWORD; }
"char"     { return ApTokenTypes.CHAR_KEYWORD; }
"const"    { return ApTokenTypes.CONST_KEYWORD; }
"continue" { yybegin(MAYBE_SEMICOLON); return ApTokenTypes.CONTINUE_KEYWORD; }
"default"  { return ApTokenTypes.DEFAULT_KEYWORD; }
"defined"  { return ApTokenTypes.DEFINED_KEYWORD; }
"do"       { return ApTokenTypes.DO_KEYWORD; }
"else"     { return ApTokenTypes.ELSE_KEYWORD; }
"enum"     { return ApTokenTypes.ENUM_KEYWORD; }
"exit"     { return ApTokenTypes.EXIT_KEYWORD; }
"for"      { return ApTokenTypes.FOR_KEYWORD; }
"forward"  { return ApTokenTypes.FORWARD_KEYWORD; }
"goto"     { return ApTokenTypes.GOTO_KEYWORD; }
"if"       { return ApTokenTypes.IF_KEYWORD; }
"native"   { return ApTokenTypes.NATIVE_KEYWORD; }
"new"      { return ApTokenTypes.NEW_KEYWORD; }
"operator" { return ApTokenTypes.OPERATOR_KEYWORD; }
"public"   { return ApTokenTypes.PUBLIC_KEYWORD; }
"return"   { yybegin(MAYBE_SEMICOLON); return ApTokenTypes.RETURN_KEYWORD; }
"sizeof"   { return ApTokenTypes.SIZEOF_KEYWORD; }
"sleep"    { return ApTokenTypes.SLEEP_KEYWORD; }
"state"    { return ApTokenTypes.STATE_KEYWORD; }
"static"   { return ApTokenTypes.STATIC_KEYWORD; }
"stock"    { return ApTokenTypes.STOCK_KEYWORD; }
"switch"   { return ApTokenTypes.SWITCH_KEYWORD; }
"tagof"    { return ApTokenTypes.TAGOF_KEYWORD; }
"while"    { return ApTokenTypes.WHILE_KEYWORD; }

{IDENTIFIER} { yybegin(MAYBE_SEMICOLON); return ApTokenTypes.IDENTIFIER; }

"==" { return ApTokenTypes.EQEQ; }
"!=" { return ApTokenTypes.NE; }
"||" { return ApTokenTypes.OROR; }
"++" { yybegin(MAYBE_SEMICOLON); return ApTokenTypes.PLUSPLUS; }
"--" { yybegin(MAYBE_SEMICOLON); return ApTokenTypes.MINUSMINUS; }

"<"    { return ApTokenTypes.LT; }
"<="   { return ApTokenTypes.LE; }
"<<="  { return ApTokenTypes.LTLTEQ; }
"<<"   { return ApTokenTypes.LTLT; }
">"    { return ApTokenTypes.GT; }
">="   { return ApTokenTypes.GE; } // Not defined in _JavaLexer.flex
">>="  { return ApTokenTypes.GTGTEQ; } // Not defined in _JavaLexer.flex
">>"   { return ApTokenTypes.GTGT; } // Not defined in _JavaLexer.flex
">>>=" { return ApTokenTypes.GTGTGTEQ; } // Not defined in _JavaLexer.flex
">>>"  { return ApTokenTypes.GTGTGT; } // Not defined in _JavaLexer.flex
"&"    { return ApTokenTypes.AND; }
"&&"   { return ApTokenTypes.ANDAND; }

"+=" { return ApTokenTypes.PLUSEQ; }
"-=" { return ApTokenTypes.MINUSEQ; }
"*=" { return ApTokenTypes.ASTERISKEQ; }
"/=" { return ApTokenTypes.DIVEQ; }
"&=" { return ApTokenTypes.ANDEQ; }
"|=" { return ApTokenTypes.OREQ; }
"^=" { return ApTokenTypes.XOREQ; }
"%=" { return ApTokenTypes.PERCEQ; }

"("   { return ApTokenTypes.LPARENTH; }
")"   { yybegin(MAYBE_SEMICOLON); return ApTokenTypes.RPARENTH; }
"{"   { return ApTokenTypes.LBRACE; }
"}"   { yybegin(MAYBE_SEMICOLON); return ApTokenTypes.RBRACE; }
"["   { return ApTokenTypes.LBRACKET; }
"]"   { yybegin(MAYBE_SEMICOLON); return ApTokenTypes.RBRACKET; }
","   { return ApTokenTypes.COMMA; }
"..." { return ApTokenTypes.ELLIPSIS; }
".."  { return ApTokenTypes.RANGE; }
"."   { return ApTokenTypes.DOT; }

"=" { return ApTokenTypes.EQ; }
//"!" { return ApTokenTypes.EXCL; } // Handled below in string lexing
"~" { return ApTokenTypes.TILDE; }
"?" { return ApTokenTypes.QUEST; }
":" { return ApTokenTypes.COLON; }
"+" { return ApTokenTypes.PLUS; }
"-" { return ApTokenTypes.MINUS; }
"*" { return ApTokenTypes.ASTERISK; }
"/" { return ApTokenTypes.DIV; }
"|" { return ApTokenTypes.OR; }
"^" { return ApTokenTypes.XOR; }
"%" { return ApTokenTypes.PERC; }
"@" { return ApTokenTypes.AT; }
";" { return ApTokenTypes.SEMICOLON; }
"#" { yybegin(PREPROCESSOR); return ApTokenTypes.HASH; }

"_"  { return ApTokenTypes.UNDER; }
"::" { return ApTokenTypes.DOUBLE_COLON; }

"\""       { yybegin(STRING); packed = false; }
"!\""      { yybegin(STRING); packed = true; }
"!" . "\"" { if (yycharat(1) == sc_ctrlchar) {
               yybegin(RAW_STRING);
               packed = true;
             } else {
               yypushback(2);
               return ApTokenTypes.EXCL;
             }
           }
"!"        { return ApTokenTypes.EXCL; } // Handled below in string lexing

[^] { if (yycharat(0) == sc_ctrlchar) {
        yybegin(HARD_STRING);
      } else {
        return ApTokenTypes.BAD_CHARACTER;
      }
    }

<STRING> {
  "\""             { yybegin(MAYBE_SEMICOLON); return packed ? ApTokenTypes.PACKED_STRING_LITERAL : ApTokenTypes.STRING_LITERAL; }
  {CONT}           {}
  [^\"\r\n][^\r\n] { if (yycharat(0) != sc_ctrlchar) {
                       yypushback(1);
                     }
                   }
  [^\r\n]          {}
  [^]              { yybegin(MAYBE_SEMICOLON); yypushback(1); return packed ? ApTokenTypes.PACKED_STRING_LITERAL : ApTokenTypes.STRING_LITERAL; }
  <<EOF>>          { yybegin(MAYBE_SEMICOLON); return packed ? ApTokenTypes.PACKED_STRING_LITERAL : ApTokenTypes.STRING_LITERAL; }
}

<RAW_STRING> {
  "\""      { yybegin(MAYBE_SEMICOLON); return packed ? ApTokenTypes.PACKED_RAW_STRING_LITERAL : ApTokenTypes.RAW_STRING_LITERAL; }
  {CONT}    |
  [^\r\n]   {}
  [^]       { yybegin(MAYBE_SEMICOLON); return packed ? ApTokenTypes.PACKED_RAW_STRING_LITERAL : ApTokenTypes.RAW_STRING_LITERAL; }
  <<EOF>>   { yybegin(MAYBE_SEMICOLON); return packed ? ApTokenTypes.PACKED_RAW_STRING_LITERAL : ApTokenTypes.RAW_STRING_LITERAL; }
}

<HARD_STRING> {
  "!" "\"" { yybegin(RAW_STRING); packed = true; }
  "\""     { yybegin(RAW_STRING); packed = false; }
  [^]      { yybegin(YYINITIAL); yypushback(yylength()); return ApTokenTypes.BAD_CHARACTER; }
}

<CHARACTER> {
  "'"              { yybegin(MAYBE_SEMICOLON); return ApTokenTypes.CHARACTER_LITERAL; }
  {CONT}           {}
  [^\'\r\n][^\r\n] { if (yycharat(0) != sc_ctrlchar) {
                       yypushback(1);
                     }
                   }
  [^\r\n]          {}
  [^]              { yybegin(MAYBE_SEMICOLON); yypushback(1); return ApTokenTypes.CHARACTER_LITERAL; }
  <<EOF>>          { yybegin(MAYBE_SEMICOLON); return ApTokenTypes.CHARACTER_LITERAL; }
}
