package net.alliedmods.lang.amxxpawn.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NotNull;

public class ApParser {
  public static final ApParser INSTANCE = new ApParser();

  public static final char PUBLIC_CHAR = '@';
  public static final int DIMEN_MAX = 3;

  @NotNull private final FileParser fileParser;
  @NotNull private final DeclarationParser declarationParser;
  @NotNull private final StatementParser statementParser;
  @NotNull private final ExpressionParser expressionParser;

  @NotNull
  public static PsiParser createParser() {
    return new _ApParser();
  }

  public ApParser() {
    this.fileParser = new FileParser(this);
    this.declarationParser = new DeclarationParser(this);
    this.statementParser = new StatementParser(this);
    this.expressionParser = new ExpressionParser(this);
  }

  @NotNull
  public FileParser getFileParser() {
    return fileParser;
  }

  @NotNull
  public DeclarationParser getDeclarationParser() {
    return declarationParser;
  }

  @NotNull
  public StatementParser getStatementParser() {
    return statementParser;
  }

  @NotNull
  public ExpressionParser getExpressionParser() {
    return expressionParser;
  }

  private static class _ApParser implements PsiParser, LightPsiParser {

    @NotNull
    @Override
    public ASTNode parse(@NotNull IElementType elementType, @NotNull PsiBuilder builder) {
      final PsiBuilder.Marker rootMarker = builder.mark();
      parseLight(elementType, builder);
      rootMarker.done(elementType);
      return builder.getTreeBuilt();
    }

    @Override
    public void parseLight(IElementType elementType, PsiBuilder builder) {
      ApParser.INSTANCE.fileParser.parse(builder);
    }


  }

}
