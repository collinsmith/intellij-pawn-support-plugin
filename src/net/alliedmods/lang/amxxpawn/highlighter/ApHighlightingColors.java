package net.alliedmods.lang.amxxpawn.highlighter;

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.ui.JBColor;

public class ApHighlightingColors {

  private ApHighlightingColors() {}

  public static final TextAttributesKey LINE_COMMENT
      = TextAttributesKey.createTextAttributesKey("AMXX_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
  public static final TextAttributesKey BLOCK_COMMENT
      = TextAttributesKey.createTextAttributesKey("AMXX_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
  public static final TextAttributesKey DOC_COMMENT
      = TextAttributesKey.createTextAttributesKey("AMXX_DOC_COMMENT", DefaultLanguageHighlighterColors.DOC_COMMENT);

  public static final TextAttributesKey KEYWORD
      = TextAttributesKey.createTextAttributesKey("AMXX_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
  public static final TextAttributesKey NUMBER
      = TextAttributesKey.createTextAttributesKey("AMXX_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
  public static final TextAttributesKey STRING
      = TextAttributesKey.createTextAttributesKey("AMXX_STRING", DefaultLanguageHighlighterColors.STRING);
  public static final TextAttributesKey OPERATION_SIGN
      = TextAttributesKey.createTextAttributesKey("AMXX_OPERATION_SIGN", DefaultLanguageHighlighterColors.OPERATION_SIGN);

  public static final TextAttributesKey PARENTHESES
      = TextAttributesKey.createTextAttributesKey("AMXX_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
  public static final TextAttributesKey BRACKETS
      = TextAttributesKey.createTextAttributesKey("AMXX_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
  public static final TextAttributesKey BRACES
      = TextAttributesKey.createTextAttributesKey("AMXX_BRACES", DefaultLanguageHighlighterColors.BRACES);

  public static final TextAttributesKey COMMA
      = TextAttributesKey.createTextAttributesKey("AMXX_COMMA", DefaultLanguageHighlighterColors.COMMA);
  public static final TextAttributesKey DOT
      = TextAttributesKey.createTextAttributesKey("AMXX_DOT", DefaultLanguageHighlighterColors.DOT);
  public static final TextAttributesKey SEMICOLON
      = TextAttributesKey.createTextAttributesKey("AMXX_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);

  public static final TextAttributesKey VALID_STRING_ESCAPE
    = TextAttributesKey.createTextAttributesKey("AMXX_VALID_STRING_ESCAPE", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE);
  public static final TextAttributesKey INVALID_STRING_ESCAPE
    = TextAttributesKey.createTextAttributesKey("AMXX_INVALID_STRING_ESCAPE", DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE);

  public static final TextAttributesKey DOC_COMMENT_TAG
    = TextAttributesKey.createTextAttributesKey("AMXX_DOC_TAG", DefaultLanguageHighlighterColors.DOC_COMMENT_TAG);
  public static final TextAttributesKey DOC_COMMENT_MARKUP
    = TextAttributesKey.createTextAttributesKey("AMXX_DOC_MARKUP", DefaultLanguageHighlighterColors.DOC_COMMENT_MARKUP);
  public static final TextAttributesKey DOC_COMMENT_TAG_VALUE
    = TextAttributesKey.createTextAttributesKey("DOC_COMMENT_TAG_VALUE", DefaultLanguageHighlighterColors.DOC_COMMENT_TAG_VALUE);

  public static final TextAttributesKey PREPROCESSOR = createPreprocessorAttrs();
  //public static final TextAttributesKey PREPROCESSOR
  //  = TextAttributesKey.createTextAttributesKey("AMXX_PREPROCESSOR", DefaultLanguageHighlighterColors.BLOCK_COMMENT);

  private static TextAttributesKey createPreprocessorAttrs() {
    TextAttributesKey key = TextAttributesKey.find("AMXX_PREPROCESSOR");
    key.getDefaultAttributes().setForegroundColor(JBColor.MAGENTA);
    return key;
  }

}
