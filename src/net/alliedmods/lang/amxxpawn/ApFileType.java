package net.alliedmods.lang.amxxpawn;

import com.intellij.openapi.fileTypes.LanguageFileType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ApFileType extends LanguageFileType {

  @NotNull
  public static final ApFileType INSTANCE = new ApFileType();

  ApFileType() {
    super(ApLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return "AmxxPawn.Script";
  }

  @NotNull
  @Override
  public String getDescription() {
    return ApBundle.message("amxx.file.script.name");
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return ApSupport.SMA;
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return ApIcons.AmxxScript16;
  }

}
