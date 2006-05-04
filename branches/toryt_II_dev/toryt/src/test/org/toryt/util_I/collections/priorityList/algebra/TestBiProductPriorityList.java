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

  private int elementSum(Map<String, PriorityElementDummy> map) {
    int acc = 0;
    for (PriorityElementDummy ped : map.values()) {
      acc += ped.getPriority();
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

//  public void testToString3_1() {
//    ArrayHashPriorityList[] components = buildComponents(3);
//    BiArrayProductPriorityList subject = new BiArrayProductPriorityList(components[0], new BiArrayProductPriorityList(components[1], components[2]));
//    for (int i = 0; i < components.length; i++) {
//      System.out.println(components[i]);
//    }
//    System.out.println(subject);
//  }
//
//  public void testToString3_2() {
//    ArrayHashPriorityList[] components = buildComponents(3);
//    BiArrayProductPriorityList subject = new BiArrayProductPriorityList(new BiArrayProductPriorityList(components[0], components[1]), components[2]);
//    for (int i = 0; i < components.length; i++) {
//      System.out.println(components[i]);
//    }
//    System.out.println(subject);
//  }
//
//  public void testBuckets3_1() {
//    ArrayHashPriorityList[] components = buildComponents(3);
//    BiArrayProductPriorityList subject = new BiArrayProductPriorityList(components[0], new BiArrayProductPriorityList(components[1], components[2]));
//    ListIterator iter = subject.listIterator();
//    while (iter.hasNext()) {
//      int priority = iter.nextIndex();
//      LockableBigSet bucket = (LockableBigSet)iter.next();
//      Iterator bucketIter = bucket.iterator();
//      while (bucketIter.hasNext()) {
//        int elementSum = elementSum((Object[])bucketIter.next());
//        assertEquals(priority, elementSum);
//      }
//    }
//  }
//
//  public void testBuckets3_2() {
//    ArrayHashPriorityList[] components = buildComponents(3);
//    BiArrayProductPriorityList subject = new BiArrayProductPriorityList(new BiArrayProductPriorityList(components[0], components[1]), components[2]);
//    ListIterator iter = subject.listIterator();
//    while (iter.hasNext()) {
//      int priority = iter.nextIndex();
//      LockableBigSet bucket = (LockableBigSet)iter.next();
//      Iterator bucketIter = bucket.iterator();
//      while (bucketIter.hasNext()) {
//        int elementSum = elementSum((Object[])bucketIter.next());
//        assertEquals(priority, elementSum);
//      }
//    }
//  }
//
//  public void testPriorityElementIterator3_1() {
//    ArrayHashPriorityList[] components = buildComponents(3);
//    BiArrayProductPriorityList subject = new BiArrayProductPriorityList(components[0], new BiArrayProductPriorityList(components[1], components[2]));
//    int previousPriority = 0;
//    Iterator iter = subject.priorityElementIterator();
//    while (iter.hasNext()) {
//      int elementSum = elementSum((Object[])iter.next());
//      assertTrue(elementSum >= previousPriority);
//      previousPriority = elementSum;
//    }
//  }
//
//  public void testPriorityElementIterator3_2() {
//    ArrayHashPriorityList[] components = buildComponents(3);
//    BiArrayProductPriorityList subject = new BiArrayProductPriorityList(new BiArrayProductPriorityList(components[0], components[1]), components[2]);
//    int previousPriority = 0;
//    Iterator iter = subject.priorityElementIterator();
//    while (iter.hasNext()) {
//      int elementSum = elementSum((Object[])iter.next());
//      assertTrue(elementSum >= previousPriority);
//      previousPriority = elementSum;
//    }
//  }

//  public void testAssociativity2() {
//    helperTestAssociativity(2);
//  }
//
//  public void testAssociativity3() {
//    helperTestAssociativity(3);
//  }
//
//  public void testAssociativity4() {
//    helperTestAssociativity(4);
//  }
//
//  private void helperTestAssociativity(int size) {
//    assert size >= 2;
//    ArrayHashPriorityList[] components = buildComponents(size);
//    BiArrayProductPriorityList subject1 = buildRightCurry(components);
//    BiArrayProductPriorityList subject2 = buildLeftCurry(components);
//System.out.println("Size: " + subject1.size());
//System.out.println("Cardinality: " + subject1.getCardinality());
//    assertTrue(equalsFlattened(subject1, subject2));
//  }
//
//  private static BiArrayProductPriorityList buildRightCurry(PriorityList[] components) {
//    assert components.length >= 2;
//    PriorityList acc = components[components.length - 1];
//    for (int i = components.length - 2; i >= 0; i--) {
//      acc = new BiArrayProductPriorityList(components[i], acc);
//    }
//    return (BiArrayProductPriorityList)acc;
//  }
//
//  private static BiArrayProductPriorityList buildLeftCurry(PriorityList[] components) {
//    assert components.length >= 2;
//    PriorityList acc = components[0];
//    for (int i = 1; i < components.length; i++) {
//      acc = new BiArrayProductPriorityList(acc, components[i]);
//    }
//    return (BiArrayProductPriorityList)acc;
//  }
//
//  private static boolean equalsFlattened(PriorityList pl1, PriorityList pl2) {
//    assert pl1 != null;
//    assert pl2 != null;
//    return (pl1.size() == pl2.size()) &&
//           containsAllFlattened(pl1, pl2) &&
//           containsAllFlattened(pl2, pl1);
//  }
//
//  private static boolean containsAllFlattened(PriorityList pl1, final PriorityList pl2) {
//    assert pl1 != null;
//    assert pl2 != null;
//    return (pl1.size() <= pl2.size()) &&
//            Collections.forAll(pl1, new Assertion() {
//
//              private int index = -1;
//
//              public boolean isTrueFor(Object o) {
//                LockableBigSet bucket = flatten((LockableBigSet)o);
//                index++;
//                final LockableBigSet b2 = flatten((LockableBigSet)pl2.get(index));
//System.out.println(index + "(" + bucket.getBigSize() + "): Comparing " + bucket + " to " + b2);
//                return ((o == null) || bucket.isEmpty()) ?
//                         ((b2 == null) || b2.isEmpty()) :
//                         ((bucket.getBigSize().compareTo(b2.getBigSize()) <= 0) &&
//                          Collections.forAll(bucket, new Assertion() {
//
//                            public boolean isTrueFor(Object o1) {
//                              assert o1 != null;
//                              final Object[] flattened = (Object[])o1;
////System.out.println("    Looking for " + Arrays.asList(flattened).toString());
//                              return Collections.exists(b2, new Assertion() {
//
//                                       public boolean isTrueFor(Object o2) {
//                                         assert o2 != null;
//                                         Object[] f2 = (Object[])o2;
//                                         return org.apache.commons.lang.ArrayUtils.isEquals(flattened, f2);
//                                       }
//
//                              });
//                            }
//
//                          }));
//              }
//
//            });
//  }
//
//  private static LockableBigSet flatten(LockableBigSet lbs) {
//    SetBackedLockableBigSet result = new SetBackedLockableBigSet(Object[].class, false);
//    Iterator iter = lbs.iterator();
//    while (iter.hasNext()) {
//      Object[] e = (Object[])iter.next();
//      result.add(ArrayUtils.flatten(e));
//    }
//    result.lock();
//    return result;
//  }

}

