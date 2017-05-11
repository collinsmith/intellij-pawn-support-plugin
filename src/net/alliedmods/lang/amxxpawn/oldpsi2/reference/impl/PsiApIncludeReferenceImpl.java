package net.alliedmods.lang.amxxpawn.oldpsi2.reference.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.TokenType;
import com.intellij.psi.impl.source.SourceTreeToPsiMap;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.tree.ChildRoleBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiUtilCore;
import com.intellij.util.IncorrectOperationException;

import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;
import net.alliedmods.lang.amxxpawn.oldpsi2.ApElementTypes;
import net.alliedmods.lang.amxxpawn.oldpsi2.ElementTypes;
import net.alliedmods.lang.amxxpawn.oldpsi2.PsiApFile;
import net.alliedmods.lang.amxxpawn.oldpsi2.impl.ChildRole;
import net.alliedmods.lang.amxxpawn.oldpsi2.reference.ApFileManager;
import net.alliedmods.lang.amxxpawn.oldpsi2.reference.PsiApIncludeReference;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PsiApIncludeReferenceImpl extends CompositePsiElement implements PsiApIncludeReference {
  public static final int PATH_REFERENCE_KIND = 1;
  public static final int RELATIVE_REFERENCE_KIND = 2;

  private int kindWhenDummy = PATH_REFERENCE_KIND;

  private static final TokenSet INCLUDE_KEYWORDS = TokenSet.create(ApTokenTypes.INCLUDE, ApTokenTypes.TRYINCLUDE);

  public PsiApIncludeReferenceImpl() {
    super(ApElementTypes.AP_FILE_REFERENCE);
  }

  public void setKindWhenDummy(int kind) {
    assert isDummy(getTreeParent().getElementType());
    this.kindWhenDummy = kind;
  }

  private static boolean isDummy(IElementType type) {
    return type == TokenType.DUMMY_HOLDER || type == ApElementTypes.DUMMY_ELEMENT;
  }

  public int getKind() {
    PsiUtilCore.ensureValid(this);
    CompositeElement treeParent = getTreeParent();
    IElementType i = treeParent.getElementType();
    if (isDummy(i)) {
      return kindWhenDummy;
    }

    if (i == ApElementTypes.INCLUDE_STATEMENT) {
      return PATH_REFERENCE_KIND;
    } else if (i == ApElementTypes.RELATIVE_INCLUDE_STATEMENT) {
      return RELATIVE_REFERENCE_KIND;
    }

    return kindWhenDummy;
  }

  @Nullable
  @Override
  public ASTNode findChildByRole(int role) {
    // TODO: Override
    switch (role) {
      case ChildRole.INCLUDE_KEYWORD:
        return findChildByType(INCLUDE_KEYWORDS);
      case ChildRole.INCLUDE_REFERENCE:
        return findChildByType(ElementTypes.AMXX_INCLUDE);
      default:
        return null;
    }
  }

  @Override
  public int getChildRole(ASTNode child) {
    // TODO: Override
    IElementType i = child.getElementType();
    if (INCLUDE_KEYWORDS.contains(i)) {
      return ChildRole.INCLUDE_KEYWORD;
    } else if (ElementTypes.AMXX_INCLUDE.contains(i)) {
      return ChildRole.INCLUDE_REFERENCE;
    }

    return ChildRoleBase.NONE;
  }

  @NotNull
  @Override
  public String getCanonicalText() {
    switch (getKind()) {
      case PATH_REFERENCE_KIND:
      case RELATIVE_REFERENCE_KIND:
        return getNormalizedText();
      default:
        return null;
    }
  }

  @Override
  public PsiElement handleElementRename(String s) throws IncorrectOperationException {
    return null;
  }

  @Override
  public PsiElement bindToElement(@NotNull PsiElement psiElement) throws IncorrectOperationException {
    return null;
  }

  @Override
  public boolean isReferenceTo(PsiElement psiElement) {
    return false;
  }

  @NotNull
  @Override
  public Object[] getVariants() {
    return new Object[0];
  }

  @Override
  public boolean isSoft() {
    return false;
  }

  @NotNull
  private String getNormalizedText() {
    ASTNode reference = findChildByRole(ChildRole.INCLUDE_REFERENCE);
    if (reference == null) {
      return "";
    }

    String text = reference.getText();
    text = text.substring(1, text.length() - 1);
    text = text.trim();
    return text;
  }

  @Override
  public PsiReference getReference() {
    return this;
  }

  @Override
  public PsiElement getElement() {
    return null;
  }

  @Override
  public TextRange getRangeInElement() {
    return null;
  }

  @Nullable
  @Override
  public PsiElement resolve() {
    ResolveResult[] results = multiResolve(false);
    return results.length == 1 ? results[0].getElement() : null;
  }

  @NotNull
  @Override
  public ResolveResult[] multiResolve(boolean incompleteCode) {
    Module module = ModuleUtilCore.findModuleForPsiElement(this);
    if (module == null) {
      return ResolveResult.EMPTY_ARRAY;
    }

    GlobalSearchScope scope = GlobalSearchScope.moduleWithLibrariesScope(module);
    ApFileManager fileManager = ApFileManagerImpl.getInstance(getProject());
    PsiApFile[] files = fileManager.findIncludes(getNormalizedText(), scope);
    return PsiElementResolveResult.createResults(files);
  }

  @Override
  public int getTextOffset() {
    ASTNode refName = getReferenceNameNode();
    return refName != null ? refName.getStartOffset() : super.getTextOffset();
  }

  @Nullable
  @Override
  public PsiElement getReferenceNameElement() {
    return SourceTreeToPsiMap.treeElementToPsi(getReferenceNameNode());
  }

  @Nullable
  private ASTNode getReferenceNameNode() {
    return findChildByRole(ChildRole.INCLUDE_REFERENCE);
  }
}
