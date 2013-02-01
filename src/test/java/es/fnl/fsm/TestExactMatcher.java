package es.fnl.fsm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestExactMatcher extends TestMatcherBase {
  ExactMatcher<Character> matcher;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    matcher = new ExactMatcher<Character>(Arrays.asList(pattern));
    base = matcher;
  }

  @Test(expected = IllegalArgumentException.class)
  public final void testMatcherSetupEmptyList() {
    new ExactMatcher<Object>(new ArrayList<Object>());
  }

  @Test
  public final void testFind() {
    List<Character> s = newCharacterList(test);
    assertEquals(test.indexOf("abcba"), matcher.find(s));
    s.set(s.size() / 2, 'x');
    assertEquals(-1, matcher.find(s));
  }

  @Test
  public final void testFindDoesNotMatchEmptyLists() {
    List<Character> s = newCharacterList("");
    assertEquals(-1, matcher.find(s));
  }

  @Test
  public final void testFindNullsCanMatchNulls() {
    Character[] p = new Character[] { 'a', null, 'a' };
    List<Character> s = new ArrayList<Character>();
    s.add('a');
    s.add(null);
    s.add('a');
    matcher = new ExactMatcher<Character>(Arrays.asList(p));
    assertEquals(0, matcher.find(s));
  }

  @Test
  public final void testFindWithNulls() {
    List<Character> s = newCharacterList(test);
    s.set(4, null);
    assertEquals(test.indexOf("abcba"), matcher.find(s));
  }

  @Test
  public final void testFindWithOffset() {
    List<Character> s = newCharacterList(test);
    assertEquals(test.indexOf("abcba"), matcher.find(s, 4));
    s.set(s.size() / 2, 'x');
    assertEquals(-1, matcher.find(s, 4));
  }
}
