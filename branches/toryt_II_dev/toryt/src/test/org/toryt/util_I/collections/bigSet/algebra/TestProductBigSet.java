package org.toryt.util_I.collections.bigSet.algebra;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.lockable.SetBackedLockableBigSet;



@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestProductBigSet {

  @Before
  public void setUp() {
    SetBackedLockableBigSet<Integer> factor1 = new SetBackedLockableBigSet<Integer>(false);
    fillSet(factor1, 10);
    SetBackedLockableBigSet<Integer> factor2 = new SetBackedLockableBigSet<Integer>(false);
    fillSet(factor2, 100);
    SetBackedLockableBigSet<Integer> factor3 = new SetBackedLockableBigSet<Integer>(false);
    fillSet(factor3, 1000);
    HashMap<String, SetBackedLockableBigSet<Integer>> factors = new HashMap<String, SetBackedLockableBigSet<Integer>>(3);
    factors.put("A", factor1);
    factors.put("B", factor2);
    factors.put("C", factor3);
    $subject = new ProductBigSet<String, Number>(factors);
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

  private ProductBigSet<String, Number> $subject;

  @Test
  public void testEmpty() {
    assertTrue(! ($subject.isEmpty()));
  }

  @Test
  public void testBigSize() {
    assertEquals(BigInteger.valueOf(27), $subject.getBigSize());
  }

  @Test
  public void testIterator() {
    Iterator<Map<String, Number>> iter = $subject.iterator();
    while (iter.hasNext()) {
      System.out.println(iter.next());
    }
  }

}

