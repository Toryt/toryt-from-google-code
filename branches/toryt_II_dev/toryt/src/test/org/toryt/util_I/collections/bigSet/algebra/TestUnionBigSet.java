package org.toryt.util_I.collections.bigSet.algebra;


import java.math.BigInteger;
import java.util.Iterator;

import junit.framework.TestCase;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.lockable.SetBackedLockableBigSet;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestUnionBigSet extends TestCase {

  @Override
  @SuppressWarnings("unchecked")
  public void setUp() {
    SetBackedLockableBigSet<Integer> component1 = new SetBackedLockableBigSet<Integer>(false);
    fillSet(component1, 10);
    SetBackedLockableBigSet<Integer> component2 = new SetBackedLockableBigSet<Integer>(false);
    fillSet(component2, 100);
    SetBackedLockableBigSet<Integer> component3 = new SetBackedLockableBigSet<Integer>(false);
    fillSet(component3, 1000);
    $subject = new UnionBigSet<Integer>(false, component1, component2, component3);
  }

  @Override
  public void tearDown() {
    $subject = null;
  }

  private void fillSet(SetBackedLockableBigSet<Integer> lbs, int factor) {
    for (int i = 0; i < 3; i++) {
      lbs.add(Integer.valueOf((i + 1) * factor));
    }
    lbs.lock();
  }

  private UnionBigSet<Integer> $subject;

  public void testEmpty() {
    assertTrue(! ($subject.isEmpty()));
  }

  public void testBigSize() {
    assertEquals(BigInteger.valueOf(9), $subject.getBigSize());
  }

  public void testIterator() {
    Iterator<Integer> iter = $subject.iterator();
    while (iter.hasNext()) {
      System.out.println(iter.next());
    }
  }

}

