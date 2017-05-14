package net.alliedmods.lang.amxxpawn.lexer;

import com.google.common.base.Strings;

import com.intellij.lexer.LexerBase;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.text.CharArrayUtil;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Parses an AP file and performs preprocessor operations.
 *
 * Identifiers will be read and if the identifier matches a macro pattern, then the token text will
 * be replaced with the substitution text. The line will then be processed by the default AP lexer.
 */
public class ApPreprocessorLexer extends LexerBase {

  private final Map<String, Pair<Pattern, String>> PATTERNS = new HashMap<>();
  private final StringBuilder BUFFER = new StringBuilder(32);

  @Nullable private CharSequence buffer;
  @Nullable private char[] bufferChars;
  private int bufferIndex;
  private int bufferEndOffset;
  private int tokenEndOffset;
  @Nullable private IElementType tokenType;

  public ApPreprocessorLexer() {
    // defined by the compiler at compile-time
    define("__DATE__", "", "__DATE__");
    define("__TIME__", "", "__TIME__");
    define("__BINARY_PATH__", "", "__BINARY_PATH__");
    define("__BINARY_NAME__", "", "__BINARY_NAME__");
  }

  public void define(@NotNull @NonNls String prefix, @NotNull @NonNls String postfix) {
    define(prefix, postfix, null);
  }

  public void define(@NotNull @NonNls String prefix,
                     @NotNull @NonNls String postfix,
                     @Nullable @NonNls String substitution) {
    postfix = postfix.replaceAll("%[0-9]", ".*");
    Pair<Pattern, String> tuple = Pair.create(Pattern.compile(postfix), Strings.nullToEmpty(substitution));
    PATTERNS.put(prefix, tuple);
  }

  public void undef(@NotNull @NonNls String prefix) {
    PATTERNS.remove(prefix);
  }

  @NotNull
  public String resolve(@NotNull @NonNls String prefix, @NotNull @NonNls String[] args) {
    Pair<Pattern, String> definition = PATTERNS.get(prefix);
    String substitution = definition.second;
    for (int i = 0; i < args.length; i++) {
      substitution = substitution.replaceAll("%" + i, args[i]);
    }

    return substitution;
  }

  @Override
  public void start(@NotNull CharSequence buffer, int startOffset, int endOffset, int initialState) {
    this.buffer = buffer;
    this.bufferChars = CharArrayUtil.fromSequenceWithoutCopying(buffer);
    this.bufferIndex = startOffset;
    this.bufferEndOffset = endOffset;

    this.tokenType = null;
    this.tokenEndOffset = startOffset;
  }

  @Override
  @Nullable
  public IElementType getTokenType() {
    return tokenType;
  }

  @NotNull
  @Override
  public CharSequence getTokenSequence() {
    return super.getTokenSequence();
  }

  @Override
  public void advance() {
    if (tokenType == null) {
      locateToken();
    }

    tokenType = null;
  }

  private char charAt(int offset) {
    return bufferChars[offset];
  }

  private void locateToken() {
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
    }
  }

  private int getWhitespaces(int offset) {
    while (offset < bufferEndOffset && charAt(offset++) <= ' ');
    return offset;
  }
}
