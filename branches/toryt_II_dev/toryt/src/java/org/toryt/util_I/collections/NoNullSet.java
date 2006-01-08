package org.toryt.util_I.collections;


import java.util.Collection;
import java.util.Set;


/**
 * <p>Set that does not allow <code>null</code> as
 *   element.</p>
 */
public interface NoNullSet extends NoNullCollection, Set {

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
   * @post   (o == null) ? false;
   * @throws UnsupportedOperationException
   *         o == null;
   */
  boolean add(Object o);

  /**
   * @post   ((c != null) && c.contains(null)) ? false;
   * @throws UnsupportedOperationException
   *         (c != null) && c.contains(null);
   */
  boolean addAll(Collection c);

}