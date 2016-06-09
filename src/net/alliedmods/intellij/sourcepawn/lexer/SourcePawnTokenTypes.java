package net.alliedmods.intellij.sourcepawn.lexer;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.intellij.sourcepawn.psi.SourcePawnTokenType;

public class SourcePawnTokenTypes {

  private SourcePawnTokenTypes() {
  }

  // GENERIC
  public static IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
  public static IElementType WHITESPACE = TokenType.WHITE_SPACE;
  public static IElementType NEW_LINE = new SourcePawnTokenType("EOL");

  // PREPROCESSOR DIRECTIVES
  public static IElementType PREPROCESSOR_ASSERT = new SourcePawnTokenType("assert");
  public static IElementType PREPROCESSOR_DEFINE = new SourcePawnTokenType("define");
  public static IElementType PREPROCESSOR_ELSE = new SourcePawnTokenType("else");
  public static IElementType PREPROCESSOR_ELSEIF = new SourcePawnTokenType("elseif");
  public static IElementType PREPROCESSOR_ENDIF = new SourcePawnTokenType("endif");
  public static IElementType PREPROCESSOR_ENDINPUT = new SourcePawnTokenType("endinput");
  public static IElementType PREPROCESSOR_ENDSCRIPT = new SourcePawnTokenType("endscript");
  public static IElementType PREPROCESSOR_ERROR = new SourcePawnTokenType("error");
  public static IElementType PREPROCESSOR_FILE = new SourcePawnTokenType("file");
  public static IElementType PREPROCESSOR_IF = new SourcePawnTokenType("if");
  public static IElementType PREPROCESSOR_INCLUDE = new SourcePawnTokenType("include");
  public static IElementType PREPROCESSOR_LINE = new SourcePawnTokenType("line");
  public static IElementType PREPROCESSOR_PRAGMA = new SourcePawnTokenType("pragma");
  public static IElementType PREPROCESSOR_TRYINCLUDE = new SourcePawnTokenType("tryinclude");
  public static IElementType PREPROCESSOR_UNDEF = new SourcePawnTokenType("undef");

  // INCLUDE FILE FORMATS
  public static IElementType PREPROCESSOR_INCLUDE_SYSTEMFILE = new SourcePawnTokenType("-system file-");
  public static IElementType PREPROCESSOR_INCLUDE_RELATIVEFILE = new SourcePawnTokenType("-relative file-");

  // PRAGMA DIRECTIVES
  public static IElementType PRAGMA_CODEPAGE = new SourcePawnTokenType("codepage");
  public static IElementType PRAGMA_CTRLCHAR = new SourcePawnTokenType("ctrlchar");
  public static IElementType PRAGMA_DEPRECATED = new SourcePawnTokenType("deprecated");
  public static IElementType PRAGMA_DEPRECATED_STRING = new SourcePawnTokenType("PRAGMA_DEPRECATED_STRING");
  public static IElementType PRAGMA_DYNAMIC = new SourcePawnTokenType("dynamic");
  public static IElementType PRAGMA_RATIONAL = new SourcePawnTokenType("rational");
  public static IElementType PRAGMA_SEMICOLON = new SourcePawnTokenType("semicolon");
  public static IElementType PRAGMA_NEWDECLS = new SourcePawnTokenType("newdecls");
  public static IElementType PRAGMA_TABSIZE = new SourcePawnTokenType("tabsize");
  public static IElementType PRAGMA_UNUSED = new SourcePawnTokenType("unused");
  
  // PRAGMA NEWDECLS DIRECTIVES
  public static IElementType PRAGMA_NEWDECLS_REQUIRED = new SourcePawnTokenType("required");
  public static IElementType PRAGMA_NEWDECLS_OPTIONAL = new SourcePawnTokenType("optional");

  // OPERATORS
  public static IElementType AMPERSAND = new SourcePawnTokenType("&");
  public static IElementType ASSIGN = new SourcePawnTokenType("=");
  public static IElementType ASTERISK = new SourcePawnTokenType("*");
  public static IElementType AT_SIGN = new SourcePawnTokenType("@");
  public static IElementType CARET = new SourcePawnTokenType("^");
  public static IElementType COLON = new SourcePawnTokenType(":");
  public static IElementType COMMA = new SourcePawnTokenType(",");
  public static IElementType EXCLAMATION = new SourcePawnTokenType("!");
  public static IElementType HASH = new SourcePawnTokenType("#");
  public static IElementType MINUS = new SourcePawnTokenType("-");
  public static IElementType PERCENT = new SourcePawnTokenType("%");
  public static IElementType PERIOD = new SourcePawnTokenType(".");
  public static IElementType PLUS = new SourcePawnTokenType("+");
  public static IElementType SEMICOLON = new SourcePawnTokenType(";");
  public static IElementType SLASH = new SourcePawnTokenType("/");
  public static IElementType TILDE = new SourcePawnTokenType("~");
  public static IElementType UNDERSCORE = new SourcePawnTokenType("_");
  public static IElementType VERTICAL_BAR = new SourcePawnTokenType("|");

