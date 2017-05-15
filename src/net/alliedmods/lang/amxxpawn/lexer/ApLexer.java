package net.alliedmods.lang.amxxpawn.lexer;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.text.CharArrayUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class ApLexer extends LexerBase {
  private static final int INITIAL_STATE = 0;

  @NotNull private final _ApLexer flexLexer;

  @NotNull private CharSequence buffer;
  @Nullable private char[] bufferChars;
  private int bufferIndex;
  private int bufferEndOffset;

  @Nullable private IElementType tokenType;
  private int tokenEndOffset;

  public ApLexer() {
    this.flexLexer = new _ApLexer();
  }

  @Override
  public final void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
    this.buffer = buffer;
    this.bufferChars = CharArrayUtil.fromSequenceWithoutCopying(buffer);
    this.bufferIndex = startOffset;
    this.bufferEndOffset = endOffset;

    this.tokenType = null;
    this.tokenEndOffset = startOffset;

    this.flexLexer.reset(buffer, startOffset, endOffset, INITIAL_STATE);
  }

  @Override
  public int getState() {
    return INITIAL_STATE;
  }

  @Override
  @Nullable
  public final IElementType getTokenType() {
    if (tokenType == null) {
      _locateToken();
    }

    return tokenType;
  }

  @Override
  public final int getTokenStart() {
    return bufferIndex;
  }

  @Override
  public final int getTokenEnd() {
    if (tokenType == null) {
      _locateToken();
    }

    return tokenEndOffset;
  }

  @Override
  public final void advance() {
    if (tokenType == null) {
      _locateToken();
    }

    tokenType = null;
  }

  private char charAt(int offset) {
    return bufferChars != null ? bufferChars[offset] : buffer.charAt(bufferIndex);
  }

  private void _locateToken() {
    if (tokenEndOffset == bufferEndOffset) {
      tokenType = null;
      bufferIndex = bufferEndOffset;
      return;
    }

    this.bufferIndex = tokenEndOffset;
    char ch = charAt(bufferIndex);
    switch (ch) {
      case ' ':
      case '\t':
      case '\f':
      case '\r':
      case '\n':
        tokenType = ApTokenTypes.WHITE_SPACE;
        tokenEndOffset = getWhitespaces(bufferIndex + 1);
        break;

      case '/':
        if (bufferEndOffset <= bufferIndex + 1) {
          tokenType = ApTokenTypes.DIV;
          tokenEndOffset = bufferEndOffset;
          break;
        }

        ch = charAt(bufferIndex + 1);
        switch (ch) {
          case '/':
            tokenType = ApTokenTypes.END_OF_LINE_COMMENT;
            tokenEndOffset = getLineTerminator(bufferIndex + 2, false);
            break;

          case '*':
            if (bufferEndOffset <= bufferIndex + 2 || charAt(bufferIndex + 2) != '*'
             || bufferEndOffset >  bufferIndex + 3 || charAt(bufferIndex + 3) == '/') {
              tokenType = ApTokenTypes.C_STYLE_COMMENT;
              tokenEndOffset = getClosingComment(bufferIndex + 2);
            } else {
              tokenType = ApTokenTypes.DOC_COMMENT;
              tokenEndOffset = getClosingComment(bufferIndex + 3);
            }
            break;

          default:
            flexLocateToken();
            break;
        }
        break;

      case '"':
      case '\'':
        tokenType = ch == '"' ? ApTokenTypes.STRING_LITERAL : ApTokenTypes.CHARACTER_LITERAL;
        tokenEndOffset = getClosingQuote(bufferIndex + 1, ch);
        break;

      default:
        if (bufferIndex + 1 < bufferEndOffset) {
          tokenType = getSpecialStringStart(bufferIndex);
          if (tokenType == null) {
            flexLocateToken();
            break;
          }

          tokenEndOffset = getClosingQuote(
              tokenType == ApTokenTypes.PACKED_RAW_STRING_LITERAL ? bufferIndex + 2 : bufferIndex + 1,
              '"');
          break;
        }

        flexLocateToken();
        break;
    }

    if (bufferEndOffset < tokenEndOffset) {
      tokenEndOffset = bufferEndOffset;
    }
  }

  private void flexLocateToken() {
    try {
      flexLexer.goTo(bufferIndex);
      tokenType = flexLexer.advance();
      tokenEndOffset = flexLexer.getTokenEnd();
    } catch (IOException e) {
      throw new AssertionError("flexLocateToken should never throw an IOException", e);
    }
  }

  private boolean isWhitespace(char ch) {
    //return ch == ' ' || ch == '\t' || ch == '\f' || ch == '\r' || ch == '\n';
    return ch <= ' ';
  }

  private int getWhitespaces(int offset) {
    while (offset < bufferEndOffset && isWhitespace(charAt(offset))) offset++;
    return Math.min(offset, bufferEndOffset);
  }

  private boolean isEOL(char ch) {
    return ch == '\r' || ch == '\n';
  }

  private int getLineTerminator(int offset) {
    while (offset < bufferEndOffset && !isEOL(charAt(offset))) offset++;
    return offset;
  }

  private int getClosingComment(int offset) {
    while (offset < bufferEndOffset - 1 && charAt(offset) != '*' && charAt(offset + 1) != '/') offset++;
    return offset + 2;
  }

  /**
   * This method will return flags associated with multi-char string literals (special strings).
   * It assumes {@code offset + 1} has already been checked for validity and handles the below
   * cases:
   *
   * <ul>
   *   <li>{@code \"..."}</li>
   *   <li>{@code !"..."}</li>
   *   <li>{@code !\"..."}</li>
   *   <li>{@code \!"..."}</li>
   * </ul>
   */
  @Nullable
  private IElementType getSpecialStringStart(int offset) {
    if (charAt(offset) == flexLexer.sc_ctrlchar && charAt(offset + 1) == '"') {
      return ApTokenTypes.RAW_STRING_LITERAL; // \"..."
    } else if (charAt(offset) == '!' && charAt(offset + 1) == '"') {
      return ApTokenTypes.PACKED_STRING_LITERAL; // !"..."
    } else if (offset + 2 < bufferEndOffset && charAt(offset + 2) == '"') {
      if (charAt(offset) == '!' && charAt(offset + 1) == flexLexer.sc_ctrlchar) {
        return ApTokenTypes.PACKED_RAW_STRING_LITERAL; // !\"..."
      } else if (charAt(offset) == flexLexer.sc_ctrlchar && charAt(offset + 1) == '!') {
        return ApTokenTypes.PACKED_RAW_STRING_LITERAL; // \!"..."
      }
    }

    return null;
  }

  private int getClosingQuote(int offset, char quote) {
    if (offset >= bufferEndOffset) {
      return bufferEndOffset;
    }

    char ch = charAt(offset);
    final int sc_ctrlchar = flexLexer.sc_ctrlchar;
    for (;;) {
      while (ch != quote && ch != '\r' && ch != '\n' && ch != '\\' && ch != sc_ctrlchar) {
        offset++;
        if (offset >= bufferEndOffset) {
          return bufferEndOffset;
        }

        ch = charAt(offset);
      }

      // line continuation
      if (ch == '\\') {
        int pos = offset + 1;
        if (pos >= bufferEndOffset) {
          return bufferEndOffset;
        }

        ch = charAt(pos);
        if (isWhitespace(ch)) {
          pos = getWhitespaces(pos);
          ch = charAt(pos);
        }

        if (ch == '\r' || ch == '\n') {
          offset = getWhitespaces(pos);
          continue;
        }
      }

      if (ch == sc_ctrlchar) {
        offset++;
        if (offset >= bufferEndOffset) {
          return bufferEndOffset;
        }

        ch = charAt(offset);

      }
    }

    return offset;
  }
}
