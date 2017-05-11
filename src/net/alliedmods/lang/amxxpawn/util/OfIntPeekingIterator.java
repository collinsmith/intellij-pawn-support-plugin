package net.alliedmods.lang.amxxpawn.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.PeekingIterator;

import org.jetbrains.annotations.NotNull;

import java.util.PrimitiveIterator;

/**
 * Specialized implementation of com.google.common.collect.PeekingIterator for OfInt
 */
public class OfIntPeekingIterator implements PeekingIterator<Integer> {

  private final PrimitiveIterator.OfInt iterator;
  private boolean hasPeeked;
  private int peekedElement;

  public OfIntPeekingIterator(@NotNull PrimitiveIterator.OfInt iterator) {
    Preconditions.checkArgument(iterator != null);
    this.iterator = iterator;
  }

  public boolean hasNext() {
    return this.hasPeeked || this.iterator.hasNext();
  }

  public Integer next() {
    if (!this.hasPeeked) {
      return this.iterator.next();
    } else {
      int result = this.peekedElement;
      this.hasPeeked = false;
      this.peekedElement = Integer.MIN_VALUE;
      return result;
    }
  }

  public void remove() {
    Preconditions.checkState(!this.hasPeeked, "Can\'t remove after you\'ve peeked at next");
    this.iterator.remove();
  }

  public Integer peek() {
    if (!this.hasPeeked) {
      this.peekedElement = this.iterator.next();
      this.hasPeeked = true;
    }

    return this.peekedElement;
  }

  public int nextInt() {
    if (!this.hasPeeked) {
      return this.iterator.nextInt();
    } else {
      int result = this.peekedElement;
      this.hasPeeked = false;
      this.peekedElement = Integer.MIN_VALUE;
      return result;
    }
  }

  public int peekInt() {
    if (!this.hasPeeked) {
      this.peekedElement = this.iterator.nextInt();
      this.hasPeeked = true;
    }

    return this.peekedElement;
  }

}