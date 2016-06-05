package net.alliedmods.intellij.sourcepawn.file;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;

import net.alliedmods.intellij.sourcepawn.SourcePawnLanguage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SourcePawnIncludeFile extends PsiFileBase {

  public SourcePawnIncludeFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, SourcePawnLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return SourcePawnScriptFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return getFileType().getName();
  }

  @Nullable
  @Override
  public Icon getIcon(int flags) {
    return super.getIcon(flags);
  }

}
