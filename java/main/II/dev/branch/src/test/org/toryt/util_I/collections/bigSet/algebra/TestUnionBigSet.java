package org.toryt.util_I.collections.bigSet.algebra;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.lockable.SetBackedLockableBigSet;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestUnionBigSet {

  @Before
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

  @After
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

  @Test
  public void testEmpty() {
    assertTrue(! ($subject.isEmpty()));
  }

  @Test
  public void testBigSize() {
    assertEquals(BigInteger.valueOf(9), $subject.getBigSize());
  }

  @Test
  public void testIterator() {
    Iterator<Integer> iter = $subject.iterator();
    while (iter.hasNext()) {
      System.out.println(iter.next());
    }
  }

}

