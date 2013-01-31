/* Created on Dec 20, 2012 by Florian Leitner. Copyright 2012. All rights reserved. */
package es.fnl.fsm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A generic DFA for doing exact pattern matching. The implementation is based on the classical
 * Knuth-Morris-Pratt algorithm to search for an exact, sequential pattern.
 * <p>
 * This is an exact matcher, so elements are compared using <code>equals(Object)</code>. Note that
 * the empty pattern never match, while a <code>null</code> in the pattern is allowed to match a
 * <code>null</code> in the sequence if in the right positions (because the DFA uses a HashMap).
 * 
 * @author Florian Leitner
 */
public class ExactMatcher<E> {
  final E[] pattern;
  final Map<E, int[]> dfa;

  /**
   * Create a matcher for a sequence of elements.
   * <p>
   * Automatically {@link #compile() compiles} the DFA transition table.
   * 
   * @param pattern sequence that should lead to a match
   */
  @SuppressWarnings("unchecked")
  public ExactMatcher(List<E> pattern) {
    this((E[]) pattern.toArray());
  }

  /**
   * Create a matcher from a List of elements (warning: unchecked/unsafe).
   * <p>
   * Automatically {@link #compile() compiles} the DFA transition table.
   * 
   * @param p sequence that should lead to a match
   * @param klass of the instances (<E>)
   */
  public ExactMatcher(E[] p) {
    pattern = p;
    dfa = new HashMap<E, int[]>();
    compile();
  }

  /** @return a clone of the pattern sequence */
  public List<E> getPattern() {
    return Arrays.asList(pattern);
  }

  /** Compile the DFA transition table using the KMP construction algorithm. */
  private void compile() {
    int[] next;
    for (E transition : pattern)
      dfa.put(transition, new int[pattern.length]);
    if (pattern.length > 0) {
      dfa.get(pattern[0])[0] = 1; // initial state match transition
      for (int x = 0, i = 1; i < pattern.length; i++) {
        for (int[] change : dfa.values())
          change[i] = change[x]; // set state changes for mismatches
        next = dfa.get(pattern[i]); // get the table for the current element
        next[i] = i + 1; // store state change for match
        x = next[x]; // update current base state x
      }
    }
  }

  /** @return length of the pattern (total number of elements). */
  public int length() {
    return pattern.length;
  }

  /** @return size of the pattern (total number of elements). */
  public int size() {
    return length();
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
  public int find(List<E> sequence) {
    return find(sequence, 0);
  }

  /**
   * Find the index at which the pattern matches in the sequence at or after the offset.
   * 
   * @return the offset of the match or <code>-1</code> if no match is found
   */
  public int find(List<E> sequence, int offset) {
    int p = 0;
    int n = sequence.size();
    while (offset < n && p < pattern.length) {
      E element = sequence.get(offset);
      p = transition(element, p);
      if (p == pattern.length) return offset - pattern.length + 1;
      else offset++;
    }
    return -1;
  }

  /** Determine if the pattern matches anywhere in a sequence iterator. */
  public boolean scan(Iterator<E> seqIt) {
    int p = 0;
    while (seqIt.hasNext() && p < pattern.length) {
      E element = seqIt.next();
      p = transition(element, p);
      if (p == pattern.length) return true;
    }
    return false;
  }

  private int transition(E element, int p) {
    try {
      return dfa.get(element)[p];
    } catch (NullPointerException e) {
      return 0;
    }
  }
}
