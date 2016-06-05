package net.alliedmods.intellij.sourcepawn.file;

import com.intellij.openapi.fileTypes.LanguageFileType;

import net.alliedmods.intellij.sourcepawn.SourcePawnIcons;
import net.alliedmods.intellij.sourcepawn.SourcePawnLanguage;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SourcePawnIncludeFileType extends LanguageFileType {

  public static final SourcePawnIncludeFileType INSTANCE = new SourcePawnIncludeFileType();

  @NotNull
  @NonNls
  public static final String[] EXTS = { "inc", "p", "pawn", "inl" };

  private SourcePawnIncludeFileType() {
    super(SourcePawnLanguage.INSTANCE);
  }

  @NotNull
  @Override
  public String getName() {
    return "SourcePawn Include";
  }

  @NotNull
  @Override
  public String getDescription() {
    return "Plugin script include for SourceMod";
  }

  @NotNull
  @Override
  public String getDefaultExtension() {
    return EXTS[0];
  }

  @Nullable
  @Override
  public Icon getIcon() {
    return SourcePawnIcons.INCLUDE;
  }

}
