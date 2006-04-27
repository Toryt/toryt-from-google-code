package org.toryt.util_I.collections.priorityList;


import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.toryt.util_I.collections.bigSet.lockable.NullSingletonBigSet;


/**
 * <p>A {@link PriorityList} with {@link #size() size() == 1}
 *   and {@link #getCardinality() getCardinality() == 1}, which
 *   contains exactly 1 priority bucket with priority <code>0</code>
 *   that contains exactly 1 priority element that is <code>null</code>.</p>
 * <p>Since <code>null</code> has no type, we need to provide a
 *   {@link #getPriorityElementType() priority element type} at construction.
 *
 * @author Jan Dockx
 */
public final class NullSingletonPriorityList extends AbstractLockedPriorityList {

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
   * This method is introduced for use in {@link #subList(int, int)}.
   *
   * @pre priorityPriorityElementType != null;
   */
  public NullSingletonPriorityList(Class priorityElementType) {
    super(priorityElementType, BigInteger.ONE);
    $lbs = new NullSingletonBigSet(priorityElementType);
  }

  /**
   * @invar $lbs != null;
   */
  private final NullSingletonBigSet $lbs;

  public final boolean equals(Object o) {
    if ((o == null) || (! (o instanceof List)) || (((List)o).size() != 1)) {
      return false;
    }
    else {
      Object otherElement =  ((List)o).get(0);
      if ((otherElement == null) ||
          (! (otherElement instanceof Set))) {
        return false;
      }
      else {
        Set otherSet = (Set)otherElement;
        return ((otherSet.size() == 1) &&
                otherSet.contains(null));
      }
    }
  }

  public final int hashCode() {
    return 0;
  }

  public final boolean containsPriorityElement(final Object o) {
    return (o == null);
  }

  public final Iterator priorityElementIterator() {
    return new AbstractLockedCollectionIterator() {

                  private boolean $done = false;

                  public final boolean hasNext() {
                    return ! $done;
                  }

                  public Object next() {
                    return null;
                  }

                };
  }

  public final int size() {
    return 1;
  }

  public final boolean isEmpty() {
    return false;
  }

  public final boolean contains(Object o) {
    return $lbs.equals(o);
  }

  public final Object get(int index) {
    if (index != 0) {
      throw new IndexOutOfBoundsException();
    }
    return $lbs;
  }

  public final int indexOf(Object o) {
    return $lbs.equals(o) ? 0 : -1;
  }

  public final int lastIndexOf(Object o) {
    return indexOf(o);
  }

  public final ListIterator listIterator(final int index) {
    return new AbstractLockedListIterator() {

                  private boolean $delivered = (index == 1);

                  public final boolean hasNext() {
                    return $delivered;
                  }

                  public final Object next() {
                    return $lbs;
                  }

                  public final boolean hasPrevious() {
                    return ! $delivered;
                  }

                  public final Object previous() {
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

  public final List subList(int fromIndex, int toIndex) {
    if ((fromIndex == 0) && (toIndex == 1)) {
      return this;
    }
    else if ((fromIndex == 0) && (toIndex == 0)) {
      return new EmptyPriorityList(getElementType());
    }
    else {
      throw new IndexOutOfBoundsException();
    }
  }

}