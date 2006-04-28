package org.toryt.util_I.collections.lockable;


import java.util.Collection;
import java.util.Iterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>{@link Collection} that becomes unmodifiable once it is
 *   locked. The collection could be locked at construction.</p>
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getElementType() !=  null;
 * @invar cC:noNull(this);
 * @invar cC:instanceOf(this, getElementType());
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface LockableCollection extends Collection {

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
    void remove() throws UnsupportedOperationException;

  }



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean add(Object o) throws UnsupportedOperationException;

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean remove(Object o) throws UnsupportedOperationException;

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean addAll(Collection c) throws UnsupportedOperationException;

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean retainAll(Collection c) throws UnsupportedOperationException;

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean removeAll(Collection c) throws UnsupportedOperationException;

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  void clear() throws UnsupportedOperationException;

  /*</section>*/

}