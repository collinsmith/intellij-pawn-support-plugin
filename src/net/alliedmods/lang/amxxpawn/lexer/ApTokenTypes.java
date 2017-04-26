package net.alliedmods.lang.amxxpawn.lexer;

import com.intellij.psi.TokenType;
import com.intellij.psi.impl.source.tree.JavaDocElementType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import net.alliedmods.lang.amxxpawn.psi.ApElementType;

public interface ApTokenTypes {

  IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;

  IElementType NEW_LINE = TokenType.NEW_LINE_INDENT;
  IElementType WHITE_SPACE = TokenType.WHITE_SPACE;

  TokenSet WHITE_SPACES = TokenSet.create(WHITE_SPACE);

  IElementType IDENTIFIER = new ApElementType("IDENTIFIER");

  IElementType C_STYLE_COMMENT = new ApElementType("C_STYLE_COMMENT");
  IElementType END_OF_LINE_COMMENT = new ApElementType("END_OF_LINE_COMMENT");
  IElementType DOC_COMMENT = JavaDocElementType.DOC_COMMENT;

  TokenSet COMMENTS = TokenSet.create(C_STYLE_COMMENT, END_OF_LINE_COMMENT, DOC_COMMENT);

  // Literals
  IElementType TRUE = new ApElementType("TRUE");
  IElementType FALSE = new ApElementType("FALSE");

  IElementType NUMERIC_LITERAL = new ApElementType("-integer value-");
  IElementType RATIONAL_LITERAL = new ApElementType("-rational value-");
  IElementType CHARACTER_LITERAL = new ApElementType("-integer value-");
  IElementType STRING_LITERAL = new ApElementType("-string-");

  TokenSet STRING_LITERALS = TokenSet.create(CHARACTER_LITERAL, STRING_LITERAL);

  // Operators
  IElementType PLUS = new ApElementType("PLUS");// +
  IElementType MINUS = new ApElementType("MINUS");// -
  IElementType MULT = new ApElementType("MULT");// *
  IElementType DIV = new ApElementType("DIV");// /
  IElementType PERC = new ApElementType("PERC");// %
  IElementType GT = new ApElementType("GT");// >
  IElementType LT = new ApElementType("LT");// <
  IElementType EXCL = new ApElementType("EXCL");// !
  IElementType TILDE = new ApElementType("TILDE");// ~
  IElementType EQ = new ApElementType("EQ");// =
  IElementType COLON = new ApElementType("COLON");// :
  IElementType COMMA = new ApElementType("COMMA");// ,
  IElementType DOT = new ApElementType("DOT");// .
  IElementType UNDERSCORE = new ApElementType("UNDERSCORE");// _
  IElementType AND = new ApElementType("AND");// &
  IElementType XOR = new ApElementType("OR");// ^
  IElementType OR = new ApElementType("OR");// |
  IElementType QUESTION = new ApElementType("QUESTION");// ?

  // SEMICOLON is a special use-case because it's optional
  IElementType TERM = new ApElementType("TERM");
  IElementType ENDEXPR = new ApElementType("ENDEXPR");

  // Escaping Slash is also a special use-case (line continuation), this is not the same as the ctrl char
  IElementType ESCAPING_SLASH = new ApElementType("ESCAPING_SLASH");// ?

  // Multi-Character Operators
  IElementType MULTEQ = new ApElementType("MULTEQ");// *=
  IElementType DIVEQ = new ApElementType("DIVEQ");// /=
  IElementType PERCEQ = new ApElementType("PERCEQ");// %=
  IElementType PLUSEQ = new ApElementType("PLUSEQ");// +=
  IElementType MINUSEQ = new ApElementType("MINUSEQ");// -=
  IElementType LTLTEQ = new ApElementType("LTLTEQ");// <<=
  IElementType GTGTGTEQ = new ApElementType("GTGTGTEQ");// >>>=
  IElementType GTGTEQ = new ApElementType("GTGTEQ");// >>=
  IElementType ANDEQ = new ApElementType("ANDEQ");// &=
  IElementType XOREQ = new ApElementType("XOREQ");// ^=
  IElementType OREQ = new ApElementType("OREQ");// |=
  IElementType OROR = new ApElementType("OROR");// ||
  IElementType ANDAND = new ApElementType("ANDAND");// &&
  IElementType EQEQ = new ApElementType("EQEQ");// ==
  IElementType NE = new ApElementType("NE");// !=
  IElementType LE = new ApElementType("LE");// <=
  IElementType GE = new ApElementType("GE");// >=
  IElementType LTLT = new ApElementType("LTLT");// <<
  IElementType GTGTGT = new ApElementType("GTGTGT");// >>>
  IElementType GTGT = new ApElementType("GTGT");// >>
  IElementType PLUSPLUS = new ApElementType("PLUSPLUS");// ++
  IElementType MINUSMINUS = new ApElementType("MINUSMINUS");// --
  IElementType DOTDOTDOT = new ApElementType("DOTDOTDOT");// ...
  IElementType DOTDOT = new ApElementType("DOTDOT");// ..
  IElementType QUAL = new ApElementType("QUAL");// ::

