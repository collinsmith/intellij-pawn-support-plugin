package net.alliedmods.lang.amxxpawn.psi.preprocessor;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.impl.source.resolve.reference.impl.providers.PsiFileReference;
import com.intellij.util.ArrayFactory;

import javax.annotation.Nullable;

public interface PsiApFileReference extends PsiElement, PsiFileReference {
  PsiApFileReference[] EMPTY_ARRAY = new PsiApFileReference[0];
  ArrayFactory<PsiApFileReference> ARRAY_FACTORY
      = i -> i == 0 ? EMPTY_ARRAY : new PsiApFileReference[i];

  @Nullable
  PsiElement getReferenceNameElement();

  @Nullable
  ResolveResult advancedResolve(boolean incompleteCode);
}
