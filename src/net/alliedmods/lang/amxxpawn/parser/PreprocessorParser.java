package net.alliedmods.lang.amxxpawn.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderUtil;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;
import net.alliedmods.lang.amxxpawn.psi.ApElementTypes;
import net.alliedmods.lang.amxxpawn.psi.ElementTypes;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.alliedmods.lang.amxxpawn.parser.ApParserUtils.done;
import static net.alliedmods.lang.amxxpawn.parser.ApParserUtils.error;

public class PreprocessorParser {

  @NotNull
  private final ApParser apParser;

  public PreprocessorParser(@NotNull ApParser apParser) {
    this.apParser = apParser;
  }

  @Nullable
  public PsiBuilder.Marker parse(@NotNull PsiBuilder builder) {
    return parsePreprocessorDirective(builder);
  }

  @Nullable
  public PsiBuilder.Marker parsePreprocessorDirective(@NotNull PsiBuilder builder) {
    IElementType tokenType = builder.getTokenType();
    if (tokenType == null) {
      return null;
    }

    if (tokenType != ApTokenTypes.HASH) {
      return null;
    }

    PsiBuilder.Marker directive = builder.mark();

    builder.advanceLexer();
    tokenType = builder.getTokenType();
    if (ElementTypes.AMXX_INCLUDE_KEYWORDS.contains(tokenType)) {
      return parseIncludeStatement(builder, directive);
    }

    directive.drop();
    return null;
  }

  @Nullable
  private PsiBuilder.Marker parseIncludeStatement(@NotNull PsiBuilder builder, @NotNull PsiBuilder.Marker directive) {
    IElementType tokenType = builder.getTokenType();
    if (tokenType == null) {
      return null;
    }

    if (!ElementTypes.AMXX_INCLUDE_KEYWORDS.contains(tokenType)) {
      String tokenText = builder.getTokenText();
      error(builder, "amxx.err.001.include", "include", "tryinclude", tokenText == null ? "-eof-" : tokenText);
      directive.drop();
      return null;
    }

    builder.advanceLexer();
    PsiBuilder.Marker reference = builder.mark();
    tokenType = builder.getTokenType();
    if (tokenType != ApTokenTypes.INCLUDE_REFERENCE) {
      String tokenText = builder.getTokenText();
      error(builder, "amxx.err.001.include", "<include>", "\"path/to/include.inc\"", tokenText == null ? "-eof-" : tokenText);
      PsiBuilderUtil.drop(directive, reference);
      //directive.drop();
      return null;
    }

    builder.advanceLexer();
    done(reference, ApElementTypes.FILE_REFERENCE);
    return done(directive, ApElementTypes.INCLUDE_STATEMENT);
  }
}
