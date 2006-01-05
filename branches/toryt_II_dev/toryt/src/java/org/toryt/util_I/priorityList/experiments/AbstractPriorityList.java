package org.toryt.util_I.priorityList.experiments;


import java.math.BigInteger;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.ListIterator;


/**
 * <p>This class provides a skeleton implementation of the
 *   {@link PriorityList} interface based on the {@link AbstractList}
 *   implementation.</p>
 * 
 * @author Jan Dockx
 * @author PeopleWare n.v.
 * 
 * @invar bigIntegerSize() <= Integer.MAX_VALUE;
 */
public abstract class AbstractPriorityList
    extends AbstractList
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
   * @pre size >= 0;
   * @post size() == size;
   */
  public AbstractPriorityList(int size) {
    assert size >= 0;
    $size = size;
  }

  public final BigInteger bigIntegerSize() {
    return BigInteger.valueOf($size);
  }
  
  public final int size() {
    return $size;
  }
  
  /**
   * @invar $size >= 0;
   */
  private int $size;
  
  public final Iterator iterator() {
    return priorityListIterator();
  }
  
  public final ListIterator listIterator() {
    return priorityListIterator();
  }
  
  public final ListIterator listIterator(int index) {
    return priorityListIterator(index);
  }

  public final PriorityListIterator priorityListIterator() {
    return priorityListIterator(0);
  }
  
  /**
   * Return a fresh unwrapped list iterator. Needed for
   * {@link PriorityListIteratorWrapper}.
   */
  ListIterator createUnwrappedListIterator(int index) {
    return super.listIterator(index);
  }
  
  public final PriorityListIterator priorityListIterator(int index) {
    return new PriorityListIteratorWrapper(this, index);
  }

  /**
   * //throws index.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0;
   */
  public final PriorityListIterator priorityListIterator(BigInteger index) {
    assert index != null;
    if (index.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0) {
// MUDO      throw new Exception
    }
    return priorityListIterator(index.intValue());
  }
  
}