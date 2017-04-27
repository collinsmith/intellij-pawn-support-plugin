package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;

import net.alliedmods.lang.amxxpawn.ApBundle;
import net.alliedmods.lang.amxxpawn.ApLanguage;
import net.alliedmods.lang.amxxpawn.file.ApScriptFileType;

import org.jetbrains.annotations.NotNull;

public class ApScriptFile extends PsiFileBase {

  public ApScriptFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, ApLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return ApScriptFileType.INSTANCE;
  }

  @Override
  public String toString() {
    return ApBundle.message("amxx.file.script.name");
  }

}
