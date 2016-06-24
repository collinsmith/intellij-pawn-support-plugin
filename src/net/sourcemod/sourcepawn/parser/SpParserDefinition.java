package net.sourcemod.sourcepawn.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;

import net.sourcemod.sourcepawn.SpLanguage;
import net.sourcemod.sourcepawn.lexer.SpTokenTypes;
import net.sourcemod.sourcepawn.psi.SpElementTypes;
import net.sourcemod.sourcepawn.psi.SpScriptFile;

import org.jetbrains.annotations.NotNull;

public class SpParserDefinition implements ParserDefinition {

  public static final IFileElementType SP_FILE = new IFileElementType(SpLanguage.INSTANCE);

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return SpLanguage.createLexer();
  }

  @Override
  public PsiParser createParser(Project project) {
    return new SpParser();
  }

  @Override
  public PsiFile createFile(FileViewProvider viewProvider) {
    return new SpScriptFile(viewProvider);
  }

  @Override
  public IFileElementType getFileNodeType() {
    return SP_FILE;
  }

  @NotNull
  @Override
  public TokenSet getWhitespaceTokens() {
    return SpTokenTypes.getWhiteSpaceTokens();
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return SpTokenTypes.getCommentTokens();
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    return SpTokenTypes.getStringLiteralTokens();
  }

  @NotNull
  @Override
  public PsiElement createElement(ASTNode node) {
    return SpElementTypes.Factory.createElement(node);
  }

  @Override
  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
    /*if (left.getElementType() == SpTokenTypes.HASH) {
      return SpaceRequirements.MUST_NOT;
    } else if (left.getElementType() == SpTokenTypes.DEFINED_PATTERN
        && right.getElementType() == SpElementTypes.DEFINE_ARGS) {
      return SpaceRequirements.MUST_NOT;
    } else if (left.getElementType() == SpElementTypes.DEFINE_ARGS
        && right.getElementType() == SpElementTypes.DEFINE_SUBSTITUTION) {
      return SpaceRequirements.MUST;
    }*/

    return SpaceRequirements.MAY;
  }
  
}
