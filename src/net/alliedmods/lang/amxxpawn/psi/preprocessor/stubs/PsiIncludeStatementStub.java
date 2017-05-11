package net.alliedmods.lang.amxxpawn.psi.preprocessor.stubs;

import com.intellij.psi.stubs.StubElement;

import net.alliedmods.lang.amxxpawn.psi.preprocessor.PsiApFileReference;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.PsiIncludeStatement;

import org.jetbrains.annotations.Nullable;

public interface PsiIncludeStatementStub extends StubElement<PsiIncludeStatement> {
  String getIncludeReferenceText();
  @Nullable PsiApFileReference getReference();
}
