package es.fnl.fsm;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public abstract class TestMatcherBase {
  String test;
  Character[] pattern = new Character[] { 'a', 'b', 'c', 'b', 'a' };
  MatcherBase<Character> base;

  public void setUp() {
    test = "xabcaabcbaabax";
  }

  @Test
  public final void testPattern() {
    assertEquals(Arrays.asList(pattern), base.pattern());
  }

  @Test
  public final void testLengthAndRadix() {
    assertEquals(5, base.length());
    assertEquals(3, base.radix());
  }

  protected List<Character> newCharacterList(String s) {
    List<Character> result = new ArrayList<Character>(s.length());
    for (char c : s.toCharArray())
      result.add(c);
    return result;
  }
}
