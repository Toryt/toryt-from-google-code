package org.toryt.util_I.collections.priorityList.algebra.biProductPLCurried;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.ArrayHashPriorityList;
import org.toryt.util_I.collections.priorityList.PriorityList;
import org.toryt.util_I.collections.priorityList.algebra.BiProductPriorityList;
import org.toryt.util_I.collections.priorityList.algebra.PriorityElementDummy;


@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestAssoc extends TestCase {

  protected ArrayHashPriorityList<PriorityElementDummy> $ahplA;
  protected ArrayHashPriorityList<PriorityElementDummy> $ahplB;
  protected ArrayHashPriorityList<PriorityElementDummy> $ahplC;
  protected ArrayHashPriorityList<PriorityElementDummy> $ahplD;
  protected PriorityList<Map<String, Object>> $leftCurry;
  protected PriorityList<Map<String, Object>> $rightCurry;

  @Override
  public void setUp() {
    $ahplA = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahplA, "A", 5, 2);
    $ahplB = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahplB, "B", 3, 4);
    $ahplC = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahplC, "C", 1, 2);
    // left curry
    HashMap<String, PriorityList<PriorityElementDummy>> factorsLC_AB = new HashMap<String, PriorityList<PriorityElementDummy>>(2);
    factorsLC_AB.put("A", $ahplA);
    factorsLC_AB.put("B", $ahplB);
    BiProductPriorityList<String, PriorityElementDummy> ab = new BiProductPriorityList<String, PriorityElementDummy>(factorsLC_AB);
    HashMap<String, PriorityList<?>> factorsLC = new HashMap<String, PriorityList<?>>(2);
    factorsLC.put("AB", ab);
    factorsLC.put("C", $ahplC);
    $leftCurry = new BiProductPriorityList<String, Object>(factorsLC);
    // right curry
    HashMap<String, PriorityList<PriorityElementDummy>> factorsRC_BC = new HashMap<String, PriorityList<PriorityElementDummy>>(2);
    factorsRC_BC.put("B", $ahplB);
    factorsRC_BC.put("C", $ahplC);
    BiProductPriorityList<String, PriorityElementDummy> bc = new BiProductPriorityList<String, PriorityElementDummy>(factorsRC_BC);
    HashMap<String, PriorityList<?>> factorsRC = new HashMap<String, PriorityList<?>>(2);
    factorsRC.put("A", $ahplA);
    factorsRC.put("BC", bc);
    $rightCurry = new BiProductPriorityList<String, Object>(factorsRC);
  }

  private void fillPriorityList(ArrayHashPriorityList<PriorityElementDummy> ahpl, String plName, int maxPriority, int maxInBucket) {
    for (int priority = 0; priority <= maxPriority; priority++) {
      for (int inBucket = 1; inBucket <= maxInBucket; inBucket++) {
        ahpl.addPriorityElement(priority, new PriorityElementDummy(plName, priority));
      }
    }
    ahpl.lock();
  }

  @Override
  public void tearDown() {
    $ahplA = null;
    $ahplB = null;
    $ahplC = null;
    $ahplD = null;
    $leftCurry = null;
    $rightCurry = null;
  }

  public void testSize() {
    assertEquals($leftCurry.size(), $rightCurry.size());
  }

  public void testCardinality() {
    assertEquals($leftCurry.getCardinality(), $rightCurry.getCardinality());
  }

  public void testEquals() {
    assertTrue(equalsFlattened($leftCurry, $rightCurry));
  }

  private static boolean equalsFlattened(PriorityList<Map<String, Object>> pl1, PriorityList<Map<String, Object>> pl2) {
    return containsAllFlattened(pl1, pl2) &&
           containsAllFlattened(pl2, pl1);
  }

  private static boolean containsAllFlattened(PriorityList<Map<String, Object>> pl1, final PriorityList<Map<String, Object>> pl2) {
    Iterator<? extends Map<String, ?>> iter = pl1.priorityElementIterator();
    int counter = 0;
    BigInteger card = pl1.getCardinality();
    while (iter.hasNext()) {
      Map<String, ?> e = iter.next();
      if (counter % 100 == 0) {
        System.out.println("checking " + counter + "/" + card + " (" + e + ")");
      }
      if (! containsFlattened(pl2, e)) {
        return false;
      }
      counter++;
    }
    return true;
  }

  private static boolean containsFlattened(PriorityList<Map<String, Object>> pl, Map<String, ?> o) {
    Map<String, Object> oflat = new HashMap<String, Object>();
    flatten(o, oflat);
    Iterator<? extends Map<String, ?>> iter = pl.priorityElementIterator();
    while (iter.hasNext()) {
      Map<String, ?> e = iter.next();
      Map<String, Object> eflat = new HashMap<String, Object>();
      flatten(e, eflat);
      if (oflat.equals(eflat)) {
        return true;
      }
    }
    return false;
  }

  private static void flatten(Map<String, ?> map, Map<String, Object> acc) {
    for (Map.Entry<String, ?> e : map.entrySet()) {
      if (e.getValue() instanceof PriorityElementDummy) {
        acc.put(e.getKey(), e.getValue());
      }
      else if (e.getValue() instanceof Map) {
        flatten((Map<String, ?>)e.getValue(), acc);
      }
    }
  }

}

