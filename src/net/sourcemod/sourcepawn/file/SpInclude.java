package net.sourcemod.sourcepawn.file;

import com.intellij.openapi.fileTypes.LanguageFileType;

import net.sourcemod.sourcepawn.SpIcons;
import net.sourcemod.sourcepawn.SpLanguage;
import net.sourcemod.sourcepawn.SpSupport;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SpInclude extends LanguageFileType {

  @NotNull
  private static final SpInclude INSTANCE = new SpInclude();

  @NotNull
  public static SpInclude getInstance() {
    return INSTANCE;
  }

  private SpInclude() {
    super(SpLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return "SourcePawn Include";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Include file for a SourceMod plugin";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return SpSupport.INC_EXTENSION;
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return SpIcons.INCLUDE;
  }
}
