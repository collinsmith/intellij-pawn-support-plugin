package net.alliedmods.lang.amxxpawn.oldpsi2.stubs;

import com.intellij.lang.LighterAST;
import com.intellij.psi.PsiFile;
import com.intellij.psi.stubs.LightStubBuilder;
import com.intellij.psi.stubs.StubElement;

import net.alliedmods.lang.amxxpawn.oldpsi2.PsiApFile;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.impl.PsiApFileStubImpl;

import org.jetbrains.annotations.NotNull;

public class ApLightStubBuilder extends LightStubBuilder {
  @NotNull
  @Override
  protected StubElement createStubForFile(@NotNull PsiFile file, @NotNull LighterAST tree) {
    if (!(file instanceof PsiApFile)) {
      return super.createStubForFile(file, tree);
    }

    return new PsiApFileStubImpl((PsiApFile) file);
  }
}
