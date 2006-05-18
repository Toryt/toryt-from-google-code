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
public class ListBackedLockableList<_Element_>
    extends AbstractCollectionBackedLockableCollection<_Element_, List<_Element_>>
    implements LockableList<_Element_> {

  /**
   * The <code>backingList</code> should not be exposed to protect integrity
   * when {@link #isLocked()}.
   *
   * @pre backingList != null;
   * @post new.isNullAllowed() == nullAllowed;
   * @post new.getBackingCollection() == backingList;
   * @post ! new.isLocked();
   */
  protected ListBackedLockableList(List<_Element_> backingList, boolean nullAllowed) {
    super(backingList, nullAllowed);
  }

  /**
   * Create an instance backed by a fresh {@link ArrayList}.
   */
  public ListBackedLockableList(boolean nullAllowed) {
    this(new ArrayList<_Element_>(), nullAllowed);
  }



  /* <section name="Inspectors"> */
  //------------------------------------------------------------------

  /**
   * This method is not final. Some lazy subclasses want to override it.
   */
  public ListBackedLockableList<_Element_> subList(int fromIndex, int toIndex) {
    ListBackedLockableList<_Element_> result =
        new ListBackedLockableList<_Element_>(getBackingCollection().subList(fromIndex, toIndex),
                                                  isNullAllowed());
    if (isLocked()) {
      result.lock();
    }
    return result;
  }

  public final _Element_ get(int index) {
    return getBackingCollection().get(index);
  }

  public final int indexOf(Object o) {
    return getBackingCollection().indexOf(o);
  }

  public final int lastIndexOf(Object o) {
    return getBackingCollection().lastIndexOf(o);
  }

  public class ListBackedLockableListIterator extends CollectionBackedLockedIterator
      implements ListIterator<_Element_> {

    protected ListBackedLockableListIterator(int index) {
      $iterator = getBackingCollection().listIterator(index);
    }

    @Override
    protected final ListIterator<_Element_> getBackingIterator() {
      return $iterator;
    }

    /**
     * @invar $backingIterator != null;
     */
    private final ListIterator<_Element_> $iterator;


    public final boolean hasPrevious() {
      return $iterator.hasPrevious();
    }

    public final _Element_ previous() {
      return $iterator.previous();
    }

    public final int nextIndex() {
     return $iterator.nextIndex();
    }

    public final int previousIndex() {
      return $iterator.previousIndex();
    }

    /**
     * Not final, because locked subclasses want to override this.
     *
     * @post   isLocked() ? false;
     * @throws UnsupportedOperationException
     *         isLocked();
     */
    public void set(_Element_ o) {
      if (isLocked()) {
        throw new UnsupportedOperationException("List is locked");
      }
      if ((! isNullAllowed()) && (o == null)) {
        throw new NullPointerException("Null is not allowed");
      }
      $iterator.set(o);
    }

    /**
     * Not final, because locked subclasses want to override this.
     *
     * @post   isLocked() ? false;
     * @throws UnsupportedOperationException
     *         isLocked();
     */
    public void add(_Element_ o) {
      if (isLocked()) {
        throw new UnsupportedOperationException("List is locked");
      }
      if ((! isNullAllowed()) && (o == null)) {
        throw new NullPointerException("Null is not allowed");
      }
      $iterator.add(o);
    }

  }

  public final Iterator<_Element_> iterator() {
    return listIterator();
  }

  public final ListIterator<_Element_> listIterator() {
    return new ListBackedLockableListIterator(0);
  }

  /**
   * Not final, because locked subclasses want to override this.
   */
  public ListIterator<_Element_> listIterator(int index) {
    return new ListBackedLockableListIterator(index);
  }

  /*</section>*/



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  /**
   * Not final, because locked subclasses want to override this.
   */
  public void add(int index, _Element_ o)
      throws UnsupportedOperationException, NullPointerException,
             IndexOutOfBoundsException {
    if (isLocked()) {
      throw new UnsupportedOperationException("List is locked");
    }
    if ((! isNullAllowed()) && (o == null)) {
      throw new NullPointerException("Null is not allowed");
    }
    getBackingCollection().add(index, o);
  }

  /**
   * Not final, because lazy and locked subclasses want to override this.
   */
  public boolean addAll(int index, Collection<? extends _Element_> c)
      throws UnsupportedOperationException, NullPointerException,
             IndexOutOfBoundsException {
    if (isLocked()) {
      throw new UnsupportedOperationException("List is locked");
    }
    if ((! isNullAllowed()) && (c != null) && (c.contains(null))) {
      throw new NullPointerException("Null is not allowed");
    }
    return getBackingCollection().addAll(index, c);
  }

  /**
   * Not final, because locked subclasses want to override this.
   */
  public _Element_ set(int index, _Element_ o)
      throws UnsupportedOperationException, NullPointerException,
             IndexOutOfBoundsException {
    if (isLocked()) {
      throw new UnsupportedOperationException("List is locked");
    }
    if ((! isNullAllowed()) && (o == null)) {
      throw new NullPointerException("Null is not allowed");
    }
    return getBackingCollection().set(index, o);
  }

  /**
   * Not final, because locked subclasses want to override this.
   */
  public _Element_ remove(int index)
      throws UnsupportedOperationException,
             IndexOutOfBoundsException {
    if (isLocked()) {
      throw new UnsupportedOperationException("List is locked");
    }
    return getBackingCollection().remove(index);
  }

  /*</section>*/

}