package net.alliedmods.lang.amxxpawn.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

%%

%unicode
%class _ApLexer
%implements FlexLexer
%function advance
%type IElementType
//%debug

%eof{  return;
%eof}

%{
    public _ApLexer() {
      this((java.io.Reader) null);
    }
%}

WHITE_SPACE         = [\ \t\f]
NEW_LINE            = \r|\n|\r\n

ALPHA               = [_@a-zA-Z]
ALPHA_NUM           = [_@a-zA-Z0-9]
IDENTIFIER          = ([_@]{ALPHA_NUM}+) | ([a-zA-Z]{ALPHA_NUM}*)

COMMENT_TAIL        = ([^"*"]*("*"+[^"*""/"])?)*("*"+"/")?
C_STYLE_COMMENT     = ("/*"[^"*"]{COMMENT_TAIL})|"/*"
DOC_COMMENT         = "/*""*"+("/"|([^"/""*"]{COMMENT_TAIL}))?
END_OF_LINE_COMMENT = "/""/"[^\r\n]*

BINARY_DIGIT        = [01]
//OCTAL_DIGIT       = [0-7]
DECIMAL_DIGIT       = [0-9]
HEXADECIMAL_DIGIT   = [0-9a-fA-F]

BINARY_LITERAL      = 0b ( _ | {BINARY_DIGIT} )*
//OCTAL_LITERAL     = 0o ( _ | {OCTAL_DIGIT} )* // Not Supported by AMXX Pawn
DECIMAL_LITERAL     = {DECIMAL_DIGIT} ( _ | {DECIMAL_DIGIT} )*
HEXADECIMAL_LITERAL = 0x ( _ | {HEXADECIMAL_DIGIT} )*

RATIONAL_LITERAL    = {DECIMAL_DIGIT} "." {DECIMAL_DIGIT} {RATIONAL_EXPONENT}?
RATIONAL_EXPONENT   = e -? {DECIMAL_DIGIT}+

