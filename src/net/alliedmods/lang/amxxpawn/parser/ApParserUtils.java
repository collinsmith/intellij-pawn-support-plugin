package net.alliedmods.lang.amxxpawn.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.LighterLazyParseableNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderFactory;
import com.intellij.lang.PsiBuilderUtil;
import com.intellij.lang.WhitespacesAndCommentsBinder;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.TreeUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.indexing.IndexingDataKeys;

import net.alliedmods.lang.amxxpawn.ApLanguage;
import net.alliedmods.lang.amxxpawn.ApParserDefinition;
import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;
import net.alliedmods.lang.amxxpawn.psi.ElementTypes;
import net.alliedmods.lang.amxxpawn.util.OfIntPeekingIterator;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.PropertyKey;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.PrimitiveIterator;

public class ApParserUtils extends GeneratedParserUtilBase {

  private static final TokenSet PRECEDING_COMMENT_SET = TokenSet.EMPTY;
  private static final TokenSet TRAILING_COMMENT_SET = TokenSet.EMPTY;

  public static final WhitespacesAndCommentsBinder PRECEDING_COMMENT_BINDER = new PrecedingWhitespacesAndCommentsBinder(false);
  public static final WhitespacesAndCommentsBinder SPECIAL_PRECEDING_COMMENT_BINDER = new PrecedingWhitespacesAndCommentsBinder(true);
  public static final WhitespacesAndCommentsBinder TRAILING_COMMENT_BINDER = new TrailingWhitespacesAndCommentsBinder();

  private ApParserUtils() {}

  @NotNull
  public static PsiBuilder createBuilder(ASTNode chameleon) {
    PsiElement psi = chameleon.getPsi();
    assert psi != null : chameleon;
    Project project = psi.getProject();

    CharSequence text;
    if (TreeUtil.isCollapsedChameleon(chameleon)) {
      text = chameleon.getChars();
    } else {
      text = psi.getUserData(IndexingDataKeys.FILE_TEXT_CONTENT_KEY);
      if (text == null) {
        text = chameleon.getChars();
      }
    }

    Language language = psi.getLanguage();
    if (!language.isKindOf(ApLanguage.INSTANCE)) {
      language = ApLanguage.INSTANCE;
    }

    Lexer lexer = ApParserDefinition.createLexer();
    PsiBuilderFactory factory = PsiBuilderFactory.getInstance();
    PsiBuilder builder = factory.createBuilder(project, chameleon, lexer, language, text);
    return builder;
  }

  @NotNull
  public static PsiBuilder createBuilder(LighterLazyParseableNode chameleon) {
    PsiElement psi = chameleon.getContainingFile();
    assert psi != null : chameleon;
    Project project = psi.getProject();
    CharSequence text = chameleon.getText();
    Language language = chameleon.getTokenType().getLanguage();

    Lexer lexer = ApParserDefinition.createLexer();
    PsiBuilderFactory factory = PsiBuilderFactory.getInstance();
    PsiBuilder builder = factory.createBuilder(project, chameleon, lexer, language, text);
    return builder;
  }

  public static boolean isAlpha(int codePoint) {
    return ('A' <= codePoint && codePoint <= 'Z')
        || ('a' <= codePoint && codePoint <= 'z')
        || codePoint == '_'
        || codePoint == '@';
  }

  public static boolean isAlphaNumeric(int codePoint) {
    return isAlpha(codePoint) || ('0' <= codePoint && codePoint <= '9');
  }

  public static int parseCellFast(@NotNull @NonNls CharSequence text, int ctrl) {
    return parseCell(text, ctrl).intValueExact();
  }

  @Nullable
  public static BigInteger parseCell(@NotNull @NonNls CharSequence text, int ctrl) {
    BigInteger value;
    if ((value = parseNumber(text)) != null) {
      return value;
    } else if ((value = parseCharacter(text, ctrl)) != null) {
      return value;
    }

    BigDecimal rational;
    if ((rational = parseRational(text)) != null) {
      double doubleValue = rational.doubleValue();
      value = BigInteger.valueOf(Double.doubleToRawLongBits(doubleValue));
      return value;
    }

    return null;
  }

