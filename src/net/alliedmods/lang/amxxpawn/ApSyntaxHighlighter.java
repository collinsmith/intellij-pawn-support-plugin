package net.alliedmods.lang.amxxpawn;

import com.intellij.lexer.LayeredLexer;
import com.intellij.lexer.Lexer;
import com.intellij.lexer.StringLiteralLexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.StringEscapesTokenTypes;
import com.intellij.psi.tree.IElementType;
import com.intellij.ui.JBColor;

import net.alliedmods.lang.amxxpawn.lexer.ApLexerAdapter;
import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ApSyntaxHighlighter extends SyntaxHighlighterBase {

  public static final TextAttributesKey BAD_CHARACTER = TextAttributesKey.createTextAttributesKey(
      "AMXX.BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);
  public static final TextAttributesKey VALID_STRING_ESCAPE = TextAttributesKey.createTextAttributesKey(
      "AMXX.VALID_STRING_ESCAPE", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE);
  public static final TextAttributesKey INVALID_STRING_ESCAPE = TextAttributesKey.createTextAttributesKey(
      "AMXX.INVALID_STRING_ESCAPE", DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE);
  public static final TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey(
      "AMXX.KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
  public static final TextAttributesKey OPERATORS = TextAttributesKey.createTextAttributesKey(
      "AMXX.PREPROC", DefaultLanguageHighlighterColors.OPERATION_SIGN);
  public static final TextAttributesKey PARENTHESES = TextAttributesKey.createTextAttributesKey(
      "AMXX.PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES);
  public static final TextAttributesKey BRACKETS = TextAttributesKey.createTextAttributesKey(
      "AMXX.BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);
  public static final TextAttributesKey BRACES = TextAttributesKey.createTextAttributesKey(
      "AMXX.BRACES", DefaultLanguageHighlighterColors.BRACES);
  public static final TextAttributesKey LINE_COMMENT = TextAttributesKey.createTextAttributesKey(
      "AMXX.COMMA", DefaultLanguageHighlighterColors.LINE_COMMENT);
  public static final TextAttributesKey BLOCK_COMMENT = TextAttributesKey.createTextAttributesKey(
      "AMXX.COMMA", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
  public static final TextAttributesKey DOC_COMMENT = TextAttributesKey.createTextAttributesKey(
      "AMXX.DOC_COMMENT", DefaultLanguageHighlighterColors.DOC_COMMENT);
  public static final TextAttributesKey SEMICOLON = TextAttributesKey.createTextAttributesKey(
      "AMXX.SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);
  public static final TextAttributesKey COMMA = TextAttributesKey.createTextAttributesKey(
      "AMXX.COMMA", DefaultLanguageHighlighterColors.COMMA);
  public static final TextAttributesKey NUMBER = TextAttributesKey.createTextAttributesKey(
      "AMXX.NUMBER", DefaultLanguageHighlighterColors.NUMBER);
  public static final TextAttributesKey STRING = TextAttributesKey.createTextAttributesKey(
      "AMXX.STRING", DefaultLanguageHighlighterColors.STRING);

  public static final TextAttributesKey PRE_KEYWORD = TextAttributesKey.createTextAttributesKey(
      "AMXX.PRE_KEYWORD", createPreprocessorAttr());

  private static TextAttributesKey createPreprocessorAttr() {
    TextAttributes attrs = new TextAttributes();
    attrs.setForegroundColor(JBColor.MAGENTA);
    // FIXME: This is deprecated, perhaps there is a better method (wasn't documented as to why deprecated)
    TextAttributesKey key = TextAttributesKey.createTextAttributesKey("AMXX.PREPROCESSOR", attrs);
    key.setFallbackAttributeKey(DefaultLanguageHighlighterColors.KEYWORD);
    return key;
  }

  private static final Map<IElementType, TextAttributesKey> KEYS;
  static {
    KEYS = new HashMap<>();
    fillMap(KEYS, ApTokenTypes.KEYWORDS, KEYWORD);
    KEYS.put(ApTokenTypes.PRE_KEYWORD, PRE_KEYWORD);
    fillMap(KEYS, ApTokenTypes.OPERATORS, OPERATORS);

    KEYS.put(ApTokenTypes.END_OF_LINE_COMMENT, LINE_COMMENT);
    KEYS.put(ApTokenTypes.C_STYLE_COMMENT, BLOCK_COMMENT);
    KEYS.put(ApTokenTypes.DOC_COMMENT, DOC_COMMENT);

    KEYS.put(ApTokenTypes.NUMERIC_LITERAL, NUMBER);
    KEYS.put(ApTokenTypes.STRING_LITERAL, STRING);
    KEYS.put(ApTokenTypes.CHARACTER_LITERAL, STRING);

    KEYS.put(ApTokenTypes.LPAREN, PARENTHESES);
    KEYS.put(ApTokenTypes.RPAREN, PARENTHESES);

    KEYS.put(ApTokenTypes.LBRACKET, BRACKETS);
    KEYS.put(ApTokenTypes.RBRACKET, BRACKETS);

    KEYS.put(ApTokenTypes.LBRACE, BRACES);
    KEYS.put(ApTokenTypes.RBRACE, BRACES);

    KEYS.put(ApTokenTypes.TERM, SEMICOLON);
    KEYS.put(ApTokenTypes.COMMA, COMMA);

    KEYS.put(StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN, VALID_STRING_ESCAPE);
    KEYS.put(StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN, INVALID_STRING_ESCAPE);
    KEYS.put(StringEscapesTokenTypes.INVALID_UNICODE_ESCAPE_TOKEN, INVALID_STRING_ESCAPE);

    KEYS.put(ApTokenTypes.BAD_CHARACTER, BAD_CHARACTER);
  }

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new LayeredLexer(new ApLexerAdapter()) {
      @Override
      public void registerSelfStoppingLayer(Lexer lexer, IElementType[] startTokens, IElementType[] stopTokens) {
        super.registerSelfStoppingLayer(new StringLiteralLexer('\"', ApTokenTypes.STRING_LITERAL),
            new IElementType[] { ApTokenTypes.STRING_LITERAL }, IElementType.EMPTY_ARRAY);
        super.registerSelfStoppingLayer(new StringLiteralLexer('\'', ApTokenTypes.CHARACTER_LITERAL),
            new IElementType[]{ApTokenTypes.CHARACTER_LITERAL}, IElementType.EMPTY_ARRAY);
      }
    };
  }

  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType iElementType) {
    return pack(KEYS.get(iElementType));
  }
}
