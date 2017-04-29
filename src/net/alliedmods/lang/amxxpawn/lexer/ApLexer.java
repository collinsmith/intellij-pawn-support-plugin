package net.alliedmods.lang.amxxpawn.lexer;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.psi.tree.TokenSet;

import net.alliedmods.lang.amxxpawn.parser.ApParserDefinition;

public class ApLexer extends MergingLexerAdapter {

  public ApLexer() {
    super(new FlexAdapter(new _ApLexer()),
        TokenSet.orSet(ApParserDefinition.AMXX_COMMENT_BIT_SET, ApParserDefinition.AMXX_WHITESPACE_BIT_SET));
  }

}
