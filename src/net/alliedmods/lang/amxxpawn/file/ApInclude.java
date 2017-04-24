package net.alliedmods.lang.amxxpawn.file;

import com.intellij.openapi.fileTypes.LanguageFileType;

import net.alliedmods.lang.amxxpawn.ApBundle;
import net.alliedmods.lang.amxxpawn.ApIcons;
import net.alliedmods.lang.amxxpawn.ApLanguage;
import net.alliedmods.lang.amxxpawn.ApSupport;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ApInclude extends LanguageFileType {

  @NotNull
  public static final ApInclude INSTANCE = new ApInclude();

  @NotNull
  public static ApInclude getInstance() {
    return INSTANCE;
  }

  private ApInclude() {
    super(ApLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return ApBundle.message("amxx.file.inc.name");
  }

  @NotNull
  @Override
  public String getDescription() {
    return ApBundle.message("amxx.file.inc.desc");
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return ApSupport.INC;
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return ApIcons.AmxxInclude16;
  }

}
