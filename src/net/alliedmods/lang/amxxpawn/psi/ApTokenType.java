package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.JavaTokenType;
import com.intellij.psi.TokenType;
import com.intellij.psi.impl.source.tree.JavaDocElementType;
import com.intellij.psi.tree.IElementType;

@SuppressWarnings("SpellCheckingInspection")
public interface ApTokenType extends TokenType {

  IElementType NEW_LINE = new IApElementType("NEW_LINE");

  IElementType IDENTIFIER = new IApElementType("IDENTIFIER");
  IElementType C_STYLE_COMMENT = JavaTokenType.C_STYLE_COMMENT;
  IElementType END_OF_LINE_COMMENT = JavaTokenType.END_OF_LINE_COMMENT;
  IElementType DOC_COMMENT = JavaDocElementType.DOC_COMMENT;

  IElementType CELL_LITERAL = new IApElementType("CELL_LITERAL");
  IElementType RATIONAL_LITERAL = new IApElementType("RATIONAL_LITERAL");
  IElementType CHARACTER_LITERAL = new IApElementType("CHARACTER_LITERAL");
  IElementType STRING_LITERAL = new IApElementType("STRING_LITERAL");

  IElementType TRUE_KEYWORD = new IApElementType("TRUE_KEYWORD");
  IElementType FALSE_KEYWORD = new IApElementType("FALSE_KEYWORD");

  IElementType ASSERT_KEYWORD = new IApElementType("ASSERT_KEYWORD");
  IElementType BREAK_KEYWORD = new IApElementType("BREAK_KEYWORD");
  IElementType CASE_KEYWORD = new IApElementType("CASE_KEYWORD");
  IElementType CHAR_KEYWORD = new IApElementType("CHAR_KEYWORD");
  IElementType CONST_KEYWORD = new IApElementType("CONST_KEYWORD");
  IElementType CONTINUE_KEYWORD = new IApElementType("CONTINUE_KEYWORD");
  IElementType DEFAULT_KEYWORD = new IApElementType("DEFAULT_KEYWORD");
  IElementType DEFINED_KEYWORD = new IApElementType("DEFINED_KEYWORD");
  IElementType DO_KEYWORD = new IApElementType("DO_KEYWORD");
  IElementType ELSE_KEYWORD = new IApElementType("ELSE_KEYWORD");
  IElementType ENUM_KEYWORD = new IApElementType("ENUM_KEYWORD");
  IElementType EXIT_KEYWORD = new IApElementType("EXIT_KEYWORD");
  IElementType FOR_KEYWORD = new IApElementType("FOR_KEYWORD");
  IElementType FORWARD_KEYWORD = new IApElementType("FORWARD_KEYWORD");
  IElementType GOTO_KEYWORD = new IApElementType("GOTO_KEYWORD");
  IElementType IF_KEYWORD = new IApElementType("IF_KEYWORD");
  IElementType NATIVE_KEYWORD = new IApElementType("NATIVE_KEYWORD");
  IElementType NEW_KEYWORD = new IApElementType("NEW_KEYWORD");
  IElementType OPERATOR_KEYWORD = new IApElementType("OPERATOR_KEYWORD");
  IElementType PUBLIC_KEYWORD = new IApElementType("PUBLIC_KEYWORD");
  IElementType RETURN_KEYWORD = new IApElementType("RETURN_KEYWORD");
  IElementType SIZEOF_KEYWORD = new IApElementType("SIZEOF_KEYWORD");
  IElementType SLEEP_KEYWORD = new IApElementType("SLEEP_KEYWORD");
  IElementType STATE_KEYWORD = new IApElementType("STATE_KEYWORD");
  IElementType STATIC_KEYWORD = new IApElementType("STATIC_KEYWORD");
  IElementType STOCK_KEYWORD = new IApElementType("STOCK_KEYWORD");
  IElementType SWITCH_KEYWORD = new IApElementType("SWITCH_KEYWORD");
  IElementType TAGOF_KEYWORD = new IApElementType("TAGOF_KEYWORD");
  IElementType WHILE_KEYWORD = new IApElementType("WHILE_KEYWORD");

  IElementType LPARENTH = new IApElementType("LPARENTH");
  IElementType RPARENTH = new IApElementType("RPARENTH");
  IElementType LBRACE = new IApElementType("LBRACE");
  IElementType RBRACE = new IApElementType("RBRACE");
  IElementType LBRACKET = new IApElementType("LBRACKET");
  IElementType RBRACKET = new IApElementType("RBRACKET");
  IElementType SEMICOLON = new IApElementType("SEMICOLON");
  IElementType COMMA = new IApElementType("COMMA");
  IElementType DOT = new IApElementType("DOT");
  IElementType ELLIPSIS = new IApElementType("ELLIPSIS");
  IElementType AT = new IApElementType("AT");

  IElementType EQ = new IApElementType("EQ");
  IElementType GT = new IApElementType("GT");
  IElementType LT = new IApElementType("LT");
  IElementType EXCL = new IApElementType("EXCL");
  IElementType TILDE = new IApElementType("TILDE");
  IElementType QUEST = new IApElementType("QUEST");
  IElementType COLON = new IApElementType("COLON");
  IElementType PLUS = new IApElementType("PLUS");
  IElementType MINUS = new IApElementType("MINUS");
  IElementType ASTERISK = new IApElementType("ASTERISK");
  IElementType DIV = new IApElementType("DIV");
  IElementType AND = new IApElementType("AND");
  IElementType OR = new IApElementType("OR");
  IElementType XOR = new IApElementType("XOR");
  IElementType PERC = new IApElementType("PERC");

  IElementType EQEQ = new IApElementType("EQEQ");
  IElementType LE = new IApElementType("LE");
  IElementType GE = new IApElementType("GE");
  IElementType NE = new IApElementType("NE");
  IElementType ANDAND = new IApElementType("ANDAND");
  IElementType OROR = new IApElementType("OROR");
  IElementType PLUSPLUS = new IApElementType("PLUSPLUS");
  IElementType MINUSMINUS = new IApElementType("MINUSMINUS");
  IElementType LTLT = new IApElementType("LTLT");
  IElementType GTGT = new IApElementType("GTGT");
  IElementType GTGTGT = new IApElementType("GTGTGT");
  IElementType PLUSEQ = new IApElementType("PLUSEQ");
  IElementType MINUSEQ = new IApElementType("MINUSEQ");
  IElementType ASTERISKEQ = new IApElementType("ASTERISKEQ");
  IElementType DIVEQ = new IApElementType("DIVEQ");
  IElementType ANDEQ = new IApElementType("ANDEQ");
  IElementType OREQ = new IApElementType("OREQ");
  IElementType XOREQ = new IApElementType("XOREQ");
  IElementType PERCEQ = new IApElementType("PERCEQ");
  IElementType LTLTEQ = new IApElementType("LTLTEQ");
  IElementType GTGTEQ = new IApElementType("GTGTEQ");
  IElementType GTGTGTEQ = new IApElementType("GTGTGTEQ");

  // Operators specific to AMXX Pawn (the above are pretty generic to all high-level languages)
  IElementType RANGE = new IApElementType("RANGE");
  IElementType UNDER = new IApElementType("UNDER");
  IElementType DOUBLE_COLON = new IApElementType("DOUBLE_COLON");

  IElementType PREPROCESSOR = new IPreprocessorElementType("PREPROCESSOR");
  IElementType ESCAPING_SLASH = new IPreprocessorElementType("ESCAPING_SLASH");

}
