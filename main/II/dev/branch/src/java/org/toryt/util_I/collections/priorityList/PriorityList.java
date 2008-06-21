package org.toryt.util_I.collections.priorityList;


import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.lockable.LockableList;


/**
 * <p>A {@link List} of priority buckets. A priority bucket is a
 *   {@link LockableBigSet} of type <code>_PriorityElementType_</code>.
 *   When <code>PriorityLists</code> are combined,
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
 * @invar ! isNullAllowed();
 * @invar (forall LockableBigSet lbs; contains(lbs);
 *          lbs.isLocked() == isLocked());
 * @invar (forall LockableBigSet lbs; contains(lbs);
 *          lbs.isNullAllowed() == isNullPriorityElementAllowed());
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface PriorityList<_PriorityElement_>
    extends LockableList<LockableBigSet<? extends _PriorityElement_>> {

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
  Iterator<? extends _PriorityElement_> priorityElementIterator();

  /**
   * @basic
   */
  boolean isNullPriorityElementAllowed();

  /**
   * More strict return type.
   */
  PriorityList<_PriorityElement_> subList(int fromIndexInclusive, int toIndexExclusive);

}