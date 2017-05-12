package net.alliedmods.lang.amxxpawn.highlighter;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.StringEscapesTokenTypes;
import com.intellij.psi.TokenType;
import com.intellij.psi.impl.source.tree.JavaDocElementType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.xml.XmlTokenType;

import net.alliedmods.lang.amxxpawn.ApParserDefinition;
import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;
import net.alliedmods.lang.amxxpawn.psi.ElementTypes;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ApSyntaxHighlighter extends SyntaxHighlighterBase {

  private static final Map<IElementType, TextAttributesKey> ourMap1;
  private static final Map<IElementType, TextAttributesKey> ourMap2;
  static {
    ourMap1 = new HashMap<>();
    ourMap2 = new HashMap<>();

    fillMap(ourMap1, ElementTypes.KEYWORD_BIT_SET, ApHighlightingColors.KEYWORD);
    fillMap(ourMap1, ElementTypes.LITERAL_BIT_SET, ApHighlightingColors.KEYWORD);
    fillMap(ourMap1, ElementTypes.OPERATION_BIT_SET, ApHighlightingColors.OPERATION_SIGN);
    fillMap(ourMap1, ElementTypes.AMXX_PREPROCESSOR_BIT_SET, ApHighlightingColors.PREPROCESSOR);

    ourMap1.put(ApTokenTypes.INCLUDE_REFERENCE, ApHighlightingColors.STRING);
    ourMap1.put(ApTokenTypes.DEPRECATION_REASON, ApHighlightingColors.STRING);

    for (IElementType type : JavaDocTokenType.ALL_JAVADOC_TOKENS.getTypes()) {
      ourMap1.put(type, ApHighlightingColors.DOC_COMMENT);
    }

    ourMap1.put(XmlTokenType.XML_DATA_CHARACTERS, ApHighlightingColors.DOC_COMMENT);
    ourMap1.put(XmlTokenType.XML_REAL_WHITE_SPACE, ApHighlightingColors.DOC_COMMENT);
    ourMap1.put(XmlTokenType.TAG_WHITE_SPACE, ApHighlightingColors.DOC_COMMENT);

    ourMap1.put(ApTokenTypes.CELL_LITERAL, ApHighlightingColors.NUMBER);
    ourMap1.put(ApTokenTypes.RATIONAL_LITERAL, ApHighlightingColors.NUMBER);
    ourMap1.put(ApTokenTypes.STRING_LITERAL, ApHighlightingColors.STRING);
    ourMap1.put(ApTokenTypes.RAW_STRING_LITERAL, ApHighlightingColors.STRING); // TODO: Special coloring for raw strings?
    ourMap1.put(ApTokenTypes.PACKED_STRING_LITERAL, ApHighlightingColors.STRING); // TODO: Special coloring for packed strings?
    ourMap1.put(ApTokenTypes.PACKED_RAW_STRING_LITERAL, ApHighlightingColors.STRING); // TODO: Special coloring for packed+raw strings?
    ourMap1.put(ApTokenTypes.CHARACTER_LITERAL, ApHighlightingColors.STRING);
    ourMap1.put(StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN, ApHighlightingColors.VALID_STRING_ESCAPE);
    ourMap1.put(StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN, ApHighlightingColors.INVALID_STRING_ESCAPE);
    ourMap1.put(StringEscapesTokenTypes.INVALID_UNICODE_ESCAPE_TOKEN, ApHighlightingColors.INVALID_STRING_ESCAPE);

    ourMap1.put(ApTokenTypes.LPARENTH, ApHighlightingColors.PARENTHESES);
    ourMap1.put(ApTokenTypes.RPARENTH, ApHighlightingColors.PARENTHESES);

    ourMap1.put(ApTokenTypes.LBRACE, ApHighlightingColors.BRACES);
    ourMap1.put(ApTokenTypes.RBRACE, ApHighlightingColors.BRACES);

    ourMap1.put(ApTokenTypes.LBRACKET, ApHighlightingColors.BRACKETS);
    ourMap1.put(ApTokenTypes.RBRACKET, ApHighlightingColors.BRACKETS);

    ourMap1.put(ApTokenTypes.COMMA, ApHighlightingColors.COMMA);
    ourMap1.put(ApTokenTypes.DOT, ApHighlightingColors.DOT);
    ourMap1.put(ApTokenTypes.SEMICOLON, ApHighlightingColors.SEMICOLON);
    
    ourMap1.put(ApTokenTypes.C_STYLE_COMMENT, ApHighlightingColors.BLOCK_COMMENT);
    ourMap1.put(JavaDocElementType.DOC_COMMENT, ApHighlightingColors.DOC_COMMENT);
    ourMap1.put(ApTokenTypes.END_OF_LINE_COMMENT, ApHighlightingColors.LINE_COMMENT);
    ourMap1.put(TokenType.BAD_CHARACTER, HighlighterColors.BAD_CHARACTER);

    ourMap1.put(JavaDocTokenType.DOC_TAG_NAME, ApHighlightingColors.DOC_COMMENT);
    ourMap2.put(JavaDocTokenType.DOC_TAG_NAME, ApHighlightingColors.DOC_COMMENT_TAG);

    IElementType[] javaDocMarkup = {
        XmlTokenType.XML_START_TAG_START, XmlTokenType.XML_END_TAG_START, XmlTokenType.XML_TAG_END,
        XmlTokenType.XML_EMPTY_ELEMENT_END, XmlTokenType.TAG_WHITE_SPACE, XmlTokenType.XML_TAG_NAME,
        XmlTokenType.XML_NAME, XmlTokenType.XML_ATTRIBUTE_VALUE_TOKEN, XmlTokenType.XML_ATTRIBUTE_VALUE_START_DELIMITER,
        XmlTokenType.XML_ATTRIBUTE_VALUE_END_DELIMITER, XmlTokenType.XML_CHAR_ENTITY_REF, XmlTokenType.XML_EQ
    };

    for (IElementType idx : javaDocMarkup) {
      ourMap1.put(idx, ApHighlightingColors.DOC_COMMENT);
      ourMap2.put(idx, ApHighlightingColors.DOC_COMMENT_MARKUP);
    }
  }

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    final boolean LAYERED = true;
    if (LAYERED) {
      return new ApHighlightingLexer();
    } else {
      return ApParserDefinition.createLexer();
    }
  }

  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    return pack(ourMap1.get(tokenType), ourMap1.get(tokenType));
  }
}
