package net.sourcemod.sourcepawn.lexer;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import net.sourcemod.sourcepawn.SpTokenType;

import org.jetbrains.annotations.NotNull;

public class SpTokenTypes {

  // GENERIC
  public static final IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;

  public static final IElementType NEW_LINE = new SpTokenType("EOL");

  public static final IElementType WHITE_SPACE = TokenType.WHITE_SPACE;

  // PREPROCESSOR DIRECTIVES
  public static final IElementType PREPROCESSOR_ASSERT = new SpTokenType("ASSERT");
  public static final IElementType PREPROCESSOR_DEFINE = new SpTokenType("DEFINE");
  public static final IElementType PREPROCESSOR_ELSE = new SpTokenType("ELSE");
  public static final IElementType PREPROCESSOR_ELSEIF = new SpTokenType("ELSEIF");
  public static final IElementType PREPROCESSOR_ENDIF = new SpTokenType("ENDIF");
  public static final IElementType PREPROCESSOR_ENDINPUT = new SpTokenType("ENDINPUT");
  public static final IElementType PREPROCESSOR_ENDSCRIPT = new SpTokenType("ENDSCRIPT");
  public static final IElementType PREPROCESSOR_ERROR = new SpTokenType("ERROR");
  public static final IElementType PREPROCESSOR_FILE = new SpTokenType("FILE");
  public static final IElementType PREPROCESSOR_IF = new SpTokenType("IF");
  public static final IElementType PREPROCESSOR_INCLUDE = new SpTokenType("INCLUDE");
  public static final IElementType PREPROCESSOR_LINE = new SpTokenType("LINE");
  public static final IElementType PREPROCESSOR_PRAGMA = new SpTokenType("PRAGMA");
  public static final IElementType PREPROCESSOR_TRYINCLUDE = new SpTokenType("TRYINCLUDE");
  public static final IElementType PREPROCESSOR_UNDEF = new SpTokenType("UNDEF");

  // DEFINE
  public static final IElementType DEFINE_PATTERN = new SpTokenType("PATTERN");
  public static final IElementType DEFINE_PATTERN_ARG = new SpTokenType("PATTERN_ARG");
  public static final IElementType BAD_PATTERN = new SpTokenType("BAD_PATTERN");

  // PREPROCESSOR
  public static final IElementType PREPROCESSOR_STRING = new SpTokenType("PREPROCESSOR_STRING");

  // INCLUDE FILE FORMATS
  public static final IElementType PREPROCESSOR_INCLUDE_SYSTEMPATH
      = new SpTokenType("-SYSTEM FILE-");
  public static final IElementType PREPROCESSOR_INCLUDE_RELATIVEPATH
      = new SpTokenType("-RELATIVE PATH-");

  // PRAGMA DIRECTIVES
  public static final IElementType PRAGMA_CODEPAGE = new SpTokenType("codepage");
  public static final IElementType PRAGMA_CTRLCHAR = new SpTokenType("ctrlchar");
  public static final IElementType PRAGMA_DEPRECATED = new SpTokenType("deprecated");
  public static final IElementType PRAGMA_DYNAMIC = new SpTokenType("dynamic");
  public static final IElementType PRAGMA_RATIONAL = new SpTokenType("rational");
  public static final IElementType PRAGMA_SEMICOLON = new SpTokenType("semicolon");
  public static final IElementType PRAGMA_NEWDECLS = new SpTokenType("newdecls");
  public static final IElementType PRAGMA_TABSIZE = new SpTokenType("tabsize");
  public static final IElementType PRAGMA_UNUSED = new SpTokenType("unused");

  // PRAGMA NEWDECLS DIRECTIVES
  public static final IElementType PRAGMA_NEWDECLS_REQUIRED = new SpTokenType("required");
  public static final IElementType PRAGMA_NEWDECLS_OPTIONAL = new SpTokenType("optional");


