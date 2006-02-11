package org.toryt.util_I.collections.priorityList.algebra;


import java.util.Iterator;
import java.util.ListIterator;

import junit.framework.TestCase;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.collections.ArrayUtils;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.priorityList.ArrayHashPriorityList;
import org.toryt.util_I.collections.priorityList.PriorityList;


public class TestBiProductPriorityList extends TestCase {

  private ArrayHashPriorityList[] buildComponents(int nr) {
    ArrayHashPriorityList[] components = new ArrayHashPriorityList[nr];
    for (int i = 0; i < nr; i++) {
      components[i] = new ArrayHashPriorityList(Integer.class);
      fillSet(components[i], 12 * (i + 1));
    }
    return components;
  }

  private void fillSet(ArrayHashPriorityList ahpl, int size) {
    for (int i = 0; i < size; i++) {
      ahpl.addPriorityElement(i, new Integer(i));
    }
    ahpl.lock();
  }

  private int elementSum(Object[] integers) {
    int acc = 0;
    for (int i = 0; i < integers.length; i++) {
      int iValue = (integers[i] instanceof Integer) ?
                     ((Integer)integers[i]).intValue() :
                     elementSum((Object[])integers[i]);
      acc += iValue;
    }
    return acc;
  }

  public void testEmpty() {
    ArrayHashPriorityList[] components = buildComponents(2);
    BiProductPriorityList subject = new BiProductPriorityList(components[0], components[1]);
    assertTrue(! (subject.isEmpty()));
  }

  public void testToString2() {
    ArrayHashPriorityList[] components = buildComponents(2);
    BiProductPriorityList subject = new BiProductPriorityList(components[0], components[1]);
    for (int i = 0; i < components.length; i++) {
      System.out.println(components[i]);
    }
    System.out.println(subject);
  }

  public void testBuckets2() {
    ArrayHashPriorityList[] components = buildComponents(2);
    BiProductPriorityList subject = new BiProductPriorityList(components[0], components[1]);
    ListIterator iter = subject.listIterator();
    while (iter.hasNext()) {
      int priority = iter.nextIndex();
      LockableBigSet bucket = (LockableBigSet)iter.next();
      Iterator bucketIter = bucket.iterator();
      while (bucketIter.hasNext()) {
        int elementSum = elementSum((Object[])bucketIter.next());
        assertEquals(priority, elementSum);
      }
    }
  }

  public void testPriorityElementIterator2() {
    ArrayHashPriorityList[] components = buildComponents(2);
    BiProductPriorityList subject = new BiProductPriorityList(components[0], components[1]);
    int previousPriority = 0;
    Iterator iter = subject.priorityElementIterator();
    while (iter.hasNext()) {
      int elementSum = elementSum((Object[])iter.next());
      assertTrue(elementSum >= previousPriority);
      previousPriority = elementSum;
    }
  }

  public void testToString3_1() {
    ArrayHashPriorityList[] components = buildComponents(3);
    BiProductPriorityList subject = new BiProductPriorityList(components[0], new BiProductPriorityList(components[1], components[2]));
    for (int i = 0; i < components.length; i++) {
      System.out.println(components[i]);
    }
    System.out.println(subject);
  }

  public void testToString3_2() {
    ArrayHashPriorityList[] components = buildComponents(3);
    BiProductPriorityList subject = new BiProductPriorityList(new BiProductPriorityList(components[0], components[1]), components[2]);
    for (int i = 0; i < components.length; i++) {
      System.out.println(components[i]);
    }
    System.out.println(subject);
  }

  public void testBuckets3_1() {
    ArrayHashPriorityList[] components = buildComponents(3);
    BiProductPriorityList subject = new BiProductPriorityList(components[0], new BiProductPriorityList(components[1], components[2]));
    ListIterator iter = subject.listIterator();
    while (iter.hasNext()) {
      int priority = iter.nextIndex();
      LockableBigSet bucket = (LockableBigSet)iter.next();
      Iterator bucketIter = bucket.iterator();
      while (bucketIter.hasNext()) {
        int elementSum = elementSum((Object[])bucketIter.next());
        assertEquals(priority, elementSum);
      }
    }
  }

