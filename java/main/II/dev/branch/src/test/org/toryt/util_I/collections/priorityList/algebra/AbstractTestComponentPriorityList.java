package org.toryt.util_I.collections.priorityList.algebra;


import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.ListIterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.ArrayHashPriorityList;
import org.toryt.util_I.collections.priorityList.PriorityList;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractTestComponentPriorityList {

  protected ArrayHashPriorityList<PriorityElementDummy> $ahplA;
  protected ArrayHashPriorityList<PriorityElementDummy> $ahplB;
  protected ArrayHashPriorityList<PriorityElementDummy> $ahplC;
  protected ArrayHashPriorityList<PriorityElementDummy> $ahplD;
  protected PriorityList<?> $subject;

  @Before
  public void setUp() {
    $ahplA = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahplA, "A", 5, 2);
    $ahplB = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahplB, "B", 3, 4);
    $ahplC = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahplC, "C", 1, 2);
    $ahplD = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahplD, "D", 3, 3);
  }

  private void fillPriorityList(ArrayHashPriorityList<PriorityElementDummy> ahpl, String plName, int maxPriority, int maxInBucket) {
    for (int priority = 0; priority <= maxPriority; priority++) {
      for (int inBucket = 1; inBucket <= maxInBucket; inBucket++) {
        ahpl.addPriorityElement(priority, new PriorityElementDummy(plName, priority));
      }
    }
    ahpl.lock();
  }

  @After
  public void tearDown() {
    $ahplA = null;
    $ahplB = null;
    $ahplC = null;
    $ahplD = null;
    $subject = null;
  }

  @Test
  public void testToString() {
//    System.out.println($ahpl1.toString());
//    System.out.println();
//    System.out.println($ahpl2.toString());
//    System.out.println();
//    System.out.println($ahpl3.toString());
//    System.out.println();
//    System.out.println($ahpl4.toString());
//    System.out.println();
    System.out.println($subject.toString());
    System.out.println();
  }

  @Test
  public void testIteration() {
    int counter = 0;
    ListIterator<?> iter = $subject.listIterator();
    while (iter.hasNext()) {
      assertEquals(counter, iter.nextIndex());
      assertEquals($subject.get(counter), iter.next());
      assertEquals(counter, iter.previousIndex());
      counter++;
    }
    assertEquals($subject.size(), counter);
  }

  @Test
  public void testPriorityElementIteration() {
    int counter = 0;
    Iterator<?> iter = $subject.priorityElementIterator();
    while (iter.hasNext()) {
      System.out.println(iter.next());
      counter++;
    }
    assertEquals($subject.getCardinality(), BigInteger.valueOf(counter));
  }

}