  @Nullable
  public static BigInteger parseNumber(@NotNull @NonNls CharSequence text) {
    BigInteger value;
    if ((value = parseBoolean(text)) != null) {
      return value;
    } else if ((value = parseBinary(text)) != null) {
      return value;
    } else if ((value = parseOctal(text)) != null) {
      return value;
    } else if ((value = parseDecimal(text)) != null) {
      return value;
    } else if ((value = parseHexadecimal(text)) != null) {
      return value;
    }

    return null;
  }

  @Nullable
  public static BigDecimal parseRational(@NotNull @NonNls CharSequence text) {
    if (text.length() < 3) {
      return null;
    }

    PrimitiveIterator.OfInt iterator = text.codePoints().iterator();
    int codePoint = iterator.nextInt();
    if (!Character.isDigit(codePoint)) {
      return null; // Must start with digit
    }

    BigDecimal value = BigDecimal.valueOf(Character.getNumericValue(codePoint));

    FoundPoint:
    while (iterator.hasNext()) {
      codePoint = iterator.next();
      switch (codePoint) {
        case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
          value = value.scaleByPowerOfTen(1);
          value = value.add(BigDecimal.valueOf(Character.getNumericValue(codePoint)));
          break;
        case '_':
          break;
        case '.':
          break FoundPoint;
        default:
          return null;
      }
    }

    if (codePoint != '.') {
      return null; // Must contain a '.'
    }

    if (!iterator.hasNext()) {
      return null;
    }

    codePoint = iterator.nextInt();
    if (!Character.isDigit(codePoint)) {
      return null; // Must have a digit after '.'
    }

    int scale = 1;
    value = value.scaleByPowerOfTen(1);
    value = value.add(BigDecimal.valueOf(Character.getNumericValue(codePoint)));

    FoundExponent:
    while (iterator.hasNext()) {
      codePoint = iterator.next();
      switch (codePoint) {
        case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
          scale++;
          value = value.scaleByPowerOfTen(1);
          value = value.add(BigDecimal.valueOf(Character.getNumericValue(codePoint)));
          break;
        case '_':
          break;
        case 'e':
          break FoundExponent;
        default:
          return null;
      }
    }

    value = value.scaleByPowerOfTen(-scale);
    if (codePoint != 'e') {
      return value;
    }

    if (!iterator.hasNext()) {
      return null;
    }

    int sign = 1;
    codePoint = iterator.nextInt();
    if (codePoint == '-') {
      sign = -1;
    } else if (Character.isDigit(codePoint)) {
      value = value.scaleByPowerOfTen(Character.getNumericValue(codePoint));
    } else {
      return null; // Must have '-' or [0-1] following 'e'
    }

    while (iterator.hasNext()) {
      codePoint = iterator.next();
      if (!Character.isDigit(codePoint)) {
        return null;
      }

      value = value.scaleByPowerOfTen(Character.getNumericValue(codePoint) * sign);
    }

    return value;
  }

  @Nullable
  public static BigInteger parseCharacter(@NotNull @NonNls CharSequence text, int ctrl) {
    PrimitiveIterator.OfInt iterator = text.codePoints().iterator();
    OfIntPeekingIterator peekingIterator = new OfIntPeekingIterator(iterator);
    if (!peekingIterator.hasNext() || peekingIterator.nextInt() != '\'') {
      return null;
    }

    BigInteger value = null;
    try {
      int codePoint = parseCodePoint(peekingIterator, ctrl);
      value = BigInteger.valueOf(codePoint);
    } catch (IllegalArgumentException e) {
      return null;
    }

    if (!peekingIterator.hasNext() || peekingIterator.nextInt() != '\'') {
      return null;
    }

    return value;
  }

