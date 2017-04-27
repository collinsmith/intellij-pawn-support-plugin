package net.alliedmods.lang.amxxpawn.file;

import com.intellij.openapi.fileTypes.LanguageFileType;

import net.alliedmods.lang.amxxpawn.ApBundle;
import net.alliedmods.lang.amxxpawn.ApIcons;
import net.alliedmods.lang.amxxpawn.ApLanguage;
import net.alliedmods.lang.amxxpawn.ApSupport;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ApScriptFileType extends LanguageFileType {

  @NotNull
  public static final ApScriptFileType INSTANCE = new ApScriptFileType();

  ApScriptFileType() {
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