  // PUNCTUATION
  public static final IElementType AT_SIGN = new SpTokenType("AT_SIGN");
  public static final IElementType COLON = new SpTokenType("COLON");
  public static final IElementType COMMA = new SpTokenType("COMMA");
  public static final IElementType HASH = new SpTokenType("HASH");
  public static final IElementType PERIOD = new SpTokenType("PERIOD");
  public static final IElementType SEMICOLON = new SpTokenType("SEMICOLON");
  public static final IElementType UNDERSCORE = new SpTokenType("UNDERSCORE");
  public static final IElementType ELLIPSIS = new SpTokenType("ELLIPSIS");

  // OPERATORS
  public static final IElementType AMPERSAND = new SpTokenType("AMPERSAND");
  public static final IElementType ASSIGN = new SpTokenType("ASSIGN");
  public static final IElementType ASTERISK = new SpTokenType("ASTERISK");
  public static final IElementType CARET = new SpTokenType("CARET");
  public static final IElementType EXCLAMATION = new SpTokenType("EXCLAMATION");
  public static final IElementType MINUS = new SpTokenType("MINUS");
  public static final IElementType PERCENT = new SpTokenType("PERCENT");
  public static final IElementType PLUS = new SpTokenType("PLUS");
  public static final IElementType SLASH = new SpTokenType("SLASH");
  public static final IElementType TILDE = new SpTokenType("TILDE");
  public static final IElementType VERTICAL_BAR = new SpTokenType("VERTICAL_BAR");

  public static final IElementType ADDEQ = new SpTokenType("ADDEQ");
  public static final IElementType AND = new SpTokenType("AND");
  public static final IElementType ANDEQ = new SpTokenType("ANDEQ");
  public static final IElementType DECREMENT = new SpTokenType("DECREMENT");
  public static final IElementType DIVEQ = new SpTokenType("DIVEQ");
  public static final IElementType EQUALTO = new SpTokenType("EQUALTO");
  public static final IElementType GTEQ = new SpTokenType("GTEQ");
  public static final IElementType INCREMENT = new SpTokenType("INCREMENT");
  public static final IElementType LTEQ = new SpTokenType("LTEQ");
  public static final IElementType MODEQ = new SpTokenType("MODEQ");
  public static final IElementType MULEQ = new SpTokenType("MULEQ");
  public static final IElementType NEQUALTO = new SpTokenType("NEQUALTO");
  public static final IElementType OR = new SpTokenType("OR");
  public static final IElementType OREQ = new SpTokenType("OREQ");
  public static final IElementType RANGE = new SpTokenType("RANGE");
  public static final IElementType SCOPE_RESOLUTION = new SpTokenType("SCOPE_RESOLUTION");
  public static final IElementType SL = new SpTokenType("SL");
  public static final IElementType SLEQ = new SpTokenType("SLEQ");
  public static final IElementType SRA = new SpTokenType("SRA");
  public static final IElementType SRAEQ = new SpTokenType("SRAEQ");
  public static final IElementType SRL = new SpTokenType("SRL");
  public static final IElementType SRLEQ = new SpTokenType("SRLEQ");
  public static final IElementType SUBEQ = new SpTokenType("SUBEQ");
  public static final IElementType XOREQ = new SpTokenType("XOREQ");

  // MATCHED PAIRS
  public static final IElementType LBRACE = new SpTokenType("LBRACE");
  public static final IElementType RBRACE = new SpTokenType("RBRACE");
  public static final IElementType LBRACKET = new SpTokenType("LBRACKET");
  public static final IElementType RBRACKET = new SpTokenType("RBRACKET");
  public static final IElementType LPAREN = new SpTokenType("LPAREN");
  public static final IElementType RPAREN = new SpTokenType("RPAREN");
  public static final IElementType LT = new SpTokenType("LT");
  public static final IElementType GT = new SpTokenType("GT");

