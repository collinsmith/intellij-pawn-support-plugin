package net.alliedmods.lang.amxxpawn;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageUtil;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiBuilder;
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

import net.alliedmods.lang.amxxpawn.file.ApScriptFileType;
import net.alliedmods.lang.amxxpawn.lexer.ApLexer;
import net.alliedmods.lang.amxxpawn.parser.ApParser;
import net.alliedmods.lang.amxxpawn.psi.ApTokenType;
import net.alliedmods.lang.amxxpawn.psi.IApElementType;
import net.alliedmods.lang.amxxpawn.psi.deprecated.PsiElementWrapper;
import net.alliedmods.lang.amxxpawn.psi.file.ApIncludeFile;
import net.alliedmods.lang.amxxpawn.psi.file.ApScriptFile;
import net.alliedmods.lang.amxxpawn.psi.impl.source.tree.ElementType;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

import static net.alliedmods.lang.amxxpawn.psi.ApTokenType.END_OF_LINE_COMMENT;

public class ApParserDefinition implements ParserDefinition {

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
    //return new ApBracesParser();
    return ApParser.createParser();
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
    // TODO: return TokenSet.create(ApElementTypeDisabled.LITERAL_EXPRESSION);
    return ElementType.AMXX_STRING_LITERAL_BIT_SET;
  }

  @NotNull
  @Override
  public PsiElement createElement(ASTNode astNode) {
    //IElementType elementType = astNode.getElementType();
    //if (ElementType.AMXX_INCLUDE.contains(elementType)) {
    //  return new PsiIncludeImpl(astNode);
    //}

    return new PsiElementWrapper(astNode);
    //return ApElementTypes.Factory.createElement(astNode);
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

  public static final IApElementType BLOCK = new IApElementType("BLOCK");
  public static final IApElementType STATEMENT = new IApElementType("STATEMENT");
  public static final IApElementType PARENS = new IApElementType("PARENS");

  public static final TokenSet STRUCTURE = TokenSet.create(
      BLOCK, STATEMENT, PARENS);

  private static class ApBracesParser implements PsiParser {
    @NotNull
    @Override
    public ASTNode parse(IElementType iElementType, PsiBuilder psiBuilder) {
      final PsiBuilder.Marker rootMarker = psiBuilder.mark();
      final LinkedList<BlockInfo> blocks = new LinkedList<>();
      boolean openBlock = true;

      while (psiBuilder.getTokenType() != null) {
        if (openBlock) {
          final IElementType type = psiBuilder.getTokenType();
          if (type != ApTokenType.RBRACE && type != ApTokenType.SEMICOLON && type != ApTokenType.SEMICOLON_SYNTHETIC && type != ApTokenType.COMMA &&
              type != ApTokenType.LBRACE && (type != ApTokenType.PREPROCESSOR || !psiBuilder.getTokenText().equals("\\"))) {
            openBlock = false;
            blocks.add(new BlockInfo(psiBuilder.mark(), blocks.size() > 0 ? BlockInfo.BlockType.STATEMENT : BlockInfo.BlockType.FUNC));
          }
        }

        final IElementType tokenType = psiBuilder.getTokenType();

        if (tokenType == ApTokenType.LBRACE) {
          blocks.add(new BlockInfo(psiBuilder.mark(), BlockInfo.BlockType.BLOCK));
        } else if (tokenType == ApTokenType.LPARENTH || tokenType == ApTokenType.LBRACKET) {
          if (blocks.size() > 0) blocks.getLast().addParens(psiBuilder.mark());
        }

        final PsiBuilder.Marker tokenMarker = requiresComposite(tokenType) ? psiBuilder.mark() : null;
        psiBuilder.advanceLexer();
        if (tokenMarker != null) tokenMarker.done(tokenType);

        if (tokenType == ApTokenType.LBRACE) {
          openBlock = true;
        } else if (tokenType == ApTokenType.RPARENTH || tokenType == ApTokenType.RBRACKET) {
          if (blocks.size() > 0) blocks.getLast().doneParens();
        } else if (tokenType == ApTokenType.RBRACE && blocks.size() > 0) {
          blocks.removeLast().done();
          if (blocks.size() > 0) blocks.removeLast().done();
          openBlock = true;
        } else if ((tokenType == ApTokenType.SEMICOLON || tokenType == ApTokenType.SEMICOLON_SYNTHETIC) && blocks.size() > 0 && !blocks.getLast().hasParens() && !openBlock) {
          blocks.removeLast().done();
          openBlock = true;
        }
      }

      while (blocks.size() > 0) {
        blocks.removeLast().done();
      }
      rootMarker.done(iElementType);
      return psiBuilder.getTreeBuilt();
    }

    private static boolean requiresComposite(IElementType tokenType) {
      return
          tokenType == ApTokenType.IDENTIFIER ||
              tokenType == ApTokenType.STRING_LITERAL ||
              ElementType.OVERLOADABLE_OPERATIONS.contains(tokenType);
    }

    static class BlockInfo {
      enum BlockType { BLOCK, STATEMENT, FUNC }

      final PsiBuilder.Marker block;
      LinkedList<PsiBuilder.Marker> parensList;
      final BlockType blockType;

      BlockInfo(PsiBuilder.Marker _block, BlockType _blockType) {
        block = _block;
        blockType = _blockType;
      }

      void done() {
        if (parensList != null) {
          while (!parensList.isEmpty()) doneParens();
        }

        switch (blockType) {
          case BLOCK:
            block.done(BLOCK);
            break;
          case STATEMENT: default:
            block.done(STATEMENT);
            break;
        }
      }

      public void addParens(PsiBuilder.Marker marker) {
        if (parensList == null) parensList = new LinkedList<PsiBuilder.Marker>();
        parensList.add(marker);
      }

      public void doneParens() {
        if (parensList != null && parensList.size() > 0)
          parensList.removeLast().done(PARENS);
      }

      public boolean hasParens() {
        return parensList != null && parensList.size() > 0;
      }
    }
  }
}
