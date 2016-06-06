package net.alliedmods.intellij.sourcepawn.lexer;
import com.intellij.lexer.*;
import com.intellij.psi.tree.IElementType;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.*;

%%

%{
  public _SourcePawnLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _SourcePawnLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL="\r"|"\n"|"\r\n"
LINE_WS=[\ \t\f]
WHITE_SPACE=({LINE_WS}|{EOL})+

LINE_WS=[\ \t\f]+
EOL=(\r|\n|\r\n)
LINE_COMMENT="//".*
BLOCK_COMMENT="/"\*[^]*\*"/"
BINARY_LITERAL=0b(0|1|_)*
DECIMAL_LITERAL=[0-9][_0-9]*
HEXADECIMAL_LITERAL=0x[_0-9a-fA-F]*
RATIONAL_LITERAL=[0-9][_0-9]*\.[0-9][_0-9]*(e-?[0-9]+)?
CHARACTER_STRING='.+?'
STRING_LITERAL=\"([^\"\\]|\\.)*\"
PREPROCESSOR_DIRECTIVE=#(([^\\\r\n])|(\\\h*(\r|\n|\r\n)))*(\r|\n|\r\n)
IDENTIFIER=([a-zA-Z][_@a-zA-Z0-9]*)|([_@][_@a-zA-Z0-9]+)

%%
<YYINITIAL> {
  {WHITE_SPACE}                 { return com.intellij.psi.TokenType.WHITE_SPACE; }

  "+"                           { return PLUS; }
  "-"                           { return MINUS; }
  "*"                           { return ASTERISK; }
  "/"                           { return SLASH; }
  "%"                           { return PERCENT; }
  "!"                           { return EXCLAMATION; }
  "~"                           { return TILDE; }
  "="                           { return ASSIGN; }
  "&"                           { return AMPERSAND; }
  "^"                           { return CARET; }
  "|"                           { return VERTICAL_BAR; }
  "."                           { return PERIOD; }
  ","                           { return COMMA; }
  ";"                           { return SEMICOLON; }
  "["                           { return LBRACKET; }
  "]"                           { return RBRACKET; }
  "{"                           { return LBRACE; }
  "}"                           { return RBRACE; }
  "("                           { return LPAREN; }
  ")"                           { return RPAREN; }
  "<"                           { return LT; }
  ">"                           { return GT; }
  "*="                          { return MULEQ; }
  "/="                          { return DIVEQ; }
  "%="                          { return MODEQ; }
  "+="                          { return ADDEQ; }
  "-="                          { return SUBEQ; }
  "<<="                         { return SLEQ; }
  ">>>="                        { return SRLEQ; }
  ">>="                         { return SRAEQ; }
  "&="                          { return ANDEQ; }
  "^="                          { return XOREQ; }
  "|="                          { return OREQ; }
  "||"                          { return OR; }
  "&&"                          { return AND; }
  "=="                          { return EQUALTO; }
  "!="                          { return NEQUALTO; }
  "<="                          { return LTEQ; }
  ">="                          { return GTEQ; }
  "<<"                          { return SL; }
  ">>>"                         { return SRL; }
  ">>"                          { return SRA; }
  "++"                          { return INCREMENT; }
  "--"                          { return DECREMENT; }
  "..."                         { return ELLIPSIS; }
  ".."                          { return RANGE; }
  "::"                          { return SCOPE_RESOLUTION; }
  "acquire"                     { return ACQUIRE; }
  "as"                          { return AS; }
  "assert"                      { return ASSERT; }
  "break"                       { return BREAK; }
  "builtin"                     { return BUILTIN; }
  "catch"                       { return CATCH; }
  "case"                        { return CASE; }
  "cast_to"                     { return CAST_TO; }
  "cellsof"                     { return CELLSOF; }
  "char"                        { return CHAR; }
  "const"                       { return CONST; }
  "continue"                    { return CONTINUE; }
  "decl"                        { return DECL; }
  "default"                     { return DEFAULT; }
  "defined"                     { return DEFINED; }
  "delete"                      { return DELETE; }
  "do"                          { return DO; }
  "double"                      { return DOUBLE; }
  "else"                        { return ELSE; }
  "enum"                        { return ENUM; }
  "exit"                        { return EXIT; }
  "explicit"                    { return EXPLICIT; }
  "finally"                     { return FINALLY; }
  "for"                         { return FOR; }
  "foreach"                     { return FOREACH; }
  "forward"                     { return FORWARD; }
  "funcenum"                    { return FUNCENUM; }
  "functag"                     { return FUNCTAG; }
  "function"                    { return FUNCTION; }
  "goto"                        { return GOTO; }
  "if"                          { return IF; }
  "implicit"                    { return IMPLICIT; }
  "import"                      { return IMPORT; }
  "in"                          { return IN; }
  "int"                         { return INT; }
  "int8"                        { return INT8; }
  "int16"                       { return INT16; }
  "int32"                       { return INT32; }
  "int64"                       { return INT64; }
  "interface"                   { return INTERFACE; }
  "intn"                        { return INTN; }
  "let"                         { return LET; }
  "methodmap"                   { return METHODMAP; }
  "namespace"                   { return NAMESPACE; }
  "native"                      { return NATIVE; }
  "new"                         { return NEW; }
  "null"                        { return NULL; }
  "__nullable__"                { return NULLABLE; }
  "object"                      { return OBJECT; }
  "operator"                    { return OPERATOR; }
  "package"                     { return PACKAGE; }
  "private"                     { return PRIVATE; }
  "protected"                   { return PROTECTED; }
  "public"                      { return PUBLIC; }
  "readonly"                    { return READONLY; }
  "return"                      { return RETURN; }
  "sealed"                      { return SEALED; }
  "sizeof"                      { return SIZEOF; }
  "sleep"                       { return SLEEP; }
  "static"                      { return STATIC; }
  "stock"                       { return STOCK; }
  "struct"                      { return STRUCT; }
  "switch"                      { return SWITCH; }
  "tagof"                       { return TAGOF; }
  "this"                        { return THIS; }
  "throw"                       { return THROW; }
  "try"                         { return TRY; }
  "typedef"                     { return TYPEDEF; }
  "typeof"                      { return TYPEOF; }
  "typeset"                     { return TYPESET; }
  "uint8"                       { return UINT8; }
  "uint16"                      { return UINT16; }
  "uint32"                      { return UINT32; }
  "uint64"                      { return UINT64; }
  "uintn"                       { return UINTN; }
  "union"                       { return UNION; }
  "using"                       { return USING; }
  "var"                         { return VAR; }
  "variant"                     { return VARIANT; }
  "view_as"                     { return VIEW_AS; }
  "virtual"                     { return VIRTUAL; }
  "void"                        { return VOID; }
  "volatile"                    { return VOLATILE; }
  "while"                       { return WHILE; }
  "with"                        { return WITH; }
  "true"                        { return TRUE; }
  "false"                       { return FALSE; }
  "EOS"                         { return EOS; }
  "INVALID_FUNCTION"            { return INVALID_FUNCTION; }
  "cellbits"                    { return CELLBITS; }
  "cellmin"                     { return CELLMIN; }
  "cellmax"                     { return CELLMAX; }
  "charbits"                    { return CHARBITS; }
  "charmin"                     { return CHARMIN; }
  "charmax"                     { return CHARMAX; }
  "ucharmax"                    { return UCHARMAX; }
  "__Pawn"                      { return SP_VERSION; }
  "debug"                       { return DEBUG; }
  "#"                           { return HASH; }
  "pragma"                      { return PRAGMA; }
  "semicolon"                   { return PRAGMA_SEMICOLON; }
  "ctrlchar"                    { return PRAGMA_CTRLCHAR; }
  "deprecated"                  { return PRAGMA_DEPRECATED; }

  {LINE_WS}                     { return LINE_WS; }
  {EOL}                         { return EOL; }
  {LINE_COMMENT}                { return LINE_COMMENT; }
  {BLOCK_COMMENT}               { return BLOCK_COMMENT; }
  {BINARY_LITERAL}              { return BINARY_LITERAL; }
  {DECIMAL_LITERAL}             { return DECIMAL_LITERAL; }
  {HEXADECIMAL_LITERAL}         { return HEXADECIMAL_LITERAL; }
  {RATIONAL_LITERAL}            { return RATIONAL_LITERAL; }
  {CHARACTER_STRING}            { return CHARACTER_STRING; }
  {STRING_LITERAL}              { return STRING_LITERAL; }
  {PREPROCESSOR_DIRECTIVE}      { return PREPROCESSOR_DIRECTIVE; }
  {IDENTIFIER}                  { return IDENTIFIER; }

  [^] { return com.intellij.psi.TokenType.BAD_CHARACTER; }
}
