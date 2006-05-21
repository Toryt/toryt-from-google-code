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
public interface LockableCollection<_Element_> extends Collection<_Element_>, Cloneable {

  /**
   * Clone method must be public.
   */
  Object clone();

  /**
   * Is <code>null</code> allowed as element in this collection?
   *
   * @basic
   */
  boolean isNullAllowed();

  /**
   * @basic
   * @init false;
   */
  boolean isLocked();

  public interface LockIterator<_IteratorElement_> extends Iterator<_IteratorElement_> {

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
   * @post   ((! isNullAllowed()) && (o == null)) ? false;
   * @post   isLocked() ? false;
   * @throws NullPointerException
   *         (! isNullAllowed()) && (o == null);
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean add(_Element_ o) throws UnsupportedOperationException;

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean remove(Object o) throws UnsupportedOperationException;

  /**
   * @post   ((! isNullAllowed()) && c.contains(null)) ? false;
   * @post   isLocked() ? false;
   * @throws NullPointerException
   *         (! isNullAllowed()) && c.contains(null);
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean addAll(Collection<? extends _Element_> c) throws UnsupportedOperationException;

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean retainAll(Collection<?> c) throws UnsupportedOperationException;

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean removeAll(Collection<?> c) throws UnsupportedOperationException;

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  void clear() throws UnsupportedOperationException;

  /*</section>*/

}