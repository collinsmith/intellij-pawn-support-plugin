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
    if (t == PRAGMA) {
      r = pragma(b, 0);
    }
    else if (t == PREPROCESSOR) {
      r = preprocessor(b, 0);
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
  // NEW_LINE+
  //   | HASH preprocessor (NEW_LINE | <<eof>>)
  static boolean compilation_unit(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit")) return false;
    if (!nextTokenIs(b, "", HASH, NEW_LINE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = compilation_unit_0(b, l + 1);
    if (!r) r = compilation_unit_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEW_LINE+
  private static boolean compilation_unit_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NEW_LINE);
    int c = current_position_(b);
    while (r) {
      if (!consumeToken(b, NEW_LINE)) break;
      if (!empty_element_parsed_guard_(b, "compilation_unit_0", c)) break;
      c = current_position_(b);
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // HASH preprocessor (NEW_LINE | <<eof>>)
  private static boolean compilation_unit_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, HASH);
    r = r && preprocessor(b, l + 1);
    r = r && compilation_unit_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEW_LINE | <<eof>>
  private static boolean compilation_unit_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "compilation_unit_1_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, NEW_LINE);
    if (!r) r = eof(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER (LPAREN define_pattern_args? RPAREN)?
  static boolean define_pattern(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "define_pattern")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && define_pattern_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (LPAREN define_pattern_args? RPAREN)?
  private static boolean define_pattern_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "define_pattern_1")) return false;
    define_pattern_1_0(b, l + 1);
    return true;
  }

  // LPAREN define_pattern_args? RPAREN
  private static boolean define_pattern_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "define_pattern_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && define_pattern_1_0_1(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // define_pattern_args?
  private static boolean define_pattern_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "define_pattern_1_0_1")) return false;
    define_pattern_args(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // (PERCENT NUMBER_LITERAL) (COMMA PERCENT NUMBER_LITERAL)*
  static boolean define_pattern_args(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "define_pattern_args")) return false;
    if (!nextTokenIs(b, PERCENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = define_pattern_args_0(b, l + 1);
    r = r && define_pattern_args_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PERCENT NUMBER_LITERAL
  private static boolean define_pattern_args_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "define_pattern_args_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PERCENT, NUMBER_LITERAL);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA PERCENT NUMBER_LITERAL)*
  private static boolean define_pattern_args_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "define_pattern_args_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!define_pattern_args_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "define_pattern_args_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA PERCENT NUMBER_LITERAL
  private static boolean define_pattern_args_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "define_pattern_args_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMA, PERCENT, NUMBER_LITERAL);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_STRING
  static boolean define_substitution(PsiBuilder b, int l) {
    return consumeToken(b, PREPROCESSOR_STRING);
  }

  /* ********************************************************** */
  // PREPROCESSOR_INCLUDE_SYSTEMPATH
  //   | PREPROCESSOR_INCLUDE_RELATIVEPATH
  static boolean include_file_reference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "include_file_reference")) return false;
    if (!nextTokenIs(b, "", PREPROCESSOR_INCLUDE_RELATIVEPATH, PREPROCESSOR_INCLUDE_SYSTEMPATH)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_INCLUDE_SYSTEMPATH);
    if (!r) r = consumeToken(b, PREPROCESSOR_INCLUDE_RELATIVEPATH);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PRAGMA_CODEPAGE STRING_LITERAL
  //   | PRAGMA_CTRLCHAR (CHARACTER_LITERAL | NUMBER_LITERAL)?
  //   | PRAGMA_DEPRECATED PREPROCESSOR_STRING?
  //   | PRAGMA_DYNAMIC NUMBER_LITERAL
  //   | PRAGMA_RATIONAL IDENTIFIER (LPAREN NUMBER_LITERAL RPAREN)?
  //   | PRAGMA_SEMICOLON NUMBER_LITERAL
  //   | PRAGMA_NEWDECLS (PRAGMA_NEWDECLS_OPTIONAL | PRAGMA_NEWDECLS_REQUIRED)
  //   | PRAGMA_TABSIZE NUMBER_LITERAL
  //   | PRAGMA_UNUSED IDENTIFIER (COMMA IDENTIFIER)*
  public static boolean pragma(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PRAGMA, "<pragma directive>");
    r = parseTokens(b, 0, PRAGMA_CODEPAGE, STRING_LITERAL);
    if (!r) r = pragma_1(b, l + 1);
    if (!r) r = pragma_2(b, l + 1);
    if (!r) r = parseTokens(b, 0, PRAGMA_DYNAMIC, NUMBER_LITERAL);
    if (!r) r = pragma_4(b, l + 1);
    if (!r) r = parseTokens(b, 0, PRAGMA_SEMICOLON, NUMBER_LITERAL);
    if (!r) r = pragma_6(b, l + 1);
    if (!r) r = parseTokens(b, 0, PRAGMA_TABSIZE, NUMBER_LITERAL);
    if (!r) r = pragma_8(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // PRAGMA_CTRLCHAR (CHARACTER_LITERAL | NUMBER_LITERAL)?
  private static boolean pragma_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PRAGMA_CTRLCHAR);
    r = r && pragma_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (CHARACTER_LITERAL | NUMBER_LITERAL)?
  private static boolean pragma_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_1_1")) return false;
    pragma_1_1_0(b, l + 1);
    return true;
  }

  // CHARACTER_LITERAL | NUMBER_LITERAL
  private static boolean pragma_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, CHARACTER_LITERAL);
    if (!r) r = consumeToken(b, NUMBER_LITERAL);
    exit_section_(b, m, null, r);
    return r;
  }

  // PRAGMA_DEPRECATED PREPROCESSOR_STRING?
  private static boolean pragma_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PRAGMA_DEPRECATED);
    r = r && pragma_2_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PREPROCESSOR_STRING?
  private static boolean pragma_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_2_1")) return false;
    consumeToken(b, PREPROCESSOR_STRING);
    return true;
  }

  // PRAGMA_RATIONAL IDENTIFIER (LPAREN NUMBER_LITERAL RPAREN)?
  private static boolean pragma_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PRAGMA_RATIONAL, IDENTIFIER);
    r = r && pragma_4_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (LPAREN NUMBER_LITERAL RPAREN)?
  private static boolean pragma_4_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_4_2")) return false;
    pragma_4_2_0(b, l + 1);
    return true;
  }

  // LPAREN NUMBER_LITERAL RPAREN
  private static boolean pragma_4_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_4_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LPAREN, NUMBER_LITERAL, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  // PRAGMA_NEWDECLS (PRAGMA_NEWDECLS_OPTIONAL | PRAGMA_NEWDECLS_REQUIRED)
  private static boolean pragma_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PRAGMA_NEWDECLS);
    r = r && pragma_6_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PRAGMA_NEWDECLS_OPTIONAL | PRAGMA_NEWDECLS_REQUIRED
  private static boolean pragma_6_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_6_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PRAGMA_NEWDECLS_OPTIONAL);
    if (!r) r = consumeToken(b, PRAGMA_NEWDECLS_REQUIRED);
    exit_section_(b, m, null, r);
    return r;
  }

  // PRAGMA_UNUSED IDENTIFIER (COMMA IDENTIFIER)*
  private static boolean pragma_8(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_8")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, PRAGMA_UNUSED, IDENTIFIER);
    r = r && pragma_8_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (COMMA IDENTIFIER)*
  private static boolean pragma_8_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_8_2")) return false;
    int c = current_position_(b);
    while (true) {
      if (!pragma_8_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "pragma_8_2", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // COMMA IDENTIFIER
  private static boolean pragma_8_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pragma_8_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMA, IDENTIFIER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // PREPROCESSOR_ASSERT
  //   | PREPROCESSOR_DEFINE define_pattern define_substitution?
  //   | PREPROCESSOR_ENDINPUT
  //   | PREPROCESSOR_ENDSCRIPT
  //   | PREPROCESSOR_ERROR PREPROCESSOR_STRING?
  //   | PREPROCESSOR_FILE STRING_LITERAL
  //   | PREPROCESSOR_IF PREPROCESSOR_ELSEIF* PREPROCESSOR_ELSE? PREPROCESSOR_ENDIF
  //   | PREPROCESSOR_INCLUDE include_file_reference
  //   | PREPROCESSOR_LINE NUMBER_LITERAL
  //   | PREPROCESSOR_PRAGMA pragma
  //   | PREPROCESSOR_TRYINCLUDE include_file_reference
  //   | PREPROCESSOR_UNDEF IDENTIFIER
  public static boolean preprocessor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, PREPROCESSOR, "<preprocessor directive>");
    r = consumeToken(b, PREPROCESSOR_ASSERT);
    if (!r) r = preprocessor_1(b, l + 1);
    if (!r) r = consumeToken(b, PREPROCESSOR_ENDINPUT);
    if (!r) r = consumeToken(b, PREPROCESSOR_ENDSCRIPT);
    if (!r) r = preprocessor_4(b, l + 1);
    if (!r) r = parseTokens(b, 0, PREPROCESSOR_FILE, STRING_LITERAL);
    if (!r) r = preprocessor_6(b, l + 1);
    if (!r) r = preprocessor_7(b, l + 1);
    if (!r) r = parseTokens(b, 0, PREPROCESSOR_LINE, NUMBER_LITERAL);
    if (!r) r = preprocessor_9(b, l + 1);
    if (!r) r = preprocessor_10(b, l + 1);
    if (!r) r = parseTokens(b, 0, PREPROCESSOR_UNDEF, IDENTIFIER);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // PREPROCESSOR_DEFINE define_pattern define_substitution?
  private static boolean preprocessor_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_DEFINE);
    r = r && define_pattern(b, l + 1);
    r = r && preprocessor_1_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // define_substitution?
  private static boolean preprocessor_1_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_1_2")) return false;
    define_substitution(b, l + 1);
    return true;
  }

  // PREPROCESSOR_ERROR PREPROCESSOR_STRING?
  private static boolean preprocessor_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_4")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_ERROR);
    r = r && preprocessor_4_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PREPROCESSOR_STRING?
  private static boolean preprocessor_4_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_4_1")) return false;
    consumeToken(b, PREPROCESSOR_STRING);
    return true;
  }

  // PREPROCESSOR_IF PREPROCESSOR_ELSEIF* PREPROCESSOR_ELSE? PREPROCESSOR_ENDIF
  private static boolean preprocessor_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_6")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_IF);
    r = r && preprocessor_6_1(b, l + 1);
    r = r && preprocessor_6_2(b, l + 1);
    r = r && consumeToken(b, PREPROCESSOR_ENDIF);
    exit_section_(b, m, null, r);
    return r;
  }

  // PREPROCESSOR_ELSEIF*
  private static boolean preprocessor_6_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_6_1")) return false;
    int c = current_position_(b);
    while (true) {
      if (!consumeToken(b, PREPROCESSOR_ELSEIF)) break;
      if (!empty_element_parsed_guard_(b, "preprocessor_6_1", c)) break;
      c = current_position_(b);
    }
    return true;
  }

  // PREPROCESSOR_ELSE?
  private static boolean preprocessor_6_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_6_2")) return false;
    consumeToken(b, PREPROCESSOR_ELSE);
    return true;
  }

  // PREPROCESSOR_INCLUDE include_file_reference
  private static boolean preprocessor_7(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_7")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_INCLUDE);
    r = r && include_file_reference(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PREPROCESSOR_PRAGMA pragma
  private static boolean preprocessor_9(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_9")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_PRAGMA);
    r = r && pragma(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // PREPROCESSOR_TRYINCLUDE include_file_reference
  private static boolean preprocessor_10(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "preprocessor_10")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PREPROCESSOR_TRYINCLUDE);
    r = r && include_file_reference(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
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
    if (!nextTokenIs(b, "", HASH, NEW_LINE)) return false;
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