  public static IElementType ADDEQ = new SourcePawnTokenType("+=");
  public static IElementType AND = new SourcePawnTokenType("&&");
  public static IElementType ANDEQ = new SourcePawnTokenType("&=");
  public static IElementType DECREMENT = new SourcePawnTokenType("--");
  public static IElementType DIVEQ = new SourcePawnTokenType("/=");
  public static IElementType ELLIPSIS = new SourcePawnTokenType("...");
  public static IElementType EQUALTO = new SourcePawnTokenType("==");
  public static IElementType GTEQ = new SourcePawnTokenType(">=");
  public static IElementType INCREMENT = new SourcePawnTokenType("++");
  public static IElementType LTEQ = new SourcePawnTokenType("<=");
  public static IElementType MODEQ = new SourcePawnTokenType("%=");
  public static IElementType MULEQ = new SourcePawnTokenType("*=");
  public static IElementType NEQUALTO = new SourcePawnTokenType("!=");
  public static IElementType OR = new SourcePawnTokenType("||");
  public static IElementType OREQ = new SourcePawnTokenType("|=");
  public static IElementType RANGE = new SourcePawnTokenType("..");
  public static IElementType SCOPE_RESOLUTION = new SourcePawnTokenType("::");
  public static IElementType SL = new SourcePawnTokenType("<<");
  public static IElementType SLEQ = new SourcePawnTokenType("<<=");
  public static IElementType SRA = new SourcePawnTokenType(">>");
  public static IElementType SRAEQ = new SourcePawnTokenType(">>=");
  public static IElementType SRL = new SourcePawnTokenType(">>>");
  public static IElementType SRLEQ = new SourcePawnTokenType(">>>=");
  public static IElementType SUBEQ = new SourcePawnTokenType("-=");
  public static IElementType XOREQ = new SourcePawnTokenType("^=");

  // MATCHED PAIRS
  public static IElementType LBRACE = new SourcePawnTokenType("{");
  public static IElementType RBRACE = new SourcePawnTokenType("}");
  public static IElementType LBRACKET = new SourcePawnTokenType("[");
  public static IElementType RBRACKET = new SourcePawnTokenType("]");
  public static IElementType LPAREN = new SourcePawnTokenType("(");
  public static IElementType RPAREN = new SourcePawnTokenType(")");
  public static IElementType LT = new SourcePawnTokenType("<");
  public static IElementType GT = new SourcePawnTokenType(">");

