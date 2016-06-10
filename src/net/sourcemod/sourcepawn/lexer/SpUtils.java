package net.sourcemod.sourcepawn.lexer;

import net.sourcemod.sourcepawn.util.OfIntPeekingIterator;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.PrimitiveIterator;

public class SpUtils {

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
        case '0': case '1': case '2': case '3': case '4':
        case '5': case '6': case '7': case '8': case '9':
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
        case '0': case '1': case '2': case '3': case '4':
        case '5': case '6': case '7': case '8': case '9':
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
        case 'a': return 7;
        case 'b': return 8;
        case 'e': return 27;
        case 'f': return 12;
        case 'n': return 10;
        case 'r': return 13;
        case 't': return 9;
        case 'v': return 11;
        case 'x':
          final int MAX_HEX_DIGITS = 2;
          int digits = 0;
          HexDigits:
          while (codePoints.hasNext() && digits < MAX_HEX_DIGITS) {
            codePoint = codePoints.peekInt();
            switch (codePoint) {
              case '0': case '1': case '2': case '3': case '4':
              case '5': case '6': case '7': case '8': case '9':
              case 'A': case 'B': case 'C': case 'D': case 'E':
              case 'a': case 'b': case 'c': case 'd': case 'e':
                codePoints.nextInt(); digits++;
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
        case '0': case '1': case '2': case '3': case '4':
        case '5': case '6': case '7': case '8': case '9':
          codePointValue = Character.getNumericValue(codePoint);

          DecimalDigits:
          while (codePoints.hasNext()) {
            codePoint = codePoints.peekInt();
            switch (codePoint) {
              case '0': case '1': case '2': case '3': case '4':
              case '5': case '6': case '7': case '8': case '9':
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
      case "true":  return BigInteger.ONE;
      case "false": return BigInteger.ZERO;
      default:      return null;
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
        case '0': case '1': case '2': case '3': case '4':
        case '5': case '6': case '7':
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
        case '0': case '1': case '2': case '3': case '4':
        case '5': case '6': case '7': case '8': case '9':
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
        case '0': case '1': case '2': case '3': case '4':
        case '5': case '6': case '7': case '8': case '9':
        case 'A': case 'B': case 'C': case 'D': case 'E':
        case 'a': case 'b': case 'c': case 'd': case 'e':
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

  private SpUtils() {
  }

}
