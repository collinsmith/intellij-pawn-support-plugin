package net.alliedmods.lang.amxxpawn.parser;

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
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;

import net.alliedmods.lang.amxxpawn.ApLanguage;
import net.alliedmods.lang.amxxpawn.file.ApScriptFileType;
import net.alliedmods.lang.amxxpawn.lexer.ApLexer;
import net.alliedmods.lang.amxxpawn.psi.ApElementTypes;
import net.alliedmods.lang.amxxpawn.psi.ApIncludeFile;
import net.alliedmods.lang.amxxpawn.psi.ApScriptFile;

import org.jetbrains.annotations.NotNull;

import static net.alliedmods.lang.amxxpawn.psi.ApTokenType.*;

public class ApParserDefinition implements ParserDefinition {

  public static final TokenSet AMXX_WHITESPACE_BIT_SET = TokenSet.create(WHITE_SPACE, NEW_LINE);

  public static final TokenSet AMXX_PLAIN_COMMENT_BIT_SET = TokenSet.create(END_OF_LINE_COMMENT, C_STYLE_COMMENT);
  public static final TokenSet AMXX_COMMENT_BIT_SET = TokenSet.orSet(AMXX_PLAIN_COMMENT_BIT_SET, TokenSet.create(DOC_COMMENT));

  public static final TokenSet AMXX_COMMENT_OR_WHITESPACE_BIT_SET = TokenSet.orSet(
      AMXX_WHITESPACE_BIT_SET, AMXX_COMMENT_BIT_SET);

  public static final TokenSet KEYWORD_BIT_SET = TokenSet.create(
      ASSERT_KEYWORD, BREAK_KEYWORD, CASE_KEYWORD, CHAR_KEYWORD, CONST_KEYWORD, CONTINUE_KEYWORD,
      DEFAULT_KEYWORD, DEFINED_KEYWORD, DO_KEYWORD, ELSE_KEYWORD, ENUM_KEYWORD, EXIT_KEYWORD,
      FOR_KEYWORD, FORWARD_KEYWORD, GOTO_KEYWORD, IF_KEYWORD, NATIVE_KEYWORD, NEW_KEYWORD,
      OPERATOR_KEYWORD, PUBLIC_KEYWORD, RETURN_KEYWORD, SIZEOF_KEYWORD, SLEEP_KEYWORD,
      STATE_KEYWORD, STATIC_KEYWORD, STOCK_KEYWORD, SWITCH_KEYWORD, TAGOF_KEYWORD, WHILE_KEYWORD);

  public static final TokenSet LITERAL_BIT_SET = TokenSet.create(TRUE_KEYWORD, FALSE_KEYWORD);

  public static final TokenSet OPERATION_BIT_SET = TokenSet.create(
      EQ, GT, LT, EXCL, TILDE, QUEST, COLON, PLUS, MINUS, ASTERISK, DIV, AND, OR, XOR, PERC, EQEQ,
      LE, GE, NE, ANDAND, OROR, PLUSPLUS, MINUSMINUS, LTLT, GTGT, GTGTGT, PLUSEQ, MINUSEQ,
      ASTERISKEQ, DIVEQ, ANDEQ, OREQ, XOREQ, PERCEQ, LTLTEQ, GTGTEQ, GTGTGTEQ);

  public static final TokenSet AMXX_STRING_LITERAL_BIT_SET = TokenSet.create(
      CHARACTER_LITERAL, STRING_LITERAL, RAW_STRING_LITERAL, PACKED_STRING_LITERAL, PACKED_RAW_STRING_LITERAL);

  public static final TokenSet AMXX_PREPROCESSOR_BIT_SET = TokenSet.create(
      PREPROCESSOR, ESCAPING_SLASH);

  public static final IFileElementType AP_FILE = new IFileElementType(ApLanguage.INSTANCE);

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
    return new ApParser();
  }

  @Override
  public IFileElementType getFileNodeType() {
    return AP_FILE;
  }

  @NotNull
  @Override
  public TokenSet getWhitespaceTokens() {
    return AMXX_WHITESPACE_BIT_SET;
  }

  @NotNull
  @Override
  public TokenSet getCommentTokens() {
    return AMXX_COMMENT_BIT_SET;
  }

  @NotNull
  @Override
  public TokenSet getStringLiteralElements() {
    // TODO: return TokenSet.create(ApElementTypeDisabled.LITERAL_EXPRESSION);
    return AMXX_STRING_LITERAL_BIT_SET;
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

    if (left.getElementType() == END_OF_LINE_COMMENT) {
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
