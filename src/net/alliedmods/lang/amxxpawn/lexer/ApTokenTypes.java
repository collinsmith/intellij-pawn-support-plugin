package net.alliedmods.lang.amxxpawn.lexer;

import com.intellij.psi.JavaTokenType;
import com.intellij.psi.TokenType;
import com.intellij.psi.impl.source.tree.JavaDocElementType;
import com.intellij.psi.tree.IElementType;

@SuppressWarnings("SpellCheckingInspection")
public interface ApTokenTypes extends TokenType {

  IElementType SEMICOLON = new ApTokenType("SEMICOLON");
  IElementType SEMICOLON_SYNTHETIC = new ApTokenType("SEMICOLON_SYNTHETIC");

  IElementType IDENTIFIER = new ApTokenType("-identifier-");
  IElementType C_STYLE_COMMENT = JavaTokenType.C_STYLE_COMMENT;
  IElementType END_OF_LINE_COMMENT = JavaTokenType.END_OF_LINE_COMMENT;
  IElementType DOC_COMMENT = JavaDocElementType.DOC_COMMENT;

  IElementType CELL_LITERAL = new ApTokenType("CELL_LITERAL");
  IElementType RATIONAL_LITERAL = new ApTokenType("RATIONAL_LITERAL");
  IElementType CHARACTER_LITERAL = new ApTokenType("CHARACTER_LITERAL");
  IElementType STRING_LITERAL = new ApTokenType("STRING_LITERAL");
  IElementType RAW_STRING_LITERAL = new ApTokenType("RAW_STRING_LITERAL");
  IElementType PACKED_STRING_LITERAL = new ApTokenType("PACKED_STRING_LITERAL");
  IElementType PACKED_RAW_STRING_LITERAL = new ApTokenType("PACKED_RAW_STRING_LITERAL");

  IElementType TRUE_KEYWORD = new ApTokenType("TRUE_KEYWORD");
  IElementType FALSE_KEYWORD = new ApTokenType("FALSE_KEYWORD");

  IElementType ASSERT_KEYWORD = new ApTokenType("ASSERT_KEYWORD");
  IElementType BREAK_KEYWORD = new ApTokenType("BREAK_KEYWORD");
  IElementType CASE_KEYWORD = new ApTokenType("CASE_KEYWORD");
  IElementType CHAR_KEYWORD = new ApTokenType("CHAR_KEYWORD");
  IElementType CONST_KEYWORD = new ApTokenType("CONST_KEYWORD");
  IElementType CONTINUE_KEYWORD = new ApTokenType("CONTINUE_KEYWORD");
  IElementType DEFAULT_KEYWORD = new ApTokenType("DEFAULT_KEYWORD");
  IElementType DEFINED_KEYWORD = new ApTokenType("DEFINED_KEYWORD");
  IElementType DO_KEYWORD = new ApTokenType("DO_KEYWORD");
  IElementType ELSE_KEYWORD = new ApTokenType("ELSE_KEYWORD");
  IElementType ENUM_KEYWORD = new ApTokenType("ENUM_KEYWORD");
  IElementType EXIT_KEYWORD = new ApTokenType("EXIT_KEYWORD");
  IElementType FOR_KEYWORD = new ApTokenType("FOR_KEYWORD");
  IElementType FORWARD_KEYWORD = new ApTokenType("FORWARD_KEYWORD");
  IElementType GOTO_KEYWORD = new ApTokenType("GOTO_KEYWORD");
  IElementType IF_KEYWORD = new ApTokenType("IF_KEYWORD");
  IElementType NATIVE_KEYWORD = new ApTokenType("NATIVE_KEYWORD");
  IElementType NEW_KEYWORD = new ApTokenType("NEW_KEYWORD");
  IElementType OPERATOR_KEYWORD = new ApTokenType("OPERATOR_KEYWORD");
  IElementType PUBLIC_KEYWORD = new ApTokenType("PUBLIC_KEYWORD");
  IElementType RETURN_KEYWORD = new ApTokenType("RETURN_KEYWORD");
  IElementType SIZEOF_KEYWORD = new ApTokenType("SIZEOF_KEYWORD");
  IElementType SLEEP_KEYWORD = new ApTokenType("SLEEP_KEYWORD");
  IElementType STATE_KEYWORD = new ApTokenType("STATE_KEYWORD");
  IElementType STATIC_KEYWORD = new ApTokenType("STATIC_KEYWORD");
  IElementType STOCK_KEYWORD = new ApTokenType("STOCK_KEYWORD");
  IElementType SWITCH_KEYWORD = new ApTokenType("SWITCH_KEYWORD");
  IElementType TAGOF_KEYWORD = new ApTokenType("TAGOF_KEYWORD");
  IElementType WHILE_KEYWORD = new ApTokenType("WHILE_KEYWORD");

  IElementType LPARENTH = new ApTokenType("LPARENTH");
  IElementType RPARENTH = new ApTokenType("RPARENTH");
  IElementType LBRACE = new ApTokenType("LBRACE");
  IElementType RBRACE = new ApTokenType("RBRACE");
  IElementType LBRACKET = new ApTokenType("LBRACKET");
  IElementType RBRACKET = new ApTokenType("RBRACKET");
  IElementType COMMA = new ApTokenType("COMMA");
  IElementType DOT = new ApTokenType("DOT");
  IElementType ELLIPSIS = new ApTokenType("ELLIPSIS");
  IElementType AT = new ApTokenType("AT");

