package net.alliedmods.lang.amxxpawn.oldpsi2.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.PsiFileImpl;

import net.alliedmods.lang.amxxpawn.ApLanguage;
import net.alliedmods.lang.amxxpawn.oldpsi2.ApElementVisitor;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.ApStubElementTypes;
import net.alliedmods.lang.amxxpawn.file.ApScriptFileType;
import net.alliedmods.lang.amxxpawn.oldpsi2.PsiApFile;

import org.jetbrains.annotations.NotNull;

public class PsiApFileImpl extends PsiFileBase implements PsiApFile {
  private final ApScriptFileType fileType;

  public PsiApFileImpl(FileViewProvider viewProvider) {
    this(viewProvider, (ApScriptFileType) viewProvider.getFileType());
  }

  public PsiApFileImpl(FileViewProvider viewProvider, @NotNull ApScriptFileType fileType) {
    super(ApStubElementTypes.AP_FILE, ApStubElementTypes.AP_FILE, viewProvider);
    this.fileType = fileType;
  }

  @NotNull
  @Override
  public Language getLanguage() {
    return ApLanguage.INSTANCE;
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return fileType;
  }

  @Override
  public String toString() {
    return "PsiApFile:" + getName();
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ApElementVisitor) {
      ((ApElementVisitor) visitor).visitApFile(this);
    } else {
      visitor.visitFile(this);
    }
  }

  @Override
  public PsiReference getReference() {
    return super.getReference();
  }
}
