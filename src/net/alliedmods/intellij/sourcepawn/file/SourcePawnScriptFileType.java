package net.alliedmods.intellij.sourcepawn.file;

import com.intellij.openapi.fileTypes.LanguageFileType;

import net.alliedmods.intellij.sourcepawn.SourcePawnIcons;
import net.alliedmods.intellij.sourcepawn.SourcePawnLanguage;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SourcePawnScriptFileType extends LanguageFileType {

  public static final SourcePawnScriptFileType INSTANCE = new SourcePawnScriptFileType();

  @NotNull
  @NonNls
  public static final String[] EXTS = { "sp" };

  private SourcePawnScriptFileType() {
    super(SourcePawnLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return "SourcePawn Script";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Plugin script for SourceMod";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return EXTS[0];
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return SourcePawnIcons.SCRIPT;
  }

}
