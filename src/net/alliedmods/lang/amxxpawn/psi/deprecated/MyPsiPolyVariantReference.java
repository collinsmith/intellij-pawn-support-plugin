package net.alliedmods.lang.amxxpawn.psi.deprecated;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.ResolveResult;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class MyPsiPolyVariantReference implements PsiPolyVariantReference {
  private final PsiElement psiElement;
  private final TextRange range;

  MyPsiPolyVariantReference(PsiElement _element) {
    this(_element, null);
  }

  MyPsiPolyVariantReference(PsiElement _element, TextRange _range) {
    psiElement = _element;
    range = _range;
  }

  public PsiElement getElement() {
    return psiElement;
  }

  public TextRange getRangeInElement() {
    return range != null ? range : new TextRange(0, psiElement.getTextLength());
  }

  @Nullable
  public PsiElement resolve() {
    final ResolveResult[] resolveResults = multiResolve(true);
    return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
  }

  public String getCanonicalText() {
    String s = psiElement.getText();
    if (range != null) s = s.substring(range.getStartOffset(), range.getEndOffset());
    return s;
  }

  public PsiElement handleElementRename(String string) throws IncorrectOperationException {
    if (range != null) {
      final String s = psiElement.getText();
      string = s.substring(0, range.getStartOffset()) + string + s.substring(range.getEndOffset());
    }
    psiElement.getNode().replaceChild(psiElement.getFirstChild().getNode(), PsiElementWrapper.createNameIdentifier(psiElement.getProject(), string));
    return psiElement;
  }

  public PsiElement bindToElement(PsiElement psiElement) throws IncorrectOperationException {
    return null;
  }

  public boolean isReferenceTo(PsiElement _psiElement) {
    return psiElement == _psiElement;
  }

  @NotNull
  public Object[] getVariants() {
    /*
    final PsiFile psiFile = psiElement.getContainingFile().getOriginalFile();
    int offset = psiElement.getText().indexOf(CompletionUtil.DUMMY_IDENTIFIER_TRIMMED);
    CompletionCommand completion = new CompletionCommand(psiFile.getVirtualFile().getPath(), psiElement.getTextOffset() + offset);
    completion.post(psiFile.getProject());

    if (!completion.hasReadyResult()) return ArrayUtil.EMPTY_OBJECT_ARRAY;

    final List<String> list = completion.getVariants();
    if (list != null) {
      Object[] result = new Object[list.size()];
      int i = 0;

      EnvironmentFacade facade = EnvironmentFacade.getInstance();
      for (String s : list) {
        result[i++] = facade.createLookupElement(s);
      }
      return result;
    }*/
    return ArrayUtil.EMPTY_OBJECT_ARRAY;
  }

  public boolean isSoft() {
    return true;
  }

  @NotNull
  public ResolveResult[] multiResolve(boolean b) {
    return ResolveResult.EMPTY_ARRAY;
  }

  public ResolveResult[] resolveInner() {
    return ResolveResult.EMPTY_ARRAY;
  }
}