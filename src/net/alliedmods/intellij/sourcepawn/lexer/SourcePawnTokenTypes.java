package net.alliedmods.intellij.sourcepawn.lexer;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.intellij.sourcepawn.psi.SourcePawnTokenType;

public class SourcePawnTokenTypes {

  private SourcePawnTokenTypes() {
  }

  // GENERIC
  static IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
  static IElementType WHITESPACE = TokenType.WHITE_SPACE;
  static IElementType NEW_LINE = new SourcePawnTokenType("EOL");

  // PREPROCESSOR DIRECTIVES
  static IElementType PREPROCESSOR_ASSERT = new SourcePawnTokenType("PREPROCESSOR_ASSERT");
  static IElementType PREPROCESSOR_DEFINE = new SourcePawnTokenType("PREPROCESSOR_DEFINE");
  static IElementType PREPROCESSOR_ELSE = new SourcePawnTokenType("PREPROCESSOR_ELSE");
  static IElementType PREPROCESSOR_ELSEIF = new SourcePawnTokenType("PREPROCESSOR_ELSEIF");
  static IElementType PREPROCESSOR_ENDIF = new SourcePawnTokenType("PREPROCESSOR_ENDIF");
  static IElementType PREPROCESSOR_ENDINPUT = new SourcePawnTokenType("PREPROCESSOR_ENDINPUT");
  static IElementType PREPROCESSOR_ENDSCRIPT = new SourcePawnTokenType("PREPROCESSOR_ENDSCRIPT");
  static IElementType PREPROCESSOR_ERROR = new SourcePawnTokenType("PREPROCESSOR_ERROR");
  static IElementType PREPROCESSOR_FILE = new SourcePawnTokenType("PREPROCESSOR_FILE");
  static IElementType PREPROCESSOR_IF = new SourcePawnTokenType("PREPROCESSOR_IF");
  static IElementType PREPROCESSOR_INCLUDE = new SourcePawnTokenType("PREPROCESSOR_INCLUDE");
  static IElementType PREPROCESSOR_LINE = new SourcePawnTokenType("PREPROCESSOR_LINE");
  static IElementType PREPROCESSOR_PRAGMA = new SourcePawnTokenType("PREPROCESSOR_PRAGMA");
  static IElementType PREPROCESSOR_TRYINCLUDE = new SourcePawnTokenType("PREPROCESSOR_TRYINCLUDE");
  static IElementType PREPROCESSOR_UNDEF = new SourcePawnTokenType("PREPROCESSOR_UNDEF");

  // PRAGMA DIRECTIVES
  static IElementType PRAGMA_CODEPAGE = new SourcePawnTokenType("PRAGMA_CODEPAGE");
  static IElementType PRAGMA_CTRLCHAR = new SourcePawnTokenType("PRAGMA_CTRLCHAR");
  static IElementType PRAGMA_DEPRECATED = new SourcePawnTokenType("PRAGMA_DEPRECATED");
  static IElementType PRAGMA_DEPRECATED_STRING = new SourcePawnTokenType("PRAGMA_DEPRECATED_STRING");
  static IElementType PRAGMA_DYNAMIC = new SourcePawnTokenType("PRAGMA_DYNAMIC");
  static IElementType PRAGMA_RATIONAL = new SourcePawnTokenType("PRAGMA_RATIONAL");
  static IElementType PRAGMA_SEMICOLON = new SourcePawnTokenType("PRAGMA_SEMICOLON");
  static IElementType PRAGMA_NEWDECLS = new SourcePawnTokenType("PRAGMA_NEWDECLS");
  static IElementType PRAGMA_TABSIZE = new SourcePawnTokenType("PRAGMA_TABSIZE");
  static IElementType PRAGMA_UNUSED = new SourcePawnTokenType("PRAGMA_UNUSED");
  
  // PRAGMA NEWDECLS DIRECTIVES
  static IElementType PRAGMA_NEWDECLS_REQUIRED = new SourcePawnTokenType("PRAGMA_NEWDECLS_REQUIRED");
  static IElementType PRAGMA_NEWDECLS_OPTIONAL = new SourcePawnTokenType("PRAGMA_NEWDECLS_OPTIONAL");

  // OPERATORS
  static IElementType AMPERSAND = new SourcePawnTokenType("&");
  static IElementType ASSIGN = new SourcePawnTokenType("=");
  static IElementType ASTERISK = new SourcePawnTokenType("*");
  static IElementType AT_SIGN = new SourcePawnTokenType("@");
  static IElementType CARET = new SourcePawnTokenType("^");
  static IElementType COLON = new SourcePawnTokenType(":");
  static IElementType COMMA = new SourcePawnTokenType(",");
  static IElementType EXCLAMATION = new SourcePawnTokenType("!");
  static IElementType HASH = new SourcePawnTokenType("#");
  static IElementType MINUS = new SourcePawnTokenType("-");
  static IElementType PERCENT = new SourcePawnTokenType("%");
  static IElementType PERIOD = new SourcePawnTokenType(".");
  static IElementType PLUS = new SourcePawnTokenType("+");
  static IElementType SEMICOLON = new SourcePawnTokenType(";");
  static IElementType SLASH = new SourcePawnTokenType("/");
  static IElementType TILDE = new SourcePawnTokenType("~");
  static IElementType UNDERSCORE = new SourcePawnTokenType("_");
  static IElementType VERTICAL_BAR = new SourcePawnTokenType("|");

