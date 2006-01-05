package org.toryt.util_I.priorityList.experiments;

import java.math.BigInteger;
import java.util.ListIterator;


/**
 * A wrapper for regular {@link ListIterator} instances that
 * makes them {@link PriorityListIterator} instances.
 * 
 * @author Jan Dockx
 * @author PeopleWare n.v.
 * 
 * @invar getPriorityList().bigIntegerSize() <= Integer.MAX_VALUE;
 * @invar bigIntegerNextIndex() <= Integer.MAX_VALUE;
 * @invar getPriorityList() instanceof AbstractPriorityList;
 */
public final class PriorityListIteratorWrapper
    extends AbstractPriorityListIterator {

  /*<section name="Meta Information">*/
  //------------------------------------------------------------------

  /** {@value} */
  public static final String CVS_REVISION = "$Revision$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_DATE = "$Date$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_STATE = "$State$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_TAG = "$Name$"; //$NON-NLS-1$

  /*</section>*/


  /**
   * @param priorityList
   *        The list this is an iterator for.
   * @param index
   *        The index to start the iteration from.
   * @pre priorityList != null;
   * @post  new.getPriorityList() == priorityList;
   * @post  new.nextIndex() == index;
   */
  protected PriorityListIteratorWrapper(AbstractPriorityList priorityList, int index) {
    super(priorityList);
    $listIterator = priorityList.createUnwrappedListIterator(index);
  }
  
  // deep clone needed
  public Object clone() {
    PriorityListIteratorWrapper result = (PriorityListIteratorWrapper)super.clone();
    result.$listIterator = ((AbstractPriorityList)getPriorityList()).createUnwrappedListIterator($listIterator.nextIndex());
    return result;
  }

  /**
   * @invar $listIterator != null;
   */
  private ListIterator $listIterator;
  
  public final boolean hasNext() {
    return $listIterator.hasNext();
  }

  public final boolean hasPrevious() {
    return $listIterator.hasPrevious();
  }

  public final int nextIndex() {
    return $listIterator.nextIndex();
  }

  public final int previousIndex() {
    return $listIterator.previousIndex();
  }

  public final Object next() {
    return $listIterator.next();
  }

  public final Object previous() {
    return $listIterator.previous();
  }

  public final BigInteger bigIntegerNextIndex() {
    return BigInteger.valueOf(nextIndex());
  }

  public final BigInteger bigIntegerPreviousIndex() {
    return BigInteger.valueOf(previousIndex());
  }
  
}

