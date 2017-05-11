package net.alliedmods.lang.amxxpawn.editor;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;
import net.alliedmods.lang.amxxpawn.psi.ElementTypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApBraceMatcher implements PairedBraceMatcher {

  private static final BracePair[] PAIRS = new BracePair[]{
      new BracePair(ApTokenTypes.LBRACE, ApTokenTypes.RBRACE, true),
      new BracePair(ApTokenTypes.LPARENTH, ApTokenTypes.RPARENTH, false),
      new BracePair(ApTokenTypes.LBRACKET, ApTokenTypes.RBRACKET, false),
  };

  @NotNull
  @Override
  public BracePair[] getPairs() {
    return PAIRS;
  }

  @Override
  public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType iElementType, @Nullable IElementType tokenType) {
    return ElementTypes.AMXX_WHITESPACE_BIT_SET.contains(tokenType)
        || ElementTypes.AMXX_COMMENT_BIT_SET.contains(tokenType)
        || tokenType == ApTokenTypes.SEMICOLON
        || tokenType == ApTokenTypes.COLON
        || tokenType == ApTokenTypes.COMMA
        || tokenType == ApTokenTypes.RPARENTH
        || tokenType == ApTokenTypes.RBRACKET
        || tokenType == ApTokenTypes.RBRACE
        || tokenType == ApTokenTypes.LBRACE
        || null == tokenType;
  }

  @Override
  public int getCodeConstructStart(PsiFile psiFile, int openingBraceOffset) {
    return openingBraceOffset;
  }

}
