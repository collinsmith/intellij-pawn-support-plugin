package net.alliedmods.intellij.sourcepawn;

import com.intellij.psi.tree.IElementType;

import net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes;

public class SourcePawnUtils {

  private SourcePawnUtils() {
  }

  public static int parseNumber(String text) {
    try {
      return parsePawnBooleanLiteral(text);
    } catch (NumberFormatException e1) {
      try {
        return parsePawnBinaryLiteral(text);
      } catch (NumberFormatException e2) {
        try {
          return parsePawnOctalLiteral(text);
        } catch (NumberFormatException e3) {
          try {
            return parsePawnDecimalLiteral(text);
          } catch (NumberFormatException e4) {
            return parsePawnHexadecimalLiteral(text);
          }
        }
      }
    }
  }

  public static int parsePawnBooleanLiteral(IElementType type) {
    if (type == SourcePawnTypes.TRUE) {
      return 1;
    } else if (type == SourcePawnTypes.FALSE) {
      return 0;
    }

    throw new NumberFormatException(String.format(
        "Invalid SourcePawn boolean literal: %s; " +
            "SourcePawn boolean literals should be either \"true\" or \"false\"",
        type));
  }

  public static int parsePawnBooleanLiteral(String text) {
    if (text.equals("true")) {
      return 1;
    } else if (text.equals("false")) {
      return 0;
    }

    throw new NumberFormatException(String.format(
        "Invalid SourcePawn boolean literal: %s; " +
            "SourcePawn boolean literals should be either \"true\" or \"false\"",
        text));
  }

  public static int parsePawnBinaryLiteral(String text) {
    if (!text.startsWith("0b")) {
      throw new NumberFormatException(String.format(
          "Invalid SourcePawn binary literal: %s; " +
              "SourcePawn binary literals should start with 0b",
          text));
    }

    int value = 0;

    char ch;
    for (int i = 2; i < text.length(); i++) {
      ch = text.charAt(i);
      switch (ch) {
        case '0':
        case '1':
          value = (value << 1) + (ch - '0');
          // fall through
        case '_':
          continue;
        default:
          throw new NumberFormatException(String.format(
              "Invalid SourcePawn binary literal: %s; Invalid character at index %d (%c)",
              text, i, ch));
      }
    }

    return value;
  }

  public static int parsePawnOctalLiteral(String text) {
    if (!text.startsWith("0o")) {
      throw new NumberFormatException(String.format(
          "Invalid SourcePawn octal literal: %s; " +
              "SourcePawn octal literals should start with 0x",
          text));
    }

    int value = 0;

    char ch;
    for (int i = 2; i < text.length(); i++) {
      ch = text.charAt(i);
      switch (ch) {
        case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7':
          value = (value << 3) + (ch - '0');
          // fall through
        case '_':
          continue;
        default:
          throw new NumberFormatException(String.format(
              "Invalid SourcePawn octal literal: %s; Invalid character at index %d (%c)",
              text, i, ch));
      }
    }

    return value;
  }

  public static int parsePawnDecimalLiteral(String text) {
    char ch = text.charAt(0);
    if (ch < '0' || '9' < ch) {
      throw new NumberFormatException(String.format(
          "Invalid SourcePawn decimal literal: %s; " +
              "SourcePawn decimal literals should start with a digit",
          text));
    }

    int value = ch - '0';

    for (int i = 1; i < text.length(); i++) {
      ch = text.charAt(i);
      switch (ch) {
        case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9':
          value = (value * 10) + (ch - '0');
          // fall through
        case '_':
          continue;
        default:
          throw new NumberFormatException(String.format(
              "Invalid SourcePawn decimal literal: %s; Invalid character at index %d (%c)",
              text, i, ch));
      }
    }

    return value;
  }

  public static int parsePawnHexadecimalLiteral(String text) {
    if (!text.startsWith("0x")) {
      throw new NumberFormatException(String.format(
          "Invalid SourcePawn binary literal: %s; " +
              "SourcePawn binary literals should start with 0x",
          text));
    }

    int value = 0;

    char ch;
    for (int i = 2; i < text.length(); i++) {
      ch = text.charAt(i);
      switch (ch) {
        case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9':
          value = (value << 4) + (ch - '0');
          break;
        case 'A': case 'B': case 'C': case 'D': case 'E':
          value = (value << 4) + (ch - 'A' + 10);
          break;
        case 'a': case 'b': case 'c': case 'd': case 'e':
          value = (value << 4) + (ch - 'a' + 10);
          break;
        case '_':
          continue;
        default:
          throw new NumberFormatException(String.format(
              "Invalid SourcePawn hexadecimal literal: %s; Invalid character at index %d (%c)",
              text, i, ch));
      }
    }

    return value;
  }

  public static int parsePawnCharacterLiteral(String text, char ctrlChar) {
    int value = 0;
    try {
      int i = 0;
      char ch = text.charAt(i++);
      if (ch != ctrlChar) {
        value = ch;
        ch = text.charAt(i++);
      } else {
        ch = text.charAt(i++);
        if (ch == ctrlChar) {
          value = ch;
          ch = text.charAt(i++);
        } else {
          switch (ch) {
            case 'a':
              value = 7;
              ch = text.charAt(i++);
              break;
            case 'b':
              value = 8;
              ch = text.charAt(i++);
              break;
            case 'e':
              value = 27;
              ch = text.charAt(i++);
              break;
            case 'f':
              value = 12;
              ch = text.charAt(i++);
              break;
            case 'n':
              value = 10;
              ch = text.charAt(i++);
              break;
            case 'r':
              value = 13;
              ch = text.charAt(i++);
              break;
            case 't':
              value = 9;
              ch = text.charAt(i++);
              break;
            case 'v':
              value = 11;
              ch = text.charAt(i++);
              break;
            case 'x':
              HexDigits:
              for (int digits = 0; digits < 2; digits++) {
                switch (ch = text.charAt(i++)) {
                  case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9':
                    value = (value << 4) + (ch - '0');
                    break;
                  case 'A': case 'B': case 'C': case 'D': case 'E':
                    value = (value << 4) + (ch - 'A' + 10);
                    break;
                  case 'a': case 'b': case 'c': case 'd': case 'e':
                    value = (value << 4) + (ch - 'a' + 10);
                    break;
                  default:
                    break HexDigits;
                }
              }

              if (ch == ';') {
                ch = text.charAt(i++);
              }

              break;
            case '\'':
            case '\"':
            case '%':
              value = ch;
              ch = text.charAt(i++);
              break;
            default:
              if ('0' <= ch && ch <= '9') {
                do {
                  value = (value * 10) + (ch - '0');
                  ch = text.charAt(i++);
                } while ('0' <= ch && ch <= '9');

                if (ch == ';') {
                  ch = text.charAt(i++);
                }
              } else {
                throw new NumberFormatException("Invalid SourcePawn character literal: " + text);
              }
          }
        }
      }

      return value;
    } catch (IndexOutOfBoundsException e) {
      //throw new NumberFormatException(String.format("Invalid SourcePawn character literal: " + text));
      return value;
    }
  }

  public static double parseRational(String text) {
    return Double.parseDouble(text);
  }

}
