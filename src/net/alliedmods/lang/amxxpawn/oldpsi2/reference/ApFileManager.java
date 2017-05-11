package net.alliedmods.lang.amxxpawn.oldpsi2.reference;

import com.intellij.psi.search.GlobalSearchScope;

import net.alliedmods.lang.amxxpawn.oldpsi2.PsiApFile;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public interface ApFileManager {
  @Nullable
  PsiApFile findInclude(@NotNull String name, @NotNull GlobalSearchScope scope);

  @NotNull
  PsiApFile[] findIncludes(@NotNull String name, @NotNull GlobalSearchScope scope);
}
