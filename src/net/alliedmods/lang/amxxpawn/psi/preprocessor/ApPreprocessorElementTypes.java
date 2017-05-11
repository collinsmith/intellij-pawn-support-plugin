package net.alliedmods.lang.amxxpawn.psi.preprocessor;

import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.psi.ApElementTypes;
import net.alliedmods.lang.amxxpawn.psi.ApStubElementTypes;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.impl.PsiApFileReferenceImpl;

public interface ApPreprocessorElementTypes {
  IElementType INCLUDE_STATEMENT = ApStubElementTypes.INCLUDE_STATEMENT;

  IElementType FILE_REFERENCE = new ApElementTypes.ApCompositeElementType(
      "FILE_REFERENCE", PsiApFileReferenceImpl.class);
}