  // KEYWORDS
  public static final IElementType ACQUIRE = new SpTokenType("ACQUIRE");
  public static final IElementType AS = new SpTokenType("AS");
  public static final IElementType ASSERT = new SpTokenType("ASSERT");
  public static final IElementType BEGIN = new SpTokenType("*BEGIN");
  public static final IElementType BREAK = new SpTokenType("BREAK");
  public static final IElementType BUILTIN = new SpTokenType("BUILTIN");
  public static final IElementType CASE = new SpTokenType("CASE");
  public static final IElementType CAST_TO = new SpTokenType("CAST_TO");
  public static final IElementType CATCH = new SpTokenType("CATCH");
  public static final IElementType CELLSOF = new SpTokenType("CELLSOF");
  public static final IElementType CHAR = new SpTokenType("CHAR");
  public static final IElementType CONST = new SpTokenType("CONST");
  public static final IElementType CONTINUE = new SpTokenType("CONTINUE");
  public static final IElementType DECL = new SpTokenType("DECL");
  public static final IElementType DEFAULT = new SpTokenType("DEFAULT");
  public static final IElementType DEFINED = new SpTokenType("DEFINED");
  public static final IElementType DELETE = new SpTokenType("DELETE");
  public static final IElementType DO = new SpTokenType("DO");
  public static final IElementType DOUBLE = new SpTokenType("DOUBLE");
  public static final IElementType ELSE = new SpTokenType("ELSE");
  public static final IElementType END = new SpTokenType("*END");
  public static final IElementType ENUM = new SpTokenType("ENUM");
  public static final IElementType EXIT = new SpTokenType("EXIT");
  public static final IElementType EXPLICIT = new SpTokenType("EXPLICIT");
  public static final IElementType FINALLY = new SpTokenType("FINALLY");
  public static final IElementType FOR = new SpTokenType("FOR");
  public static final IElementType FOREACH = new SpTokenType("FOREACH");
  public static final IElementType FORWARD = new SpTokenType("FORWARD");
  public static final IElementType FUNCENUM = new SpTokenType("FUNCENUM");
  public static final IElementType FUNCTAG = new SpTokenType("FUNCTAG");
  public static final IElementType FUNCTION = new SpTokenType("FUNCTION");
  public static final IElementType GOTO = new SpTokenType("GOTO");
  public static final IElementType IF = new SpTokenType("IF");
  public static final IElementType IMPLICIT = new SpTokenType("IMPLICIT");
  public static final IElementType IMPORT = new SpTokenType("IMPORT");
  public static final IElementType IN = new SpTokenType("IN");
  public static final IElementType INT = new SpTokenType("INT");
  public static final IElementType INT8 = new SpTokenType("INT8");
  public static final IElementType INT16 = new SpTokenType("INT16");
  public static final IElementType INT32 = new SpTokenType("INT32");
  public static final IElementType INT64 = new SpTokenType("INT64");
  public static final IElementType INTN = new SpTokenType("INTN");
  public static final IElementType INTERFACE = new SpTokenType("INTERFACE");
  public static final IElementType LET = new SpTokenType("LET");
  public static final IElementType METHODMAP = new SpTokenType("METHODMAP");
  public static final IElementType NAMESPACE = new SpTokenType("NAMESPACE");
  public static final IElementType NATIVE = new SpTokenType("NATIVE");
  public static final IElementType NEW = new SpTokenType("NEW");
  public static final IElementType NULL = new SpTokenType("NULL");
  public static final IElementType NULLABLE = new SpTokenType("__NULLABLE__");
  public static final IElementType OBJECT = new SpTokenType("OBJECT");
  public static final IElementType OPERATOR = new SpTokenType("OPERATOR");
  public static final IElementType PACKAGE = new SpTokenType("PACKAGE");
  public static final IElementType PRIVATE = new SpTokenType("PRIVATE");
  public static final IElementType PROTECTED = new SpTokenType("PROTECTED");
  public static final IElementType PUBLIC = new SpTokenType("PUBLIC");
  public static final IElementType READONLY = new SpTokenType("READONLY");
  public static final IElementType RETURN = new SpTokenType("RETURN");
  public static final IElementType SEALED = new SpTokenType("SEALED");
  public static final IElementType SIZEOF = new SpTokenType("SIZEOF");
  public static final IElementType SLEEP = new SpTokenType("SLEEP");
  public static final IElementType STATIC = new SpTokenType("STATIC");
  public static final IElementType STOCK = new SpTokenType("STOCK");
  public static final IElementType STRUCT = new SpTokenType("STRUCT");
  public static final IElementType SWITCH = new SpTokenType("SWITCH");
  public static final IElementType TAGOF = new SpTokenType("TAGOF");
  public static final IElementType THEN = new SpTokenType("*THEN");
  public static final IElementType THIS = new SpTokenType("THIS");
  public static final IElementType THROW = new SpTokenType("THROW");
  public static final IElementType TRY = new SpTokenType("TRY");
  public static final IElementType TYPEDEF = new SpTokenType("TYPEDEF");
  public static final IElementType TYPEOF = new SpTokenType("TYPEOF");
  public static final IElementType TYPESET = new SpTokenType("TYPESET");
  public static final IElementType UINT8 = new SpTokenType("UINT8");
  public static final IElementType UINT16 = new SpTokenType("UINT16");
  public static final IElementType UINT32 = new SpTokenType("UINT32");
  public static final IElementType UINT64 = new SpTokenType("UINT64");
  public static final IElementType UINTN = new SpTokenType("UINTN");
  public static final IElementType UNION = new SpTokenType("UNION");
  public static final IElementType USING = new SpTokenType("USING");
  public static final IElementType VAR = new SpTokenType("VAR");
  public static final IElementType VARIANT = new SpTokenType("VARIANT");
  public static final IElementType VIEW_AS = new SpTokenType("VIEW_AS");
  public static final IElementType VIRTUAL = new SpTokenType("VIRTUAL");
  public static final IElementType VOID = new SpTokenType("VOID");
  public static final IElementType VOLATILE = new SpTokenType("VOLATILE");
  public static final IElementType WHILE = new SpTokenType("WHILE");
  public static final IElementType WITH = new SpTokenType("WITH");

