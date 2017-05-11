package net.alliedmods.lang.amxxpawn.editor;

import com.intellij.lang.ASTNode;
import com.intellij.lang.CodeDocumentationAwareCommenterEx;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;

import org.jetbrains.annotations.Nullable;

public class ApCommenter implements CodeDocumentationAwareCommenterEx {

  @Nullable
  public String getLineCommentPrefix() {
    return "//";
  }

  @Nullable
  public String getBlockCommentPrefix() {
    return "/*";
  }

  @Nullable
  public String getBlockCommentSuffix() {
    return "*/";
  }

  public String getCommentedBlockCommentPrefix() {
    return null;
  }

  public String getCommentedBlockCommentSuffix() {
    return null;
  }

  @Override
  @Nullable
  public IElementType getLineCommentTokenType() {
    return ApTokenTypes.END_OF_LINE_COMMENT;
  }

  @Override
  @Nullable
  public IElementType getBlockCommentTokenType() {
    return ApTokenTypes.C_STYLE_COMMENT;
  }

  @Override
  @Nullable
  public IElementType getDocumentationCommentTokenType() {
    return ApTokenTypes.DOC_COMMENT;
  }

  @Override
  public String getDocumentationCommentPrefix() {
    return "/**";
  }

  @Override
  public String getDocumentationCommentLinePrefix() {
    return "*";
  }

  @Override
  public String getDocumentationCommentSuffix() {
    return "*/";
  }

  @Override
  public boolean isDocumentationComment(final PsiComment element) {
    return element instanceof PsiDocComment;
  }

  @Override
  public boolean isDocumentationCommentText(final PsiElement element) {
    if (element == null) return false;
    final ASTNode node = element.getNode();
    return node != null
        && (node.getElementType() == JavaDocTokenType.DOC_COMMENT_DATA
            || node .getElementType() == JavaDocTokenType.DOC_TAG_VALUE_TOKEN);
  }
}
