/* Created on Dec 20, 2012 by Florian Leitner. Copyright 2012. All rights reserved. */
package es.fnl.fsm;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic DFA for exact pattern matching. The implementations are based on the classical
 * Knuth-Morris-Pratt and Boyer-Moore algorithms to search for exact, sequential patterns.
 * <p>
 * As this is an exact matcher, elements are compared using <code>equals(Object)</code>. Note that
 * the empty pattern is illegal, while a <code>null</code> in the pattern is allowed to match a
 * <code>null</code> in the sequence if at the right position.
 * 
 * @author Florian Leitner
 */
abstract class ExactMatcherBase<E> {
  /** The actual pattern sequence being matched. */
  protected final List<E> pattern;
  /** The length of the pattern. */
  final int end;

  /**
   * Create a new exact matcher for a pattern sequence.
   * 
   * @param pattern sequence that should lead to a match
   * @throws IllegalArgumentException if the pattern is empty
   */
  public ExactMatcherBase(final List<E> pattern) {
    end = pattern.size();
    this.pattern = new ArrayList<E>(pattern);
    if (end == 0) throw new IllegalArgumentException("empty patterns are illegal");
  }

  /** @return a clone of the pattern */
  public List<E> pattern() {
    return new ArrayList<E>(pattern);
  }

  /** @return length of the pattern (total number of elements) */
  public int length() {
    return end;
  }

  /** @return radix of the pattern (number of non-equal elements) */
  public abstract int radix();
}
