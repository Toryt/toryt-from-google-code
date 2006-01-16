package org.toryt.util_I.priorityList;


import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.toryt.util_I.collections.LockableList;
import org.toryt.util_I.collections.TypedList;
import org.toryt.util_I.collections.bigSet.LockableBigSet;


/**
 * <p>A {@link List} of priority buckets. A priority bucket is a
 *   {@link LockableBigSet}. When <code>PriorityLists</code> are combined,
 *   the priority of elements is kept.</p>
 * <p>0 is considered the highest priority, and larger bucket numbers
 *   represent a lower priority.</p>
 * <p><code>PriorityLists</code> are always lockable. The list cannot
 *   contain <code>null</code>, but priority buckets can.</p>
 * <p>Often <code>PriorityLists</code> and their priority buckets
 *   are lazy, and generative. Therefor, care should be taken to avoid
 *   expensive methods.</p>
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getPriorityElementType() !=  null;
 * @invar getElementType() == LockableBigSet.class;
 * @invar ! isNullAllowed();
 * @invar (forall LockableBigSet lbs; contains(lbs);
 *          lbs.getElementType() == getPriorityElementType());
 * @invar (forall LockableBigSet lbs; contains(lbs);
 *          lbs.isLocked() == isLocked());
 */
public interface PriorityList extends TypedList, LockableList {

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
   * The type of the elements of the priority buckets.
   * No other types of objects are allowed in the buckets.
   *
   * @basic
   *
   * @note When moving to Java 5, replace this with a generic implementation.
   */
  Class getPriorityElementType();

  /**
   * The total number of elements in the all priority buckets.
   *
   * @return sum({pb.getBigSize() | LockableBigSet pb: contains(pb)};
   */
  BigInteger getCardinality();

  /**
   * There is a {@link LockableBigSet} in this list that contains <code>o</code>.
   *
   * @return (exists LockableBigSet lbs; contains(lbs); lbs.contains(o));
   */
  boolean containsPriorityElement(Object o);

  /**
   * An iterator that iterates over the priority elements in priority
   * order. Elements of a bucket with lower priority are only visited
   * after the elements of buckets with higher priority (respecting
   * the list order). The elements of a bucket or visited in no
   * particular order.
   */
  Iterator priorityElementIterator();

}