/* Created on Dec 20, 2012 by Florian Leitner. Copyright 2012. All rights reserved. */
package es.fnl.fsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * A generic DFA for doing exact pattern matching. The implementation is based on the classical
 * Knuth-Morris-Pratt algorithm to search for an exact, sequential pattern.
 * <p>
 * This is an exact matcher, so elements are compared using <code>equals(Object)</code>. Note that
 * the empty pattern never match, while a <code>null</code> in the pattern is allowed to match a
 * <code>null</code> in the sequence if in the right positions.
 * 
 * @author Florian Leitner
 */
public class ExactMatcher<E> {
  final E[] pattern;
  final List<E> alphabet;
  final int[][] dfa;
  final int radix;
  final int length;

  /** The matching function; returns <code>true</code> if the element and transition are null. */
  private static boolean matches(Object element, Object transition) {
    return transition != null && transition.equals(element) || transition == null &&
        element == null;
  }

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
   * @param pattern sequence that should lead to a match
   * @param klass of the instances (<E>)
   */
  public ExactMatcher(E[] pattern) {
    this.pattern = pattern;
    length = pattern.length;
    alphabet = new ArrayList<E>(new HashSet<E>(Arrays.asList(pattern)));
    radix = alphabet.size();
    dfa = new int[radix][length];
    if (length > 0) compile();
  }

  /** @return a clone of the pattern sequence */
  public List<E> getPattern() {
    return Arrays.asList(pattern);
  }

  /** Compile DFA transition table using the classical KMP construction algorithm. */
  private void compile() {
    dfa[alphabet.indexOf(pattern[0])][0] = 1; // initial state match transition
    for (int x = 0, i = 1; i < length; i++) {
      int r;
      for (r = 0; r < radix; r++)
        dfa[r][i] = dfa[r][x]; // set state changes given a mismatch
      r = alphabet.indexOf(pattern[i]); // find the radix
      dfa[r][i] = i + 1; // set the state change given a match
      x = dfa[r][x]; // update current base state x
    }
  }

  /** Return the length of the pattern (total number of elements). */
  public int length() {
    return length;
  }

  /** Return the size (radix) of the pattern (number of different elements). */
  public int size() {
    return radix;
  }

  /**
   * Find the index at which the pattern matches in a sequence.
   * <p>
   * Return <code>-1</code> if no match is found.
   */
  public int find(List<E> sequence) {
    return find(sequence, 0);
  }

  /**
   * Find the index at which the pattern matches in a sequence at or after the offset in the array.
   * <p>
   * Return <code>-1</code> if no match is found.
   */
  public int find(List<E> sequence, int offset) {
    int p = 0;
    int n = sequence.size();
    for (; offset < n && p < length; offset++) {
      E element = sequence.get(offset);
      if (matches(element, pattern[p])) p++;
      else if (p > 0) try {
        p = dfa[alphabet.indexOf(element)][p];
      } catch (IndexOutOfBoundsException e) {
        p = 0;
      }
      if (p == length) return offset - length + 1;
    }
    return -1;
  }

  /** Determine if the pattern matches anywhere in a sequence iterator. */
  public boolean scan(Iterator<E> seqIt) {
    int p = 0;
    while (seqIt.hasNext() && p < length) {
      E element = seqIt.next();
      if (matches(element, pattern[p])) p++;
      else if (p > 0) try {
        p = dfa[alphabet.indexOf(element)][p];
      } catch (IndexOutOfBoundsException e) {
        p = 0;
      }
      if (p == length) return true;
    }
    return false;
  }
}
