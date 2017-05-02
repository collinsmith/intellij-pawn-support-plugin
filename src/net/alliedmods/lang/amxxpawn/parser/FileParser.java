package net.alliedmods.lang.amxxpawn.parser;

import com.intellij.AbstractBundle;
import com.intellij.lang.PsiBuilder;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class FileParser {

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
    PsiBuilder.Marker marker;
    //while (!builder.eof()) {
    //  final IElementType tokenType = builder.getTokenType();
    marker = apParser.getDeclarationParser().parse(builder, DeclarationParser.Context.FILE);
    //}
  }

  private Pair<PsiBuilder.Marker, Boolean> parseInclude(PsiBuilder builder,
                                                        Predicate<PsiBuilder> stopper) {
    PsiBuilder.Marker list = builder.mark();

    IElementType tokenType = builder.getTokenType();
    boolean isEmpty = tokenType != null;
    return null;
  }

}
