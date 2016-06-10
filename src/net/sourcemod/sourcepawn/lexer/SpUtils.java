package net.sourcemod.sourcepawn.lexer;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.PrimitiveIterator;

public class SpUtils {

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
    if (!iterator.hasNext()) {
      return null;
    }

    int codePoint = iterator.nextInt();
    if (codePoint != '\'') {
      return null; // First character must be '\''
    }

    if (!iterator.hasNext()) {
      return null;
    }

    BigInteger value;
    boolean needsConsume = true;
    codePoint = iterator.nextInt();
    if (codePoint != ctrl) {
      value = BigInteger.valueOf(codePoint);
    } else if (iterator.hasNext()) {
      codePoint = iterator.nextInt();
      switch (codePoint) {
        case 'a': value = BigInteger.valueOf(7); break;
        case 'b': value = BigInteger.valueOf(8); break;
        case 'e': value = BigInteger.valueOf(27); break;
        case 'f': value = BigInteger.valueOf(12); break;
        case 'n': value = BigInteger.valueOf(10); break;
        case 'r': value = BigInteger.valueOf(13); break;
        case 't': value = BigInteger.valueOf(9); break;
        case 'v': value = BigInteger.valueOf(11); break;
        case 'x':
          value = BigInteger.ZERO;
          HexDigits:
          while (iterator.hasNext()) {
            codePoint = iterator.nextInt();
            switch (codePoint) {
              case '0': case '1': case '2': case '3': case '4':
              case '5': case '6': case '7': case '8': case '9':
              case 'A': case 'B': case 'C': case 'D': case 'E':
              case 'a': case 'b': case 'c': case 'd': case 'e':
                value = value.shiftLeft(4);
                value = value.add(BigInteger.valueOf(Character.getNumericValue(codePoint)));
                break;
              default:
                break HexDigits;
            }
          }

          if (iterator.hasNext() && codePoint != ';' && codePoint != '\'') {
            return null; // Only ';' can follow hex literals
          }

          needsConsume = false;
          break;
        case '\'':
        case '\"':
        case '%':
          value = BigInteger.valueOf(codePoint);
          break;
        case '0': case '1': case '2': case '3': case '4':
        case '5': case '6': case '7': case '8': case '9':
          value = BigInteger.ZERO;
          DecimalDigits:
          do {
            switch (codePoint) {
              case '0': case '1': case '2': case '3': case '4':
              case '5': case '6': case '7': case '8': case '9':
                value = value.multiply(BigInteger.TEN);
                value = value.add(BigInteger.valueOf(Character.getNumericValue(codePoint)));
                break;
              default:
                break DecimalDigits;
            }
            codePoint = iterator.nextInt();
          } while (iterator.hasNext());

          if (iterator.hasNext() && codePoint != ';' && codePoint != '\'') {
            return null; // Only ';' can follow decimal literals
          }

          needsConsume = false;
          break;
        default:
          return null; // Invalid character code
      }
    } else {
      return null; // Escape sequences must be nonempty
    }

    if (needsConsume) {
      if (!iterator.hasNext()) {
        return null; // Need to attempt to consume in order to find '\''
      }

      codePoint = iterator.nextInt();
    }

    if (codePoint != '\'' || iterator.hasNext()) {
      return null; // Last character must be '\''
    }

    return value;
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