  IElementType EQ = new ApTokenType("EQ");
  IElementType GT = new ApTokenType("GT");
  IElementType LT = new ApTokenType("LT");
  IElementType EXCL = new ApTokenType("EXCL");
  IElementType TILDE = new ApTokenType("TILDE");
  IElementType QUEST = new ApTokenType("QUEST");
  IElementType COLON = new ApTokenType("COLON");
  IElementType PLUS = new ApTokenType("PLUS");
  IElementType MINUS = new ApTokenType("MINUS");
  IElementType ASTERISK = new ApTokenType("ASTERISK");
  IElementType DIV = new ApTokenType("DIV");
  IElementType AND = new ApTokenType("AND");
  IElementType OR = new ApTokenType("OR");
  IElementType XOR = new ApTokenType("XOR");
  IElementType PERC = new ApTokenType("PERC");

  IElementType EQEQ = new ApTokenType("EQEQ");
  IElementType LE = new ApTokenType("LE");
  IElementType GE = new ApTokenType("GE");
  IElementType NE = new ApTokenType("NE");
  IElementType ANDAND = new ApTokenType("ANDAND");
  IElementType OROR = new ApTokenType("OROR");
  IElementType PLUSPLUS = new ApTokenType("PLUSPLUS");
  IElementType MINUSMINUS = new ApTokenType("MINUSMINUS");
  IElementType LTLT = new ApTokenType("LTLT");
  IElementType GTGT = new ApTokenType("GTGT");
  IElementType GTGTGT = new ApTokenType("GTGTGT");
  IElementType PLUSEQ = new ApTokenType("PLUSEQ");
  IElementType MINUSEQ = new ApTokenType("MINUSEQ");
  IElementType ASTERISKEQ = new ApTokenType("ASTERISKEQ");
  IElementType DIVEQ = new ApTokenType("DIVEQ");
  IElementType ANDEQ = new ApTokenType("ANDEQ");
  IElementType OREQ = new ApTokenType("OREQ");
  IElementType XOREQ = new ApTokenType("XOREQ");
  IElementType PERCEQ = new ApTokenType("PERCEQ");
  IElementType LTLTEQ = new ApTokenType("LTLTEQ");
  IElementType GTGTEQ = new ApTokenType("GTGTEQ");
  IElementType GTGTGTEQ = new ApTokenType("GTGTGTEQ");

  // Operators specific to AMXX Pawn (the above are pretty generic to all high-level languages)
  IElementType RANGE = new ApTokenType("RANGE");
  IElementType UNDER = new ApTokenType("UNDER");
  IElementType DOUBLE_COLON = new ApTokenType("DOUBLE_COLON");

  IElementType HASH = new PreprocessorTokenType("HASH");
  IElementType ESCAPING_SLASH = new PreprocessorTokenType("ESCAPING_SLASH");

  IElementType ASSERT_DIRECTIVE = new PreprocessorTokenType("ASSERT_DIRECTIVE");
  IElementType DEFINE_DIRECTIVE = new PreprocessorTokenType("DEFINE_DIRECTIVE");
  IElementType ELSE_DIRECTIVE = new PreprocessorTokenType("ELSE_DIRECTIVE");
  IElementType ELSEIF_DIRECTIVE = new PreprocessorTokenType("ELSEIF_DIRECTIVE");
  IElementType EMIT_DIRECTIVE = new PreprocessorTokenType("EMIT_DIRECTIVE");
  IElementType ENDIF_DIRECTIVE = new PreprocessorTokenType("ENDIF_DIRECTIVE");
  IElementType ENDINPUT_DIRECTIVE = new PreprocessorTokenType("ENDINPUT_DIRECTIVE");
  IElementType ENDSCRIPT_DIRECTIVE = new PreprocessorTokenType("ENDSCRIPT_DIRECTIVE");
  IElementType ERROR_DIRECTIVE = new PreprocessorTokenType("ERROR_DIRECTIVE");
  IElementType FILE_DIRECTIVE = new PreprocessorTokenType("FILE_DIRECTIVE");
  IElementType IF_DIRECTIVE = new PreprocessorTokenType("IF_DIRECTIVE");
  IElementType INCLUDE_DIRECTIVE = new PreprocessorTokenType("INCLUDE_DIRECTIVE");
  IElementType LINE_DIRECTIVE = new PreprocessorTokenType("LINE_DIRECTIVE");
  IElementType PRAGMA_DIRECTIVE = new PreprocessorTokenType("PRAGMA_DIRECTIVE");
  IElementType TRYINCLUDE_DIRECTIVE = new PreprocessorTokenType("TRYINCLUDE_DIRECTIVE");
  IElementType UNDEF_DIRECTIVE = new PreprocessorTokenType("UNDEF_DIRECTIVE");

  //IElementType DEFINED_KEYWORD = new PreprocessorTokenType("DEFINED_KEYWORD");

  IElementType INCLUDE_REFERENCE = new PreprocessorTokenType("INCLUDE_REFERENCE");
  IElementType PRAGMA_IDENTIFIER = new PreprocessorTokenType("PRAGMA_IDENTIFIER");
  IElementType DEPRECATION_REASON = new PreprocessorTokenType("DEPRECATION_REASON");
}
