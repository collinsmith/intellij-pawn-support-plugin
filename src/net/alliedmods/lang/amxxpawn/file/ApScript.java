package net.alliedmods.lang.amxxpawn.file;

import com.intellij.openapi.fileTypes.LanguageFileType;

import net.alliedmods.lang.amxxpawn.ApBundle;
import net.alliedmods.lang.amxxpawn.ApIcons;
import net.alliedmods.lang.amxxpawn.ApLanguage;
import net.alliedmods.lang.amxxpawn.ApSupport;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ApScript extends LanguageFileType {

  @NotNull
  public static final ApScript INSTANCE = new ApScript();

  @NotNull
  public static ApScript getInstance() {
    return INSTANCE;
  }

  private ApScript() {
    super(ApLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return ApBundle.message("amxx.file.script.name");
  }

  @NotNull
  @Override
  public String getDescription() {
    return ApBundle.message("amxx.file.script.desc");
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
