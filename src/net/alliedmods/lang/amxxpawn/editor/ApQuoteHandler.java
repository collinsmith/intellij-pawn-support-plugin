package net.alliedmods.lang.amxxpawn.editor;

import com.intellij.codeInsight.editorActions.JavaLikeQuoteHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import net.alliedmods.lang.amxxpawn.psi.ApTokenType;

import org.jetbrains.annotations.NotNull;

public class ApQuoteHandler implements JavaLikeQuoteHandler {

  private static final TokenSet SINGLE_LINE_LITERALS = TokenSet.create(
      ApTokenType.STRING_LITERAL, ApTokenType.RAW_STRING_LITERAL);

  private final TokenSet literalTokens;

  public ApQuoteHandler() {
    literalTokens = TokenSet.create(
        ApTokenType.STRING_LITERAL, ApTokenType.CHARACTER_LITERAL, ApTokenType.RAW_STRING_LITERAL,
        ApTokenType.PACKED_STRING_LITERAL, ApTokenType.PACKED_RAW_STRING_LITERAL);
  }

  @Override
  public boolean isOpeningQuote(HighlighterIterator iterator, int offset) {
    final IElementType tokenType = iterator.getTokenType();
    if (!literalTokens.contains(tokenType)) {
      return false;
    }

    final int start = iterator.getStart();
    if (tokenType == ApTokenType.RAW_STRING_LITERAL || tokenType == ApTokenType.PACKED_STRING_LITERAL) {
      return offset == start + 1;
    } else if (tokenType == ApTokenType.PACKED_RAW_STRING_LITERAL) {
      return offset == start + 2;
    }

    return offset == start;
    }

  @Override
  public boolean isClosingQuote(HighlighterIterator iterator, int offset) {
    final IElementType tokenType = iterator.getTokenType();
    if (!literalTokens.contains(tokenType)) {
      return false;
    }

    int start = iterator.getStart();
    final int end = iterator.getEnd();
    if (tokenType == ApTokenType.RAW_STRING_LITERAL || tokenType == ApTokenType.PACKED_STRING_LITERAL) {
      start += 1;
    } else if (tokenType == ApTokenType.PACKED_RAW_STRING_LITERAL) {
      start += 2;
    }

    return end - start >= 1 && offset == end - 1;
  }

  @Override
  public boolean hasNonClosedLiteral(Editor editor, HighlighterIterator iterator, int offset) {
    return true;
  }

  @Override
  public boolean isInsideLiteral(HighlighterIterator iterator) {
    return literalTokens.contains(iterator.getTokenType());
  }

  @Override
  public TokenSet getConcatenatableStringTokenTypes() {
    return SINGLE_LINE_LITERALS;
  }

  @Override
  public String getStringConcatenationOperatorRepresentation() {
    return "+";
  }

  @Override
  public TokenSet getStringTokenTypes() {
    return SINGLE_LINE_LITERALS;
  }

  @Override
  public boolean isAppropriateElementTypeForLiteral(@NotNull IElementType tokenType) {
    return true;
  }

  @Override
  public boolean needParenthesesAroundConcatenation(PsiElement element) {
    return false;
  }
}