  @Nullable
  public static String parseString(@NotNull @NonNls CharSequence text, int ctrl) {
    PrimitiveIterator.OfInt iterator = text.codePoints().iterator();
    OfIntPeekingIterator peekingIterator = new OfIntPeekingIterator(iterator);
    StringBuilder string = new StringBuilder(text.length());
    try {
      while (peekingIterator.hasNext()) {
        int codePoint = parseCodePoint(peekingIterator, ctrl);
        string.appendCodePoint(codePoint);
      }
    } catch (IllegalArgumentException e) {
      return null;
    }

    return string.toString();
  }

  public static int parseCodePoint(@NotNull @NonNls CharSequence text, int ctrl) {
    PrimitiveIterator.OfInt iterator = text.codePoints().iterator();
    OfIntPeekingIterator peekingIterator = new OfIntPeekingIterator(iterator);
    int codePoint = parseCodePoint(peekingIterator, ctrl);
    return codePoint;
  }

  public static int parseCodePoint(@NotNull OfIntPeekingIterator codePoints, int ctrl) {
    if (!codePoints.hasNext()) {
      throw new IllegalArgumentException("Empty sequence");
    }

    int codePointValue = 0;
    int codePoint = codePoints.nextInt();
    if (codePoint != ctrl) {
      return codePoint;
    } else if (codePoints.hasNext()) {
      codePoint = codePoints.nextInt();
      if (codePoint == ctrl) {
        return ctrl;
      }

      switch (codePoint) {
        case 'a':
          return 7;
        case 'b':
          return 8;
        case 'e':
          return 27;
        case 'f':
          return 12;
        case 'n':
          return 10;
        case 'r':
          return 13;
        case 't':
          return 9;
        case 'v':
          return 11;
        case 'x':
          final int MAX_HEX_DIGITS = 2;
          int digits = 0;
          HexDigits:
          while (codePoints.hasNext() && digits < MAX_HEX_DIGITS) {
            codePoint = codePoints.peekInt();
            switch (codePoint) {
              case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
              case 'A':case 'B':case 'C':case 'D':case 'E':case 'F':
              case 'a':case 'b':case 'c':case 'd':case 'e':case 'f':
                codePoints.nextInt();
                digits++;
                codePointValue = (codePointValue << 4) | Character.getNumericValue(codePoint);
                break;
              default:
                break HexDigits;
            }
          }

          if (codePoints.hasNext() && codePoints.peekInt() == ';') {
            codePoints.nextInt();
          }

          return codePointValue;
        case '\'':
        case '\"':
        case '%':
          return codePoint;
        case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
          codePointValue = Character.getNumericValue(codePoint);

          DecimalDigits:
          while (codePoints.hasNext()) {
            codePoint = codePoints.peekInt();
            switch (codePoint) {
              case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
                codePoints.nextInt();
                codePointValue = (codePointValue * 10) + Character.getNumericValue(codePoint);
                break;
              default:
                break DecimalDigits;
            }
          }

          if (codePoints.hasNext() && codePoints.peekInt() == ';') {
            codePoints.nextInt();
          }

          return codePointValue;
        default:
          String string = new String(Character.toChars(codePoint));
          throw new IllegalArgumentException("Illegal escape sequence character: " + string);
      }
    }

    throw new IllegalArgumentException("Empty escape sequence");
  }

  @Nullable
  public static BigInteger parseBoolean(@NotNull @NonNls CharSequence text) {
    String string = text.toString();
    switch (string) {
      case "true":
        return BigInteger.ONE;
      case "false":
        return BigInteger.ZERO;
      default:
        return null;
    }
  }

  @Nullable
  public static BigInteger parseBinary(@NotNull @NonNls CharSequence text) {
    if (text.length() < 2) {
      return null;
    }

    PrimitiveIterator.OfInt iterator = text.codePoints().iterator();
    if (iterator.nextInt() != '0'
        || iterator.nextInt() != 'b') {
      return null;
    }

    BigInteger value = BigInteger.ZERO;
    while (iterator.hasNext()) {
      int codePoint = iterator.nextInt();
      switch (codePoint) {
        case '0':
          value = value.shiftLeft(1);
          value = value.add(BigInteger.ZERO);
          break;
        case '1':
          value = value.shiftLeft(1);
          value = value.add(BigInteger.ONE);
          break;
        case '_':
          break;
        default:
          return null;
      }
    }

    return value;
  }

