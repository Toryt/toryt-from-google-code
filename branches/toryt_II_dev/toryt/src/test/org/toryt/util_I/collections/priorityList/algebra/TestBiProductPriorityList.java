package org.toryt.util_I.collections.priorityList.algebra;


import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.priorityList.PriorityList;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestBiProductPriorityList extends AbstractTestComponentPriorityList {

  @Override
  public void setUp() {
    super.setUp();
    HashMap<String, PriorityList<PriorityElementDummy>> factors = new HashMap<String, PriorityList<PriorityElementDummy>>(2);
    factors.put("A", $ahplA);
    factors.put("B", $ahplB);
    $subject = new BiProductPriorityList<String, PriorityElementDummy>(factors);
  }

  protected int elementSum(Map<String, ?> map) {
    int acc = 0;
    for (Object o : map.values()) {
      if (o instanceof PriorityElementDummy) {
        acc += ((PriorityElementDummy)o).getPriority();
      }
      else if (o instanceof Map) {
        acc += elementSum(((Map<String, ?>)o));
      }
    }
    return acc;
  }

  public void testEmpty() {
    assertTrue(! ($subject.isEmpty()));
  }

  public void testBuckets2() {
    @SuppressWarnings("unchecked")
    ListIterator<LockableBigSet<? extends Map<String, PriorityElementDummy>>> iter =
        ((PriorityList<Map<String, PriorityElementDummy>>)$subject).listIterator();
    while (iter.hasNext()) {
      int priority = iter.nextIndex();
      LockableBigSet<? extends Map<String, PriorityElementDummy>> bucket = iter.next();
      Iterator<? extends Map<String, PriorityElementDummy>> bucketIter = bucket.iterator();
      while (bucketIter.hasNext()) {
        int elementSum = elementSum(bucketIter.next());
        assertEquals(priority, elementSum);
      }
    }
  }

  public void testPriorityElementIterator2() {
    int previousPriority = 0;
    @SuppressWarnings("unchecked") Iterator<? extends Map<String, PriorityElementDummy>> iter =
      ((PriorityList<Map<String, PriorityElementDummy>>)$subject).priorityElementIterator();
    while (iter.hasNext()) {
      int elementSum = elementSum(iter.next());
      assertTrue(elementSum >= previousPriority);
      previousPriority = elementSum;
    }
  }

}

