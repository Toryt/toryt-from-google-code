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
public class TestTreeVsList extends TestCase {

  protected ArrayHashPriorityList<PriorityElementDummy> $ahplA;
  protected ArrayHashPriorityList<PriorityElementDummy> $ahplB;
  protected ArrayHashPriorityList<PriorityElementDummy> $ahplC;
  protected ArrayHashPriorityList<PriorityElementDummy> $ahplD;
  protected PriorityList<Map<String, Object>> $leftCurry;
  protected PriorityList<Map<String, Object>> $rightCurry;
  protected PriorityList<Map<String, Object>> $treeCurry;

  @Override
  public void setUp() {
    $ahplA = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahplA, "A", 5, 2);
    $ahplB = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahplB, "B", 3, 4);
    $ahplC = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahplC, "C", 1, 2);
    $ahplD = new ArrayHashPriorityList<PriorityElementDummy>(false);
    fillPriorityList($ahplD, "D", 8, 3);

    // left curry
    HashMap<String, PriorityList<PriorityElementDummy>> factorsLC_AB = new HashMap<String, PriorityList<PriorityElementDummy>>(2);
    factorsLC_AB.put("A", $ahplA);
    factorsLC_AB.put("B", $ahplB);
    BiProductPriorityList<String, PriorityElementDummy> LCab = new BiProductPriorityList<String, PriorityElementDummy>(factorsLC_AB);
    HashMap<String, PriorityList<?>> factorsLC_AB_C = new HashMap<String, PriorityList<?>>(2);
    factorsLC_AB_C.put("AB", LCab);
    factorsLC_AB_C.put("C", $ahplC);
    BiProductPriorityList<String, Object> LCab_c = new BiProductPriorityList<String, Object>(factorsLC_AB_C);
    HashMap<String, PriorityList<?>> factorsLC_AB_C_D = new HashMap<String, PriorityList<?>>(2);
    factorsLC_AB_C_D.put("ABC", LCab_c);
    factorsLC_AB_C_D.put("D", $ahplD);
    $leftCurry = new BiProductPriorityList<String, Object>(factorsLC_AB_C_D);

    // left curry
    HashMap<String, PriorityList<PriorityElementDummy>> factorsRC_CD = new HashMap<String, PriorityList<PriorityElementDummy>>(2);
    factorsRC_CD.put("C", $ahplC);
    factorsRC_CD.put("D", $ahplD);
    BiProductPriorityList<String, PriorityElementDummy> RCcd = new BiProductPriorityList<String, PriorityElementDummy>(factorsRC_CD);
    HashMap<String, PriorityList<?>> factorsRC_CD_B = new HashMap<String, PriorityList<?>>(2);
    factorsRC_CD_B.put("CD", RCcd);
    factorsRC_CD_B.put("B", $ahplB);
    BiProductPriorityList<String, Object> RCcd_b = new BiProductPriorityList<String, Object>(factorsRC_CD_B);
    HashMap<String, PriorityList<?>> factorsRC_CD_B_A = new HashMap<String, PriorityList<?>>(2);
    factorsRC_CD_B_A.put("CDB", RCcd_b);
    factorsRC_CD_B_A.put("A", $ahplA);
    $rightCurry = new BiProductPriorityList<String, Object>(factorsRC_CD_B_A);

    // left curry
    HashMap<String, PriorityList<?>> factorsTC_AB_CD = new HashMap<String, PriorityList<?>>(2);
    factorsTC_AB_CD.put("AB", LCab);
    factorsTC_AB_CD.put("CD", RCcd);
    $treeCurry = new BiProductPriorityList<String, Object>(factorsTC_AB_CD);
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
    $treeCurry = null;
  }

  public void testSize() {
    assertEquals($leftCurry.size(), $rightCurry.size());
    assertEquals($treeCurry.size(), $rightCurry.size());
  }

  public void testCardinality() {
    assertEquals($leftCurry.getCardinality(), $rightCurry.getCardinality());
    assertEquals($treeCurry.getCardinality(), $rightCurry.getCardinality());
  }

  public void testSpeedLC() {
    speedTest("left curry", $leftCurry, true);
  }

  public void testSpeedRC() {
    speedTest("right curry", $rightCurry, true);
  }

  public void testSpeedTC() {
    speedTest("tree curry", $treeCurry, true);
  }

  private long speedTest(String name, PriorityList<?> pl, boolean print) {
    long start = System.nanoTime();
    int counter = 0;
    BigInteger card = pl.getCardinality();
    Iterator<?> iter = pl.priorityElementIterator();
    while (iter.hasNext()) {
      Object something = iter.next();
      if (something == null) {
        System.out.println("won't do anything really, but will force vm, compilers and processors to really create the element.");
      }
      if (print && (counter % 5000 == 0)) {
        System.out.print(counter + "/" + card + " -- ");
      }
      counter++;
    }
    if (print) {
      System.out.println();
    }
    long end = System.nanoTime();
    long nanos = end - start;
    if (print) {
      System.out.println(name + " done: " + (nanos / 10E6) + " nano's");
    }
    return nanos;
  }

  public void testStatistics() {
    long lc = 0;
    long rc = 0;
    long tc = 0;
    for (int i = 0; i < 100; i++) {
      rc += speedTest("right curry", $rightCurry, false);
      lc += speedTest("left curry", $leftCurry, false);
      tc += speedTest("tree curry", $treeCurry, false);
      System.out.print(i + ".");
      if (i % 10 == 0) {
        System.out.println();
      }
    }
    System.out.println();
    System.out.println("left curry: " + ((lc / 100) / 10E6));
    System.out.println("right curry: " + ((rc / 100) / 10E6));
    System.out.println("tree curry: " + ((tc / 100) / 10E6));
  }

//ok, but takes a while
//  public void testEquals() {
//    assertTrue(equalsFlattened($leftCurry, $rightCurry));
//    assertTrue(equalsFlattened($leftCurry, $treeCurry));
//  }

 public static boolean equalsFlattened(PriorityList<Map<String, Object>> pl1, PriorityList<Map<String, Object>> pl2) {
    return containsAllFlattened(pl1, pl2) &&
           containsAllFlattened(pl2, pl1);
  }

  private static boolean containsAllFlattened(PriorityList<Map<String, Object>> pl1, final PriorityList<Map<String, Object>> pl2) {
    Iterator<? extends Map<String, ?>> iter = pl1.priorityElementIterator();
    int counter = 0;
    BigInteger card = pl1.getCardinality();
    while (iter.hasNext()) {
      Map<String, ?> e = iter.next();
      if (counter % 500 == 0) {
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

