package org.toryt.util_I.priorityList.experiments;


import java.math.BigInteger;


/**
 * <p>General methods for working with {@link PriorityList} instances.</p>
 * 
 * @author    Jan Dockx
 * @author    PeopleWare n.v.
 */
public class PriorityLists {

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
   * This class cannot be instantiated
   */
  private PriorityLists() {
    // NOP
  }
  
  /**
   * Projection of array of {@link PriorityLists} to array of their
   * sizes.
   */
  public static BigInteger[] extractSize(PriorityList[] priorityLists) {
    BigInteger[] result = new BigInteger[priorityLists.length];
    for (int i = 0; i < priorityLists.length; i++) {
      result[i] = priorityLists[i].bigIntegerSize();
    }
    return result;
  }
  
}