  // LITERALS
  public static final IElementType NUMBER_LITERAL = new SpTokenType("-number-");
  public static final IElementType RATIONAL_LITERAL = new SpTokenType("-rational number-");

  public static final IElementType BAD_ESCAPE_SEQUENCE = TokenType.BAD_CHARACTER;

  public static final IElementType CHARACTER_LITERAL = new SpTokenType("-character-");
  public static final IElementType BAD_CHARACTER_LITERAL = TokenType.BAD_CHARACTER;
  public static final IElementType INCOMPLETE_CHARACTER_LITERAL = TokenType.BAD_CHARACTER;
  public static final IElementType EMPTY_CHARACTER_LITERAL = BAD_CHARACTER_LITERAL;

  public static final IElementType BAD_STRING_LITERAL = TokenType.BAD_CHARACTER;
  public static final IElementType INCOMPLETE_STRING_LITERAL = TokenType.BAD_CHARACTER;
  public static final IElementType STRING_LITERAL = new SpTokenType("-string-");

  // IDENTIFIER
  public static final IElementType IDENTIFIER = new SpTokenType("-identifier-");
  public static final IElementType TAG = new SpTokenType("-tag-");
  public static final IElementType LABEL = new SpTokenType("-label-");

  // COMMENTS
  public static final IElementType LINE_COMMENT = new SpTokenType("LINE_COMMENT");
  public static final IElementType BLOCK_COMMENT = new SpTokenType("BLOCK_COMMENT");
  public static final IElementType DOC_COMMENT = new SpTokenType("DOC_COMMENT");
  public static final IElementType UNTERMINATED_COMMENT = new SpTokenType("UNTERMINATED_COMMENT");

  private static final TokenSet WHITE_SPACES = TokenSet.create(WHITE_SPACE);

