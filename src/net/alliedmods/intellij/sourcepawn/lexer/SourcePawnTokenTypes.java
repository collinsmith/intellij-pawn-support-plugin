package net.alliedmods.intellij.sourcepawn.lexer;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.intellij.sourcepawn.psi.SourcePawnTokenType;

public class SourcePawnTokenTypes {

  private SourcePawnTokenTypes() {
  }

  // GENERIC
  public static final IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
  public static final IElementType WHITESPACE = TokenType.WHITE_SPACE;
  public static final IElementType NEW_LINE = new SourcePawnTokenType("EOL");

  // PREPROCESSOR DIRECTIVES
  public static final IElementType PREPROCESSOR_ASSERT = new SourcePawnTokenType("assert");
  public static final IElementType PREPROCESSOR_DEFINE = new SourcePawnTokenType("define");
  public static final IElementType PREPROCESSOR_ELSE = new SourcePawnTokenType("else");
  public static final IElementType PREPROCESSOR_ELSEIF = new SourcePawnTokenType("elseif");
  public static final IElementType PREPROCESSOR_ENDIF = new SourcePawnTokenType("endif");
  public static final IElementType PREPROCESSOR_ENDINPUT = new SourcePawnTokenType("endinput");
  public static final IElementType PREPROCESSOR_ENDSCRIPT = new SourcePawnTokenType("endscript");
  public static final IElementType PREPROCESSOR_ERROR = new SourcePawnTokenType("error");
  public static final IElementType PREPROCESSOR_FILE = new SourcePawnTokenType("file");
  public static final IElementType PREPROCESSOR_IF = new SourcePawnTokenType("if");
  public static final IElementType PREPROCESSOR_INCLUDE = new SourcePawnTokenType("include");
  public static final IElementType PREPROCESSOR_LINE = new SourcePawnTokenType("line");
  public static final IElementType PREPROCESSOR_PRAGMA = new SourcePawnTokenType("pragma");
  public static final IElementType PREPROCESSOR_TRYINCLUDE = new SourcePawnTokenType("tryinclude");
  public static final IElementType PREPROCESSOR_UNDEF = new SourcePawnTokenType("undef");

  // INCLUDE FILE FORMATS
  public static final IElementType PREPROCESSOR_INCLUDE_SYSTEMFILE = new SourcePawnTokenType("-system file-");
  public static final IElementType PREPROCESSOR_INCLUDE_RELATIVEFILE = new SourcePawnTokenType("-relative file-");

  // PRAGMA DIRECTIVES
  public static final IElementType PRAGMA_CODEPAGE = new SourcePawnTokenType("codepage");
  public static final IElementType PRAGMA_CTRLCHAR = new SourcePawnTokenType("ctrlchar");
  public static final IElementType PRAGMA_DEPRECATED = new SourcePawnTokenType("deprecated");
  public static final IElementType PRAGMA_DEPRECATED_STRING = new SourcePawnTokenType("PRAGMA_DEPRECATED_STRING");
  public static final IElementType PRAGMA_DYNAMIC = new SourcePawnTokenType("dynamic");
  public static final IElementType PRAGMA_RATIONAL = new SourcePawnTokenType("rational");
  public static final IElementType PRAGMA_SEMICOLON = new SourcePawnTokenType("semicolon");
  public static final IElementType PRAGMA_NEWDECLS = new SourcePawnTokenType("newdecls");
  public static final IElementType PRAGMA_TABSIZE = new SourcePawnTokenType("tabsize");
  public static final IElementType PRAGMA_UNUSED = new SourcePawnTokenType("unused");
  
  // PRAGMA NEWDECLS DIRECTIVES
  public static final IElementType PRAGMA_NEWDECLS_REQUIRED = new SourcePawnTokenType("required");
  public static final IElementType PRAGMA_NEWDECLS_OPTIONAL = new SourcePawnTokenType("optional");

  // OPERATORS
  public static final IElementType AMPERSAND = new SourcePawnTokenType("&");
  public static final IElementType ASSIGN = new SourcePawnTokenType("=");
  public static final IElementType ASTERISK = new SourcePawnTokenType("*");
  public static final IElementType AT_SIGN = new SourcePawnTokenType("@");
  public static final IElementType CARET = new SourcePawnTokenType("^");
  public static final IElementType COLON = new SourcePawnTokenType(":");
  public static final IElementType COMMA = new SourcePawnTokenType(",");
  public static final IElementType EXCLAMATION = new SourcePawnTokenType("!");
  public static final IElementType HASH = new SourcePawnTokenType("#");
  public static final IElementType MINUS = new SourcePawnTokenType("-");
  public static final IElementType PERCENT = new SourcePawnTokenType("%");
  public static final IElementType PERIOD = new SourcePawnTokenType(".");
  public static final IElementType PLUS = new SourcePawnTokenType("+");
  public static final IElementType SEMICOLON = new SourcePawnTokenType(";");
  public static final IElementType SLASH = new SourcePawnTokenType("/");
  public static final IElementType TILDE = new SourcePawnTokenType("~");
  public static final IElementType UNDERSCORE = new SourcePawnTokenType("_");
  public static final IElementType VERTICAL_BAR = new SourcePawnTokenType("|");

