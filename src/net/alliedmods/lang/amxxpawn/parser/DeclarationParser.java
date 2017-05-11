package net.alliedmods.lang.amxxpawn.parser;

public class DeclarationParser {
  /*
  public enum Context {
    FILE, CODE_BLOCK
  }

  private static final int SPECIFIER_PUBLIC = 0x1;
  private static final int SPECIFIER_STATIC = 0x2;
  private static final int SPECIFIER_STOCK  = 0x4;
  private static final int SPECIFIER_CONST  = 0x8;

  private static final int PUBLIC_STATIC = SPECIFIER_PUBLIC | SPECIFIER_STATIC;
  private static final int PUBLIC_STOCK = SPECIFIER_PUBLIC | SPECIFIER_STOCK;

  private static final TokenSet GLBDECL_RECOVER = TokenSet.create(
      ApTokenTypes.COMMA, ApTokenTypes.SEMICOLON, ApTokenTypes.SEMICOLON_SYNTHETIC);

  private static final TokenSet CLASSSPEC_RECOVER = TokenSet.create(
      ApTokenTypes.SEMICOLON, ApTokenTypes.SEMICOLON_SYNTHETIC);

  private static final TokenSet FUNCVAR_RECOVER = CLASSSPEC_RECOVER;

  private static final TokenSet FUNCVAR_EXPECTED = TokenSet.create(
      ApTokenTypes.IDENTIFIER, ApTokenTypes.OPERATOR_KEYWORD);

  private static final TokenSet NEWFUNC_RECOVER = TokenSet.create(
      ApTokenTypes.SEMICOLON, ApTokenTypes.SEMICOLON_SYNTHETIC, ApTokenTypes.LPARENTH);

  private static final TokenSet OPNAME_RECOVER = NEWFUNC_RECOVER;

  @NotNull
  private final ApParser apParser;

  public DeclarationParser(@NotNull ApParser apParser) {
    this.apParser = apParser;
  }

  @Nullable
  public PsiBuilder.Marker parse(@NotNull PsiBuilder builder, final Context context) {
    builder.setDebugMode(true);

    IElementType tokenType = builder.getTokenType();
    if (tokenType == null) {
      return null;
    }

    //PsiBuilder.Marker decl = builder.mark();
    if (!ElementTypes.DECL_BIT_SET.contains(tokenType)) {
      //decl.drop();
      return null;
    }

    Pair<PsiBuilder.Marker, Integer> modifiers = parseModifierList(builder);
    //if (tokenType == ApTokenTypes.NEW_KEYWORD) {
    //  declglb(builder, modifiers.second);
    //} else {
    //  declfuncvar(builder, modifiers.second);
    //}

    //return done(decl, ApElementType.DECLARATION);
    return null;
  }

  @Nullable
  public Pair<PsiBuilder.Marker, Integer> parseModifierList(@NotNull PsiBuilder builder) {
    PsiBuilder.Marker classspec = builder.mark();

    int specifiers = 0;
    IElementType tokenType = builder.getTokenType();
    if (tokenType == ApTokenTypes.NEW_KEYWORD) {
      // do nothing
    } else if (tokenType == ApTokenTypes.CONST_KEYWORD) {
      specifiers = SPECIFIER_CONST;
    } else if (tokenType == ApTokenTypes.STOCK_KEYWORD) {
      specifiers = SPECIFIER_STOCK;
    } else if (tokenType == ApTokenTypes.STATIC_KEYWORD) {
      specifiers = SPECIFIER_STATIC;
    } else if (tokenType == ApTokenTypes.PUBLIC_KEYWORD) {
      specifiers = SPECIFIER_PUBLIC;
    }

    while (expect(builder, ElementTypes.DECL_BIT_SET)) {
      tokenType = builder.getTokenType();
      if (tokenType == ApTokenTypes.CONST_KEYWORD) {
        if ((specifiers & SPECIFIER_CONST) == SPECIFIER_CONST) {
          error(classspec, "amxx.err.042.duplicate", "const");
          recoverAt(builder, CLASSSPEC_RECOVER);
          return null;
        }

        specifiers |= SPECIFIER_CONST;
      } else if (tokenType == ApTokenTypes.STOCK_KEYWORD) {
        if ((specifiers & SPECIFIER_STOCK) == SPECIFIER_STOCK) {
          error(classspec, "amxx.err.042.duplicate", "stock");
          recoverAt(builder, CLASSSPEC_RECOVER);
          return null;
        }

        specifiers |= SPECIFIER_STOCK;
      } else if (tokenType == ApTokenTypes.STATIC_KEYWORD) {
        if ((specifiers & SPECIFIER_STATIC) == SPECIFIER_STATIC) {
          error(classspec, "amxx.err.042.duplicate", "static");
          recoverAt(builder, CLASSSPEC_RECOVER);
          return null;
        }

        specifiers |= SPECIFIER_STATIC;
      } else if (tokenType == ApTokenTypes.PUBLIC_KEYWORD) {
        if ((specifiers & SPECIFIER_PUBLIC) == SPECIFIER_PUBLIC) {
          error(classspec, "amxx.err.042.duplicate", "public");
          recoverAt(builder, CLASSSPEC_RECOVER);
          return null;
        }

        specifiers |= SPECIFIER_PUBLIC;
      }
    }

    if ((specifiers & PUBLIC_STATIC) == PUBLIC_STATIC) {
      error(classspec, "amxx.err.042.conflict", "public", "static");
      recoverAt(builder, CLASSSPEC_RECOVER);
      return null;
    }

    done(classspec, ApElementType.MODIFIER_LIST);
    return Pair.create(classspec, specifiers);
  }

  @Nullable
  public Pair<PsiBuilder.Marker, Integer> declglb(@NotNull PsiBuilder builder, int specifiers) {
    do {
      PsiBuilder.Marker glb = builder.mark();

      parseTag(builder);
      if (expectOrError(builder, ApTokenTypes.IDENTIFIER, "amxx.err.020.expected", builder.getTokenText())) {
        glb.drop();
        recoverAt(builder, GLBDECL_RECOVER);
        continue;
      }

      assert builder.getTokenText() != null;
      if (builder.getTokenText().charAt(0) == ApParser.PUBLIC_CHAR) {
        specifiers |= SPECIFIER_PUBLIC;
        if ((specifiers & SPECIFIER_STATIC) == SPECIFIER_STATIC) {
          specifiers &= ~SPECIFIER_STATIC;
          // TODO: throw warning that this glb is implicitly public but declared static
        }
      }

      parseArray(builder);
      done(glb, ApElementType.FIELD);
    } while (expect(builder, ApTokenTypes.COMMA));
    semicolon(builder);
    return Pair.create(null, specifiers);
  }

  @Nullable
  public Pair<PsiBuilder.Marker, Integer> declfuncvar(@NotNull PsiBuilder builder, int specifiers) {
    PsiBuilder.Marker declfuncvar = builder.mark();

    parseTag(builder);

    IElementType tokenType = builder.getTokenType();
    if (tokenType == ApTokenTypes.NATIVE_KEYWORD) {
      error(builder, declfuncvar, "amxx.err.042.cannotbe", "native");
      builder.advanceLexer();
    }

    tokenType = builder.getTokenType();
    if (!FUNCVAR_EXPECTED.contains(tokenType)) {
      error(declfuncvar, "amxx.err.001", "-identifier-", builder.getTokenText());
      recoverAt(builder, FUNCVAR_RECOVER);
      return null;
    }

    if (tokenType == ApTokenTypes.OPERATOR_KEYWORD) {
      if (newfunc(builder, specifiers) == null) {
        error(declfuncvar, "amxx.err.010");
        recoverAt(builder, FUNCVAR_RECOVER);
        return null;
      }
    } else {
      if ((specifiers & SPECIFIER_CONST) == SPECIFIER_CONST
          || (specifiers & PUBLIC_STOCK) == PUBLIC_STOCK
          || newfunc(builder, specifiers) == null) {
        return declglb(builder, specifiers);
      }
    }

    declfuncvar.drop();
    return Pair.create(declfuncvar, specifiers);
  }

  public Pair<PsiBuilder.Marker, Integer> newfunc(@NotNull PsiBuilder builder, int specifiers) {
    parseTag(builder);

    PsiBuilder.Marker newfunc = builder.mark();
    IElementType tokenType = builder.getTokenType();
    if (tokenType == ApTokenTypes.NATIVE_KEYWORD) {
      error(newfunc, "amxx.err.042.cannotbe", "native");
      recoverAt(builder, NEWFUNC_RECOVER);
    } else if (tokenType == ApTokenTypes.PUBLIC_KEYWORD
        && (specifiers & SPECIFIER_STOCK) == SPECIFIER_STOCK) {
      error(newfunc, "amxx.err.042.conflict", "public", "stock");
      recoverAt(builder, NEWFUNC_RECOVER);
    }

    if (tokenType == ApTokenTypes.OPERATOR_KEYWORD) {
      operatorname(builder);
    } else if (tokenType != ApTokenTypes.IDENTIFIER) {
      error(newfunc, "amxx.err.020.expected", builder.getTokenText());
      recoverAt(builder, NEWFUNC_RECOVER);
    } else if (tokenType == ApTokenTypes.IDENTIFIER) {
      assert builder.getTokenText() != null;
      if (builder.getTokenText().charAt(0) == ApParser.PUBLIC_CHAR) {
        specifiers |= SPECIFIER_PUBLIC;
        if ((specifiers & SPECIFIER_STOCK) == SPECIFIER_STOCK) {
          specifiers &= ~SPECIFIER_STOCK;
          error(newfunc, "amxx.err.042.conflict", "public", "stock");
        }
      }
    }

    declargs(builder);
    // TODO: finish...

    done(newfunc, ApElementType.METHOD);
    return Pair.create(newfunc, specifiers);
  }

  @Nullable
  public PsiBuilder.Marker operatorname(@NotNull PsiBuilder builder) {
    if (expectOrError(builder, ElementTypes.OVERLOADABLE_OPERATIONS, "amxx.err.007")) {
      recoverAt(builder, OPNAME_RECOVER);
      return null;
    }

    return null;
  }

  @Nullable
  public PsiBuilder.Marker declargs(@NotNull PsiBuilder builder) {
    if (!expect(builder, ApTokenTypes.LPARENTH)) {
      return null;
    }

    if (!expect(builder, ApTokenTypes.RPARENTH)) {
      return null;
    }

    return null;
  }

  @Nullable
  public PsiBuilder.Marker parseTag(@NotNull PsiBuilder builder) {
    PsiBuilder.Marker tag = builder.mark();
    if (!expect(builder, ApTokenTypes.IDENTIFIER)) {
      tag.drop();
      return null;
    }

    if (!expect(builder, ApTokenTypes.COLON)) {
      tag.rollbackTo();
      return null;
    }

    return done(tag, ApElementType.TAG);
  }

  @Nullable
  public PsiBuilder.Marker parseArray(@NotNull PsiBuilder builder) {
    PsiBuilder.Marker array = builder.mark();

    int numDims = 0;
    while (expect(builder, ApTokenTypes.LBRACE)) {
      numDims++;
      if (expect(builder, ApTokenTypes.RBRACE)) {
        continue;
      }

      // TODO: support const expressions
      builder.mark().error("const expressions are unsupported");
      recoverAt(builder, CLASSSPEC_RECOVER);
    }

    if (numDims == 0) {
      array.drop();
      return null;
    }

    return array;
  }*/

}
