package net.alliedmods.intellij.sourcepawn.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;

import net.alliedmods.intellij.sourcepawn.SourcePawnLanguage;
import net.alliedmods.intellij.sourcepawn.file.SourcePawnScriptFile;
import net.alliedmods.intellij.sourcepawn.lexer.SourcePawnLexerAdapter;
import net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes;

import org.jetbrains.annotations.NotNull;

public class SourcePawnParserDefinition implements ParserDefinition {

  public static final TokenSet WHITESPACES = TokenSet.create(TokenType.WHITE_SPACE);
  public static final TokenSet COMMENTS
      = TokenSet.create(SourcePawnTypes.BLOCK_COMMENT, SourcePawnTypes.LINE_COMMENT);

  public static final IFileElementType SCRIPT_FILE
      = new IFileElementType(Language.findInstance(SourcePawnLanguage.class));

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return new SourcePawnLexerAdapter();
  }

  @Override
  public PsiParser createParser(Project project) {
    return new SourcePawnParser();
  }

  @Override
  public PsiFile createFile(FileViewProvider viewProvider) {
    return new SourcePawnScriptFile(viewProvider);
  }

  @Override
  public IFileElementType getFileNodeType() {
    return SCRIPT_FILE;
  }

  @NotNull
  @Override
  public TokenSet getWhitespaceTokens() {
    return WHITESPACES;
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return COMMENTS;
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    return TokenSet.EMPTY;
  }

  @NotNull
  @Override
  public PsiElement createElement(ASTNode node) {
    return SourcePawnTypes.Factory.createElement(node);
  }

  @Override
  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
    if (left.getElementType() == SourcePawnTypes.EXPRESSION
        && right.getElementType() != SourcePawnTypes.SEMICOLON
        && !SourcePawnParserUtils.areSemicolonsRequired()) {
      return SpaceRequirements.MUST_LINE_BREAK;
    }

    return SpaceRequirements.MAY;
  }
}
