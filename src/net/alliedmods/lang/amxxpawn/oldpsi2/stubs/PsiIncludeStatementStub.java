package net.alliedmods.lang.amxxpawn.oldpsi2.stubs;

import com.intellij.psi.stubs.StubElement;

import net.alliedmods.lang.amxxpawn.oldpsi2.PsiApFile;
import net.alliedmods.lang.amxxpawn.oldpsi2.PsiIncludeStatementBase;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public interface PsiIncludeStatementStub extends StubElement<PsiIncludeStatementBase> {
  boolean isRelative();
  @Nullable
  PsiApFile getReferencedFile();
  @NotNull
  String getIncludeReferenceText();
}