  @Nullable
  public static BigInteger parseOctal(@NotNull @NonNls CharSequence text) {
    if (text.length() < 2) {
      return null;
    }

    PrimitiveIterator.OfInt iterator = text.codePoints().iterator();
    if (iterator.nextInt() != '0'
        || iterator.nextInt() != 'o') {
      return null;
    }

    BigInteger value = BigInteger.ZERO;
    while (iterator.hasNext()) {
      int codePoint = iterator.nextInt();
      switch (codePoint) {
        case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':
          value = value.shiftLeft(3);
          value = value.add(BigInteger.valueOf(Character.getNumericValue(codePoint)));
          break;
        case '_':
          break;
        default:
          return null;
      }
    }

    return value;
  }

  @Nullable
  public static BigInteger parseDecimal(@NotNull @NonNls CharSequence text) {
    if (text.length() < 1) {
      return null;
    }

    PrimitiveIterator.OfInt iterator = text.codePoints().iterator();
    int codePoint = iterator.nextInt();
    if (!Character.isDigit(codePoint)) {
      return null;
    }

    BigInteger value = BigInteger.valueOf(Character.getNumericValue(codePoint));
    while (iterator.hasNext()) {
      codePoint = iterator.nextInt();
      switch (codePoint) {
        case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
          value = value.multiply(BigInteger.TEN);
          value = value.add(BigInteger.valueOf(Character.getNumericValue(codePoint)));
          break;
        case '_':
          break;
        default:
          return null;
      }
    }

    return value;
  }

  @Nullable
  public static BigInteger parseHexadecimal(@NotNull @NonNls CharSequence text) {
    if (text.length() < 2) {
      return null;
    }

    PrimitiveIterator.OfInt iterator = text.codePoints().iterator();
    if (iterator.nextInt() != '0'
        || iterator.nextInt() != 'x') {
      return null;
    }

    BigInteger value = BigInteger.ZERO;
    while (iterator.hasNext()) {
      int codePoint = iterator.nextInt();
      switch (codePoint) {
        case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
        case 'A':case 'B':case 'C':case 'D':case 'E':case 'F':
        case 'a':case 'b':case 'c':case 'd':case 'e':case 'f':
          value = value.shiftLeft(4);
          value = value.add(BigInteger.valueOf(Character.getNumericValue(codePoint)));
          break;
        case '_':
          break;
        default:
          return null;
      }
    }

    return value;
  }

  public static void error(PsiBuilder.Marker marker, @PropertyKey(resourceBundle = ApErrorMessages.BUNDLE) String key, Object... params) {
    marker.error(ApErrorMessages.message(key, params));
  }

  public static void error(PsiBuilder builder, @PropertyKey(resourceBundle = ApErrorMessages.BUNDLE) String key, Object... params) {
    builder.mark().error(ApErrorMessages.INSTANCE.getMessage(key, params));
  }

  public static void error(PsiBuilder builder, @Nullable PsiBuilder.Marker before, @PropertyKey(resourceBundle = ApErrorMessages.BUNDLE) String key, Object... params) {
    if (before == null) {
      error(builder, key, params);
    } else {
      before.precede().errorBefore(ApErrorMessages.INSTANCE.getMessage(key, params), before);
    }
  }

  public static boolean expectOrError(PsiBuilder builder, TokenSet expected, @PropertyKey(resourceBundle = ApErrorMessages.BUNDLE) String key, Object... params) {
    if (!PsiBuilderUtil.expect(builder, expected)) {
      error(builder, key, params);
      return false;
    } else {
      return true;
    }
  }

