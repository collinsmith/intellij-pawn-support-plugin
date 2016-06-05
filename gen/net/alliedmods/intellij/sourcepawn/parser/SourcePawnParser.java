// This is a generated file. Not intended for manual editing.
package net.alliedmods.intellij.sourcepawn.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.*;
import static net.alliedmods.intellij.sourcepawn.parser.SourcePawnParserUtils.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

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
    else if (t == PRAGMA_DIRECTIVE) {
      r = pragma_directive(b, 0);
    }
    else if (t == PREPROCESSOR_DIRECTIVE) {
      r = preprocessor_directive(b, 0);
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
  // character_string
  static boolean character_string_proxy(PsiBuilder b, int l) {
    return consumeToken(b, CHARACTER_STRING);
  }

  /* ********************************************************** */
  // hash preprocessor_directive
  //  | expression
  static boolean compilation_unit(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit")) return false;
    if (!nextTokenIs(b, "", HASH, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compilation_unit_0(b, l + 1);
    if (!r) r = expression(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // hash preprocessor_directive
  private static boolean compilation_unit_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, HASH);
    r = r && preprocessor_directive(b, l + 1);
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
    if (!r) r = parsePawnCharacterLiteral(b, l + 1, character_string_proxy_parser_);
    if (!r) r = consumeToken(b, BINARY_LITERAL);
    if (!r) r = consumeToken(b, DECIMAL_LITERAL);
    if (!r) r = consumeToken(b, HEXADECIMAL_LITERAL);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // pragma_semicolon <<parsePragmaSemicolons number>>
  //   | pragma_ctrlchar <<parsePragmaCtrlchar number>>
  public static boolean pragma_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_directive")) return false;
    if (!nextTokenIs(b, "<pragma directive>", PRAGMA_CTRLCHAR, PRAGMA_SEMICOLON)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PRAGMA_DIRECTIVE, "<pragma directive>");
    r = pragma_directive_0(b, l + 1);
    if (!r) r = pragma_directive_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // pragma_semicolon <<parsePragmaSemicolons number>>
  private static boolean pragma_directive_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_directive_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PRAGMA_SEMICOLON);
    r = r && parsePragmaSemicolons(b, l + 1, number_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  // pragma_ctrlchar <<parsePragmaCtrlchar number>>
  private static boolean pragma_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_directive_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PRAGMA_CTRLCHAR);
    r = r && parsePragmaCtrlchar(b, l + 1, number_parser_);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // pragma pragma_directive
  public static boolean preprocessor_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_directive")) return false;
    if (!nextTokenIs(b, PRAGMA)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PRAGMA);
    r = r && pragma_directive(b, l + 1);
    exit_section_(b, m, PREPROCESSOR_DIRECTIVE, r);
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
  // !( identifier | hash )
  static boolean root_recover(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_recover")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !root_recover_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // identifier | hash
  private static boolean root_recover_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_recover_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, HASH);
    exit_section_(b, m, null, r);
    return r;
  }

  final static Parser character_string_proxy_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return character_string_proxy(b, l + 1);
    }
  };
  final static Parser number_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return number(b, l + 1);
    }
  };
  final static Parser root_recover_parser_ = new Parser() {
    public boolean parse(PsiBuilder b, int l) {
      return root_recover(b, l + 1);
    }
  };
}
