package net.sourcemod.sourcepawn.file;

import com.intellij.openapi.fileTypes.LanguageFileType;

import net.sourcemod.sourcepawn.SpIcons;
import net.sourcemod.sourcepawn.SpLanguage;
import net.sourcemod.sourcepawn.SpSupport;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SpScript extends LanguageFileType {

  @NotNull
  public static final SpScript INSTANCE = new SpScript();

  private SpScript() {
    super(SpLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return "SourcePawn Script";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Plugin for SourceMod";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return SpSupport.SP_EXTENSION;
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return SpIcons.SCRIPT;
  }
}
