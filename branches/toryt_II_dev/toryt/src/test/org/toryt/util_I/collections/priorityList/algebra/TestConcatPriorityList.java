package org.toryt.util_I.collections.priorityList.algebra;


import junit.framework.TestCase;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.ArrayHashPriorityList;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestConcatPriorityList extends TestCase {

  private ArrayHashPriorityList<PriorityElementDummy> $ahpl1;
  private ArrayHashPriorityList<PriorityElementDummy> $ahpl2;
  private ArrayHashPriorityList<PriorityElementDummy> $ahpl3;
  private ArrayHashPriorityList<PriorityElementDummy> $ahpl4;
  private ConcatPriorityList<PriorityElementDummy> $subject;

  @Override
  public void setUp() {
    $ahpl1 = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahpl1, 1, 5, 2);
    $ahpl2 = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahpl2, 2, 3, 4);
    $ahpl3 = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahpl3, 3, 1, 2);
    $ahpl4 = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahpl4, 4, 3, 3);
    $subject = new ConcatPriorityList<PriorityElementDummy>($ahpl1, $ahpl2, $ahpl3, $ahpl4);
  }

  private void fillPriorityList(ArrayHashPriorityList<PriorityElementDummy> ahpl, int priorityListNumber, int maxPriority, int maxInBucket) {
    for (int priority = 0; priority <= maxPriority; priority++) {
      for (int inBucket = 1; inBucket <= maxInBucket; inBucket++) {
        ahpl.addPriorityElement(priority, new PriorityElementDummy(priorityListNumber, priority, inBucket));
      }
    }
    ahpl.lock();
  }

  @Override
  public void tearDown() {
    $ahpl1 = null;
    $ahpl2 = null;
    $ahpl3 = null;
    $ahpl4 = null;
    $subject = null;
  }

  public void testToString() {
    System.out.println($ahpl1.toString());
    System.out.println();
    System.out.println($ahpl2.toString());
    System.out.println();
    System.out.println($ahpl3.toString());
    System.out.println();
    System.out.println($ahpl4.toString());
    System.out.println();
    System.out.println($subject.toString());
  }

}

