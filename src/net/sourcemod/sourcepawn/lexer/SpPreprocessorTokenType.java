package net.sourcemod.sourcepawn.lexer;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.psi.impl.source.tree.LazyParseablePsiElement;
import com.intellij.psi.tree.IReparseableElementType;

import net.sourcemod.sourcepawn.SpLanguage;
import net.sourcemod.sourcepawn.parser.SpParserUtils;
import net.sourcemod.sourcepawn.parser.SpPreprocessorParser;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpPreprocessorTokenType extends IReparseableElementType {

  private final SpParserUtils.ParserWrapper parserWrapper = SpPreprocessorParser::parse;
  private SpLexer lexer;

  public SpPreprocessorTokenType(@NotNull @NonNls String debugName) {
    super(debugName, SpLanguage.INSTANCE);
  }

  public void setSpLexer(SpLexer lexer) {
    this.lexer = lexer;
  }

  @Nullable
  @Override
  public ASTNode createNode(CharSequence text) {
    return new LazyParseablePsiElement(this, text);
  }

  @Override
  public ASTNode parseContents(ASTNode chameleon) {
    return SpParserUtils.parsePreprocessorFragment(chameleon, parserWrapper);
  }

  @Override
  public boolean isParsable(CharSequence buffer, Language fileLanguage, Project project) {
    final SpLexer lexer = new SpLexer();
    lexer.start(buffer);
    if (lexer.getTokenType() == SpTokenTypes.PREPROCESSOR) {
      lexer.advance();
      if (lexer.getTokenType() == null) {
        return true;
      }
    }

    return false;
  }

}
