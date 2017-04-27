package net.alliedmods.lang.amxxpawn;

import com.intellij.lang.Language;

import org.jetbrains.annotations.NotNull;

public class ApLanguage extends Language {

  @NotNull
  public static final ApLanguage INSTANCE = new ApLanguage();

  private ApLanguage() {
    super("AmxxPawn");
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return ApBundle.message("amxx.name");
  }

  @Override
  public boolean isCaseSensitive() {
    return true;
  }

}