  // KEYWORDS
  public static IElementType ACQUIRE = new SourcePawnTokenType("acquire");
  public static IElementType AS = new SourcePawnTokenType("as");
  public static IElementType ASSERT = new SourcePawnTokenType("assert");
  public static IElementType BEGIN = new SourcePawnTokenType("*begin");
  public static IElementType BREAK = new SourcePawnTokenType("break");
  public static IElementType BUILTIN = new SourcePawnTokenType("builtin");
  public static IElementType CASE = new SourcePawnTokenType("case");
  public static IElementType CAST_TO = new SourcePawnTokenType("cast_to");
  public static IElementType CATCH = new SourcePawnTokenType("catch");
  public static IElementType CELLSOF = new SourcePawnTokenType("cellsof");
  public static IElementType CHAR = new SourcePawnTokenType("char");
  public static IElementType CONST = new SourcePawnTokenType("const");
  public static IElementType CONTINUE = new SourcePawnTokenType("continue");
  public static IElementType DECL = new SourcePawnTokenType("decl");
  public static IElementType DEFAULT = new SourcePawnTokenType("default");
  public static IElementType DEFINED = new SourcePawnTokenType("defined");
  public static IElementType DELETE = new SourcePawnTokenType("delete");
  public static IElementType DO = new SourcePawnTokenType("do");
  public static IElementType DOUBLE = new SourcePawnTokenType("double");
  public static IElementType ELSE = new SourcePawnTokenType("else");
  public static IElementType END = new SourcePawnTokenType("*end");
  public static IElementType ENUM = new SourcePawnTokenType("enum");
  public static IElementType EXIT = new SourcePawnTokenType("exit");
  public static IElementType EXPLICIT = new SourcePawnTokenType("explicit");
  public static IElementType FINALLY = new SourcePawnTokenType("finally");
  public static IElementType FOR = new SourcePawnTokenType("for");
  public static IElementType FOREACH = new SourcePawnTokenType("foreach");
  public static IElementType FORWARD = new SourcePawnTokenType("forward");
  public static IElementType FUNCENUM = new SourcePawnTokenType("funcenum");
  public static IElementType FUNCTAG = new SourcePawnTokenType("functag");
  public static IElementType FUNCTION = new SourcePawnTokenType("function");
  public static IElementType GOTO = new SourcePawnTokenType("goto");
  public static IElementType IF = new SourcePawnTokenType("if");
  public static IElementType IMPLICIT = new SourcePawnTokenType("implicit");
  public static IElementType IMPORT = new SourcePawnTokenType("import");
  public static IElementType IN = new SourcePawnTokenType("in");
  public static IElementType INT = new SourcePawnTokenType("int");
  public static IElementType INT8 = new SourcePawnTokenType("int8");
  public static IElementType INT16 = new SourcePawnTokenType("int16");
  public static IElementType INT32 = new SourcePawnTokenType("int32");
  public static IElementType INT64 = new SourcePawnTokenType("int64");
  public static IElementType INTN = new SourcePawnTokenType("intn");
  public static IElementType INTERFACE = new SourcePawnTokenType("interface");
  public static IElementType LET = new SourcePawnTokenType("let");
  public static IElementType METHODMAP = new SourcePawnTokenType("methodmap");
  public static IElementType NAMESPACE = new SourcePawnTokenType("namespace");
  public static IElementType NATIVE = new SourcePawnTokenType("native");
  public static IElementType NEW = new SourcePawnTokenType("new");
  public static IElementType NULL = new SourcePawnTokenType("null");
  public static IElementType NULLABLE = new SourcePawnTokenType("__nullable__");
  public static IElementType OBJECT = new SourcePawnTokenType("object");
  public static IElementType OPERATOR = new SourcePawnTokenType("operator");
  public static IElementType PACKAGE = new SourcePawnTokenType("package");
  public static IElementType PRIVATE = new SourcePawnTokenType("private");
  public static IElementType PROTECTED = new SourcePawnTokenType("protected");
  public static IElementType PUBLIC = new SourcePawnTokenType("public");
  public static IElementType READONLY = new SourcePawnTokenType("readonly");
  public static IElementType RETURN = new SourcePawnTokenType("return");
  public static IElementType SEALED = new SourcePawnTokenType("sealed");
  public static IElementType SIZEOF = new SourcePawnTokenType("sizeof");
  public static IElementType SLEEP = new SourcePawnTokenType("sleep");
  public static IElementType STATIC = new SourcePawnTokenType("static");
  public static IElementType STOCK = new SourcePawnTokenType("stock");
  public static IElementType STRUCT = new SourcePawnTokenType("struct");
  public static IElementType SWITCH = new SourcePawnTokenType("switch");
  public static IElementType TAGOF = new SourcePawnTokenType("tagof");
  public static IElementType THIS = new SourcePawnTokenType("this");
  public static IElementType THROW = new SourcePawnTokenType("throw");
  public static IElementType TRY = new SourcePawnTokenType("try");
  public static IElementType TYPEDEF = new SourcePawnTokenType("typedef");
  public static IElementType TYPEOF = new SourcePawnTokenType("typeof");
  public static IElementType TYPESET = new SourcePawnTokenType("typeset");
  public static IElementType UINT8 = new SourcePawnTokenType("uint8");
  public static IElementType UINT16 = new SourcePawnTokenType("uint16");
  public static IElementType UINT32 = new SourcePawnTokenType("uint32");
  public static IElementType UINT64 = new SourcePawnTokenType("uint64");
  public static IElementType UINTN = new SourcePawnTokenType("uintn");
  public static IElementType UNION = new SourcePawnTokenType("union");
  public static IElementType USING = new SourcePawnTokenType("using");
  public static IElementType VAR = new SourcePawnTokenType("var");
  public static IElementType VARIANT = new SourcePawnTokenType("variant");
  public static IElementType VIEW_AS = new SourcePawnTokenType("view_as");
  public static IElementType VIRTUAL = new SourcePawnTokenType("virtual");
  public static IElementType VOID = new SourcePawnTokenType("void");
  public static IElementType VOLATILE = new SourcePawnTokenType("volatile");
  public static IElementType WHILE = new SourcePawnTokenType("while");
  public static IElementType WITH = new SourcePawnTokenType("with");

  // LITERALS
  public static IElementType NUMBER_LITERAL = new SourcePawnTokenType("-number-");
  public static IElementType CHARACTER_LITERAL = new SourcePawnTokenType("-character-");
  public static IElementType STRING_LITERAL = new SourcePawnTokenType("-string-");

  // IDENTIFIER
  public static IElementType IDENTIFIER = new SourcePawnTokenType("-identifier-");
  public static IElementType TAG = new SourcePawnTokenType("-tag-");

  // CASE LABEL
  public static IElementType LABEL = new SourcePawnTokenType("-label-");

  // COMMENTS
  public static IElementType LINE_COMMENT = new SourcePawnTokenType("LINE_COMMENT");
  public static IElementType BLOCK_COMMENT = new SourcePawnTokenType("BLOCK_COMMENT");
  public static IElementType DOC_COMMENT = new SourcePawnTokenType("DOC_COMMENT");
  
}