  private static final TokenSet COMMENTS = TokenSet.create(
      LINE_COMMENT, BLOCK_COMMENT, DOC_COMMENT, UNTERMINATED_COMMENT);

  private static final TokenSet KEYWORDS = TokenSet.create(
      ACQUIRE, AS, ASSERT, BEGIN, BREAK, BUILTIN, CASE, CAST_TO, CATCH, CELLSOF, CHAR, CONST,
      CONTINUE, DECL, DEFAULT, DEFINED, DELETE, DO, DOUBLE, ELSE, END, ENUM, EXIT, EXPLICIT,
      FINALLY, FOR, FOREACH, FORWARD, FUNCENUM, FUNCTAG, FUNCTION, GOTO, IF, IMPLICIT, IMPORT,
      IN, INT, INT8, INT16, INT32, INT64, INTN, INTERFACE, LET, METHODMAP, NAMESPACE, NATIVE,
      NEW, NULL, NULLABLE, OBJECT, OPERATOR, PACKAGE, PRIVATE, PROTECTED, PUBLIC, READONLY,
      RETURN, SEALED, SIZEOF, SLEEP, STATIC, STOCK, STRUCT, SWITCH, TAGOF, THEN, THIS, THROW,
      TRY, TYPEDEF, TYPEOF, TYPESET,  UINT8, UINT16, UINT32, UINT64, UINTN, UNION, USING, VAR,
      VARIANT, VIEW_AS, VIRTUAL, VOID, VOLATILE, WHILE, WITH);

  private static final TokenSet OPERATORS = TokenSet.create(
      AMPERSAND, ASSIGN, ASTERISK, CARET, EXCLAMATION, MINUS, PERCENT, PLUS, SLASH, TILDE,
      VERTICAL_BAR, ADDEQ, AND, ANDEQ, DECREMENT, DIVEQ, EQUALTO, GTEQ, INCREMENT, LTEQ, MODEQ,
      MULEQ, NEQUALTO, OR, OREQ, RANGE, SCOPE_RESOLUTION, SL, SLEQ, SRA, SRAEQ, SRL, SRLEQ,
      SUBEQ, XOREQ, LT, GT);

  private static final TokenSet PREPROCESSOR_DIRECTIVES = TokenSet.create(
      PREPROCESSOR_ASSERT, PREPROCESSOR_DEFINE, PREPROCESSOR_ELSE, PREPROCESSOR_ELSEIF,
      PREPROCESSOR_ENDIF, PREPROCESSOR_ENDINPUT, PREPROCESSOR_ENDSCRIPT, PREPROCESSOR_ERROR,
      PREPROCESSOR_FILE, PREPROCESSOR_IF, PREPROCESSOR_INCLUDE, PREPROCESSOR_LINE,
      PREPROCESSOR_PRAGMA, PREPROCESSOR_TRYINCLUDE, PREPROCESSOR_UNDEF);

  private static final TokenSet MATCHED_PAIRS = TokenSet.create(
      LBRACE, RBRACE, LBRACKET, RBRACKET, LPAREN, RPAREN);

  private static final TokenSet STRING_LITERALS = TokenSet.create(STRING_LITERAL);

  @NotNull
  public static TokenSet getWhiteSpaceTokens() {
    return WHITE_SPACES;
  }

  @NotNull
  public static TokenSet getCommentTokens() {
    return COMMENTS;
  }

  @NotNull
  public static TokenSet getKeywordTokens() {
    return KEYWORDS;
  }

  @NotNull
  public static TokenSet getOperatorTokens() {
    return OPERATORS;
  }

  @NotNull
  public static TokenSet getPreprocessorTokens() {
    return PREPROCESSOR_DIRECTIVES;
  }

  @NotNull
  public static TokenSet getBracketTokens() {
    return MATCHED_PAIRS;
  }

  @NotNull
  public static TokenSet getStringLiteralTokens() {
    return STRING_LITERALS;
  }

  private SpTokenTypes() {
  }

}
