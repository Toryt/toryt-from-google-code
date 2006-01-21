package org.toryt.util_I.collections.bigSet;

import java.math.BigInteger;
import java.util.Iterator;

import org.toryt.util_I.collections.bigSet.algebra.ProductBigSet;
import org.toryt.util_I.collections.bigSet.lockable.SetBackedLockableBigSet;

import junit.framework.TestCase;



public class TestProductBigSet extends TestCase {

  public void setUp() {
    SetBackedLockableBigSet[] components = new SetBackedLockableBigSet[3];
    for (int i = 0; i < 3; i++) {
      components[i] = new SetBackedLockableBigSet(Integer.class);
      fillSet(components[i], (int)Math.pow(10, i));
    }
    $subject = new ProductBigSet(Integer[].class, components);
  }

  public void tearDown() {
    $subject = null;
  }

  private void fillSet(SetBackedLockableBigSet lbs, int factor) {
    for (int i = 0; i < 3; i++) {
      lbs.add(new Integer((i + 1) * factor));
    }
    lbs.lock();
  }

  private ProductBigSet $subject;

  public void testEmpty() {
    assertTrue(! ($subject.isEmpty()));
  }

  public void testBigSize() {
    assertEquals(BigInteger.valueOf(27), $subject.getBigSize());
  }

  public void testIterator() {
    Iterator iter = $subject.iterator();
    while (iter.hasNext()) {
      Integer[] e = (Integer[])iter.next();
      String s = "[" + e[0] + ", " + e[1] + ", " + e[2] + "]";
      System.out.println(s);
    }
  }

}

