package net.alliedmods.lang.amxxpawn.oldpsi2.reference;

import com.intellij.openapi.util.Key;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApPsiScopeProcessor implements PsiScopeProcessor {
  @Override
  public boolean execute(@NotNull PsiElement element, @NotNull ResolveState state) {
    return false;
  }

  @Nullable
  @Override
  public <T> T getHint(@NotNull Key<T> hintKey) {
    return null;
  }

  @Override
  public void handleEvent(@NotNull Event event, @Nullable Object associated) {}
}
