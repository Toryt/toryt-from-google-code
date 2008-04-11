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
public abstract class AbstractLockedList<_Element_>
    extends AbstractLockedCollection<_Element_>
    implements LockableList<_Element_> {

  protected AbstractLockedList(boolean nullAllowed) {
    super(nullAllowed);
  }

  /**
   * Trivial, {@link ListIterator} based implementation.
   */
  public int indexOf(Object o) {
    /* trivial implementation */
    ListIterator<_Element_> iter = listIterator();
    while (iter.hasNext()) {
      _Element_ e = iter.next();
      if ((o == e) || ((e != null) && e.equals(o))) {
        return iter.previousIndex();
      }
    }
    // not found
    return -1;
  }

  /**
   * Trivial, {@link ListIterator} based implementation.
   */
   public int lastIndexOf(Object o) {
     /* trivial implementation */
     ListIterator<_Element_> iter = listIterator(size());
     while (iter.hasPrevious()) {
       _Element_ e = iter.previous();
       if ((o == e) || ((e != null) && e.equals(o))) {
         return iter.nextIndex();
       }
     }
     // not found
     return -1;
   }

  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final void add(int index, _Element_ o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("List is locked");
  }

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final boolean addAll(int index, Collection<? extends _Element_> c)
      throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final _Element_ set(int index, _Element_ o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final _Element_ remove(int index) throws UnsupportedOperationException {
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
    ListIterator<_Element_> thisLi = listIterator();
    ListIterator<?> otherLi = other.listIterator();
    while (thisLi.hasNext()) {
      _Element_ thisInstance = thisLi.next();
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
    Iterator<_Element_> i = iterator();
    while (i.hasNext()) {
        _Element_ obj = i.next();
        hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
    }
    return hashCode;
  }

  public final Iterator<_Element_> iterator() {
    return listIterator();
  }

  public final ListIterator<_Element_> listIterator() {
    return listIterator(0);
  }

  protected abstract class AbstractLockedListIterator
      extends AbstractLockedCollectionIterator
      implements ListIterator<_Element_> {

    /**
     * @deprecated Unsupported
     */
    @Deprecated
    public void set(_Element_ o) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Set is locked");
    }

    /**
     * @deprecated Unsupported
     */
    @Deprecated
    public void add(_Element_ o) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Set is locked");
    }

  }

  protected final class GetBasedListIterator extends AbstractLockedListIterator {

    private final int $index;

    private int $cursor;

    public GetBasedListIterator(int index) {
      $index = index;
      $cursor = $index;
    }

    public final boolean hasNext() {
      return $cursor < size();
    }

    public _Element_ next() {
      _Element_ result = get($cursor);
      $cursor++;
      return result;
    }

    public boolean hasPrevious() {
      return $cursor > 0;
    }

    public _Element_ previous() {
      $cursor--;
      return get($cursor);
    }

    public int nextIndex() {
      return $cursor;
    }

    public int previousIndex() {
      return $cursor - 1;
    }

  }

}