  static IElementType ADDEQ = new SourcePawnTokenType("+=");
  static IElementType AND = new SourcePawnTokenType("&&");
  static IElementType ANDEQ = new SourcePawnTokenType("&=");
  static IElementType DECREMENT = new SourcePawnTokenType("--");
  static IElementType DIVEQ = new SourcePawnTokenType("/=");
  static IElementType ELLIPSIS = new SourcePawnTokenType("...");
  static IElementType EQUALTO = new SourcePawnTokenType("==");
  static IElementType GTEQ = new SourcePawnTokenType(">=");
  static IElementType INCREMENT = new SourcePawnTokenType("++");
  static IElementType LTEQ = new SourcePawnTokenType("<=");
  static IElementType MODEQ = new SourcePawnTokenType("%=");
  static IElementType MULEQ = new SourcePawnTokenType("*=");
  static IElementType NEQUALTO = new SourcePawnTokenType("!=");
  static IElementType OR = new SourcePawnTokenType("||");
  static IElementType OREQ = new SourcePawnTokenType("|=");
  static IElementType RANGE = new SourcePawnTokenType("..");
  static IElementType SCOPE_RESOLUTION = new SourcePawnTokenType("::");
  static IElementType SL = new SourcePawnTokenType("<<");
  static IElementType SLEQ = new SourcePawnTokenType("<<=");
  static IElementType SRA = new SourcePawnTokenType(">>");
  static IElementType SRAEQ = new SourcePawnTokenType(">>=");
  static IElementType SRL = new SourcePawnTokenType(">>>");
  static IElementType SRLEQ = new SourcePawnTokenType(">>>=");
  static IElementType SUBEQ = new SourcePawnTokenType("-=");
  static IElementType XOREQ = new SourcePawnTokenType("^=");

  // MATCHED PAIRS
  static IElementType LBRACE = new SourcePawnTokenType("{");
  static IElementType RBRACE = new SourcePawnTokenType("}");
  static IElementType LBRACKET = new SourcePawnTokenType("[");
  static IElementType RBRACKET = new SourcePawnTokenType("]");
  static IElementType LPAREN = new SourcePawnTokenType("(");
  static IElementType RPAREN = new SourcePawnTokenType(")");
  static IElementType LT = new SourcePawnTokenType("<");
  static IElementType GT = new SourcePawnTokenType(">");

