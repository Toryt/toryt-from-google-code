package org.toryt.util_I.priorityList.experiments;


import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * <p>A <code>PriorityList</code> is just a list, but with some extra features
 *   that permit lazy lists and priority combination (see
 *   {@link ProductPriorityList}). Notably, we need an iterator
 *   that is {@link Cloneable} (see {@link PriorityListIterator}), and we
 *   forsee extremely large sizes. For this, the {@link #size()} and index
 *   methods have {@link BigInteger} alternatives. As a consequence, the
 *   <code>int</code> versions of these methods are not reliable.</p>
 * <p><strong>This does violate behavioral subtyping. This approach will be
 *   tried for now however, to keep compatibility with the standard Java
 *   collection framework.</strong></p>
 * 
 * @author    Jan Dockx
 * @author    PeopleWare n.v.
 * 
 * @invar size() >= 1;
 * @invar bigIntegerSize() != null;
 * @invar (forall int i; priorityListIterator(i).getPriorityList() == this);
 */
public interface PriorityList extends List {

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
   * @basic
   */
  BigInteger bigIntegerSize();
  
  /**
   * @return bigIntegerSize().intValue();
   */
  int size();
  
  /**
   * @return priorityListIterator();
   */
  Iterator iterator();
  
  /**
   * @return listIterator(0);
   */
  ListIterator listIterator();
  
  /**
   * @return priorityListIterator(index);
   */
  ListIterator listIterator(int index);
  
  
  /**
   * @return priorityListIterator(0);
   */
  PriorityListIterator priorityListIterator();
  
  /**
   * @basic
   */
  PriorityListIterator priorityListIterator(int index);
  
  /**
   * @basic
   * 
   * @pre index != null;
   */
  PriorityListIterator priorityListIterator(BigInteger index);
  
}
