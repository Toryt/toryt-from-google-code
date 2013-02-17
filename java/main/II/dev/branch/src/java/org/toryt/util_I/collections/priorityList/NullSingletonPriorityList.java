package org.toryt.util_I.collections.priorityList;


import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.bigSet.lockable.NullSingletonBigSet;


/**
 * <p>A {@link PriorityList} with {@link #size() size() == 1}
 *   and {@link #getCardinality() getCardinality() == 1}, which
 *   contains exactly 1 priority bucket with priority <code>0</code>
 *   that contains exactly 1 priority element that is <code>null</code>.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public final class NullSingletonPriorityList<_PriorityElement_>
    extends AbstractLockedPriorityList<_PriorityElement_> {

  public NullSingletonPriorityList() {
    super(BigInteger.ONE, true);
    $lbs = new NullSingletonBigSet<_PriorityElement_>();
  }

  // overriding clone is not necessary: there are no instance variable here that are mutable

  /**
   * @invar $lbs != null;
   */
  private final NullSingletonBigSet<_PriorityElement_> $lbs;

  @Override
  public final boolean equals(Object o) {
    if ((o == null) || (! (o instanceof List<?>)) || (((List<?>)o).size() != 1)) {
      // at runtime, we can't now whether these are List<Set<?>>'s
      return false;
    }
    else {
      Object otherElement =  ((List<?>)o).get(0);
      if ((otherElement == null) ||
          (! (otherElement instanceof Set<?>))) {
        return false;
      }
      else {
        Set<?> otherSet = (Set<?>)otherElement;
        return ((otherSet.size() == 1) &&
                otherSet.contains(null));
      }
    }
  }

  @Override
  public final int hashCode() {
    return 0;
  }

  public final boolean containsPriorityElement(final Object o) {
    return (o == null);
  }

  @Override
  public final Iterator<_PriorityElement_> priorityElementIterator() {
    return $lbs.iterator();
  }

  public final int size() {
    return 1;
  }

  public final boolean isEmpty() {
    return false;
  }

  @Override
  public final boolean contains(Object o) {
    return $lbs.equals(o);
  }

  public final NullSingletonBigSet<_PriorityElement_> get(int index) {
    if (index != 0) {
      throw new IndexOutOfBoundsException();
    }
    return $lbs;
  }

  @Override
  public final int indexOf(Object o) {
    return $lbs.equals(o) ? 0 : -1;
  }

  @Override
  public final int lastIndexOf(Object o) {
    return indexOf(o);
  }

  public final ListIterator<LockableBigSet<? extends _PriorityElement_>> listIterator(final int index) {
    return new AbstractLockedListIterator() {

                  private boolean $delivered = (index == 1);

                  public final boolean hasNext() {
                    return $delivered;
                  }

                  public final NullSingletonBigSet<_PriorityElement_> next() {
                    return $lbs;
                  }

                  public final boolean hasPrevious() {
                    return ! $delivered;
                  }

                  public final NullSingletonBigSet<_PriorityElement_> previous() {
                    return $lbs;
                  }

                  public final int nextIndex() {
                    return $delivered ? 1 : 0;
                  }

                  public final int previousIndex() {
                    return $delivered ? 0 : -1;
                  }

                };
  }

  public final NullSingletonPriorityList<_PriorityElement_> subList(int fromIndex, int toIndex) {
    if ((fromIndex == 0) && (toIndex == 1)) {
      return this;
    }
    else if ((fromIndex == 0) && (toIndex == 0)) {
      return new NullSingletonPriorityList<_PriorityElement_>();
    }
    else {
      throw new IndexOutOfBoundsException();
    }
  }

}