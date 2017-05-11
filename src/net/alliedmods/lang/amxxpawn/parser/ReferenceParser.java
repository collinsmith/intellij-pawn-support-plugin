package net.alliedmods.lang.amxxpawn.parser;

import com.intellij.lang.PsiBuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ReferenceParser {

  @NotNull
  private final ApParser apParser;

  public ReferenceParser(@NotNull ApParser apParser) {
    this.apParser = apParser;
  }

  public boolean parseIncludeCodeReference(@NotNull PsiBuilder builder) {
    throw new UnsupportedOperationException();
  }

  @Nullable
  private PsiBuilder.Marker parseApCodeReference(@NotNull PsiBuilder builder) {
    throw new UnsupportedOperationException();
  }
}
