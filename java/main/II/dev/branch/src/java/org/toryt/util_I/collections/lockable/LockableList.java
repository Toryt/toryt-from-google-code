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
public interface LockableList<_Element_>
    extends LockableCollection<_Element_>, List<_Element_> {

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
  void add(int index, _Element_ o) throws UnsupportedOperationException;

  /**
   * @post   ((! isNullAllowed()) && c.contains(null)) ? false;
   * @post   isLocked() ? false;
   * @throws NullPointerException
   *         (! isNullAllowed()) && c.contains(null);
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  boolean addAll(int index, Collection<? extends _Element_> c) throws UnsupportedOperationException;

  /**
   * @post   ((! isNullAllowed()) && (o == null)) ? false;
   * @post   isLocked() ? false;
   * @throws NullPointerException
   *         (! isNullAllowed()) && (o == null);
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  _Element_ set(int index, _Element_ o) throws UnsupportedOperationException;

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  _Element_ remove(int index) throws UnsupportedOperationException;

  /*</section>*/


  /**
   * @result result instanceof LockableList;
   * @result result.isLocked() == isLocked();
   */
  List<_Element_> subList(int fromIndex, int toIndex);

}