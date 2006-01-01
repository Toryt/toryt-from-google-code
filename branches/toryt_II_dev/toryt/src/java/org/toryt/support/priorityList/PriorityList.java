package org.toryt.support.priorityList;


import java.math.BigInteger;
import java.util.List;

import org.toryt.support.bigSet.BigSet;


/**
 * <p>A {@link List} of priority buckets. A priority bucket is a
 *   {@link BigSet}. When <code>PriorityLists</code> are combined,
 *   the priority of elements is kept.</p>
 * <p><code>PriorityLists</code> are always immutable.
 *   Combination-<code>PriorityLists</code> should use their
 *   component-<code>PriorityLists</code> by reference always.</p>
 * <p>Often <code>PriorityLists</code> and their priority buckets
 *   are lazy, and generative.
 *   Therefor, they should be traversed using the {@link #iterator()}
 *   or {@link #listIterator()}. Direct access through {@link #get(int)}
 *   might be very expensive.</p>
 * <p>The total number of elements over all priority buckets in the list
 *   can be very big. Therefor, {@link #getCardinality()} returns an
 *   object of type {@link BigInteger}.</p>
 *
 *
 * @invar toryt:cC org.toryt_II.contract.Collections;
 * @invar getElementType() !=  null;
 * @invar cC:noNull(this);
 * @invar cC:instanceOf(this, BigSet);
 * @invar (forall Set s; contains(s); cC:noNull(s));
 * @invar (forall Set s; contains(s); cC:instanceOf(s, getElementType()));
 */
public interface PriorityList extends List {

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
  Class getElementType();

  /**
   * The total number of elements in the all priority buckets.
   *
   * @return sum({pb.getBigSize() | BigSet pb: contains(pb)};
   */
  BigInteger getCardinality();

}