package org.toryt.util_I.collections.bigSet;

import java.math.BigInteger;
import java.util.Iterator;

import junit.framework.TestCase;



public class TestUnionBigSet extends TestCase {

  public void setUp() {
    SetBackedLockableBigSet[] components = new SetBackedLockableBigSet[3];
    for (int i = 0; i < 3; i++) {
      components[i] = new SetBackedLockableBigSet(Integer.class);
      fillSet(components[i], (int)Math.pow(10, i));
    }
    $subject = new UnionBigSet(Integer.class, components);
  }

  public void tearDown() {
    $subject = null;
  }

  private void fillSet(SetBackedLockableBigSet lbs, int factor) {
    for (int i = 0; i < 3; i++) {
      lbs.add(new Integer(i * factor));
    }
    lbs.lock();
  }

  private UnionBigSet $subject;

  public void testEmpty() {
    assertTrue(! ($subject.isEmpty()));
  }

  public void testBigSize() {
    assertEquals(BigInteger.valueOf(9), $subject.getBigSize());
  }

  public void testIterator() {
    Iterator iter = $subject.iterator();
    while (iter.hasNext()) {
      System.out.println(iter.next());
    }
  }

}

