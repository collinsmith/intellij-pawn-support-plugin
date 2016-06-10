// This is a generated file. Not intended for manual editing.
package net.sourcemod.sourcepawn.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static net.sourcemod.sourcepawn.psi.SpElementTypes.*;
import static net.sourcemod.sourcepawn.parser.SpParserUtils.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;
import static net.sourcemod.sourcepawn.lexer.SpTokenTypes.*;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class SpParser implements PsiParser, LightPsiParser {

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
  // (WHITE_SPACE? NEW_LINE)+
  static boolean compilation_unit(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit")) return false;
    if (!nextTokenIs(b, "", NEW_LINE, WHITE_SPACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compilation_unit_0(b, l + 1);
    int c = current_position_(b);
    while (r) {
      if (!compilation_unit_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "compilation_unit", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // WHITE_SPACE? NEW_LINE
  private static boolean compilation_unit_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compilation_unit_0_0(b, l + 1);
    r = r && consumeToken(b, NEW_LINE);
    exit_section_(b, m, null, r);
    return r;
  }

  // WHITE_SPACE?
  private static boolean compilation_unit_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit_0_0")) return false;
    consumeToken(b, WHITE_SPACE);
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
    if (!nextTokenIs(b, "", NEW_LINE, WHITE_SPACE)) return false;
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