  // KEYWORDS
  static IElementType ACQUIRE = new SourcePawnTokenType("acquire");
  static IElementType AS = new SourcePawnTokenType("as");
  static IElementType ASSERT = new SourcePawnTokenType("assert");
  static IElementType BEGIN = new SourcePawnTokenType("*begin");
  static IElementType BREAK = new SourcePawnTokenType("break");
  static IElementType BUILTIN = new SourcePawnTokenType("builtin");
  static IElementType CASE = new SourcePawnTokenType("case");
  static IElementType CAST_TO = new SourcePawnTokenType("cast_to");
  static IElementType CATCH = new SourcePawnTokenType("catch");
  static IElementType CELLSOF = new SourcePawnTokenType("cellsof");
  static IElementType CHAR = new SourcePawnTokenType("char");
  static IElementType CONST = new SourcePawnTokenType("const");
  static IElementType CONTINUE = new SourcePawnTokenType("continue");
  static IElementType DECL = new SourcePawnTokenType("decl");
  static IElementType DEFAULT = new SourcePawnTokenType("default");
  static IElementType DEFINED = new SourcePawnTokenType("defined");
  static IElementType DELETE = new SourcePawnTokenType("delete");
  static IElementType DO = new SourcePawnTokenType("do");
  static IElementType DOUBLE = new SourcePawnTokenType("double");
  static IElementType ELSE = new SourcePawnTokenType("else");
  static IElementType END = new SourcePawnTokenType("*end");
  static IElementType ENUM = new SourcePawnTokenType("enum");
  static IElementType EXIT = new SourcePawnTokenType("exit");
  static IElementType EXPLICIT = new SourcePawnTokenType("explicit");
  static IElementType FINALLY = new SourcePawnTokenType("finally");
  static IElementType FOR = new SourcePawnTokenType("for");
  static IElementType FOREACH = new SourcePawnTokenType("foreach");
  static IElementType FORWARD = new SourcePawnTokenType("forward");
  static IElementType FUNCENUM = new SourcePawnTokenType("funcenum");
  static IElementType FUNCTAG = new SourcePawnTokenType("functag");
  static IElementType FUNCTION = new SourcePawnTokenType("function");
  static IElementType GOTO = new SourcePawnTokenType("goto");
  static IElementType IF = new SourcePawnTokenType("if");
  static IElementType IMPLICIT = new SourcePawnTokenType("implicit");
  static IElementType IMPORT = new SourcePawnTokenType("import");
  static IElementType IN = new SourcePawnTokenType("in");
  static IElementType INT = new SourcePawnTokenType("int");
  static IElementType INT8 = new SourcePawnTokenType("int8");
  static IElementType INT16 = new SourcePawnTokenType("int16");
  static IElementType INT32 = new SourcePawnTokenType("int32");
  static IElementType INT64 = new SourcePawnTokenType("int64");
  static IElementType INTN = new SourcePawnTokenType("intn");
  static IElementType INTERFACE = new SourcePawnTokenType("interface");
  static IElementType LET = new SourcePawnTokenType("let");
  static IElementType METHODMAP = new SourcePawnTokenType("methodmap");
  static IElementType NAMESPACE = new SourcePawnTokenType("namespace");
  static IElementType NATIVE = new SourcePawnTokenType("native");
  static IElementType NEW = new SourcePawnTokenType("new");
  static IElementType NULL = new SourcePawnTokenType("null");
  static IElementType NULLABLE = new SourcePawnTokenType("__nullable__");
  static IElementType OBJECT = new SourcePawnTokenType("object");
  static IElementType OPERATOR = new SourcePawnTokenType("operator");
  static IElementType PACKAGE = new SourcePawnTokenType("package");
  static IElementType PRIVATE = new SourcePawnTokenType("private");
  static IElementType PROTECTED = new SourcePawnTokenType("protected");
  static IElementType PUBLIC = new SourcePawnTokenType("public");
  static IElementType READONLY = new SourcePawnTokenType("readonly");
  static IElementType RETURN = new SourcePawnTokenType("return");
  static IElementType SEALED = new SourcePawnTokenType("sealed");
  static IElementType SIZEOF = new SourcePawnTokenType("sizeof");
  static IElementType SLEEP = new SourcePawnTokenType("sleep");
  static IElementType STATIC = new SourcePawnTokenType("static");
  static IElementType STOCK = new SourcePawnTokenType("stock");
  static IElementType STRUCT = new SourcePawnTokenType("struct");
  static IElementType SWITCH = new SourcePawnTokenType("switch");
  static IElementType TAGOF = new SourcePawnTokenType("tagof");
  static IElementType THIS = new SourcePawnTokenType("this");
  static IElementType THROW = new SourcePawnTokenType("throw");
  static IElementType TRY = new SourcePawnTokenType("try");
  static IElementType TYPEDEF = new SourcePawnTokenType("typedef");
  static IElementType TYPEOF = new SourcePawnTokenType("typeof");
  static IElementType TYPESET = new SourcePawnTokenType("typeset");
  static IElementType UINT8 = new SourcePawnTokenType("uint8");
  static IElementType UINT16 = new SourcePawnTokenType("uint16");
  static IElementType UINT32 = new SourcePawnTokenType("uint32");
  static IElementType UINT64 = new SourcePawnTokenType("uint64");
  static IElementType UINTN = new SourcePawnTokenType("uintn");
  static IElementType UNION = new SourcePawnTokenType("union");
  static IElementType USING = new SourcePawnTokenType("using");
  static IElementType VAR = new SourcePawnTokenType("var");
  static IElementType VARIANT = new SourcePawnTokenType("variant");
  static IElementType VIEW_AS = new SourcePawnTokenType("view_as");
  static IElementType VIRTUAL = new SourcePawnTokenType("virtual");
  static IElementType VOID = new SourcePawnTokenType("void");
  static IElementType VOLATILE = new SourcePawnTokenType("volatile");
  static IElementType WHILE = new SourcePawnTokenType("while");
  static IElementType WITH = new SourcePawnTokenType("with");

  // LITERALS
  static IElementType NUMBER_LITERAL = new SourcePawnTokenType("NUMBER_LITERAL");
  static IElementType CHARACTER_LITERAL = new SourcePawnTokenType("CHARACTER_LITERAL");
  static IElementType STRING_LITERAL = new SourcePawnTokenType("STRING_LITERAL");

  // IDENTIFIER
  static IElementType IDENTIFIER = new SourcePawnTokenType("IDENTIFIER");
  static IElementType TAG = new SourcePawnTokenType("TAG");

  // CASE LABEL
  static IElementType LABEL = new SourcePawnTokenType("LABEL");

  // COMMENTS
  static IElementType LINE_COMMENT = new SourcePawnTokenType("LINE_COMMENT");
  static IElementType BLOCK_COMMENT = new SourcePawnTokenType("BLOCK_COMMENT");
  static IElementType DOC_COMMENT = new SourcePawnTokenType("DOC_COMMENT");

}
