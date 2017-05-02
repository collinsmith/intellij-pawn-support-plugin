package net.alliedmods.lang.amxxpawn.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.psi.ApTokenType;
import net.alliedmods.lang.amxxpawn.psi.impl.source.tree.ApElementType;
import net.alliedmods.lang.amxxpawn.psi.impl.source.tree.ElementType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.lang.PsiBuilderUtil.expect;
import static net.alliedmods.lang.amxxpawn.parser.ApParserUtils.done;
import static net.alliedmods.lang.amxxpawn.parser.ApParserUtils.error;
import static net.alliedmods.lang.amxxpawn.parser.ApParserUtils.expectOrError;
import static net.alliedmods.lang.amxxpawn.parser.ApParserUtils.semicolon;

public class DeclarationParser {
  public enum Context {
    FILE, CODE_BLOCK
  }

  private static final int SPECIFIER_PUBLIC = 0x1;
  private static final int SPECIFIER_STATIC = 0x2;
  private static final int SPECIFIER_STOCK  = 0x4;
  private static final int SPECIFIER_CONST  = 0x8;

  private static final int PUBLIC_STATIC = SPECIFIER_PUBLIC | SPECIFIER_STATIC;
  private static final int PUBLIC_STOCK = SPECIFIER_PUBLIC | SPECIFIER_STOCK;

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

    if (!ElementType.DECL_BIT_SET.contains(tokenType)) {
      return null;
    }

    PsiBuilder.Marker declaration = builder.mark();
    Pair<PsiBuilder.Marker, Integer> classSpecifiers = getClassSpec(builder);
    if (classSpecifiers == null) {
      declaration.drop();
      return null;
    }

    if (tokenType == ApTokenType.NEW_KEYWORD) {
      if (declGlobal(builder) == null) {
        declaration.drop();
        return null;
      }
    } else {
      if (declFuncOrVar(builder, classSpecifiers.second) == null) {
        declaration.drop();
        return null;
      }
    }

