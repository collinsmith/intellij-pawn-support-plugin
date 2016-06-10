package net.sourcemod.sourcepawn.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;

import net.sourcemod.sourcepawn.SpLanguage;
import net.sourcemod.sourcepawn.file.SpScript;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class SpScriptFile extends PsiFileBase {

  public SpScriptFile(@NotNull FileViewProvider viewProvider) {
    super(viewProvider, SpLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public FileType getFileType() {
    return SpScript.INSTANCE;
  }

  @Override
  public String toString() {
    return "Include file for a SourceMod plugin";
  }

  @Override
  public Icon getIcon(int flags) {
    return super.getIcon(flags);
  }
}