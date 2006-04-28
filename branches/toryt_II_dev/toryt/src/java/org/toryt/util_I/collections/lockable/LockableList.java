package org.toryt.util_I.collections.lockable;


import java.util.Collection;
import java.util.List;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>{@link List} that becomes unmodifiable once it is
 *   locked.</p>
 *
 * @see LockableCollection
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface LockableList extends LockableCollection, List {

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