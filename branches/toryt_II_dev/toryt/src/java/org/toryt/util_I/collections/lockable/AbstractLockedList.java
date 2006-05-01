package org.toryt.util_I.collections.lockable;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;



/**
 * <p>Implementation of modifying methods for a {@link LockableList}
 *   that is locked always.</p>
 *
 * @author Jan Dockx
 *
 * @invar isLocked();
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractLockedList<_ElementType_>
    extends AbstractLockedCollection<_ElementType_>
    implements LockableList<_ElementType_> {

  protected AbstractLockedList(boolean nullAllowed) {
    super(nullAllowed);
  }


  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final void add(int index, _ElementType_ o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("List is locked");
  }

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final boolean addAll(int index, Collection<? extends _ElementType_> c)
      throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final _ElementType_ set(int index, _ElementType_ o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final _ElementType_ remove(int index) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /*</section>*/

  @Override
  public boolean equals(Object o) {
    return (o != null) &&
           (o instanceof List<?>) &&
           (size() == ((List<?>)o).size()) &&
           hasSameElementsOnSamePlace((List<?>)o);
  }

  /**
   * @pre list != null;
   * @pre list.size() == size();
   */
  protected final boolean hasSameElementsOnSamePlace(List<?> other) {
    assert other != null;
    assert other.size() == size();
    ListIterator<_ElementType_> thisLi = listIterator();
    ListIterator<?> otherLi = other.listIterator();
    while (thisLi.hasNext()) {
      _ElementType_ thisInstance = thisLi.next();
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
  @Override
  public int hashCode() {
    int hashCode = 1;
    Iterator<_ElementType_> i = iterator();
    while (i.hasNext()) {
        _ElementType_ obj = i.next();
        hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
    }
    return hashCode;
  }

  public final Iterator<_ElementType_> iterator() {
    return listIterator();
  }

  public final ListIterator<_ElementType_> listIterator() {
    return listIterator(0);
  }

  public abstract class AbstractLockedListIterator
      extends AbstractLockedCollectionIterator
      implements ListIterator<_ElementType_> {

    /**
     * @deprecated Unsupported
     */
    @Deprecated
    public void set(_ElementType_ o) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Set is locked");
    }

    /**
     * @deprecated Unsupported
     */
    @Deprecated
    public void add(_ElementType_ o) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Set is locked");
    }

  }

}