  public static boolean expectOrError(PsiBuilder builder, IElementType expected, @PropertyKey(resourceBundle = ApErrorMessages.BUNDLE) String key, Object... params) {
    if (!PsiBuilderUtil.expect(builder, expected)) {
      error(builder, key, params);
      return false;
    } else {
      return true;
    }
  }

  public static boolean expectOrError(PsiBuilder builder, TokenSet expected, String expectedStr) {
    if (!PsiBuilderUtil.expect(builder, expected)) {
      error(builder, "amxx.err.001", expectedStr, builder.getTokenText());
      return false;
    } else {
      return true;
    }
  }

  public static boolean expectOrError(PsiBuilder builder, IElementType expected, String expectedStr) {
    if (!PsiBuilderUtil.expect(builder, expected)) {
      error(builder, "amxx.err.001", expectedStr, builder.getTokenText());
      return false;
    } else {
      return true;
    }
  }

  public static PsiBuilder.Marker done(PsiBuilder.Marker marker, IElementType type) {
    marker.done(type);
    final WhitespacesAndCommentsBinder left = PRECEDING_COMMENT_SET.contains(type) ? PRECEDING_COMMENT_BINDER : null;
    final WhitespacesAndCommentsBinder right = TRAILING_COMMENT_SET.contains(type) ? TRAILING_COMMENT_BINDER : null;
    marker.setCustomEdgeTokenBinders(left, right);
    return marker;
  }

  public static void semicolon(PsiBuilder builder) {
    expectOrError(builder, ElementTypes.AMXX_TERM, "amxx.err.001", ";", builder.getTokenText());
  }

  public static void recoverAt(PsiBuilder builder, TokenSet recover) {
    while (!recover.contains(builder.getTokenType())) {
      builder.advanceLexer();
    }
  }

  private static class PrecedingWhitespacesAndCommentsBinder implements WhitespacesAndCommentsBinder {
    private final boolean myAfterEmptyImport;

    public PrecedingWhitespacesAndCommentsBinder(final boolean afterImport) {
      this.myAfterEmptyImport = afterImport;
    }

    @Override
    public int getEdgePosition(final List<IElementType> tokens, final boolean atStreamEdge, final TokenTextGetter
        getter) {
      if (tokens.size() == 0) return 0;

      // 1. bind doc comment
      for (int idx = tokens.size() - 1; idx >= 0; idx--) {
        if (tokens.get(idx) == ApTokenTypes.DOC_COMMENT) return idx;
      }

      // 2. bind plain comments
      int result = tokens.size();
      for (int idx = tokens.size() - 1; idx >= 0; idx--) {
        final IElementType tokenType = tokens.get(idx);
        if (ElementTypes.AMXX_WHITESPACE_BIT_SET.contains(tokenType)) {
          if (StringUtil.getLineBreakCount(getter.get(idx)) > 1) break;
        } else if (ElementTypes.AMXX_PLAIN_COMMENT_BIT_SET.contains(tokenType)) {
          if (atStreamEdge
              || (idx == 0 && myAfterEmptyImport)
              || (idx > 0 && ElementTypes.AMXX_WHITESPACE_BIT_SET.contains(tokens.get(idx - 1))
                  && StringUtil.containsLineBreak(getter.get(idx - 1)))) {
            result = idx;
          }
        } else break;
      }

      return result;
    }
  }

  private static class TrailingWhitespacesAndCommentsBinder implements WhitespacesAndCommentsBinder {
    @Override
    public int getEdgePosition(final List<IElementType> tokens, final boolean atStreamEdge, final TokenTextGetter getter) {
      if (tokens.size() == 0) return 0;

      int result = 0;
      for (int idx = 0; idx < tokens.size(); idx++) {
        final IElementType tokenType = tokens.get(idx);
        if (ElementTypes.AMXX_WHITESPACE_BIT_SET.contains(tokenType)) {
          if (StringUtil.containsLineBreak(getter.get(idx))) break;
        } else if (ElementTypes.AMXX_PLAIN_COMMENT_BIT_SET.contains(tokenType)) {
          result = idx + 1;
        } else break;
      }

      return result;
    }
  }

}
