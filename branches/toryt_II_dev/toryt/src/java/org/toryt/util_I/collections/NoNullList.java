package org.toryt.util_I.collections;


import java.util.Collection;
import java.util.List;


/**
 * <p>{@link List} that does not allow <code>null</code> as
 *   element.</p>
 *
 * @author Jan Dockx
 */
public interface NoNullList extends NoNullCollection, List {

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
  void add(int index, Object o) throws NullPointerException;

  /**
   * @post   ((c != null) && c.contains(null)) ? false;
   * @throws NullPointerException
   *         (c != null) && c.contains(null);
   */
  boolean addAll(int index, Collection c) throws NullPointerException;

  /**
   * @post   (o == null) ? false;
   * @throws NullPointerException
   *         o == null;
   */
  Object set(int index, Object o) throws NullPointerException;
  
}