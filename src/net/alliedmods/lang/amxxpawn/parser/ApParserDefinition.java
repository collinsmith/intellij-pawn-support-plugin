package net.alliedmods.lang.amxxpawn.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageUtil;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;

import net.alliedmods.lang.amxxpawn.ApLanguage;
import net.alliedmods.lang.amxxpawn.file.ApScriptFileType;
import net.alliedmods.lang.amxxpawn.lexer.ApLexerAdapter;
import net.alliedmods.lang.amxxpawn.psi.ApElementTypes;
import net.alliedmods.lang.amxxpawn.psi.ApIncludeFile;
import net.alliedmods.lang.amxxpawn.psi.ApScriptFile;
import net.alliedmods.lang.amxxpawn.psi.ApTokenType;
import net.alliedmods.lang.amxxpawn.psi.impl.source.tree.ElementType;

import org.jetbrains.annotations.NotNull;

public class ApParserDefinition implements ParserDefinition {

  public static final IFileElementType AP_FILE = new IFileElementType(ApLanguage.INSTANCE);

  @NotNull
  public static Lexer createLexer() {
    return new ApLexerAdapter();
  }

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return createLexer();
  }

  @Override
  public PsiParser createParser(Project project) {
    return new ApParser();
  }

  @Override
  public IFileElementType getFileNodeType() {
    return AP_FILE;
  }

  @NotNull
  @Override
  public TokenSet getWhitespaceTokens() {
    return ElementType.AMXX_WHITESPACE_BIT_SET;
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return ElementType.AMXX_COMMENT_BIT_SET;
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    // TODO: return TokenSet.create(ApElementType.LITERAL_EXPRESSION);
    return ElementType.AMXX_STRING_LITERAL_BIT_SET;
  }

  @NotNull
  @Override
  public PsiElement createElement(ASTNode astNode) {
    return ApElementTypes.Factory.createElement(astNode);
  }

  @Override
  public PsiFile createFile(FileViewProvider fileViewProvider) {
    if (fileViewProvider.getFileType() == ApScriptFileType.INSTANCE) {
      return new ApScriptFile(fileViewProvider);
    } else {
      return new ApIncludeFile(fileViewProvider);
    }
  }

  @Override
  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
    if (right.getElementType() == JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN ||
        left.getElementType() == JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN) {
      return SpaceRequirements.MUST_NOT;
    }

    if (left.getElementType() == ApTokenType.END_OF_LINE_COMMENT) {
      return SpaceRequirements.MUST_LINE_BREAK;
    }

    if (left.getElementType() == JavaDocTokenType.DOC_COMMENT_DATA) {
      String text = left.getText();
      if (text.length() > 0 && Character.isWhitespace(text.charAt(text.length() - 1))) {
        return SpaceRequirements.MAY;
      }
    }

    if (right.getElementType() == JavaDocTokenType.DOC_COMMENT_DATA) {
      String text = right.getText();
      if (text.length() > 0 && Character.isWhitespace(text.charAt(0))) {
        return SpaceRequirements.MAY;
      }
    } else if (right.getElementType() == JavaDocTokenType.DOC_INLINE_TAG_END) {
      return SpaceRequirements.MAY;
    }


    return LanguageUtil.canStickTokensTogetherByLexer(left, right, createLexer());
  }
}
