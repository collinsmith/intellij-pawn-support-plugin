package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.ide.util.PsiNavigationSupport;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.impl.CheckUtil;
import com.intellij.psi.impl.source.SourceTreeToPsiMap;
import com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import com.intellij.psi.impl.source.tree.ChangeUtil;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.SharedImplUtil;
import com.intellij.psi.impl.source.tree.TreeElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.IncorrectOperationException;

import net.alliedmods.lang.amxxpawn.ApLanguage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ApStubPsiElement<T extends StubElement>
    extends StubBasedPsiElementBase<T>
    implements StubBasedPsiElement<T> {
  private static final Logger LOG = Logger.getInstance("#net.alliedmods.lang.amxxpawn.psi.ApStubPsiElement");

  public ApStubPsiElement(@NotNull T stub, @NotNull IStubElementType type) {
    super(stub, type);
  }

  public ApStubPsiElement(@NotNull ASTNode node) {
    super(node);
  }

  @NotNull
  @Override
  public Language getLanguage() {
    return ApLanguage.INSTANCE;
  }

  protected CompositeElement calcTreeElement() {
    return (CompositeElement) getNode();
  }

  @Override
  protected Object clone() {
    CompositeElement treeElement = calcTreeElement();
    CompositeElement treeElementClone = treeElement.getTreeParent() != null
        ? (CompositeElement) treeElement.copyElement()
        : treeElement.clone();
    return cloneImpl(treeElementClone);
  }

  protected StubBasedPsiElementBase cloneImpl(@NotNull CompositeElement treeElementClone) {
    StubBasedPsiElementBase clone = (StubBasedPsiElementBase) super.clone();
    clone.setNode(treeElementClone);
    treeElementClone.setPsi(clone);
    return clone;
  }

  @Override
  public PsiElement add(@NotNull PsiElement element) throws IncorrectOperationException {
    CheckUtil.checkWritable(this);
    TreeElement elementCopy = ChangeUtil.copyToElement(element);
    calcTreeElement().addInternal(elementCopy, elementCopy, null, null);
    elementCopy = ChangeUtil.decodeInformation(elementCopy);
    return SourceTreeToPsiMap.treeElementToPsi(elementCopy);
  }

  @Override
  public PsiElement addBefore(@NotNull PsiElement element, PsiElement anchor) throws IncorrectOperationException {
    CheckUtil.checkWritable(this);
    TreeElement elementCopy = ChangeUtil.copyToElement(element);
    calcTreeElement().addInternal(elementCopy, elementCopy, SourceTreeToPsiMap.psiElementToTree(anchor), Boolean.TRUE);
    elementCopy = ChangeUtil.decodeInformation(elementCopy);
    return SourceTreeToPsiMap.treeElementToPsi(elementCopy);
  }

  @Override
  public PsiElement addAfter(@NotNull PsiElement element, @Nullable PsiElement anchor) throws IncorrectOperationException {
    CheckUtil.checkWritable(this);
    TreeElement elementCopy = ChangeUtil.copyToElement(element);
    calcTreeElement().addInternal(elementCopy, elementCopy, SourceTreeToPsiMap.psiElementToTree(anchor), Boolean.FALSE);
    elementCopy = ChangeUtil.decodeInformation(elementCopy);
    return SourceTreeToPsiMap.treeElementToPsi(elementCopy);
  }

  @Override
  public final void checkAdd(@NotNull PsiElement element) throws IncorrectOperationException {
    CheckUtil.checkWritable(this);
  }

  @Override
  public PsiElement addRange(PsiElement first, PsiElement last) throws IncorrectOperationException {
    return SharedImplUtil.addRange(this, first, last, null, null);
  }

  @Override
  public PsiElement addRangeBefore(@NotNull PsiElement first, @NotNull PsiElement last, PsiElement anchor)
      throws IncorrectOperationException {
    return SharedImplUtil.addRange(this, first, last, SourceTreeToPsiMap.psiElementToTree(anchor), Boolean.TRUE);
  }

  @Override
  public PsiElement addRangeAfter(PsiElement first, PsiElement last, PsiElement anchor)
      throws IncorrectOperationException {
    return SharedImplUtil.addRange(this, first, last, SourceTreeToPsiMap.psiElementToTree(anchor), Boolean.FALSE);
  }

  @Override
  public void delete() throws IncorrectOperationException {
    ASTNode treeElement = calcTreeElement();
    LOG.assertTrue(treeElement.getTreeParent() != null);
    CheckUtil.checkWritable(this);
    ((CompositeElement) treeElement.getTreeParent()).deleteChildInternal(treeElement);
  }

  @Override
  public void deleteChildRange(PsiElement first, PsiElement last) throws IncorrectOperationException {
    CheckUtil.checkWritable(this);
    if (first == null) {
      LOG.assertTrue(last == null);
      return;
    }
    ASTNode firstElement = SourceTreeToPsiMap.psiElementToTree(first);
    ASTNode lastElement = SourceTreeToPsiMap.psiElementToTree(last);
    CompositeElement treeElement = calcTreeElement();
    LOG.assertTrue(firstElement.getTreeParent() == treeElement);
    LOG.assertTrue(lastElement.getTreeParent() == treeElement);
    CodeEditUtil.removeChildren(treeElement, firstElement, lastElement);
  }

  @Override
  public PsiElement replace(@NotNull PsiElement newElement) throws IncorrectOperationException {
    CompositeElement treeElement = calcTreeElement();
    return SharedImplUtil.doReplace(this, treeElement, newElement);
  }

  @Override
  public void navigate(boolean requestFocus) {
    final Navigatable navigatable = PsiNavigationSupport.getInstance().getDescriptor(this);
    if (navigatable != null) {
      navigatable.navigate(requestFocus);
    }
  }

  @Override
  public boolean canNavigate() {
    return PsiNavigationSupport.getInstance().canNavigate(this);
  }

  @Override
  public boolean canNavigateToSource() {
    return canNavigate();
  }

  @Override
  public void acceptChildren(@NotNull PsiElementVisitor visitor) {
    SharedImplUtil.acceptChildren(visitor, calcTreeElement());
  }

  @Override
  public void subtreeChanged() {
    final CompositeElement compositeElement = calcTreeElement();
    if (compositeElement != null) compositeElement.clearCaches();
    super.subtreeChanged();
  }

  @Override
  @NotNull
  public PsiElement[] getChildren() {
    PsiElement psiChild = getFirstChild();
    if (psiChild == null) return EMPTY_ARRAY;

    int count = 0;
    while (psiChild != null) {
      count++;
      psiChild = psiChild.getNextSibling();
    }

    PsiElement[] answer = new PsiElement[count];
    count = 0;
    psiChild = getFirstChild();
    while (psiChild != null) {
      answer[count++] = psiChild;
      psiChild = psiChild.getNextSibling();
    }

    return answer;
  }
}
