package net.alliedmods.lang.amxxpawn.parser;

import org.jetbrains.annotations.NotNull;

public class ApParser {
  public static final ApParser INSTANCE = new ApParser();

  public static final char PUBLIC_CHAR = '@';
  public static final int DIMEN_MAX = 3;

  @NotNull private final FileParser fileParser;
  @NotNull private final PreprocessorParser preprocessorParser;
  @NotNull private final DeclarationParser declarationParser;
  @NotNull private final StatementParser statementParser;
  @NotNull private final ExpressionParser expressionParser;

  public ApParser() {
    this.fileParser = new FileParser(this);
    this.preprocessorParser = new PreprocessorParser(this);
    this.declarationParser = null;//new DeclarationParser(this);
    this.statementParser = new StatementParser(this);
    this.expressionParser = new ExpressionParser(this);
  }

  @NotNull
  public FileParser getFileParser() {
    return fileParser;
  }

  @NotNull
  public PreprocessorParser getPreprocessorParser() {
    return preprocessorParser;
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
}
