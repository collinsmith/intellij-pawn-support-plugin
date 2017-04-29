package net.alliedmods.lang.amxxpawn.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.parser.ApParserUtils;
import net.alliedmods.lang.amxxpawn.psi.ApTokenType;

@SuppressWarnings({"ALL"})
%%

%{
    private static final boolean DEBUG = true;

    public int sc_ctrlchar = '^';
    public boolean sc_needsemicolon = false;

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

SIMPLE_PREPROCESSOR = (else|emit|endif|endinput|endscript|error|file|include|line|tryinclude|undef|defined)

%state MAYBE_SEMICOLON

%state PREPROCESSOR
%state DEFINE
%state PRAGMA
%state PRAGMA_CTRLCHAR
%state PRAGMA_SEMICOLON

%xstate STRING
%xstate RAW_STRING
%xstate HARD_STRING

%xstate CHARACTER

%%

{WS}+                 { return ApTokenType.WHITE_SPACE; }
{CONT}                { return ApTokenType.ESCAPING_SLASH; }
{DOC_COMMENT}         { return ApTokenType.DOC_COMMENT; }
{C_STYLE_COMMENT}     { return ApTokenType.C_STYLE_COMMENT; }
{END_OF_LINE_COMMENT} { return ApTokenType.END_OF_LINE_COMMENT; }

<PREPROCESSOR> {
  "if" | "elseif" |
  "define"        { yybegin(DEFINE); return ApTokenType.PREPROCESSOR; }
  "pragma"        { yybegin(PRAGMA); return ApTokenType.PREPROCESSOR; }
}

<PREPROCESSOR,DEFINE> {
  {SIMPLE_PREPROCESSOR} { return ApTokenType.PREPROCESSOR; }
}

<PRAGMA> {
  "ctrlchar"   { yybegin(PRAGMA_CTRLCHAR); return ApTokenType.PREPROCESSOR; }
  "semicolon"  { yybegin(PRAGMA_SEMICOLON); return ApTokenType.PREPROCESSOR; }
  {IDENTIFIER} { return ApTokenType.PREPROCESSOR; }
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

<MAYBE_SEMICOLON> {
  {NL}  { yybegin(YYINITIAL); yypushback(yylength());
          if (!sc_needsemicolon) {
            return ApTokenType.SEMICOLON_SYNTHETIC;
          }
        }
  [^]   { yybegin(YYINITIAL); yypushback(yylength()); }
}

<PREPROCESSOR,DEFINE,PRAGMA,PRAGMA_CTRLCHAR,PRAGMA_SEMICOLON> {
  {NL} { yybegin(YYINITIAL); yypushback(yylength()); }
}

{NL} { return ApTokenType.NEW_LINE; }

<PREPROCESSOR,PRAGMA,PRAGMA_CTRLCHAR,PRAGMA_SEMICOLON> {
  [^] { yybegin(YYINITIAL); yypushback(yylength()); }
}

{BINARY_LITERAL}      |
{DECIMAL_LITERAL}     |
{HEXADECIMAL_LITERAL} { yybegin(MAYBE_SEMICOLON); return ApTokenType.CELL_LITERAL; }
{RATIONAL_LITERAL}    { yybegin(MAYBE_SEMICOLON); return ApTokenType.RATIONAL_LITERAL; }
"'"                   { yybegin(CHARACTER); }

"true"  { return ApTokenType.TRUE_KEYWORD; }
"false" { return ApTokenType.FALSE_KEYWORD; }

"assert"   { return ApTokenType.ASSERT_KEYWORD; }
"break"    { yybegin(MAYBE_SEMICOLON); return ApTokenType.BREAK_KEYWORD; }
"case"     { return ApTokenType.CASE_KEYWORD; }
"char"     { return ApTokenType.CHAR_KEYWORD; }
"const"    { return ApTokenType.CONST_KEYWORD; }
"continue" { yybegin(MAYBE_SEMICOLON); return ApTokenType.CONTINUE_KEYWORD; }
"default"  { return ApTokenType.DEFAULT_KEYWORD; }
"defined"  { return ApTokenType.DEFINED_KEYWORD; }
"do"       { return ApTokenType.DO_KEYWORD; }
"else"     { return ApTokenType.ELSE_KEYWORD; }
"enum"     { return ApTokenType.ENUM_KEYWORD; }
"exit"     { return ApTokenType.EXIT_KEYWORD; }
"for"      { return ApTokenType.FOR_KEYWORD; }
"forward"  { return ApTokenType.FORWARD_KEYWORD; }
"goto"     { return ApTokenType.GOTO_KEYWORD; }
"if"       { return ApTokenType.IF_KEYWORD; }
"native"   { return ApTokenType.NATIVE_KEYWORD; }
"new"      { return ApTokenType.NEW_KEYWORD; }
"operator" { return ApTokenType.OPERATOR_KEYWORD; }
"public"   { return ApTokenType.PUBLIC_KEYWORD; }
"return"   { yybegin(MAYBE_SEMICOLON); return ApTokenType.RETURN_KEYWORD; }
"sizeof"   { return ApTokenType.SIZEOF_KEYWORD; }
"sleep"    { return ApTokenType.SLEEP_KEYWORD; }
"state"    { return ApTokenType.STATE_KEYWORD; }
"static"   { return ApTokenType.STATIC_KEYWORD; }
"stock"    { return ApTokenType.STOCK_KEYWORD; }
"switch"   { return ApTokenType.SWITCH_KEYWORD; }
"tagof"    { return ApTokenType.TAGOF_KEYWORD; }
"while"    { return ApTokenType.WHILE_KEYWORD; }

{IDENTIFIER} { yybegin(MAYBE_SEMICOLON); return ApTokenType.IDENTIFIER; }

"==" { return ApTokenType.EQEQ; }
"!=" { return ApTokenType.NE; }
"||" { return ApTokenType.OROR; }
"++" { yybegin(MAYBE_SEMICOLON); return ApTokenType.PLUSPLUS; }
"--" { yybegin(MAYBE_SEMICOLON); return ApTokenType.MINUSMINUS; }

"<"    { return ApTokenType.LT; }
"<="   { return ApTokenType.LE; }
"<<="  { return ApTokenType.LTLTEQ; }
"<<"   { return ApTokenType.LTLT; }
">"    { return ApTokenType.GT; }
">="   { return ApTokenType.GE; } // Not defined in _JavaLexer.flex
">>="  { return ApTokenType.GTGTEQ; } // Not defined in _JavaLexer.flex
">>"   { return ApTokenType.GTGT; } // Not defined in _JavaLexer.flex
">>>=" { return ApTokenType.GTGTGTEQ; } // Not defined in _JavaLexer.flex
">>>"  { return ApTokenType.GTGTGT; } // Not defined in _JavaLexer.flex
"&"    { return ApTokenType.AND; }
"&&"   { return ApTokenType.ANDAND; }

"+=" { return ApTokenType.PLUSEQ; }
"-=" { return ApTokenType.MINUSEQ; }
"*=" { return ApTokenType.ASTERISKEQ; }
"/=" { return ApTokenType.DIVEQ; }
"&=" { return ApTokenType.ANDEQ; }
"|=" { return ApTokenType.OREQ; }
"^=" { return ApTokenType.XOREQ; }
"%=" { return ApTokenType.PERCEQ; }

"("   { return ApTokenType.LPARENTH; }
")"   { yybegin(MAYBE_SEMICOLON); return ApTokenType.RPARENTH; }
"{"   { return ApTokenType.LBRACE; }
"}"   { yybegin(MAYBE_SEMICOLON); return ApTokenType.RBRACE; }
"["   { return ApTokenType.LBRACKET; }
"]"   { yybegin(MAYBE_SEMICOLON); return ApTokenType.RBRACKET; }
","   { return ApTokenType.COMMA; }
"..." { return ApTokenType.ELLIPSIS; }
".."  { return ApTokenType.RANGE; }
"."   { return ApTokenType.DOT; }

"=" { return ApTokenType.EQ; }
//"!" { return ApTokenType.EXCL; } // Handled below in string lexing
"~" { return ApTokenType.TILDE; }
"?" { return ApTokenType.QUEST; }
":" { return ApTokenType.COLON; }
"+" { return ApTokenType.PLUS; }
"-" { return ApTokenType.MINUS; }
"*" { return ApTokenType.ASTERISK; }
"/" { return ApTokenType.DIV; }
"|" { return ApTokenType.OR; }
"^" { return ApTokenType.XOR; }
"%" { return ApTokenType.PERC; }
"@" { return ApTokenType.AT; }
";" { return ApTokenType.SEMICOLON; }
"#" { yybegin(PREPROCESSOR); }

"_"  { return ApTokenType.UNDER; }
"::" { return ApTokenType.DOUBLE_COLON; }

"\""       { yybegin(STRING); }
"!\""      { yybegin(STRING); }
"!" . "\"" { if (yycharat(1) == sc_ctrlchar) {
               yybegin(RAW_STRING);
             } else {
               yypushback(2);
               return ApTokenType.EXCL;
             }
           }
"!"        { return ApTokenType.EXCL; } // Handled below in string lexing

[^] { if (yycharat(0) == sc_ctrlchar) {
        yybegin(HARD_STRING);
      } else {
        return ApTokenType.BAD_CHARACTER;
      }
    }

<STRING> {
  "\""             { yybegin(MAYBE_SEMICOLON); return ApTokenType.STRING_LITERAL; }
  {CONT}           {}
  [^\"\r\n][^\r\n] { if (yycharat(0) != sc_ctrlchar) {
                       yypushback(1);
                     }
                   }
  [^\r\n]          {}
  [^]              { yybegin(MAYBE_SEMICOLON); yypushback(1); return ApTokenType.STRING_LITERAL; }
  <<EOF>>          { yybegin(MAYBE_SEMICOLON); return ApTokenType.STRING_LITERAL; }
}

<RAW_STRING> {
  "\""      { yybegin(MAYBE_SEMICOLON); return ApTokenType.RAW_STRING_LITERAL; }
  {CONT}    |
  [^\r\n]   {}
  [^]       { yybegin(MAYBE_SEMICOLON); return ApTokenType.RAW_STRING_LITERAL; }
  <<EOF>>   { yybegin(MAYBE_SEMICOLON); return ApTokenType.RAW_STRING_LITERAL; }
}

<HARD_STRING> {
  "!"? "\"" { yybegin(RAW_STRING); }
  [^]       { yybegin(YYINITIAL); yypushback(yylength()); return ApTokenType.BAD_CHARACTER; }
}

<CHARACTER> {
  "'"              { yybegin(MAYBE_SEMICOLON); return ApTokenType.CHARACTER_LITERAL; }
  {CONT}           {}
  [^\'\r\n][^\r\n] { if (yycharat(0) != sc_ctrlchar) {
                       yypushback(1);
                     }
                   }
  [^\r\n]          {}
  [^]              { yybegin(MAYBE_SEMICOLON); yypushback(1); return ApTokenType.CHARACTER_LITERAL; }
  <<EOF>>          { yybegin(MAYBE_SEMICOLON); return ApTokenType.CHARACTER_LITERAL; }
}
