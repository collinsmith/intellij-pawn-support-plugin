package net.sourcemod.sourcepawn;

import com.intellij.lang.Language;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;

import net.sourcemod.sourcepawn.lexer._SpLexer;

import org.jetbrains.annotations.NotNull;

public class SpLanguage extends Language {

  @NotNull
  public static final SpLanguage INSTANCE = new SpLanguage();

  @NotNull
  public static Lexer createLexer() {
    return new FlexAdapter(new _SpLexer());
  }

  private SpLanguage() {
    super("SourcePawn");
  }

}