  public static final IElementType ADDEQ = new SourcePawnTokenType("+=");
  public static final IElementType AND = new SourcePawnTokenType("&&");
  public static final IElementType ANDEQ = new SourcePawnTokenType("&=");
  public static final IElementType DECREMENT = new SourcePawnTokenType("--");
  public static final IElementType DIVEQ = new SourcePawnTokenType("/=");
  public static final IElementType ELLIPSIS = new SourcePawnTokenType("...");
  public static final IElementType EQUALTO = new SourcePawnTokenType("==");
  public static final IElementType GTEQ = new SourcePawnTokenType(">=");
  public static final IElementType INCREMENT = new SourcePawnTokenType("++");
  public static final IElementType LTEQ = new SourcePawnTokenType("<=");
  public static final IElementType MODEQ = new SourcePawnTokenType("%=");
  public static final IElementType MULEQ = new SourcePawnTokenType("*=");
  public static final IElementType NEQUALTO = new SourcePawnTokenType("!=");
  public static final IElementType OR = new SourcePawnTokenType("||");
  public static final IElementType OREQ = new SourcePawnTokenType("|=");
  public static final IElementType RANGE = new SourcePawnTokenType("..");
  public static final IElementType SCOPE_RESOLUTION = new SourcePawnTokenType("::");
  public static final IElementType SL = new SourcePawnTokenType("<<");
  public static final IElementType SLEQ = new SourcePawnTokenType("<<=");
  public static final IElementType SRA = new SourcePawnTokenType(">>");
  public static final IElementType SRAEQ = new SourcePawnTokenType(">>=");
  public static final IElementType SRL = new SourcePawnTokenType(">>>");
  public static final IElementType SRLEQ = new SourcePawnTokenType(">>>=");
  public static final IElementType SUBEQ = new SourcePawnTokenType("-=");
  public static final IElementType XOREQ = new SourcePawnTokenType("^=");

  // MATCHED PAIRS
  public static final IElementType LBRACE = new SourcePawnTokenType("{");
  public static final IElementType RBRACE = new SourcePawnTokenType("}");
  public static final IElementType LBRACKET = new SourcePawnTokenType("[");
  public static final IElementType RBRACKET = new SourcePawnTokenType("]");
  public static final IElementType LPAREN = new SourcePawnTokenType("(");
  public static final IElementType RPAREN = new SourcePawnTokenType(")");
  public static final IElementType LT = new SourcePawnTokenType("<");
  public static final IElementType GT = new SourcePawnTokenType(">");

