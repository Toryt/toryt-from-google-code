package org.toryt.contract;


import java.util.Collection;
import java.util.Iterator;


/**
 * @author Jan Dockx
 */
public class Collections {

  /*<section name="Meta Information">*/
  //  ------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /*</section>*/

  private Collections() {
    // NOP
  }
  
  /**
   * @return ! c.contains(null)
   */
  public static boolean noNull(Collection c) {
    return ! c.contains(null);
  }
  
  /**
   * @return (forall Object o, c.contains(o); type.isInstance(o));
   */
  public static boolean instanceOf(Collection c, Class type) {
    Iterator iter = c.iterator();
    while (iter.hasNext()) {
      if (! type.isInstance(iter.next())) {
        return false;
      }
    }
    return true;
  }
  
}