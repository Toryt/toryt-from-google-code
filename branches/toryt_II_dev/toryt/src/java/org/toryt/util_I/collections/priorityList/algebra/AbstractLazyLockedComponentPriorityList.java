package org.toryt.util_I.collections.priorityList.algebra;


import java.math.BigInteger;
import java.util.ListIterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.lockable.LazyList;
import org.toryt.util_I.collections.priorityList.AbstractLockedPriorityList;


/**
 * <p>Implementation of common expensive methodes for {@link ProductPriorityList},
 *   {@link BiProductPriorityList} and {@link UnionPriorityList}.
 *   These are all {@link LazyList LazyLists}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractLazyLockedComponentPriorityList<_ElementType_>
    extends AbstractLockedPriorityList<_ElementType_>  {

  /**
   * @pre priorityPriorityElementType != null;
   * @pre cardinality != null;
   * @pre cardinality >= 0;
   */
  protected AbstractLazyLockedComponentPriorityList(Class priorityPriorityElementType, BigInteger cardinality) {
    super(priorityPriorityElementType, cardinality);
  }


  /**
   * Find an equal {@link LockableBigSet}.
   *
   * @deprecated This method is very costly, as it
   *             effectively creates buckets one after the
   *             other
   */
  public final int indexOf(Object o) {
    ListIterator iter = listIterator();
    while (iter.hasNext()) {
      if (iter.next().equals(o)) {
        return iter.previousIndex();
      }
    }
    return -1;
  }

  /**
   * Find an equal {@link LockableBigSet}.
   *
   * @deprecated This method is very costly, as it
   *             effectively creates buckets one after the
   *             other
   */
  public final int lastIndexOf(Object o) {
    ListIterator iter = listIterator(size());
    while (iter.hasPrevious()) {
      if (iter.previous().equals(o)) {
        return iter.nextIndex();
      }
    }
    return -1;
  }

  /**
   * Contains an equal {@link LockableBigSet}.
   *
   * @deprecated This method is very costly, as it
   *             effectively creates buckets one after the
   *             other
   */
  public final boolean contains(final Object o) {
    return super.contains(o);
  }

}