  // KEYWORDS
  public static final IElementType ACQUIRE = new SourcePawnTokenType("acquire");
  public static final IElementType AS = new SourcePawnTokenType("as");
  public static final IElementType ASSERT = new SourcePawnTokenType("assert");
  public static final IElementType BEGIN = new SourcePawnTokenType("*begin");
  public static final IElementType BREAK = new SourcePawnTokenType("break");
  public static final IElementType BUILTIN = new SourcePawnTokenType("builtin");
  public static final IElementType CASE = new SourcePawnTokenType("case");
  public static final IElementType CAST_TO = new SourcePawnTokenType("cast_to");
  public static final IElementType CATCH = new SourcePawnTokenType("catch");
  public static final IElementType CELLSOF = new SourcePawnTokenType("cellsof");
  public static final IElementType CHAR = new SourcePawnTokenType("char");
  public static final IElementType CONST = new SourcePawnTokenType("const");
  public static final IElementType CONTINUE = new SourcePawnTokenType("continue");
  public static final IElementType DECL = new SourcePawnTokenType("decl");
  public static final IElementType DEFAULT = new SourcePawnTokenType("default");
  public static final IElementType DEFINED = new SourcePawnTokenType("defined");
  public static final IElementType DELETE = new SourcePawnTokenType("delete");
  public static final IElementType DO = new SourcePawnTokenType("do");
  public static final IElementType DOUBLE = new SourcePawnTokenType("double");
  public static final IElementType ELSE = new SourcePawnTokenType("else");
  public static final IElementType END = new SourcePawnTokenType("*end");
  public static final IElementType ENUM = new SourcePawnTokenType("enum");
  public static final IElementType EXIT = new SourcePawnTokenType("exit");
  public static final IElementType EXPLICIT = new SourcePawnTokenType("explicit");
  public static final IElementType FINALLY = new SourcePawnTokenType("finally");
  public static final IElementType FOR = new SourcePawnTokenType("for");
  public static final IElementType FOREACH = new SourcePawnTokenType("foreach");
  public static final IElementType FORWARD = new SourcePawnTokenType("forward");
  public static final IElementType FUNCENUM = new SourcePawnTokenType("funcenum");
  public static final IElementType FUNCTAG = new SourcePawnTokenType("functag");
  public static final IElementType FUNCTION = new SourcePawnTokenType("function");
  public static final IElementType GOTO = new SourcePawnTokenType("goto");
  public static final IElementType IF = new SourcePawnTokenType("if");
  public static final IElementType IMPLICIT = new SourcePawnTokenType("implicit");
  public static final IElementType IMPORT = new SourcePawnTokenType("import");
  public static final IElementType IN = new SourcePawnTokenType("in");
  public static final IElementType INT = new SourcePawnTokenType("int");
  public static final IElementType INT8 = new SourcePawnTokenType("int8");
  public static final IElementType INT16 = new SourcePawnTokenType("int16");
  public static final IElementType INT32 = new SourcePawnTokenType("int32");
  public static final IElementType INT64 = new SourcePawnTokenType("int64");
  public static final IElementType INTN = new SourcePawnTokenType("intn");
  public static final IElementType INTERFACE = new SourcePawnTokenType("interface");
  public static final IElementType LET = new SourcePawnTokenType("let");
  public static final IElementType METHODMAP = new SourcePawnTokenType("methodmap");
  public static final IElementType NAMESPACE = new SourcePawnTokenType("namespace");
  public static final IElementType NATIVE = new SourcePawnTokenType("native");
  public static final IElementType NEW = new SourcePawnTokenType("new");
  public static final IElementType NULL = new SourcePawnTokenType("null");
  public static final IElementType NULLABLE = new SourcePawnTokenType("__nullable__");
  public static final IElementType OBJECT = new SourcePawnTokenType("object");
  public static final IElementType OPERATOR = new SourcePawnTokenType("operator");
  public static final IElementType PACKAGE = new SourcePawnTokenType("package");
  public static final IElementType PRIVATE = new SourcePawnTokenType("private");
  public static final IElementType PROTECTED = new SourcePawnTokenType("protected");
  public static final IElementType PUBLIC = new SourcePawnTokenType("public");
  public static final IElementType READONLY = new SourcePawnTokenType("readonly");
  public static final IElementType RETURN = new SourcePawnTokenType("return");
  public static final IElementType SEALED = new SourcePawnTokenType("sealed");
  public static final IElementType SIZEOF = new SourcePawnTokenType("sizeof");
  public static final IElementType SLEEP = new SourcePawnTokenType("sleep");
  public static final IElementType STATIC = new SourcePawnTokenType("static");
  public static final IElementType STOCK = new SourcePawnTokenType("stock");
  public static final IElementType STRUCT = new SourcePawnTokenType("struct");
  public static final IElementType SWITCH = new SourcePawnTokenType("switch");
  public static final IElementType TAGOF = new SourcePawnTokenType("tagof");
  public static final IElementType THIS = new SourcePawnTokenType("this");
  public static final IElementType THROW = new SourcePawnTokenType("throw");
  public static final IElementType TRY = new SourcePawnTokenType("try");
  public static final IElementType TYPEDEF = new SourcePawnTokenType("typedef");
  public static final IElementType TYPEOF = new SourcePawnTokenType("typeof");
  public static final IElementType TYPESET = new SourcePawnTokenType("typeset");
  public static final IElementType UINT8 = new SourcePawnTokenType("uint8");
  public static final IElementType UINT16 = new SourcePawnTokenType("uint16");
  public static final IElementType UINT32 = new SourcePawnTokenType("uint32");
  public static final IElementType UINT64 = new SourcePawnTokenType("uint64");
  public static final IElementType UINTN = new SourcePawnTokenType("uintn");
  public static final IElementType UNION = new SourcePawnTokenType("union");
  public static final IElementType USING = new SourcePawnTokenType("using");
  public static final IElementType VAR = new SourcePawnTokenType("var");
  public static final IElementType VARIANT = new SourcePawnTokenType("variant");
  public static final IElementType VIEW_AS = new SourcePawnTokenType("view_as");
  public static final IElementType VIRTUAL = new SourcePawnTokenType("virtual");
  public static final IElementType VOID = new SourcePawnTokenType("void");
  public static final IElementType VOLATILE = new SourcePawnTokenType("volatile");
  public static final IElementType WHILE = new SourcePawnTokenType("while");
  public static final IElementType WITH = new SourcePawnTokenType("with");

  // LITERALS
  public static final IElementType NUMBER_LITERAL = new SourcePawnTokenType("-number-");
  public static final IElementType CHARACTER_LITERAL = new SourcePawnTokenType("-character-");
  public static final IElementType STRING_LITERAL = new SourcePawnTokenType("-string-");

  // IDENTIFIER
  public static final IElementType IDENTIFIER = new SourcePawnTokenType("-identifier-");
  public static final IElementType TAG = new SourcePawnTokenType("-tag-");

  // CASE LABEL
  public static final IElementType LABEL = new SourcePawnTokenType("-label-");

  // COMMENTS
  public static final IElementType LINE_COMMENT = new SourcePawnTokenType("LINE_COMMENT");
  public static final IElementType BLOCK_COMMENT = new SourcePawnTokenType("BLOCK_COMMENT");
  public static final IElementType DOC_COMMENT = new SourcePawnTokenType("DOC_COMMENT");
  
}
