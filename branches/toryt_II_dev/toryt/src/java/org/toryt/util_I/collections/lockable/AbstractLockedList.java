package org.toryt.util_I.collections.lockable;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;



/**
 * <p>Implementation of modifying methods for a {@link LockableList}
 *   that is locked always.</p>
 *
 * @author Jan Dockx
 *
 * @invar isLocked();
 */
public abstract class AbstractLockedList extends AbstractLockedCollection
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


  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  public final void add(int index, Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("List is locked");
  }

  public final boolean addAll(int index, Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public final Object set(int index, Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public final Object remove(int index) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /*</section>*/


  public boolean equals(Object o) {
    return (o != null) &&
           (o instanceof List) &&
           (size() == ((List)o).size()) &&
           hasSameElementsOnSamePlace((List)o);
  }

  /**
   * @pre list != null;
   * @pre list.size() == size();
   */
  protected final boolean hasSameElementsOnSamePlace(List other) {
    assert other != null;
    assert other.size() == size();
    ListIterator thisLi = listIterator();
    ListIterator otherLi = other.listIterator();
    while (thisLi.hasNext()) {
      Object thisInstance = thisLi.next();
      Object otherInstance = otherLi.next();
      if (((thisInstance == null) && (otherInstance != null)) ||
          ((thisInstance != null) && (! thisInstance.equals(otherInstance)))) {
        return false;
      }
    }
    // if we get here, it is ok
    return true;
  }

  /**
   * This implementation is very expensive for lazy collections.
   *
   * @protected
   * General implementation as high in the inheritance
   * hierarchy as possible. Overwrite when a more performant
   * implementation is possible.
   */
  public int hashCode() {
    int hashCode = 1;
    Iterator i = iterator();
    while (i.hasNext()) {
        Object obj = i.next();
        hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
    }
    return hashCode;
  }

  public final Iterator iterator() {
    return listIterator();
  }

  public final ListIterator listIterator() {
    return listIterator(0);
  }

  public abstract class AbstractLockedListIterator
      extends AbstractLockedCollectionIterator
      implements ListIterator {

    public void set(Object o) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Set is locked");
    }

    public void add(Object o) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Set is locked");
    }

  }

}