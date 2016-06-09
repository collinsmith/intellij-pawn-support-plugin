package net.sourcemod.sourcepawn.lexer;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.PrimitiveIterator;

public class SpUtils {

  @NotNull
  public static BigInteger parseNumber(@NotNull @NonNls CharSequence text) {
    return null;
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
