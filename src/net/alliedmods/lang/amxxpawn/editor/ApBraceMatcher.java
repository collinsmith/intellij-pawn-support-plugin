package net.alliedmods.lang.amxxpawn.editor;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.psi.ApTokenType;
import net.alliedmods.lang.amxxpawn.psi.impl.source.tree.ElementType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApBraceMatcher implements PairedBraceMatcher {

  private static final BracePair[] PAIRS = new BracePair[]{
    new BracePair(ApTokenType.LPARENTH, ApTokenType.RPARENTH, false),
    new BracePair(ApTokenType.LBRACKET, ApTokenType.RBRACKET, false),
    new BracePair(ApTokenType.LBRACE, ApTokenType.RBRACE, true)
  };

  public BracePair[] getPairs() {
    return PAIRS;
  }

  public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType iElementType, @Nullable IElementType tokenType) {
    if (tokenType == ApTokenType.WHITE_SPACE
        || ElementType.AMXX_COMMENT_BIT_SET.contains(tokenType)
        || ApTokenType.RBRACE == tokenType
        || tokenType == null
        || ApTokenType.COMMA == tokenType
        || ApTokenType.SEMICOLON == tokenType
        || ApTokenType.COLON == tokenType
        || ApTokenType.RPARENTH == tokenType
        || ApTokenType.RBRACKET == tokenType
        ) {
      return true;
    }

    return false;
  }

  // IDEA8
  public int getCodeConstructStart(PsiFile psiFile, int i) {
    return i;
  }

}
