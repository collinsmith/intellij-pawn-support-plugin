package net.alliedmods.intellij.pawn;

import com.intellij.lang.Language;

import org.jetbrains.annotations.NotNull;

public class PawnLanguage extends Language {

  @NotNull
  public static final PawnLanguage INSTANCE = new PawnLanguage();

  private PawnLanguage() {
    super("Pawn");
  }

}
