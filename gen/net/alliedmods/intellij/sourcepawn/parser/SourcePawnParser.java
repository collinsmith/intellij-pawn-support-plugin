// This is a generated file. Not intended for manual editing.
package net.alliedmods.intellij.sourcepawn.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;

import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.Parser;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.TRUE_CONDITION;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils._COLLAPSE_;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils._NONE_;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils._NOT_;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.adapt_builder_;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.consumeToken;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.consumeTokens;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.current_position_;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.empty_element_parsed_guard_;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.enter_section_;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.eof;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.exit_section_;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.nextTokenIs;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.parseExpressionEnding;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.parsePawnCharacterLiteral;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.recursion_guard_;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.resetState;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.ASSIGN;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.BINARY_LITERAL;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.BOOLEAN_LITERAL;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.CHARACTER_STRING;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.DECIMAL_LITERAL;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.EXPRESSION;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.FALSE;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.HEXADECIMAL_LITERAL;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.IDENTIFIER;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.NUMBER;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.PREPROCESSOR_DIRECTIVE;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.TRUE;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class SourcePawnParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t == BOOLEAN_LITERAL) {
      r = boolean_literal(b, 0);
    }
    else if (t == EXPRESSION) {
      r = expression(b, 0);
    }
    else if (t == NUMBER) {
      r = number(b, 0);
    }
    else {
      r = parse_root_(t, b, 0);
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return root(b, l + 1);
  }

  /* ********************************************************** */
  // true | false
  public static boolean boolean_literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "boolean_literal")) return false;
    if (!nextTokenIs(b, "<boolean literal>", FALSE, TRUE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BOOLEAN_LITERAL, "<boolean literal>");
    r = consumeToken(b, TRUE);
    if (!r) r = consumeToken(b, FALSE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // preprocessor_directive
  //  | expression
  static boolean compilation_unit(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit")) return false;
    if (!nextTokenIs(b, "", IDENTIFIER, PREPROCESSOR_DIRECTIVE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_DIRECTIVE);
    if (!r) r = expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // identifier assign number check_expression_ending
  public static boolean expression(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "expression")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, IDENTIFIER, ASSIGN);
    r = r && number(b, l + 1);
    r = r && parseExpressionEnding(b, l + 1);
    exit_section_(b, m, EXPRESSION, r);
    return r;
  }

  /* ********************************************************** */
  // boolean_literal
  //   | character_literal
  //   | binary_literal
  //   | decimal_literal
  //   | hexadecimal_literal
  public static boolean number(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "number")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, NUMBER, "<number>");
    r = boolean_literal(b, l + 1);
    if (!r) r = parsePawnCharacterLiteral(b, l + 1, number_1_0_parser_);
    if (!r) r = consumeToken(b, BINARY_LITERAL);
    if (!r) r = consumeToken(b, DECIMAL_LITERAL);
    if (!r) r = consumeToken(b, HEXADECIMAL_LITERAL);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // <<resetState>> root_item *
  static boolean root(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = resetState(b, l + 1);
    r = r && root_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // root_item *
  private static boolean root_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!root_item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "root_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // !<<eof>> compilation_unit
  static boolean root_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_item")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = root_item_0(b, l + 1);
    p = r; // pin = 1
    r = r && compilation_unit(b, l + 1);
    exit_section_(b, l, m, r, p, root_recover_parser_);
    return r || p;
  }

  // !<<eof>>
  private static boolean root_item_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_item_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !eof(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // !( compilation_unit )
  static boolean root_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !root_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ( compilation_unit )
  private static boolean root_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compilation_unit(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  final static Parser number_1_0_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return consumeToken(b, CHARACTER_STRING);
    }
  };
  final static Parser root_recover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return root_recover(b, l + 1);
    }
  };
}
