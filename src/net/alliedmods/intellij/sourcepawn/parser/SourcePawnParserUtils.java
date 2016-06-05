package net.alliedmods.intellij.sourcepawn.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes;

public class SourcePawnParserUtils extends GeneratedParserUtilBase {

  private static final boolean DEFAULT_REQUIRE_SEMICOLONS = false;
  private static final char DEFAULT_CTRL_CHAR = '\\';

  private static boolean requireSemicolons;
  private static char ctrlChar;

  private static int evaluatedValue;

  public static void resetState() {
    System.out.println("resetting state");
    setSemicolonsRequired(DEFAULT_REQUIRE_SEMICOLONS);
    setCtrlChar(DEFAULT_CTRL_CHAR);
  }

  public static boolean resetState(PsiBuilder builder, int level) {
    resetState();
    return true;
  }

  public static void setSemicolonsRequired(boolean required) {
    if (requireSemicolons != required) {
      System.out.println("semicolons are " + (required ? "required" : "not required"));
      requireSemicolons = required;
    }
  }

  public static boolean areSemicolonsRequired() {
    return requireSemicolons;
  }

  public static void setCtrlChar(char ctrlChar) {
    if (getCtrlChar() != ctrlChar) {
      System.out.printf("ctrlchar set to \'%c\'%n", ctrlChar);
      SourcePawnParserUtils.ctrlChar = ctrlChar;
    }
  }

  public static char getCtrlChar() {
    return ctrlChar;
  }

  public static boolean parsePragmaSemicolons(PsiBuilder builder, int level, Parser parser) {
    boolean parsed = parseConstantExpression(builder, level, parser);
    setSemicolonsRequired(evaluatedValue > 0);
    return parsed;
  }

  public static boolean parsePragmaCtrlchar(PsiBuilder builder, int level, Parser parser) {
    boolean parsed = parseConstantExpression(builder, level, parser);
    setCtrlChar((char)evaluatedValue);
    return parsed;
  }

  public static boolean parseConstantExpression(PsiBuilder builder, int level, Parser parser) {
    PsiBuilder.Marker marker = builder.mark();
    String text = builder.getTokenText();
    IElementType type = builder.getTokenType();
    boolean parsed = parser.parse(builder, level);
    if (!parsed) {
      marker.error("Expected constant expression");
      marker.rollbackTo();
      return false;
    }

    System.out.println("parseConstantExpression: " +
        "parsed = " + parsed + "; text = " + text + "; type = " + type.toString());
    try {
      evaluatedValue = evaluateNumber(type, text);
      marker.drop();
      return true;
    } catch (IllegalArgumentException e) {
      marker.error(e.getMessage());
      marker.rollbackTo();
      return false;
    }
  }

  public static int evaluateNumber(IElementType type, String text) {
    if (type == SourcePawnTypes.TRUE || type == SourcePawnTypes.FALSE) {
      return parsePawnBooleanLiteral(type);
    } else if (type == SourcePawnTypes.BINARY_LITERAL) {
      return parsePawnBinaryLiteral(text);
    } else if (type == SourcePawnTypes.DECIMAL_LITERAL) {
      return parsePawnDecimalLiteral(text);
    } else if (type == SourcePawnTypes.HEXADECIMAL_LITERAL) {
      return parsePawnHexadecimalLiteral(text);
    } else if (type == SourcePawnTypes.CHARACTER_STRING) {
      return parsePawnCharacterLiteral(text);
    }

    throw new RuntimeException("Unsupported type: " + type);
  }

  public static int parsePawnBooleanLiteral(IElementType type) {
    if (type == SourcePawnTypes.TRUE) {
      return 1;
    } else if (type == SourcePawnTypes.FALSE) {
      return 0;
    }

    throw new IllegalArgumentException(String.format(
        "Invalid SourcePawn boolean literal: %s; " +
            "SourcePawn boolean literals should be either \"true\" or \"false\"",
        type));
  }

  @Deprecated
  public static int parsePawnBooleanLiteral(String text) {
    if (text.equals("true")) {
      return 1;
    } else if (text.equals("false")) {
      return 0;
    }

    throw new IllegalArgumentException(String.format(
        "Invalid SourcePawn boolean literal: %s; " +
            "SourcePawn boolean literals should be either \"true\" or \"false\"",
        text));
  }