  public void testBuckets3_2() {
    ArrayHashPriorityList[] components = buildComponents(3);
    BiProductPriorityList subject = new BiProductPriorityList(new BiProductPriorityList(components[0], components[1]), components[2]);
    ListIterator iter = subject.listIterator();
    while (iter.hasNext()) {
      int priority = iter.nextIndex();
      LockableBigSet bucket = (LockableBigSet)iter.next();
      Iterator bucketIter = bucket.iterator();
      while (bucketIter.hasNext()) {
        int elementSum = elementSum((Object[])bucketIter.next());
        assertEquals(priority, elementSum);
      }
    }
  }

  public void testPriorityElementIterator3_1() {
    ArrayHashPriorityList[] components = buildComponents(3);
    BiProductPriorityList subject = new BiProductPriorityList(components[0], new BiProductPriorityList(components[1], components[2]));
    int previousPriority = 0;
    Iterator iter = subject.priorityElementIterator();
    while (iter.hasNext()) {
      int elementSum = elementSum((Object[])iter.next());
      assertTrue(elementSum >= previousPriority);
      previousPriority = elementSum;
    }
  }

  public void testPriorityElementIterator3_2() {
    ArrayHashPriorityList[] components = buildComponents(3);
    BiProductPriorityList subject = new BiProductPriorityList(new BiProductPriorityList(components[0], components[1]), components[2]);
    int previousPriority = 0;
    Iterator iter = subject.priorityElementIterator();
    while (iter.hasNext()) {
      int elementSum = elementSum((Object[])iter.next());
      assertTrue(elementSum >= previousPriority);
      previousPriority = elementSum;
    }
  }

  public void testAssociativity() {
    ArrayHashPriorityList[] components = buildComponents(3);
    BiProductPriorityList subject1 = new BiProductPriorityList(components[0], new BiProductPriorityList(components[1], components[2]));
    BiProductPriorityList subject2 = new BiProductPriorityList(new BiProductPriorityList(components[0], components[1]), components[2]);
System.out.println("Size: " + subject1.size());
System.out.println("Cardinality: " + subject1.getCardinality());
    assertTrue(equalsFlattened(subject1, subject2));
  }

  private static boolean equalsFlattened(PriorityList pl1, PriorityList pl2) {
    assert pl1 != null;
    assert pl2 != null;
    return (pl1.size() == pl2.size()) &&
           containsAllFlattened(pl1, pl2) &&
           containsAllFlattened(pl2, pl1);
  }

  private static boolean containsAllFlattened(PriorityList pl1, final PriorityList pl2) {
    assert pl1 != null;
    assert pl2 != null;
    return (pl1.size() <= pl2.size()) &&
            Collections.forAll(pl1, new Assertion() {

              private int index = -1;

              public boolean isTrueFor(Object o) {
                LockableBigSet bucket = (LockableBigSet)o;
                index++;
                final LockableBigSet b2 = (LockableBigSet)pl2.get(index);
System.out.println(index + "(" + bucket.getBigSize() + "): Comparing " + bucket + " to " + b2);
                return ((o == null) || bucket.isEmpty()) ?
                         ((b2 == null) || b2.isEmpty()) :
                         ((bucket.getBigSize().compareTo(b2.getBigSize()) <= 0) &&
                          Collections.forAll(bucket, new Assertion() {

                            public boolean isTrueFor(Object o1) {
                              assert o1 != null;
                              final Object[] flattened = ArrayUtils.flatten((Object[])o1);
//System.out.println("    Looking for " + Arrays.asList(flattened).toString());
                              return Collections.exists(b2, new Assertion() {

                                       public boolean isTrueFor(Object o2) {
                                         assert o2 != null;
                                         Object[] f2 = ArrayUtils.flatten((Object[])o2);
                                         return org.apache.commons.lang.ArrayUtils.isEquals(flattened, f2);
                                       }

                              });
                            }

                          }));
              }

            });
  }

}

