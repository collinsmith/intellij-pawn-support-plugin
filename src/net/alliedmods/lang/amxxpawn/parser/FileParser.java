package net.alliedmods.lang.amxxpawn.parser;

import com.intellij.AbstractBundle;
import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.lang.PsiBuilderUtil.expect;
import static net.alliedmods.lang.amxxpawn.parser.ApParserUtils.error;

public class FileParser {
  TokenSet RECOVER = TokenSet.create(ApTokenTypes.SEMICOLON_SYNTHETIC);

  @NotNull
  private final ApParser apParser;

  public FileParser(@NotNull ApParser apParser) {
    this.apParser = apParser;
  }

  public void parse(@NotNull PsiBuilder builder) {
    parseFile(builder, ApErrorMessages.INSTANCE, "amxx.err.013");
  }

  public void parseFile(@NotNull PsiBuilder builder,
                        @NotNull AbstractBundle bundle,
                        @NotNull String errorMessageKey) {
    while (!builder.eof()) {
      doParse(builder);
    }
  }

  @Nullable
  private PsiBuilder.Marker doParse(@NotNull PsiBuilder builder) {
    PsiBuilder.Marker line = builder.mark();
    IElementType tokenType = builder.getTokenType();
    if (tokenType == ApTokenTypes.HASH) {
      if (apParser.getPreprocessorParser().parse(builder) == null) {
        do {
          builder.advanceLexer();
          tokenType = builder.getTokenType();
        } while (tokenType != null && !RECOVER.contains(tokenType));
        error(line, "amxx.err.031");
        return null;
      }

      if (builder.eof() || expect(builder, ApTokenTypes.SEMICOLON_SYNTHETIC)) {
        line.drop();
        return null;
      }
    }

    while (tokenType != null && tokenType != ApTokenTypes.SEMICOLON_SYNTHETIC) {
      builder.advanceLexer();
      tokenType = builder.getTokenType();
    }

    builder.advanceLexer();
    line.error("invalid input: " + builder.getTokenText());
    return null;
  }

}
