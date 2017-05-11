package net.alliedmods.lang.amxxpawn.highlighter;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.HtmlHighlightingLexer;
import com.intellij.lexer.LayeredLexer;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.ApParserDefinition;
import net.alliedmods.lang.amxxpawn.lexer.ApLexer;
import net.alliedmods.lang.amxxpawn.lexer.ApStringLiteralLexer;
import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;
import net.alliedmods.lang.amxxpawn.lexer.CtrlProvider;

public class ApHighlightingLexer extends LayeredLexer {

  public ApHighlightingLexer() {
    super(ApParserDefinition.createLexer());
    ApLexer lexer = (ApLexer) getDelegate();
    FlexAdapter flexed = (FlexAdapter) lexer.getOriginal();
    CtrlProvider ctrlProvider = (CtrlProvider) flexed.getFlex();

    registerSelfStoppingLayer(
        new ApStringLiteralLexer(ctrlProvider, '\"', ApTokenTypes.STRING_LITERAL),
        new IElementType[] { ApTokenTypes.STRING_LITERAL }, IElementType.EMPTY_ARRAY);

    registerSelfStoppingLayer(
        new ApStringLiteralLexer(ctrlProvider, '\"', ApTokenTypes.PACKED_STRING_LITERAL, 2),
        new IElementType[]{ApTokenTypes.PACKED_STRING_LITERAL}, IElementType.EMPTY_ARRAY);

    registerSelfStoppingLayer(
        new ApStringLiteralLexer(ctrlProvider, '\'', ApTokenTypes.CHARACTER_LITERAL),
        new IElementType[] { ApTokenTypes.CHARACTER_LITERAL }, IElementType.EMPTY_ARRAY);

    LayeredLexer docLexer = new LayeredLexer(ApParserDefinition.createDocLexer());
    HtmlHighlightingLexer htmlLexer = new HtmlHighlightingLexer(null);
    htmlLexer.setHasNoEmbeddments(true);
    docLexer.registerLayer(htmlLexer, JavaDocTokenType.DOC_COMMENT_DATA);
    registerSelfStoppingLayer(docLexer, new IElementType[]{ ApTokenTypes.DOC_COMMENT }, IElementType.EMPTY_ARRAY);
  }

}
