package org.toryt.util_I.collections.lockable;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;



/**
 * <p>Implementation of {@link LockableCollection} and {@link List},
 *   backed by another {@link List}. The backing collection
 *   needs to be kept private, to ensure correct behavior when
 *   {@link #isLocked()}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ListBackedLockableList<_ElementType_>
    extends AbstractCollectionBackedLockableCollection<_ElementType_, List<_ElementType_>>
    implements LockableList<_ElementType_> {

  /**
   * The <code>backingList</code> should not be exposed to protect integrity
   * when {@link #isLocked()}.
   *
   * @pre backingList != null;
   * @post new.isNullAllowed() == nullAllowed;
   * @post new.getBackingCollection() == backingList;
   * @post ! new.isLocked();
   */
  protected ListBackedLockableList(List<_ElementType_> backingList, boolean nullAllowed) {
    super(backingList, nullAllowed);
  }

  /**
   * Create an instance backed by a fresh {@link ArrayList}.
   */
  public ListBackedLockableList(boolean nullAllowed) {
    this(new ArrayList<_ElementType_>(), nullAllowed);
  }



  /* <section name="Inspectors"> */
  //------------------------------------------------------------------

  public final List<_ElementType_> subList(int fromIndex, int toIndex) {
    return getBackingCollection().subList(fromIndex, toIndex);
  }

  public final _ElementType_ get(int index) {
    return getBackingCollection().get(index);
  }

  public final int indexOf(Object o) {
    return getBackingCollection().indexOf(o);
  }

  public final int lastIndexOf(Object o) {
    return getBackingCollection().lastIndexOf(o);
  }

  public class ListBackedLockListIterator extends CollectionBackedLockIterator
      implements ListIterator<_ElementType_> {

    private ListBackedLockListIterator(int index) {
      $iterator = getBackingCollection().listIterator(index);
    }

    /**
     * @invar $backingIterator != null;
     */
    private ListIterator<_ElementType_> $iterator;

    @Override
    protected final Iterator<_ElementType_> getIterator() {
      return $iterator;
    }

    public final boolean hasPrevious() {
      return $iterator.hasPrevious();
    }

    public final _ElementType_ previous() {
      return $iterator.previous();
    }

    public final int nextIndex() {
     return $iterator.nextIndex();
    }

    public final int previousIndex() {
      return $iterator.previousIndex();
    }

    /**
     * @post   isLocked() ? false;
     * @throws UnsupportedOperationException
     *         isLocked();
     */
    public final void set(_ElementType_ o) {
      if (isLocked()) {
        throw new UnsupportedOperationException("List is locked");
      }
      $iterator.set(o);
    }

    /**
     * @post   isLocked() ? false;
     * @throws UnsupportedOperationException
     *         isLocked();
     */
    public final void add(_ElementType_ o) {
      if (isLocked()) {
        throw new UnsupportedOperationException("List is locked");
      }
      $iterator.add(o);
    }

  }

  public final Iterator<_ElementType_> iterator() {
    return listIterator();
  }

  public final ListIterator<_ElementType_> listIterator() {
    return new ListBackedLockListIterator(0);
  }

  public final ListIterator<_ElementType_> listIterator(int index) {
    return new ListBackedLockListIterator(index);
  }

  /*</section>*/



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  public final void add(int index, _ElementType_ o) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("List is locked");
    }
    getBackingCollection().add(index, o);
  }

  public final boolean addAll(int index, Collection<? extends _ElementType_> c)
      throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("List is locked");
    }
    return getBackingCollection().addAll(index, c);
  }

  public final _ElementType_ set(int index, _ElementType_ o) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("List is locked");
    }
    return getBackingCollection().set(index, o);
  }

  public final _ElementType_ remove(int index) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("List is locked");
    }
    return getBackingCollection().remove(index);
  }

  /*</section>*/

}