    return done(declaration, ApElementType.DECLARATION);
  }

  @Nullable
  public Pair<PsiBuilder.Marker, Integer> getClassSpec(final PsiBuilder builder) {
    IElementType tokenType = builder.getTokenType();
    if (!ElementType.DECL_BIT_SET.contains(tokenType)) {
      return null;
    }

    final PsiBuilder.Marker specifiers = builder.mark();
    int classSpecifiers = 0;
    if (tokenType == ApTokenType.CONST_KEYWORD) {
      classSpecifiers |= SPECIFIER_CONST;
    } else if (tokenType == ApTokenType.STOCK_KEYWORD) {
      classSpecifiers |= SPECIFIER_STOCK;
    } else if (tokenType == ApTokenType.STATIC_KEYWORD) {
      classSpecifiers |= SPECIFIER_STATIC;
    } else if (tokenType == ApTokenType.PUBLIC_KEYWORD) {
      classSpecifiers |= SPECIFIER_PUBLIC;
    }

    builder.advanceLexer();
    do {
      tokenType = builder.getTokenType();
      if (tokenType == ApTokenType.CONST_KEYWORD) {
        if ((classSpecifiers & SPECIFIER_CONST) == SPECIFIER_CONST) {
          error(specifiers, "amxx.err.042");
          return null;
        }

        classSpecifiers |= SPECIFIER_CONST;
      } else if (tokenType == ApTokenType.STOCK_KEYWORD) {
        if ((classSpecifiers & SPECIFIER_STOCK) == SPECIFIER_STOCK) {
          error(specifiers, "amxx.err.042");
          return null;
        }

        classSpecifiers |= SPECIFIER_STOCK;
      } else if (tokenType == ApTokenType.STATIC_KEYWORD) {
        if ((classSpecifiers & SPECIFIER_STATIC) == SPECIFIER_STATIC) {
          error(specifiers, "amxx.err.042");
          return null;
        }

        classSpecifiers |= SPECIFIER_STATIC;
      } else if (tokenType == ApTokenType.PUBLIC_KEYWORD) {
        if ((classSpecifiers & SPECIFIER_PUBLIC) == SPECIFIER_PUBLIC) {
          error(specifiers, "amxx.err.042");
          return null;
        }

        classSpecifiers |= SPECIFIER_PUBLIC;
      }
    } while (expect(builder, ElementType.MODIFIER_BIT_SET));
    if ((classSpecifiers & PUBLIC_STATIC) == PUBLIC_STATIC) {
      error(specifiers, "amxx.err.042");
      return null;
    }

    done(specifiers, ApElementType.CLASS_SPECIFIERS);
    return Pair.create(specifiers, classSpecifiers);
  }

  @Nullable
  public PsiBuilder.Marker declGlobal(final PsiBuilder builder) {
    PsiBuilder.Marker globals = builder.mark();
    do {
      PsiBuilder.Marker global = builder.mark();

      pc_addtag(builder);
      if (expectOrError(builder, ApTokenType.IDENTIFIER, "-identifier-")) {
        // Compiler will throw error 20 (invalid symbol name), but that doesn't really make a lot of
        // sense, so I'm throwing expected "-identifier-", but found "<text>" instead
        // TODO: code inspection should catch "expected -identifier-" errors and show identifer format in tooltip
        global.drop();
        globals.drop();
        return null;
      }

      int numDims = 0;
      while (expect(builder, ApTokenType.LBRACE)) {
        if (++numDims == ApParser.DIMEN_MAX) {
          error(global, "amxx.err.053");
          globals.drop();
          return null;
        }

        // TODO: sizing...

        if (!expectOrError(builder, ApTokenType.RBRACE, "]")) {
          global.drop();
          globals.drop();
          return null;
        }
      }

      done(global, ApElementType.GLOBAL);
    } while (expect(builder, ApTokenType.COMMA));
    semicolon(builder);

    return done(globals, ApElementType.GLOBALS);
  }

  @Nullable
  public PsiBuilder.Marker declFuncOrVar(final PsiBuilder builder, int classSpecifiers) {
    PsiBuilder.Marker funcOrVar = builder.mark();

    pc_addtag(builder);

    IElementType tokenType = builder.getTokenType();
    if (tokenType == ApTokenType.NATIVE_KEYWORD) {
      error(funcOrVar, "amxx.err.042");
      return null;
    }

    if (tokenType != ApTokenType.IDENTIFIER && tokenType != ApTokenType.OPERATOR_KEYWORD) {
      expectOrError(builder, ApTokenType.IDENTIFIER, "-identifier-");
      return null;
    }

    if (tokenType == ApTokenType.OPERATOR_KEYWORD) {
      if (newFunc(builder, classSpecifiers) == null) {
        error(funcOrVar, "amxx.err.010");
        return null;
      }
    } else {
      assert tokenType == ApTokenType.IDENTIFIER;
      if ((classSpecifiers & SPECIFIER_CONST) == SPECIFIER_CONST
       || (classSpecifiers & PUBLIC_STOCK) == PUBLIC_STOCK
       || newFunc(builder, classSpecifiers) == null) {
        funcOrVar.drop();
        return declGlobal(builder);
      }
    }

    return done(funcOrVar, ApElementType.FUNC_OR_VAR);
  }

  @Nullable
  public PsiBuilder.Marker pc_addtag(final PsiBuilder builder) {
    PsiBuilder.Marker tag = builder.mark();
    if (!expect(builder, ApTokenType.IDENTIFIER)) {
      tag.drop();
      return null;
    }

    if (!expect(builder, ApTokenType.COLON)) {
      tag.rollbackTo();
      return null;
    }

    return done(tag, ApElementType.TAG);
  }

  public PsiBuilder.Marker newFunc(final PsiBuilder builder, int classSpecifiers) {
    PsiBuilder.Marker newFunc = builder.mark();

    pc_addtag(builder);

    IElementType tokenType = builder.getTokenType();
    if (tokenType == ApTokenType.NATIVE_KEYWORD
     || (tokenType == ApTokenType.PUBLIC_KEYWORD && (classSpecifiers & SPECIFIER_STOCK) == SPECIFIER_STOCK)) {
      error(newFunc, "amxx.err.042");
      return null;
    }

    if (tokenType == ApTokenType.OPERATOR_KEYWORD) {
      // TODO: Support operator<sym>()
    } else {
      if (tokenType != ApTokenType.IDENTIFIER) {
        error(newFunc, "amxx.err.020", builder.getTokenText());
        return null;
      }
    }

    if (!expect(builder, ApTokenType.LPARENTH)) {
      newFunc.drop();
      return null;
    }

    if (builder.getTokenText().charAt(0) == ApParser.PUBLIC_CHAR) {
      classSpecifiers |= SPECIFIER_PUBLIC;
      if ((classSpecifiers & SPECIFIER_STOCK) == SPECIFIER_STOCK) {
        error(newFunc, "amxx.err.042");
        return null;
      }
    }

    // TODO: support prototyping, etc...

    return done(newFunc, ApElementType.FUNCTION);
  }

}
