package net.sourcemod.sourcepawn.util;

import org.jetbrains.annotations.NotNull;

public class OffsetCharSequence implements CharSequence {

  private final CharSequence delegate;
  private final int offset;

  public OffsetCharSequence(@NotNull CharSequence delegate, int offset) {
    this.delegate = delegate;
    this.offset = offset;
  }

  @Override
  public int length() {
    return delegate.length() - offset;
  }

  @Override
  public char charAt(int index) {
    return delegate.charAt(index + offset);
  }

  @Override
  public CharSequence subSequence(int start, int end) {
    return delegate.subSequence(start + offset, end + offset);
  }
}
