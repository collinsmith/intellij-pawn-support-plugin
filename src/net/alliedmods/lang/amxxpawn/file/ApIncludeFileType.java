package net.alliedmods.lang.amxxpawn.file;

import net.alliedmods.lang.amxxpawn.ApBundle;
import net.alliedmods.lang.amxxpawn.ApIcons;
import net.alliedmods.lang.amxxpawn.ApSupport;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class ApIncludeFileType extends ApScriptFileType {

  @NotNull
  public static final ApIncludeFileType INSTANCE = new ApIncludeFileType();

  @NotNull
  public static ApIncludeFileType getInstance() {
    return INSTANCE;
  }

  ApIncludeFileType() {
    super();
  }

  @NotNull
  @Override
  public String getName() {
    return ApBundle.message("amxx.file.inc.desc");
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
