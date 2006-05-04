package org.toryt.util_I.collections.priorityList;


import java.math.BigInteger;
import java.util.Iterator;
import java.util.ListIterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.lockable.EmptyBigSet;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;


/**
 * <p>An empty {@link PriorityList}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public final class EmptyPriorityList<_PriorityElement_>
    extends AbstractLockedPriorityList<_PriorityElement_> {

  /**
   * This method is introduced for use in {@link #subList(int, int)}.
   */
  public EmptyPriorityList() {
    super(BigInteger.ZERO, false);
  }

  private EmptyBigSet<_PriorityElement_> $ebs = new EmptyBigSet<_PriorityElement_>();

  @Override
  public final int hashCode() {
    return 1;
  }

  public final boolean containsPriorityElement(final Object o) {
    return false;
  }

  public final Iterator<_PriorityElement_> priorityElementIterator() {
    return $ebs.iterator();
  }

  public final int size() {
    return 0;
  }

  public final boolean isEmpty() {
    return true;
  }

  @Override
  public final boolean contains(Object o) {
    return false;
  }

  public final LockableBigSet<_PriorityElement_> get(int index) {
    throw new IndexOutOfBoundsException();
  }

  @Override
  public final int indexOf(Object o) {
    return -1;
  }

  @Override
  public final int lastIndexOf(Object o) {
    return -1;
  }

  public final ListIterator<LockableBigSet<? extends _PriorityElement_>> listIterator(int index) {
    return new AbstractLockedListIterator() {

                  public boolean hasNext() {
                    return false;
                  }

                  public LockableBigSet<_PriorityElement_> next() {
                    throw new IndexOutOfBoundsException();
                  }

                  public boolean hasPrevious() {
                    return false;
                  }

                  public LockableBigSet<_PriorityElement_> previous() {
                    throw new IndexOutOfBoundsException();
                  }

                  public int nextIndex() {
                    return 0;
                  }

                  public int previousIndex() {
                    return -1;
                  }

                };
  }

  public final EmptyPriorityList<_PriorityElement_> subList(int fromIndex, int toIndex) {
    if ((fromIndex == 0) && (toIndex == 0)) {
      return this;
    }
    else {
      throw new IndexOutOfBoundsException();
    }
  }

}