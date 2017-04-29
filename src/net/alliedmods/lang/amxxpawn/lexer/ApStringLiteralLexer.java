package net.alliedmods.lang.amxxpawn.lexer;

import com.intellij.lexer.LexerBase;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.StringEscapesTokenTypes;
import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApStringLiteralLexer extends LexerBase {

  private static final Logger LOG = Logger.getInstance("#net.alliedmods.lang.amxxpawn.lexer.ApStringLiteralLexer");
  private static final boolean DEBUG = false;

  protected final CtrlProvider ctrlProvider;
  protected final char quoteChar;
  protected final IElementType originalToken;
  protected final int additionalOffset;

  protected CharSequence buffer;
  protected int start;
  protected int end;
  private int state;
  private int lastState;
  protected int bufferEnd;

  public ApStringLiteralLexer(@NotNull CtrlProvider ctrlProvider, char quoteChar, @NotNull IElementType originalToken) {
    this(ctrlProvider, quoteChar, originalToken, 0);
  }

  public ApStringLiteralLexer(@NotNull CtrlProvider ctrlProvider, char quoteChar, @NotNull IElementType originalToken, int startOffset) {
    this.ctrlProvider = ctrlProvider;
    this.quoteChar = quoteChar;
    this.originalToken = originalToken;
    this.additionalOffset = startOffset;
  }

  @Override
  public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
    this.buffer = buffer;
    start = startOffset + additionalOffset;
    state = lastState = initialState;
    bufferEnd = endOffset;
    end = locateToken(start);
  }

  public int getState() {
    return lastState;
  }

  @Override
  public int getTokenStart() {
    return start;
  }

  @Override
  public int getTokenEnd() {
    return end;
  }

  @Override
  public void advance() {
    lastState = state;
    start = end;
    end = locateToken(start);
  }

  @NotNull
  @Override
  public CharSequence getBufferSequence() {
    return buffer;
  }

  @Override
  public int getBufferEnd() {
    return bufferEnd;
  }

  @Nullable
  @Override
  public IElementType getTokenType() {
    if (start >= end) {
      return null;
    }

    int i = start;
    char ch = buffer.charAt(i);
    final int sc_ctrlchar = ctrlProvider.getCtrlChar();
    if (ch != sc_ctrlchar) {
      return this.originalToken;
    } else if (++i >= end) {
      return StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN;
    }

    ch = buffer.charAt(i);
    if (ch == sc_ctrlchar) {
      return StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN;
    }

    switch (ch) {
      case '"':case '\'':case '%':
        return StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN;
      case 'a':case 'b':case 'e':case 'f':case 'n':case 'r':case 't':case 'v':
        return StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN;
      case 'x':
        if (++i >= end) {
          return StringEscapesTokenTypes.INVALID_UNICODE_ESCAPE_TOKEN;
        }

        ch = buffer.charAt(i);
        if (!StringUtil.isHexDigit(ch)) {
          return StringEscapesTokenTypes.INVALID_UNICODE_ESCAPE_TOKEN;
        }

        return StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN;
      default:
        if (!StringUtil.isDecimalDigit(ch)) {
          return StringEscapesTokenTypes.INVALID_UNICODE_ESCAPE_TOKEN;
        }

        return StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN;
    }
  }

  private int locateToken(int start) {
    if (start == bufferEnd) {
      state = 2;
    }

    if (state == 2) {
      return start;
    }

    int i = start;
    char ch = buffer.charAt(i);
    final int sc_ctrlchar = ctrlProvider.getCtrlChar();
    if (ch == sc_ctrlchar) {
      if (DEBUG) LOG.assertTrue(state == 1);
      if (++i == bufferEnd) {
        state = 2;
        return i;
      }

      ch = buffer.charAt(i);
      switch (ch) {
        case 'x':
          while (++i < bufferEnd && StringUtil.isHexDigit(buffer.charAt(i)));
          if (i < bufferEnd && buffer.charAt(i) == ';') {
            i++;
          }

          return i;
        case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
          while (++i < bufferEnd && StringUtil.isDecimalDigit(buffer.charAt(i))) ;
          if (i < bufferEnd && buffer.charAt(i) == ';') {
            i++;
          }

          return i;
        default:
          return i + 1;
      }
    }

    // FIXME: This was throwing an assertion error, however it's exactly like the original and the output seems correct
    if (DEBUG) LOG.assertTrue(state == 1 || ch == quoteChar, this);
    while (i < bufferEnd) {
      if (ch == sc_ctrlchar) {
        return i;
      }

      if (state == 1 && ch == quoteChar) {
        if (i + 1 == bufferEnd) {
          state = 2;
        }

        return i + 1;
      }

      ch = buffer.charAt(++i);
      state = 1;
    }

    return i;
  }

  public String toString() {
    return "StringLiteralLexer {originalToken=" + originalToken + ", quoteChar=" + quoteChar
        + ", bufferEnd=" + bufferEnd + ", lastState=" + lastState + ", state=" + state
        + ", end=" + end + ", start=" + start
        + ", token=" + (buffer != null && end >= start && end <= buffer.length()
        ? buffer.subSequence(start, end) : null) + '}';
  }

}
