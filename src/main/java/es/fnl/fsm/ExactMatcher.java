/* Created on Dec 20, 2012 by Florian Leitner. Copyright 2012. All rights reserved. */
package es.fnl.fsm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A generic DFA for doing exact pattern matching. The implementation is based on the classical
 * Knuth-Morris-Pratt algorithm to search for an exact, sequential pattern.
 * <p>
 * This is an exact matcher, so elements are compared using <code>equals(Object)</code>. Note that
 * the empty pattern is illegal, while a <code>null</code> in the pattern is allowed to match a
 * <code>null</code> in the sequence if at the right position.
 * 
 * @author Florian Leitner
 */
public class ExactMatcher<E> {
  final int end;
  final List<E> pattern;
  private final Map<E, int[]> dfa; // "alphabetized" transition "table"

  /**
   * Create a matcher for a pattern sequence.
   * 
   * @param pattern sequence that should lead to a match
   */
  public ExactMatcher(final List<E> pattern) {
    this.end = pattern.size();
    this.pattern = new ArrayList<E>(pattern);
    this.dfa = new HashMap<E, int[]>();
    if (end == 0) throw new IllegalArgumentException("empty patterns are illegal");
    // compile the DFA transition table:
    int[] next;
    for (E transition : pattern)
      dfa.put(transition, new int[end]);
    dfa.get(pattern.get(0))[0] = 1; // initial state match transition
    for (int base = 0, pointer = 1; pointer < end; pointer++) {
      for (int[] change : dfa.values())
        change[pointer] = change[base]; // set state changes for mismatches
      next = dfa.get(pattern.get(pointer)); // get the table for the current element
      next[pointer] = pointer + 1; // store state change for match
      base = next[base]; // update current base state
    }
  }

  /** @return an updated pointer using the transition table */
  protected int transition(final E element, final int pointer) {
    // if the element is known, and given the current state (pointer), find the next (pointer)
    if (dfa.containsKey(element)) return dfa.get(element)[pointer];
    else return 0; // otherwise, return to the initial state (pointer)
  }
  
  /** @return a clone of the pattern sequence */
  public List<E> getPattern() {
    return new ArrayList<E>(pattern);
  }

  /** @return length of the pattern (total number of elements). */
  public int length() {
    return end;
  }

  /** @return radix of the pattern (number of non-equal elements). */
  public int radix() {
    return dfa.size();
  }

  /**
   * Find the index at which the pattern matches in the sequence.
   * 
   * @return the offset of the match or <code>-1</code> if no match is found
   */
  public int find(final List<E> sequence) {
    return find(sequence, 0);
  }

  /**
   * Find the index at which the pattern matches in the sequence at or after the offset.
   * 
   * @return the offset of the match or <code>-1</code> if no match is found
   */
  public int find(final List<E> sequence, int offset) {
    int pointer = 0;
    final int size = sequence.size();
    while (offset < size) {
      pointer = transition(sequence.get(offset), pointer);
      if (pointer == end) return offset - end + 1;
      else offset++;
    }
    return -1;
  }

  /** Determine if the pattern matches anywhere in a sequence iterator. */
  public boolean scan(final Iterator<E> seqIt) {
    int pointer = 0;
    while (seqIt.hasNext()) {
      pointer = transition(seqIt.next(), pointer);
      if (pointer == end) return true;
    }
    return false;
  }
}
