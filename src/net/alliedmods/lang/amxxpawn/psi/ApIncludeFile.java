package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;

import net.alliedmods.lang.amxxpawn.ApBundle;
import net.alliedmods.lang.amxxpawn.file.ApIncludeFileType;

import org.jetbrains.annotations.NotNull;

public class ApIncludeFile extends ApScriptFile {

  public ApIncludeFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return ApIncludeFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return ApBundle.message("amxx.file.inc.name");
  }

}
