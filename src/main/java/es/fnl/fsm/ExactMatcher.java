/* Created on Dec 20, 2012 by Florian Leitner. Copyright 2012. All rights reserved. */
package es.fnl.fsm;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A generic DFA for doing exact pattern matching. The implementation is based on the classical
 * Knuth-Morris-Pratt algorithm, and therefore requires the use of an exact, sequential pattern.
 * <p>
 * In addition to this class, the {@link Transition} interface has to be implemented to define how
 * elements on the sequence should be matched. This is an exact matcher, so it is not possible to
 * define optional or repeated transitions, and there is not branching ability at intermediate
 * states of the DFA.
 * 
 * @author Florian Leitner
 */
public class ExactMatcher<T extends Transition<E>, E> {
  final T[] pattern;
  final int[] next;

  /**
   * Create a matcher from an array of transitions.
   * <p>
   * Automatically {@link #compile() compiles} the transition table.
   * 
   * @param transitions pattern sequence that should lead to a match
   */
  public ExactMatcher(T[] transitions) {
    this.pattern = transitions.clone();
    this.next = new int[transitions.length];
    compile();
  }

  /**
   * Create a matcher from a List of transitions (warning: unchecked/unsafe).
   * <p>
   * Automatically {@link #compile() compiles} the transition table.
   * 
   * @param pattern transitions that should lead to a match
   * @param klass of the transition instances (T extends Transition<E>)
   */
  @SuppressWarnings("unchecked")
  public ExactMatcher(List<T> pattern, Class<T> klass) {
    final int n = pattern.size();
    this.pattern = (T[]) Array.newInstance(klass, n);
    for (int i = 0; i < n; i++)
      this.pattern[i] = pattern.get(i);
    this.next = new int[n];
    compile();
  }

  /**
   * Return a clone of the array of transitions for this pattern.
   * 
   * @return a clone of the underlying transitions (for inspection).
   */
  public T[] getTransitions() {
    return pattern.clone();
  }

  /** Compile DFA transition table using the classical KMP construction algorithm. */
  private void compile() {
    int x = 0;
    for (int i = 1; i < pattern.length; i++) {
      if (pattern[x].equals(pattern[i])) {
        next[i] = next[x];
        x += 1;
      } else {
        next[i] = x + 1;
        x = next[x];
      }
    }
  }

  /** Return the length of the pattern (number of transitions). */
  public int length() {
    return pattern.length;
  }

  /**
   * Find the index at which the pattern matches in a sequence.
   * <p>
   * Return <code>-1</code> if no match is found.
   */
  public int find(E[] sequence) {
    return find(sequence, 0);
  }

  /**
   * Find the index at which the pattern matches in a sequence at or after position
   * <code>start</code> in the array.
   * <p>
   * Return <code>-1</code> if no match is found.
   */
  public int find(E[] sequence, int offset) {
    final int n = sequence.length;
    final int m = length();
    int j = 0;
    for (int i = offset; i < n; i++) {
      if (pattern[j].matches(sequence[i])) j++;
      else j = next[j];
      if (j == m) return i - m + 1;
    }
    return -1;
  }

  /**
   * Find the first element at which the pattern matches a sequence iterator.
   * <p>
   * Return <code>null</code> if no match was made.
   */
  public E find(Iterator<E> sequenceIt) {
    List<E> sub = search(sequenceIt);
    return (null == sub) ? null : sub.get(0);
  }

  /**
   * Assert if the pattern matches anywhere in a sequence.
   */
  public boolean matches(E[] sequence) {
    return find(sequence, 0) != -1;
  }

  /**
   * Assert if the pattern matches anywhere in a sequence at or after position <code>start</code>
   * in the array.
   */
  public boolean matches(E[] sequence, int offset) {
    return find(sequence, offset) != -1;
  }

  /**
   * Assert if the pattern matches anywhere in a sequence iterator.
   */
  public boolean matches(Iterator<E> sequenceIt) {
    final int m = length();
    int j = 0;
    while (sequenceIt.hasNext()) {
      if (pattern[j].matches(sequenceIt.next())) j++;
      else j = next[j];
      if (j == m) return true;
    }
    return false;
  }

  /**
   * Extract the List of elements at which the pattern matches a sequence iterator.
   * <p>
   * Return <code>null</code> if no match was made.
   */
  public List<E> search(Iterator<E> sequence) {
    final int m = length();
    int j = 0;
    List<E> l = null;
    while (sequence.hasNext()) {
      E element = sequence.next();
      if (pattern[j].matches(element)) {
        if (j == 0) l = new LinkedList<E>();
        l.add(element);
        j++;
      } else {
        j = next[j];
      }
      if (j == m) {
        int i = l.size();
        return i == m ? l : l.subList(i - m, i);
      }
    }
    return null;
  }
}
