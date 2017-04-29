package net.alliedmods.lang.amxxpawn.highlighter;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.HtmlHighlightingLexer;
import com.intellij.lexer.LayeredLexer;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.lexer.ApLexer;
import net.alliedmods.lang.amxxpawn.lexer.ApStringLiteralLexer;
import net.alliedmods.lang.amxxpawn.lexer.CtrlProvider;
import net.alliedmods.lang.amxxpawn.parser.ApParserDefinition;
import net.alliedmods.lang.amxxpawn.psi.ApTokenType;

public class ApHighlightingLexer extends LayeredLexer {

  public ApHighlightingLexer() {
    super(ApParserDefinition.createLexer());
    ApLexer lexer = (ApLexer) getDelegate();
    FlexAdapter flexed = (FlexAdapter) lexer.getOriginal();
    CtrlProvider ctrlProvider = (CtrlProvider) flexed.getFlex();

    registerSelfStoppingLayer(
        new ApStringLiteralLexer(ctrlProvider, '\"', ApTokenType.STRING_LITERAL),
        new IElementType[] { ApTokenType.STRING_LITERAL }, IElementType.EMPTY_ARRAY);

    registerSelfStoppingLayer(
        new ApStringLiteralLexer(ctrlProvider, '\'', ApTokenType.CHARACTER_LITERAL),
        new IElementType[] { ApTokenType.CHARACTER_LITERAL }, IElementType.EMPTY_ARRAY);

    LayeredLexer docLexer = new LayeredLexer(ApParserDefinition.createDocLexer());
    HtmlHighlightingLexer htmlLexer = new HtmlHighlightingLexer(null);
    htmlLexer.setHasNoEmbeddments(true);
    docLexer.registerLayer(htmlLexer, JavaDocTokenType.DOC_COMMENT_DATA);
    registerSelfStoppingLayer(docLexer, new IElementType[]{ ApTokenType.DOC_COMMENT }, IElementType.EMPTY_ARRAY);
  }

}
