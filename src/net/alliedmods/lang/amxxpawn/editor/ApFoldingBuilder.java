package net.alliedmods.lang.amxxpawn.editor;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiImportStatement;
import com.intellij.psi.impl.source.SourceTreeToPsiMap;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;
import net.alliedmods.lang.amxxpawn.psi.ApElementTypes;
import net.alliedmods.lang.amxxpawn.psi.PsiApFile;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.PsiIncludeStatement;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApFoldingBuilder extends FoldingBuilderEx implements DumbAware {
  @NotNull
  @Override
  public FoldingDescriptor[] buildFoldRegions(@NotNull PsiElement element,
                                              @NotNull Document document,
                                              boolean quick) {
    if (!(element instanceof PsiApFile)) {
      return FoldingDescriptor.EMPTY;
    }

    List<FoldingDescriptor> result = new ArrayList<>();
    addElementsToFold(result, (PsiApFile) element, document, true, quick);
    return result.toArray(new FoldingDescriptor[result.size()]);
  }

  private void addElementsToFold(List<FoldingDescriptor> list, PsiApFile file,
                                 Document document, boolean foldApDocs, boolean quick) {
    Set<PsiElement> processedComments = new HashSet<>();
    Set<PsiElement> processedIncludes = new HashSet<>();
    for (PsiElement child : file.getChildren()) {
      ProgressManager.checkCanceled();
      if (child instanceof PsiComment) {
        addCommentFolds((PsiComment) child, processedComments, list);
      } else if (child instanceof PsiIncludeStatement) {
        addIncludeFolds((PsiIncludeStatement) child, processedIncludes, list);
      }
    }
  }

  private static void addCommentFolds(@NotNull PsiComment comment,
                                      @NotNull Set<PsiElement> processedComments,
                                      @NotNull List<FoldingDescriptor> foldElements) {
    if (processedComments.contains(comment)
     || comment.getTokenType() != ApTokenTypes.END_OF_LINE_COMMENT) {
      return;
    }

    PsiElement end = null;
    for (PsiElement current = comment.getNextSibling();
         current != null;
         current = current.getNextSibling()) {
      ASTNode node = current.getNode();
      if (node == null) {
        break;
      }

      IElementType elementType = node.getElementType();
      if (elementType == ApTokenTypes.END_OF_LINE_COMMENT) {
        end = current;
        processedComments.add(current);
        continue;
      } else if (elementType == ApTokenTypes.WHITE_SPACE) {
        continue;
      }

      break;
    }

    if (end != null) {
      int startOffset = comment.getTextRange().getStartOffset();
      int endOffset = end.getTextRange().getEndOffset();
      foldElements.add(new FoldingDescriptor(comment, new TextRange(startOffset, endOffset)));
    }
  }

  private static void addIncludeFolds(@NotNull PsiIncludeStatement include,
                                      @NotNull Set<PsiElement> processedIncludes,
                                      @NotNull List<FoldingDescriptor> foldElements) {
    if (processedIncludes.contains(include)) {
      return;
    }

    PsiElement end = null;
    for (PsiElement current = include.getNextSibling();
         current != null;
         current = current.getNextSibling()) {
      ASTNode node = current.getNode();
      if (node == null) {
        break;
      }

      IElementType elementType = node.getElementType();
      if (elementType == ApElementTypes.INCLUDE_STATEMENT) {
        end = current;
        processedIncludes.add(current);
        continue;
      } else if (elementType == ApTokenTypes.WHITE_SPACE) {
        continue;
      }

      break;
    }

    if (end != null) {
      PsiElement includeKeyword = include.getFirstChild().getNextSibling();
      int startOffset = includeKeyword.getTextRange().getEndOffset() + 1;
      int endOffset = end.getTextRange().getEndOffset();
      foldElements.add(new FoldingDescriptor(include, new TextRange(startOffset, endOffset)));
    }
  }

  /*@Nullable
  public static TextRange getRangeToFold(@Nullable PsiElement element) {
    if (element instanceof PsiComment) {
      int startOffset = element.getTextRange().getStartOffset();
      PsiElement last = element;
    }
  }*/

  @Nullable
  @Override
  public String getPlaceholderText(@NotNull ASTNode node) {
    return getPlaceholderText(SourceTreeToPsiMap.treeElementToPsi(node));
  }

  @NotNull
  private static String getPlaceholderText(@Nullable PsiElement element) {
    System.out.println("element=" + element);
    if (element instanceof PsiComment) {
      // FIXME: This is not ever called for PsiComment :(
      return "//...";
    } else if (element instanceof PsiImportStatement) {
      return "...";
    }

    return "...";
  }

  @Override
  public boolean isCollapsedByDefault(@NotNull ASTNode astNode) {
    return false;
  }
}
