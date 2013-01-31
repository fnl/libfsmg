package es.fnl.fsm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestExactMatcher {
  @Test
  public final void testMatcherSetupList() {
    assertEquals(-1,
        (new ExactMatcher<Object>(new ArrayList<Object>())).find(Arrays.asList(new Object[1])));
  }

  @Test
  public final void testMatcherSetupArray() {
    assertFalse((new ExactMatcher<Object>(new Object[0])).scan(Arrays.asList(new Object[1])
        .iterator()));
  }

  @Test
  public final void testGetPattern() {
    Character[] p = new Character[] { 'a', 'b', 'c' };
    ExactMatcher<Character> matcher = new ExactMatcher<Character>(p);
    assertEquals(Arrays.asList(p), matcher.getPattern());
  }

  @Test
  public final void testLengthAndRadix() {
    Character[] p = new Character[] { 'a', 'b', 'c', 'b', 'a' };
    ExactMatcher<Character> matcher = new ExactMatcher<Character>(p);
    assertEquals(5, matcher.length());
    assertEquals(3, matcher.radix());
  }

  private List<Character> newCharacterList(String s) {
    List<Character> result = new ArrayList<Character>(s.length());
    for (char c : s.toCharArray())
      result.add(c);
    return result;
  }

  @Test
  public final void testFind() {
    Character[] p = new Character[] { 'a', 'b', 'c', 'b', 'a' };
    String test = "xabcaabcbaabax";
    List<Character> s = newCharacterList(test);
    ExactMatcher<Character> matcher = new ExactMatcher<Character>(p);
    assertEquals(test.indexOf("abcba"), matcher.find(s));
    s.set(s.size() / 2, 'x');
    assertEquals(-1, matcher.find(s));
  }

  @Test
  public final void testDoesNotMatchEmptyLists() {
    Character[] p = new Character[] { 'a' };
    List<Character> s = newCharacterList("");
    ExactMatcher<Character> matcher = new ExactMatcher<Character>(p);
    assertEquals(-1, matcher.find(s));
  }

  @Test
  public final void testNullsCanMatchNulls() {
    Character[] p = new Character[] { 'a', null, 'a' };
    List<Character> s = new ArrayList<Character>();
    s.add('a');
    s.add(null);
    s.add('a');
    ExactMatcher<Character> matcher = new ExactMatcher<Character>(p);
    assertEquals(0, matcher.find(s));
  }

  @Test
  public final void testFindWithNulls() {
    Character[] p = new Character[] { 'a', 'b', 'c', 'b', 'a' };
    String test = "xabcaabcbaabax";
    List<Character> s = newCharacterList(test);
    s.set(4, null);
    ExactMatcher<Character> matcher = new ExactMatcher<Character>(p);
    assertEquals(test.indexOf("abcba"), matcher.find(s));
  }

  @Test
  public final void testFindWithOffset() {
    Character[] p = new Character[] { 'a', 'b', 'c', 'b', 'a' };
    String test = "xabcaabcbaabax";
    List<Character> s = newCharacterList(test);
    ExactMatcher<Character> matcher = new ExactMatcher<Character>(p);
    assertEquals(test.indexOf("abcba"), matcher.find(s, 4));
    s.set(s.size() / 2, 'x');
    assertEquals(-1, matcher.find(s, 4));
  }

  @Test
  public final void testScan() {
    Character[] p = new Character[] { 'a', 'b', 'c', 'b', 'a' };
    List<Character> s = newCharacterList("xabcaabcbaabax");
    ExactMatcher<Character> matcher = new ExactMatcher<Character>(p);
    assertTrue(matcher.scan(s.iterator()));
    s.set(s.size() / 2, 'x');
    assertFalse(matcher.scan(s.iterator()));
  }
}
