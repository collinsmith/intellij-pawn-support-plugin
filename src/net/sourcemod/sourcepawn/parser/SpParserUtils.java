package net.sourcemod.sourcepawn.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderFactory;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

import net.sourcemod.sourcepawn.SpBundle;
import net.sourcemod.sourcepawn.lexer._SpPreprocessorLexer;

import org.jetbrains.annotations.Nullable;

public class SpParserUtils extends GeneratedParserUtilBase {

  public static ASTNode parsePreprocessorFragment(final ASTNode chameleon,
                                                  final ParserWrapper wrapper) {
    return parsePreprocessorFragment(chameleon, wrapper, true);
  }

  @Nullable
  public static ASTNode parsePreprocessorFragment(final ASTNode chameleon,
                                                  final ParserWrapper wrapper,
                                                  final boolean eatAll) {
    final PsiElement psi = chameleon.getTreeParent() != null
        ? chameleon.getTreeParent().getPsi()
        : chameleon.getPsi();
    assert psi != null : chameleon;
    final Project project = psi.getProject();

    final PsiBuilderFactory factory = PsiBuilderFactory.getInstance();
    final Lexer lexer = new FlexAdapter(new _SpPreprocessorLexer());
    final PsiBuilder builder = factory.createBuilder(project, chameleon, lexer,
        chameleon.getElementType().getLanguage(), chameleon.getChars());

    final PsiBuilder.Marker root = builder.mark();
    wrapper.parse(builder);
    if (!builder.eof()) {
      if (!eatAll) {
        throw new AssertionError("Unexpected tokens");
      }

      final PsiBuilder.Marker extras = builder.mark();
      while (!builder.eof()) {
        builder.advanceLexer();
      }

      extras.error(SpBundle.message("sp.lang.unexpected_tokens"));
    }

    root.done(chameleon.getElementType());
    return builder.getTreeBuilt().getFirstChildNode();
  }

  public interface ParserWrapper {
    void parse(PsiBuilder builder);
  }

  private SpParserUtils() {
  }

}