  TokenSet OPERATORS = TokenSet.create(PLUS, MINUS, MULT, DIV, PERC, GT, LT, EXCL, TILDE, EQ, COLON,
      /*COMMA,*/ DOT, UNDERSCORE, AND, XOR, OR, QUESTION, MULTEQ, DIVEQ, PERCEQ, MINUSEQ, LTLTEQ,
      GTGTGTEQ, GTGTEQ, ANDEQ, XOREQ, OREQ, OROR, ANDAND, EQEQ, NE, LE, GE, LTLT, GTGTGT, GTGT,
      PLUSPLUS, MINUSMINUS, DOTDOTDOT, DOTDOT, QUAL);


  TokenSet OVERLOADABLE_OPERATORS = TokenSet.create(PLUS, MINUS, MULT, DIV, PERC, GT, LT, EXCL,
      TILDE, EQ, PLUSPLUS, MINUSMINUS, EQEQ, NE, LE, GE);

  // Matched pairs
  IElementType LBRACE = new ApElementType("LBRACE");// {
  IElementType RBRACE = new ApElementType("RBRACE");// }
  IElementType LBRACKET = new ApElementType("LBRACKET");// [
  IElementType RBRACKET = new ApElementType("RBRACKET");// ]
  IElementType LPAREN = new ApElementType("LPAREN");// (
  IElementType RPAREN = new ApElementType("RPAREN");// )

  // Reserved Words
  IElementType ASSERT = new ApElementType("ASSERT");
  IElementType BREAK = new ApElementType("BREAK");
  IElementType CASE = new ApElementType("CASE");
  IElementType CHAR = new ApElementType("CHAR");
  IElementType CONST = new ApElementType("CONST");
  IElementType CONTINUE = new ApElementType("CONTINUE");
  IElementType DEFAULT = new ApElementType("DEFAULT");
  IElementType DEFINED = new ApElementType("DEFINED");
  IElementType DO = new ApElementType("DO");
  IElementType ELSE = new ApElementType("ELSE");
  IElementType ENUM = new ApElementType("ENUM");
  IElementType EXIT = new ApElementType("EXIT");
  IElementType FOR = new ApElementType("FOR");
  IElementType FORWARD = new ApElementType("FORWARD");
  IElementType GOTO = new ApElementType("GOTO");
  IElementType IF = new ApElementType("IF");
  IElementType NATIVE = new ApElementType("NATIVE");
  IElementType NEW = new ApElementType("NEW");
  IElementType OPERATOR = new ApElementType("OPERATOR");
  IElementType PUBLIC = new ApElementType("PUBLIC");
  IElementType RETURN = new ApElementType("RETURN");
  IElementType SIZEOF = new ApElementType("SIZEOF");
  IElementType SLEEP = new ApElementType("SLEEP");
  IElementType STATE = new ApElementType("STATE");
  IElementType STATIC = new ApElementType("STATIC");
  IElementType STOCK = new ApElementType("STOCK");
  IElementType SWITCH = new ApElementType("SWITCH");
  IElementType TAGOF = new ApElementType("TAGOF");
  IElementType WHILE = new ApElementType("WHILE");

  TokenSet KEYWORDS = TokenSet.create(ASSERT, BREAK, CASE, CHAR, CONST, CONTINUE, DEFAULT, DEFINED,
      DO, ELSE, ENUM, EXIT, FOR, FORWARD, GOTO, IF, NATIVE, NEW, OPERATOR, PUBLIC, RETURN, SIZEOF,
      SLEEP, STATE, STATIC, STOCK, SWITCH, TAGOF, WHILE);

  // Compiler Directives
  IElementType PRE_KEYWORD = new ApElementType("PRE_KEYWORD");
}
