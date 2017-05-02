package net.alliedmods.lang.amxxpawn.parser;

import com.intellij.lang.PsiBuilder;

import org.jetbrains.annotations.NotNull;

public class StatementParser {

  @NotNull
  private final ApParser apParser;

  public StatementParser(@NotNull ApParser apParser) {
    this.apParser = apParser;
  }

  public void parse(@NotNull PsiBuilder builder) {
  }

}
