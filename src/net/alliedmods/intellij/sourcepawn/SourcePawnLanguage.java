package net.alliedmods.intellij.sourcepawn;

import com.intellij.lang.Language;

import net.alliedmods.intellij.pawn.PawnLanguage;

import org.jetbrains.annotations.NotNull;

public class SourcePawnLanguage extends Language {

  @NotNull
  public static final SourcePawnLanguage INSTANCE = new SourcePawnLanguage();

  private SourcePawnLanguage() {
    super(PawnLanguage.INSTANCE, "SourcePawn");
  }

}
