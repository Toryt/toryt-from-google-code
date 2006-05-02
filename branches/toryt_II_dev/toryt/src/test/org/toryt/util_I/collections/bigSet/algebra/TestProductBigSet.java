package org.toryt.util_I.collections.bigSet.algebra;

import java.math.BigInteger;
import java.util.Iterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.algebra.ProductBigSet;
import org.toryt.util_I.collections.bigSet.lockable.SetBackedLockableBigSet;

import junit.framework.TestCase;



@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestProductBigSet extends TestCase {

  @Override
  public void setUp() {
    SetBackedLockableBigSet<Integer> component1 = new SetBackedLockableBigSet<Integer>(false);
    fillSet(component1, 10);
    SetBackedLockableBigSet<Integer> component2 = new SetBackedLockableBigSet<Integer>(false);
    fillSet(component2, 100);
    SetBackedLockableBigSet<Integer> component3 = new SetBackedLockableBigSet<Integer>(false);
    fillSet(component3, 1000);
    $subject = new ProductBigSet<Integer[]>(component1, component2, component3);
  }

  @Override
  public void tearDown() {
    $subject = null;
  }

  private void fillSet(SetBackedLockableBigSet<Integer> lbs, int factor) {
    for (int i = 0; i < 3; i++) {
      lbs.add(new Integer((i + 1) * factor));
    }
    lbs.lock();
  }

  private ProductBigSet<Integer[]> $subject;

  public void testEmpty() {
    assertTrue(! ($subject.isEmpty()));
  }

  public void testBigSize() {
    assertEquals(BigInteger.valueOf(27), $subject.getBigSize());
  }

  public void testIterator() {
    Iterator<Integer[]> iter = $subject.iterator();
    while (iter.hasNext()) {
      Object[] e = iter.next();

//RETURNS Object[]

      String s = "[" + e[0] + ", " + e[1] + ", " + e[2] + "]";
      System.out.println(s);
    }
  }

}

