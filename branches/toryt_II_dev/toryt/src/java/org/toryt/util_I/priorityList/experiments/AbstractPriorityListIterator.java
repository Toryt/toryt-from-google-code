package org.toryt.util_I.priorityList.experiments;


/**
 * Implementation of general methods for {@link PriorityListIterator} classes.
 * 
 * @author Jan Dockx
 * @author PeopleWare n.v.
 */
public abstract class AbstractPriorityListIterator
    implements PriorityListIterator {

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
   * @pre priorityList != null;
   * @post  new.getPriorityList() == priorityList;
   */
  protected AbstractPriorityListIterator(PriorityList priorityList) {
    assert priorityList != null;
    $priorityList = priorityList;
  }

  public final PriorityList getPriorityList() {
    return $priorityList;
  }

  /**
   * @invar $priorityList != null;
   */
  private PriorityList $priorityList;
  
  public Object clone() {
    Object result = null;
    try {
      result = super.clone();
    }
    catch (CloneNotSupportedException e) {
      assert false : "CloneNotSupportedExceptionshould not happen: " + e; //$NON-NLS-1$
    }
    return result;
  }

  public final PriorityListIterator clonePriorityListIterator() {
    return (PriorityListIterator)clone();
  }

  public final void remove() throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public final void set(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

  public final void add(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException();
  }

}

