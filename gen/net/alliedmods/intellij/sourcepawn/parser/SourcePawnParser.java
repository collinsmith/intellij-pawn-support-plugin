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
import static net.alliedmods.intellij.sourcepawn.lexer.SourcePawnTokenTypes.*;

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
    r = parse_root_(t, b, 0);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return root(b, l + 1);
  }

  /* ********************************************************** */
  // (WHITESPACE? NEW_LINE)+
  //   | HASH preprocessor_directive NEW_LINE
  static boolean compilation_unit(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compilation_unit_0(b, l + 1);
    if (!r) r = compilation_unit_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (WHITESPACE? NEW_LINE)+
  private static boolean compilation_unit_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compilation_unit_0_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!compilation_unit_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "compilation_unit_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // WHITESPACE? NEW_LINE
  private static boolean compilation_unit_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compilation_unit_0_0_0(b, l + 1);
    r = r && consumeToken(b, NEW_LINE);
    exit_section_(b, m, null, r);
    return r;
  }

  // WHITESPACE?
  private static boolean compilation_unit_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit_0_0_0")) return false;
    consumeToken(b, WHITESPACE);
    return true;
  }

  // HASH preprocessor_directive NEW_LINE
  private static boolean compilation_unit_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, HASH);
    r = r && preprocessor_directive(b, l + 1);
    r = r && consumeToken(b, NEW_LINE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_INCLUDE preprocessor_include_file
  //   | PREPROCESSOR_TRYINCLUDE preprocessor_include_file
  static boolean preprocessor_directive(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_directive")) return false;
    if (!nextTokenIs(b, "", PREPROCESSOR_INCLUDE, PREPROCESSOR_TRYINCLUDE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = preprocessor_directive_0(b, l + 1);
    if (!r) r = preprocessor_directive_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PREPROCESSOR_INCLUDE preprocessor_include_file
  private static boolean preprocessor_directive_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_directive_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_INCLUDE);
    r = r && preprocessor_include_file(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PREPROCESSOR_TRYINCLUDE preprocessor_include_file
  private static boolean preprocessor_directive_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_directive_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_TRYINCLUDE);
    r = r && preprocessor_include_file(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // LT WHITESPACE? PREPROCESSOR_INCLUDE_SYSTEMFILE WHITESPACE? GT
  //   | PREPROCESSOR_INCLUDE_RELATIVEFILE
  static boolean preprocessor_include_file(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_include_file")) return false;
    if (!nextTokenIs(b, "", LT, PREPROCESSOR_INCLUDE_RELATIVEFILE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = preprocessor_include_file_0(b, l + 1);
    if (!r) r = consumeToken(b, PREPROCESSOR_INCLUDE_RELATIVEFILE);
    exit_section_(b, m, null, r);
    return r;
  }

  // LT WHITESPACE? PREPROCESSOR_INCLUDE_SYSTEMFILE WHITESPACE? GT
  private static boolean preprocessor_include_file_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_include_file_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LT);
    r = r && preprocessor_include_file_0_1(b, l + 1);
    r = r && consumeToken(b, PREPROCESSOR_INCLUDE_SYSTEMFILE);
    r = r && preprocessor_include_file_0_3(b, l + 1);
    r = r && consumeToken(b, GT);
    exit_section_(b, m, null, r);
    return r;
  }

  // WHITESPACE?
  private static boolean preprocessor_include_file_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_include_file_0_1")) return false;
    consumeToken(b, WHITESPACE);
    return true;
  }

  // WHITESPACE?
  private static boolean preprocessor_include_file_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_include_file_0_3")) return false;
    consumeToken(b, WHITESPACE);
    return true;
  }

  /* ********************************************************** */
  // root_item *
  static boolean root(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root")) return false;
    int c = current_position_(b);
    while (true) {
      if (!root_item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "root", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  /* ********************************************************** */
  // !<<eof>> compilation_unit
  static boolean root_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_item")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = root_item_0(b, l + 1);
    r = r && compilation_unit(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
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

}
