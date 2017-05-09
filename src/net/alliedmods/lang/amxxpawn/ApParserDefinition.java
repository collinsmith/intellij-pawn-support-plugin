package net.alliedmods.lang.amxxpawn;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageUtil;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lang.java.lexer.JavaDocLexer;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;

import net.alliedmods.lang.amxxpawn.lexer.ApLexer;
import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;
import net.alliedmods.lang.amxxpawn.parser.ApParser;
import net.alliedmods.lang.amxxpawn.psi.ElementTypes;
import net.alliedmods.lang.amxxpawn.psi.impl.PsiApFileImpl;
import net.alliedmods.lang.amxxpawn.psi.stubs.ApStubElementType;
import net.alliedmods.lang.amxxpawn.psi.stubs.ApStubElementTypes;

import org.jetbrains.annotations.NotNull;

public class ApParserDefinition implements ParserDefinition {
  @NotNull
  public static Lexer createLexer() {
    return new ApLexer();
  }

  @NotNull
  public static Lexer createDocLexer() {
    return new JavaDocLexer(LanguageLevel.HIGHEST);
  }

  @NotNull
  @Override
  public Lexer createLexer(Project project) {
    return createLexer();
  }

  @Override
  public PsiParser createParser(Project project) {
    //return new ApBracesParser();
    return ApParser.createParser();
  }

  @Override
  public IFileElementType getFileNodeType() {
    return ApStubElementTypes.AP_FILE;
  }

  @NotNull
  @Override
  public TokenSet getWhitespaceTokens() {
    return ElementTypes.AMXX_WHITESPACE_BIT_SET;
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return ElementTypes.AMXX_COMMENT_BIT_SET;
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    // TODO: return TokenSet.create(ApElementTypeDisabled.LITERAL_EXPRESSION);
    return ElementTypes.AMXX_STRING_LITERAL_BIT_SET;
  }

  @NotNull
  @Override
  public PsiElement createElement(ASTNode node) {
    final IElementType type = node.getElementType();
    if (type instanceof ApStubElementType) {
      return ((ApStubElementType) type).createPsi(node);
    }

    throw new IllegalStateException("Incorrect node for JavaParserDefinition: " + node + " (" + type + ")");
  }

  @Override
  public PsiFile createFile(FileViewProvider fileViewProvider) {
    return new PsiApFileImpl(fileViewProvider);
  }

  @Override
  public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
    if (right.getElementType() == JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN ||
        left.getElementType() == JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN) {
      return SpaceRequirements.MUST_NOT;
    }

    if (left.getElementType() == ApTokenTypes.END_OF_LINE_COMMENT) {
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
