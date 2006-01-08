package org.toryt.util_I.collections;


import java.util.Collection;


/**
 * <p>Collection that does not allow <code>null</code> as
 *   element.</p>
 *
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar cC:noNull(this);
 */
public interface NoNullCollection extends Collection {

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
   * @throws NullPointerException
   *         o == null;
   */
  boolean add(Object o) throws NullPointerException;

  /**
   * @post   ((c != null) && c.contains(null)) ? false;
   * @throws NullPointerException
   *         (c != null) && c.contains(null);
   */
  boolean addAll(Collection c) throws NullPointerException;

}