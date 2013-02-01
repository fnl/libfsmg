package es.fnl.fsm;

/**
 * Transitions define how elements of a sequence should match to the pattern.
 * <p>
 * E.g., if the {@link Pattern} (a non-deterministic automaton) or the {@link ExactMatcherBase} (a
 * deterministic automaton) should match a List of elements of some generic type <code>E</code>,
 * i.e., a <code>List&lt;E&gt;</code>, the transitions define if the current element in the
 * sequence is appropriate to allow the automaton to move on to its next state.
 * <p>
 * In the case of a character sequence automaton (i.e., in String pattern matching such as provided
 * by Java's regex package), the {@link Transition#matches(Object)} implementation would return the
 * Boolean result of {@link Character#compareTo(Character)} <code>== 0</code> and be instantiated
 * by defining the relevant character for the transition. Furthermore, for the backtracking
 * mechanism provided by the {@link Pattern} FSA, a {@link #weight() weight} of each transition
 * should be defined, while epsilon transitions default to a weight of {@link Double#MIN_VALUE}.
 * The path chosen for backtracking then is the path that has the highest summed transition
 * weights and therefore defines the matched sequence and capture groups.
 * 
 * <pre>
 * class CharacterTransition implements Transition&lt;Character&gt; {
 *   private Character c;
 * 
 *   public CharacterTransition(Character toMatch) {
 *     this.c = toMatch;
 *   }
 * 
 *   public boolean match(Character other) {
 *     return c.compareTo(other) == 0;
 *   }
 * 
 *   public double weight() {
 *     return 1.0; // each character is of equal "weight"
 *   }
 * }
 * </pre>
 * 
 * @author Florian Leitner
 */
public interface Transition<E> {
  /**
   * A transition implementation must define if, given some unknown element, it is valid to make
   * the transition or not.
   * 
   * @param element an element from the sequence being matched
   * @return <code>true</code> if the transition is valid, <code>false</code> otherwise.
   */
  public boolean matches(E element);

  /**
   * A transition implementation can define a weight that is used in backtracking to evalute the
   * path with the highest IC.
   * 
   * @return a value that represents the information content of this transition
   */
  public double weight();
}
