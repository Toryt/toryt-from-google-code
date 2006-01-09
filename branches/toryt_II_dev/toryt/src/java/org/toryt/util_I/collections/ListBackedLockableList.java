package org.toryt.util_I.collections;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * <p>Implementation of {@link LockableCollection} and {@link List},
 * backed by another {@link List}.</p>
 *
 * @author Jan Dockx
 */
public class ListBackedLockableList extends AbstractCollectionBackedLockableCollection
    implements LockableList {

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
   * @pre backingList != null;
   * @post ! new.isLocked();
   */
  public ListBackedLockableList(List backingList) {
    assert backingList != null;
    $backingList = backingList;
  }

  /**
   * Create an instance backed by a fresh {@link ArrayList}
   */
  public ListBackedLockableList() {
    this(new ArrayList());
  }

  protected final List getBackingList() {
    return $backingList;
  }

  protected final Collection getBackingCollection() {
    return $backingList;
  }

  /**
   * @invar $backingList != null;
   */
  private List $backingList;


  /* <section name="Inspectors"> */
  //------------------------------------------------------------------

  public final List subList(int fromIndex, int toIndex) {
    return getBackingList().subList(fromIndex, toIndex);
  }
  
  public final Object get(int index) {
    return getBackingList().get(index);
  }
  
  public final int indexOf(Object o) {
    return getBackingList().indexOf(o);
  }
  
  public final int lastIndexOf(Object o) {
    return getBackingList().lastIndexOf(o);
  }
  
  public class ListBackedLockListIterator extends CollectionBackedLockIterator
      implements ListIterator {

    private ListBackedLockListIterator(int index) {
      $iterator = $backingList.listIterator(index);
    }
    
    /**
     * @invar $backingIterator != null;
     */
    private ListIterator $iterator;
    
    protected final Iterator getIterator() {
      return $iterator;
    }

    public final boolean hasPrevious() {
      return $iterator.hasPrevious();
    }

    public final Object previous() {
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
    public final void set(Object o) {
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
    public final void add(Object o) {
      if (isLocked()) {
        throw new UnsupportedOperationException("List is locked");
      }
      $iterator.add(o);
    }

  }
  
  public final Iterator iterator() {
    return listIterator();
  }

  public final ListIterator listIterator() {
    return new ListBackedLockListIterator(0);
  }
  
  public final ListIterator listIterator(int index) {
    return new ListBackedLockListIterator(index);
  }

  /*</section>*/



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  public final void add(int index, Object o) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("List is locked");
    }
    getBackingList().add(index, o);
  }

  public final boolean addAll(int index, Collection c) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("List is locked");
    }
    return getBackingList().addAll(index, c);
  }

  public final Object set(int index, Object o) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("List is locked");
    }
    return getBackingList().set(index, o);
  }

  public final Object remove(int index) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("List is locked");
    }
    return getBackingList().remove(index);
  }

  /*</section>*/

}