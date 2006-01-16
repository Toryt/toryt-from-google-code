package org.toryt.util_I.priorityList;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.collections.AbstractLockedList;
import org.toryt.util_I.collections.bigSet.LockableBigSet;


/**
 * <p>Implementation of some methods for a locked {@link PriorityList}.</p>
 *
 * @author Jan Dockx
 */
public abstract class AbstractLockedPriorityList extends AbstractLockedList
    implements PriorityList {

  /* <section name="Meta Information"> */
  //------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /* </section> */



  /**
   * @pre priorityPriorityElementType != null;
   * @pre cardinality != null;
   * @pre cardinality >= 0;
   */
  protected AbstractLockedPriorityList(Class priorityPriorityElementType, BigInteger cardinality) {
    assert priorityPriorityElementType != null;
    assert cardinality != null;
    assert cardinality.compareTo(BigInteger.ZERO) >= 0;
    $priorityPriorityElementType = priorityPriorityElementType;
    $cardinality = cardinality;
  }



  /* <property name="element type"> */
  //------------------------------------------------------------------

  public final Class getElementType() {
    return LockableBigSet.class;
  }

  /*</property>*/



  /* <property name="priority element type"> */
  //------------------------------------------------------------------

  public final Class getPriorityElementType() {
    return $priorityPriorityElementType;
  }

  /**
   * @invar $priorityPriorityElementType != null;
   */
  private Class $priorityPriorityElementType;

  /*</property>*/



  /* <property name="size"> */
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final BigInteger getCardinality() {
    return $cardinality;
  }

  /**
   * @invar $cardinality != null;
   */
  private BigInteger $cardinality;

  /*</property>*/


  public final boolean isNullAllowed() {
    return false;
  }

  public boolean equals(Object o) {
    return (o != null) &&
           (o instanceof PriorityList) &&
           containsAll((PriorityList)o) &&
           ((PriorityList)o).containsAll(this);
  }

  /* this implementation is too expensive
  public boolean contains(final Object o) {
    Iterator iter = iterator();
    while (iter.hasNext()) {
      if (iter.next().equals(o)) {
        return true;
      }
    }
    return false;
  }
  */

  public final boolean containsAll(Collection c) {
    return Collections.forAll(c,
                              new Assertion() {
                                    public boolean isTrueFor(Object o) {
                                      return contains(o);
                                    }
                                  });
  }

  public final Iterator iterator() {
    return listIterator();
  }

  public final ListIterator listIterator() {
    return listIterator(0);
  }

  /**
   * @deprecated
   */
  public final Object[] toArray() {
    return toArray(new Object[size()]);
  }

  /**
   * @deprecated
   */
  public final Object[] toArray(Object[] a) {
    int size = size();
    Object[] result;
    if (a.length >= size) {
      result = a;
      result[size] = null;
    }
    else {
      result = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
    }
    Iterator iter = iterator();
    int i = 0;
    while (iter.hasNext()) {
      result[i] = iter.next();
      i++;
    }
    return result;
  }

//  public final boolean containsPriorityElement(final Object o) {
//    return Collections.exists(this,
//                       new Assertion() {
//                              public boolean isTrueFor(Object pb) {
//                                LockableBigSet lbs = (LockableBigSet)pb;
//                                return lbs.contains(o);
//                              }
//                            });
//  }

//  public final Iterator priorityElementIterator() {
//    return new AbstractLockedCollectionIterator() {
//
//                  /**
//                   * @invar $listIter != null;
//                   */
//                  private Iterator $listIter = iterator();
//
//                  private Iterator $bucketIter;
//
//                  {
//                    prepareNext();
//                  }
//
//                  private void prepareNext() {
//                    assert ($bucketIter == null) || (! $bucketIter.hasNext());
//                    $bucketIter = null;
//                    assert $bucketIter == null;
//                    while (($bucketIter == null) && ($listIter.hasNext())) {
//                      LockableBigSet nextBucket = (LockableBigSet)$listIter.next();
//                      if (! nextBucket.isEmpty()) {
//                        $bucketIter = nextBucket.iterator();
//                      }
//                    }
//                    assert ($bucketIter == null) ? (! $listIter.hasNext()) : $bucketIter.hasNext();
//                  }
//
//                  public boolean hasNext() {
//                    return $bucketIter != null;
//                  }
//
//                  public Object next() {
//                    Object result = $bucketIter.next();
//                    if (! $bucketIter.hasNext()) {
//                      prepareNext();
//                    }
//                    return result;
//                  }
//
//                };
//  }

}