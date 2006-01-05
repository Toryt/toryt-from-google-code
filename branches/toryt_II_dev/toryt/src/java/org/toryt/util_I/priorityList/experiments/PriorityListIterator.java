package org.toryt.util_I.priorityList.experiments;

import java.math.BigInteger;
import java.util.ListIterator;



/**
 * <p>The algorithm that traverses through a
 *   {@link ProductPriorityList} requires that it is possible to
 *   store an iteration state for later use (the algorithm is 2-phased, in
 *   the one fase we iterate in a <code>priority bucket</code>, the other
 *   fase goes from one <code>priority bucket</code> to the next).
 *   Since an {@link java.util.Iterator} is stateful, we need a
 *   PriorityListIterator to be {@link Cloneable}. Since {@link PriorityList}
 *   instances are immutable, the {@link #add(Object)},
 *   {@link #remove()} and {@link #set(Object)} methods throw
 *   {@link java.lang.UnsupportedOperationException}s.</p>
 * <p>We forsee extremely large sizes. For this, the {@link #nextIndex()}
 *   {@link #previousIndex()} methods have {@link BigInteger} alternatives.
 *   As a consequence, the <code>int</code> versions of these methods are not
 *   reliable.</p>
 * <p><strong>This does violate behavioral subtyping. This approach will be
 *   tried for now however, to keep compatibility with the standard Java
 *   collection framework.</strong></p>
 * 
 * @author Jan Dockx
 * @author PeopleWare n.v.
 * 
 * @invar getPriorityList() != null;
 * @invar bigIntegerNextIndex().compareTo(BigInteger.ZERO) >= 0;
 * @invar bigIntegerNextIndex().compareTo(getPriorityList().bigIntegerSize()) <= 0;
 */
public interface PriorityListIterator extends ListIterator, Cloneable {

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
   * The {@link PriorityList} this instance iterates over.
   * 
   * @basic
   */
  PriorityList getPriorityList();
  
  /**
   * @basic
   */
  Object clone();
  
  /**
   * This is the {@link #clone()} method with a static return type
   * that makes sense.
   * 
   * @return clone();
   */
  PriorityListIterator clonePriorityListIterator();

  /**
   * @return nextIndex() < getTestObjectList().size();
   */
  boolean hasNext();


  /**
   * @return previousIndex() > 0;
   */
  boolean hasPrevious();

  /**
   * @return bigIntegerNextIndex().intValue();
   */
  int nextIndex();
  
  /**
   * @basic
   */
  BigInteger bigIntegerNextIndex();

  /**
   * @return bigIntegerPreviousIndex().intValue();
   */
  int previousIndex();

  /**
   * @return bigIntegerNextIndex().subtract(BigInteger.ONE);
   */
  BigInteger bigIntegerPreviousIndex();
  
  /**
   * @post   false;
   * @throws UnsupportedOperationException
   *         true;
   */
  void remove() throws UnsupportedOperationException;

  /**
   * @post   false;
   * @throws UnsupportedOperationException
   *         true;
   */
  void set(Object o) throws UnsupportedOperationException;

  /**
   * @post   false;
   * @throws UnsupportedOperationException
   *         true;
   */
  void add(Object o) throws UnsupportedOperationException;

}

