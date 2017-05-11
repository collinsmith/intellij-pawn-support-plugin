package net.alliedmods.lang.amxxpawn.psi.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.impl.source.PsiFileImpl;

import net.alliedmods.lang.amxxpawn.ApLanguage;
import net.alliedmods.lang.amxxpawn.ApFileType;
import net.alliedmods.lang.amxxpawn.psi.ApElementVisitor;
import net.alliedmods.lang.amxxpawn.psi.PsiApFile;

import org.jetbrains.annotations.NotNull;

public class PsiApFileImpl extends PsiFileBase implements PsiApFile {

  public PsiApFileImpl(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, ApLanguage.INSTANCE);
  }

  @Override
  protected PsiFileImpl clone() {
    PsiApFileImpl clone = (PsiApFileImpl) super.clone();
    clone.clearCaches();
    return clone;
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return ApFileType.INSTANCE;
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ApElementVisitor) {
      ((ApElementVisitor) visitor).visitApFile(this);
    } else {
      super.accept(visitor);
    }
  }

  @Override
  public String toString() {
    return "PsiApFile:" + getName();
  }
}
