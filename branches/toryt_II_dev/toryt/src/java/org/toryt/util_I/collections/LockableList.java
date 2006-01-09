package org.toryt.util_I.collections;


import java.util.Collection;
import java.util.List;


/**
 * <p>{@link List} that becomes unmodifiable once it is
 *   locked.</p>
 *
 * @see LockableCollection
 *
 * @author Jan Dockx
 */
public interface LockableList extends LockableCollection, List {

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

  
  
  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  void add(int index, Object o) throws UnsupportedOperationException;

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean addAll(int index, Collection c) throws UnsupportedOperationException;

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  Object set(int index, Object o) throws UnsupportedOperationException;

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  Object remove(int index) throws UnsupportedOperationException;

  /*</section>*/

  
  /**
   * @result result instanceof LockableList;
   * @result result.isLocked() == isLocked();
   */
  List subList(int fromIndex, int toIndex);

}