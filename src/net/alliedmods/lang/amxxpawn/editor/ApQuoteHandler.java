package net.alliedmods.lang.amxxpawn.editor;

import com.intellij.codeInsight.editorActions.JavaLikeQuoteHandler;
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StringEscapesTokenTypes;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import net.alliedmods.lang.amxxpawn.psi.ApTokenType;
import net.alliedmods.lang.amxxpawn.psi.impl.source.tree.ElementType;

import org.jetbrains.annotations.NotNull;

public class ApQuoteHandler extends SimpleTokenSetQuoteHandler implements JavaLikeQuoteHandler {

  @NotNull
  private final TokenSet concatenatableStrings;

  public ApQuoteHandler() {
    super(ApTokenType.STRING_LITERAL, ApTokenType.CHARACTER_LITERAL);
    concatenatableStrings = TokenSet.create(ApTokenType.STRING_LITERAL);
  }

  //@Override
  public boolean isOpeningQuote(HighlighterIterator iterator, int offset) {
    boolean openingQuote = super.isOpeningQuote(iterator, offset);
    if (openingQuote) {
      // check escape next
      if (!iterator.atEnd()) {
        iterator.retreat();
        if (!iterator.atEnd() && StringEscapesTokenTypes.STRING_LITERAL_ESCAPES.contains(iterator.getTokenType())) {
          openingQuote = false;
        }

        iterator.advance();
      }
    }

    return openingQuote;
  }

  //@Override
  public boolean isClosingQuote(HighlighterIterator iterator, int offset) {
    boolean closingQuote = super.isClosingQuote(iterator, offset);
    if (closingQuote) {
      // check escape next
      if (!iterator.atEnd()) {
        iterator.advance();
        if (!iterator.atEnd() && StringEscapesTokenTypes.STRING_LITERAL_ESCAPES.contains(iterator.getTokenType())) {
          closingQuote = false;
        }

        iterator.retreat();
      }
    }

    return closingQuote;
  }

  //@Override
  public TokenSet getConcatenatableStringTokenTypes() {
    return concatenatableStrings;
  }

  //@Override
  public String getStringConcatenationOperatorRepresentation() {
    return "+";
  }

  //@Override
  public TokenSet getStringTokenTypes() {
    return myLiteralTokenSet;
  }

  //@Override
  public boolean isAppropriateElementTypeForLiteral(@NotNull IElementType tokenType) {
    return isAppropriateElementTypeForLiteralStatic(tokenType);
  }

  //@Override
  public boolean needParenthesesAroundConcatenation(PsiElement element) {
    // TODO: This is probably not needed, but I want to determine if there are cases where applicable
    // example code: "some string".length() must become ("some" + " string").length()
    // @see https://github.com/joewalnes/idea-community/blob/master/java/java-impl/src/com/intellij/codeInsight/editorActions/JavaQuoteHandler.java
    return false;
  }

  public static boolean isAppropriateElementTypeForLiteralStatic(final IElementType tokenType) {
    return ElementType.AMXX_COMMENT_OR_WHITESPACE_BIT_SET.contains(tokenType)
        || tokenType == ApTokenType.NEW_LINE
        || tokenType == ApTokenType.SEMICOLON
        || tokenType == ApTokenType.COMMA
        || tokenType == ApTokenType.RPARENTH
        || tokenType == ApTokenType.RBRACKET
        || tokenType == ApTokenType.RBRACE
        || tokenType == ApTokenType.STRING_LITERAL
        || tokenType == ApTokenType.CHARACTER_LITERAL;
  }
}
