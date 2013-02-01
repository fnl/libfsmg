package es.fnl.fsm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestExactScanner extends TestExactMatcherBase {
  ExactScanner<Character> scanner;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    scanner = new ExactScanner<Character>(Arrays.asList(pattern));
    base = scanner;
  }

  @Test(expected = IllegalArgumentException.class)
  public final void testMatcherSetupEmptyList() {
    new ExactScanner<Object>(new ArrayList<Object>());
  }

  @Test
  public final void testScan() {
    List<Character> s = newCharacterList(test);
    assertTrue(scanner.scan(s.iterator()));
    s.set(s.size() / 2, 'x');
    assertFalse(scanner.scan(s.iterator()));
  }
  
  @Test
  public final void testScanDoesNotMatchEmptyLists() {
    List<Character> s = newCharacterList("");
    assertFalse(scanner.scan(s.iterator()));
  }

  @Test
  public final void testScanNullsCanMatchNulls() {
    Character[] p = new Character[] { 'a', null, 'a' };
    List<Character> s = new ArrayList<Character>();
    s.add('a');
    s.add(null);
    s.add('a');
    scanner = new ExactScanner<Character>(Arrays.asList(p));
    assertTrue(scanner.scan(s.iterator()));
  }

  @Test
  public final void testScanWithNulls() {
    List<Character> s = newCharacterList(test);
    s.set(4, null);
    assertTrue(scanner.scan(s.iterator()));
  }
}