  public static int parsePawnBinaryLiteral(String text) {
    if (!text.startsWith("0b")) {
      throw new IllegalArgumentException(String.format(
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
          // fallthrough
        case '_':
          continue;
        default:
          throw new IllegalArgumentException(String.format(
              "Invalid SourcePawn binary literal: %s; Invalid character at index %d (%c)",
              text, i, ch));
      }
    }

    return value;
  }

  public static int parsePawnDecimalLiteral(String text) {
    char ch = text.charAt(0);
    if (ch < '0' || '9' < ch) {
      throw new IllegalArgumentException(String.format(
          "Invalid SourcePawn decimal literal: %s; " +
              "SourcePawn decimal literals should start with a digit",
          text));
    }

    int value = ch - '0';

    for (int i = 1; i < text.length(); i++) {
      ch = text.charAt(i);
      switch (ch) {
        case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
          value = (value * 10) + (ch - '0');
        case '_':
          continue;
        default:
          throw new IllegalArgumentException(String.format(
              "Invalid SourcePawn decimal literal: %s; Invalid character at index %d (%c)",
              text, i, ch));
      }
    }

    return value;
  }

  public static int parsePawnHexadecimalLiteral(String text) {
    if (!text.startsWith("0x")) {
      throw new IllegalArgumentException(String.format(
          "Invalid SourcePawn binary literal: %s; " +
              "SourcePawn binary literals should start with 0x",
          text));
    }

    int value = 0;

    char ch;
    for (int i = 2; i < text.length(); i++) {
      ch = text.charAt(i);
      switch (ch) {
        case '0':case '1':case '2':case '3':case'4':case '5':case '6':case '7':case '8':case '9':
          value = (value << 4) + (ch - '0');
          break;
        case 'A':case 'B':case 'C':case 'D':case 'E':
          value = (value << 4) + (ch - 'A' + 10);
          break;
        case 'a':case 'b':case 'c':case 'd':case 'e':
          value = (value << 4) + (ch - 'a' + 10);
          break;
        case '_':
          continue;
        default:
          throw new IllegalArgumentException(String.format(
              "Invalid SourcePawn hexadecimal literal: %s; Invalid character at index %d (%c)",
              text, i, ch));
      }
    }

    return value;
  }

  public static boolean parsePawnCharacterLiteral(PsiBuilder builder, int level, Parser parser) {
    PsiBuilder.Marker marker = builder.mark();
    String text = builder.getTokenText();
    boolean parsed = parser.parse(builder, level);
    if (!parsed) {
      marker.error("Expected character literal");
      marker.rollbackTo();
      return false;
    }

    System.out.println("parsePawnCharacterLiteral: " +
        "parsed = " + parsed + "; text = " + text + "; ctrlchar = \'" + getCtrlChar() + "\'");
    try {
      evaluatedValue = parsePawnCharacterLiteral(text);
      marker.drop();
      return true;
    } catch (IllegalArgumentException e) {
      marker.error(e.getMessage());
      marker.rollbackTo();
      return false;
    }
  }

  public static int parsePawnCharacterLiteral(String text) {
    try {
      int i = 0;
      char ch = text.charAt(i++);
      if (ch != '\'') {
        throw new IllegalArgumentException(String.format(
            "Invalid SourcePawn character literal: %s; " +
                "SourcePawn character literals should start with \'",
            text));
      }

      int value = 0;
      ch = text.charAt(i++);
      if (ch != getCtrlChar()) {
        value = ch;
        ch = text.charAt(i++);
      } else {
        ch = text.charAt(i++);
        if (ch == getCtrlChar()) {
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
                  case '0': case '1': case '2': case '3': case '4':
                  case '5': case '6': case '7': case '8': case '9':
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
                throw new IllegalArgumentException("Invalid SourcePawn character literal: " + text);
              }
          }
        }
      }


      if (ch != '\'' || i != text.length()) {
        throw new IllegalArgumentException(String.format(
            "Invalid SourcePawn character literal: %s; " +
                "SourcePawn character literals should end with \'",
            text));
      }

      return value;
    } catch (IndexOutOfBoundsException e) {
      throw new IllegalArgumentException(String.format( "Invalid SourcePawn character literal: " + text));
    }
  }

  public static boolean parseExpressionEnding(PsiBuilder builder, int level) {
    if (requireSemicolons) {
      return parseTokens(builder, 1, SourcePawnTypes.SEMICOLON);
    } else if (parseTokens(builder, 1, SourcePawnTypes.SEMICOLON)) {
      return true;
    }

    return parseTokens(builder, 1, TokenType.WHITE_SPACE);
  }

  private SourcePawnParserUtils() {
  }

}