CHARACTER_LITERAL   ="'"([^\\\'\r\n]|{ESCAPE_SEQUENCE})*("'"|\\)?
STRING_LITERAL      =\"([^\\\"\r\n]|{ESCAPE_SEQUENCE})*(\"|\\)?
ESCAPE_SEQUENCE     =\\[^\r\n]

SIMPLE_PRE_KEYWORD=(else|emit|endif|endinput|endscript|error|file|include|line|tryinclude|undef|defined)

%state PRE
%state PRAGMA
%state DEFINE
%state DEFINE_CONTINUATION
%state CONTINUATION

%%

<YYINITIAL> {
  {NEW_LINE}          { return ApTokenTypes.NEW_LINE; }
  {WHITE_SPACE}+      { return ApTokenTypes.WHITE_SPACE; }
}

{C_STYLE_COMMENT}     { return ApTokenTypes.C_STYLE_COMMENT; }
{DOC_COMMENT}         { return ApTokenTypes.DOC_COMMENT; }
{END_OF_LINE_COMMENT} { return ApTokenTypes.END_OF_LINE_COMMENT; }

{BINARY_LITERAL}      { return ApTokenTypes.NUMERIC_LITERAL; }
{DECIMAL_LITERAL}     { return ApTokenTypes.NUMERIC_LITERAL; }
{HEXADECIMAL_LITERAL} { return ApTokenTypes.NUMERIC_LITERAL; }
{RATIONAL_LITERAL}    { return ApTokenTypes.RATIONAL_LITERAL; }

{CHARACTER_LITERAL}   { return ApTokenTypes.CHARACTER_LITERAL; }
{STRING_LITERAL}      { return ApTokenTypes.STRING_LITERAL; }

<YYINITIAL> "#"       { yybegin(PRE); return ApTokenTypes.PRE_KEYWORD; }

<PRE,DEFINE> {SIMPLE_PRE_KEYWORD}  { return ApTokenTypes.PRE_KEYWORD; }
<PRE> "define" | "if" | "elseif"   { yybegin(DEFINE); return ApTokenTypes.PRE_KEYWORD; }
<PRE> "pragma"                     { yybegin(PRAGMA); return ApTokenTypes.PRE_KEYWORD; }
<PRAGMA> {IDENTIFIER}              { return ApTokenTypes.PRE_KEYWORD; }

<PRE,DEFINE,PRAGMA> {WHITE_SPACE}+ { return ApTokenTypes.WHITE_SPACE; }
<DEFINE> {NEW_LINE}                { yybegin(YYINITIAL); yypushback(yylength()); }
<DEFINE> "\\" {NEW_LINE}           { yybegin(DEFINE_CONTINUATION); yypushback(yylength()); }
<DEFINE_CONTINUATION> "\\"         { return ApTokenTypes.PRE_KEYWORD; }
<DEFINE_CONTINUATION> {NEW_LINE}   { yybegin(DEFINE); return ApTokenTypes.WHITE_SPACE; }
<PRE, PRAGMA> {NEW_LINE}           { yybegin(YYINITIAL); yypushback(yylength()); }

// OPERATORS
";"                   { return ApTokenTypes.TERM; }
"+"                   { return ApTokenTypes.PLUS; }
"-"                   { return ApTokenTypes.MINUS; }
"*"                   { return ApTokenTypes.MULT; }
"/"                   { return ApTokenTypes.DIV; }
"%"                   { return ApTokenTypes.PERC; }
">"                   { return ApTokenTypes.GT; }
"<"                   { return ApTokenTypes.LT; }
"!"                   { return ApTokenTypes.EXCL; }
"~"                   { return ApTokenTypes.TILDE; }
"="                   { return ApTokenTypes.EQ; }
":"                   { return ApTokenTypes.COLON; }
","                   { return ApTokenTypes.COMMA; }
"."                   { return ApTokenTypes.DOT; }
"_"                   { return ApTokenTypes.UNDERSCORE; }
"&"                   { return ApTokenTypes.AND; }
"^"                   { return ApTokenTypes.XOR; }
"|"                   { return ApTokenTypes.OR; }
"?"                   { return ApTokenTypes.QUESTION; }

"{"                   { return ApTokenTypes.LBRACE; }
"}"                   { return ApTokenTypes.RBRACE; }
"["                   { return ApTokenTypes.LBRACKET; }
"]"                   { return ApTokenTypes.RBRACKET; }
"("                   { return ApTokenTypes.LPAREN; }
")"                   { return ApTokenTypes.RPAREN; }

"*="                  { return ApTokenTypes.MULTEQ; }
"/="                  { return ApTokenTypes.DIVEQ; }
"%="                  { return ApTokenTypes.PERCEQ; }
"+="                  { return ApTokenTypes.PLUSEQ; }
"-="                  { return ApTokenTypes.MINUSEQ; }
"<<="                 { return ApTokenTypes.LTLTEQ; }
">>>="                { return ApTokenTypes.GTGTGTEQ; }
">>="                 { return ApTokenTypes.GTGTEQ; }
"&="                  { return ApTokenTypes.ANDEQ; }
"^="                  { return ApTokenTypes.XOREQ; }
"|="                  { return ApTokenTypes.OREQ; }
"||"                  { return ApTokenTypes.OROR; }
"&&"                  { return ApTokenTypes.ANDAND; }
"=="                  { return ApTokenTypes.EQEQ; }
"!="                  { return ApTokenTypes.NE; }
"<="                  { return ApTokenTypes.LE; }
">="                  { return ApTokenTypes.GE; }
"<<"                  { return ApTokenTypes.LTLT; }
">>>"                 { return ApTokenTypes.GTGTGT; }
">>"                  { return ApTokenTypes.GTGT; }
"++"                  { return ApTokenTypes.PLUSPLUS; }
"--"                  { return ApTokenTypes.MINUSMINUS; }
"..."                 { return ApTokenTypes.DOTDOTDOT; }
".."                  { return ApTokenTypes.DOTDOT; }
"::"                  { return ApTokenTypes.QUAL; }
";"                   { return ApTokenTypes.TERM; }

// BOOLEAN LITERALS
"true"                { return ApTokenTypes.TRUE; }
"false"               { return ApTokenTypes.FALSE; }

// KEYWORDS
"assert"              { return ApTokenTypes.ASSERT; }
"break"               { return ApTokenTypes.BREAK; }
"case"                { return ApTokenTypes.CASE; }
"char"                { return ApTokenTypes.CHAR; }
"const"               { return ApTokenTypes.CONST; }
"continue"            { return ApTokenTypes.CONTINUE; }
"default"             { return ApTokenTypes.DEFAULT; }
"defined"             { return ApTokenTypes.DEFINED; }
"do"                  { return ApTokenTypes.DO; }
"else"                { return ApTokenTypes.ELSE; }
"enum"                { return ApTokenTypes.ENUM; }
"exit"                { return ApTokenTypes.EXIT; }
"for"                 { return ApTokenTypes.FOR; }
"forward"             { return ApTokenTypes.FORWARD; }
"goto"                { return ApTokenTypes.GOTO; }
"if"                  { return ApTokenTypes.IF; }
"native"              { return ApTokenTypes.NATIVE; }
"new"                 { return ApTokenTypes.NEW; }
"operator"            { return ApTokenTypes.OPERATOR; }
"public"              { return ApTokenTypes.PUBLIC; }
"return"              { return ApTokenTypes.RETURN; }
"sizeof"              { return ApTokenTypes.SIZEOF; }
"sleep"               { return ApTokenTypes.SLEEP; }
"state"               { return ApTokenTypes.STATE; }
"static"              { return ApTokenTypes.STATIC; }
"stock"               { return ApTokenTypes.STOCK; }
"switch"              { return ApTokenTypes.SWITCH; }
"tagof"               { return ApTokenTypes.TAGOF; }
"while"               { return ApTokenTypes.WHILE; }

<YYINITIAL> "\\" {NEW_LINE} { yybegin(CONTINUATION); yypushback(yylength()); }
<CONTINUATION> "\\"         { yybegin(YYINITIAL); return ApTokenTypes.ESCAPING_SLASH; }

{IDENTIFIER}          { return ApTokenTypes.IDENTIFIER; }

<PRE,DEFINE,PRAGMA> [^] { yybegin(YYINITIAL); yypushback(yylength()); }
<YYINITIAL> [^]         { return ApTokenTypes.BAD_CHARACTER; }
