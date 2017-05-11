package net.alliedmods.lang.amxxpawn.editor;

import com.intellij.codeInsight.editorActions.MultiCharQuoteHandler;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import org.jetbrains.annotations.Nullable;

public class ApIncludeBracketHandler implements MultiCharQuoteHandler {

  private final TokenSet literalTokens =
      //TokenSet.create(ApTokenTypes.INCLUDE_PATH, ApTokenTypes.INCLUDE_RELATIVE);
      TokenSet.EMPTY;

  @Nullable
  @Override
  public CharSequence getClosingQuote(HighlighterIterator iterator, int offset) {
    iterator.retreat();
    IElementType tokenType = iterator.getTokenType();
    System.out.println("getClosingQuote @ " + tokenType);
    //if (tokenType == ApTokenTypes.INCLUDE_PATH) {
    //  return ">";
    //}

    return null;
  }

  @Override
  public boolean isOpeningQuote(HighlighterIterator iterator, int offset) {
    final IElementType tokenType = iterator.getTokenType();
    System.out.println("isOpeningQuote @ " + tokenType);
    if (!literalTokens.contains(tokenType)) {
      return false;
    }

    final int start = iterator.getStart();
    return offset == start;
  }

  @Override
  public boolean isClosingQuote(HighlighterIterator iterator, int offset) {
    final IElementType tokenType = iterator.getTokenType();
    System.out.println("isClosingQuote @ " + tokenType);
    if (!literalTokens.contains(tokenType)) {
      return false;
    }

    int start = iterator.getStart();
    final int end = iterator.getEnd();
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
}
