package net.alliedmods.lang.amxxpawn.editor.disabled;

import com.intellij.codeInsight.folding.CodeFoldingSettings;
import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiRecursiveElementVisitor;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.ApParserDefinition;
import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;
import net.alliedmods.lang.amxxpawn.psi.impl.source.tree.ElementType;

import java.util.ArrayList;
import java.util.List;

public class ApFoldingBuilder implements FoldingBuilder {
  private static final int MAX_FOLDING_LINES = 300; // IDEA needs such limit since large folding blocks at top causes performance degradation

  class FoldingCommand {
    private final ASTNode fileNode;

    FoldingCommand(ASTNode _fileNode) {
      fileNode = _fileNode;
    }

    public FoldingDescriptor[] getDescriptors() {
      return ApplicationManager.getApplication().runReadAction(new Computable<FoldingDescriptor[]>() {
        public FoldingDescriptor[] compute() {
          final List<FoldingDescriptor> result = new ArrayList<FoldingDescriptor>(50);
          final PsiFile file = fileNode.getPsi().getContainingFile();
          final Document doc = PsiDocumentManager.getInstance(file.getProject()).getDocument(file);
          //final long started = System.currentTimeMillis();
          file.getFirstChild(); // expand chameleon

          file.accept(new PsiRecursiveElementVisitor() {
            int blockLevel;

            // several subsequent comments!
            public void visitElement(PsiElement psiElement) {
              boolean isBlock = false;
              final ASTNode node = psiElement.getNode();

              if (node != null) {
                final IElementType nodeType = node.getElementType();
                if (nodeType == ApParserDefinition.BLOCK) {
                  isBlock = true;
                  //if (blockLevel > 0) {
                    addFolding(node, doc, result);
                  //}
                  blockLevel++;
                } else if (nodeType == ApTokenTypes.C_STYLE_COMMENT || nodeType == ApTokenTypes.DOC_COMMENT) {
                  addFolding(node, doc, result);
                } else if (nodeType == ApTokenTypes.INCLUDE || nodeType == ApTokenTypes.TRYINCLUDE) {
                  ASTNode start = null;
                  ASTNode end = null;

                  for (ASTNode a = node.getFirstChildNode(); a != null; a = a.getTreeNext()) {
                    IElementType elementType = a.getElementType();
                    if (elementType == ApTokenTypes.PACKED_STRING_LITERAL) {

                    }
                  }
                } else if (nodeType == ApParserDefinition.AP_FILE) {
                  ASTNode start = null;
                  ASTNode end = null;

                  for (ASTNode a = node.getFirstChildNode(); a != null; a = a.getTreeNext()) {
                    IElementType iElementType = a.getElementType();
                    if (ElementType.AMXX_COMMENT_BIT_SET.contains(iElementType)) {
                      if (start == null) start = a;
                      end = a;
                      ASTNode treeNext = a.getTreeNext();
                      if (treeNext != null && treeNext.getElementType() == ApTokenTypes.WHITE_SPACE) {
                        a = treeNext;
                      }
                    } else {
                      break;
                    }
                  }

                  if (start != null && start != end) {
                    result.add(new FoldingDescriptor(
                        node,
                        new TextRange(start.getTextRange().getStartOffset() + "// ".length(), end.getTextRange().getEndOffset())
                    ));

                    for (ASTNode n = end.getTreeNext(); n != null; n = n.getTreeNext()) {
                      super.visitElement(n.getPsi());
                    }
                    return;
                  }
                }
              }

              super.visitElement(psiElement);
              if (isBlock) --blockLevel;
            }
          });

          //System.out.println("Foldings done:"+(System.currentTimeMillis() - started));
          return result.toArray(new FoldingDescriptor[result.size()]);
        }
      });
    }

    private void addFolding(ASTNode node, Document doc, List<FoldingDescriptor> result) {
      final TextRange textRange = node.getTextRange();
      final int startLine = doc.getLineNumber(textRange.getStartOffset());
      final int endLine = doc.getLineNumber(textRange.getEndOffset());

      if (startLine != endLine && (endLine - startLine) < MAX_FOLDING_LINES) {
        result.add(new FoldingDescriptor(
            node,
            textRange
        ));
      }
    }
  }

  public FoldingDescriptor[] buildFoldRegions(ASTNode astNode, Document document) {
    final FoldingCommand foldingCommand = new FoldingCommand(astNode);
    return foldingCommand.getDescriptors();
  }

  public String getPlaceholderText(ASTNode astNode) {
    IElementType elementType = astNode.getElementType();
    if (elementType == ApParserDefinition.BLOCK) {
      return "{...}";
    } else if (elementType == ApTokenTypes.DOC_COMMENT) {
      return "/**...*/";
    } else if (elementType == ApTokenTypes.C_STYLE_COMMENT) {
      return "/*...*/";
    } else if (elementType == ApTokenTypes.END_OF_LINE_COMMENT) {
      return "//...";
    }

    return "...";
  }

  /**
   *
   * @param astNode
   * @return
   */
  public boolean isCollapsedByDefault(ASTNode astNode) {
    if (astNode.getElementType() == ApParserDefinition.AP_FILE ||
        (astNode.getTreeParent().getElementType() == ApParserDefinition.AP_FILE &&
            astNode.getTreePrev() == null &&
            astNode.getPsi() instanceof PsiComment
        )
        ) {
      return CodeFoldingSettings.getInstance().COLLAPSE_FILE_HEADER;
    }
    return false;
  }
}
