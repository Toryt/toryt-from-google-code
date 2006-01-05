package org.toryt.util_I.priorityList.experiments;


import java.math.BigInteger;
import java.util.AbstractSequentialList;
import java.util.Iterator;
import java.util.ListIterator;


/**
 * <p>This class provides a skeleton implementation of the
 *   {@link PriorityList} interface based on the {@link AbstractSequentialList}
 *   implementation.</p>
 * 
 * @author Jan Dockx
 * @author PeopleWare n.v.
 */
public abstract class AbstractSequentialPriorityList
    extends AbstractSequentialList
    implements PriorityList {

  /* <section name="Meta Information"> */
  //------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_DATE = "$Date$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_STATE = "$State$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_TAG = "$Name$"; //$NON-NLS-1$

  /* </section> */


  /**
   * @param size
   *        the size of this collection, as a <code>BigInteger</code>
   * @pre size != null;
   * @post bigIntegerSize().equals(size);
   */
  public AbstractSequentialPriorityList(BigInteger size) {
    assert size != null;
    $size = size;
  }

  public BigInteger bigIntegerSize() {
    return $size;
  }
  
  public final int size() {
    return $size.intValue();
  }
  
  /**
   * @invar $size != null;
   */
  private BigInteger $size;
  
  public final Iterator iterator() {
    return priorityListIterator();
  }
  
  public final ListIterator listIterator() {
    return priorityListIterator();
  }
  
  public final ListIterator listIterator(int index) {
    return priorityListIterator(index);
  }

  public PriorityListIterator priorityListIterator() {
    return priorityListIterator(BigInteger.ZERO);
  }
  
  public PriorityListIterator priorityListIterator(int index) {
    return priorityListIterator(BigInteger.valueOf(index));
  }
  
}