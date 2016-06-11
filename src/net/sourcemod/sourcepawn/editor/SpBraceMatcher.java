package net.sourcemod.sourcepawn.editor;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.sourcemod.sourcepawn.lexer.SpTokenTypes.*;

public class SpBraceMatcher implements PairedBraceMatcher {

  private static final BracePair[] BRACE_PAIRS = new BracePair[] {
      new BracePair(LBRACE, RBRACE, false),
      new BracePair(LBRACKET, RBRACKET, false),
      new BracePair(LPAREN, RPAREN, false)
  };

  @Override
  public BracePair[] getPairs() {
    return BRACE_PAIRS;
  }

  @Override
  public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType,
                                                 @Nullable IElementType contextType) {
    return true;
  }

  @Override
  public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
    return openingBraceOffset;
  }
}
