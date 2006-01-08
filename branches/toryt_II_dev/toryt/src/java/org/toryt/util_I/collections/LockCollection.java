package org.toryt.util_I.collections;


import java.util.Collection;
import java.util.Iterator;


/**
 * <p>{@link Collection} that becomes unmodifiable once it is
 *   locked. The collection could be locked at construction.</p>
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getElementType() !=  null;
 * @invar cC:noNull(this);
 * @invar cC:instanceOf(this, getElementType());
 */
public interface LockCollection extends Collection {

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
   * @basic
   * @init false;
   */
  boolean isLocked();



  public interface LockIterator extends Iterator {

    /**
     * @post   isLocked() ? false;
     * @throws UnsupportedOperationException
     *         isLocked();
     */
    void remove();

  }



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean add(Object o);

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean remove(Object o);

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean addAll(Collection c);

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean retainAll(Collection c);

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean removeAll(Collection c);

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  void clear();

  /*</section>*/

}