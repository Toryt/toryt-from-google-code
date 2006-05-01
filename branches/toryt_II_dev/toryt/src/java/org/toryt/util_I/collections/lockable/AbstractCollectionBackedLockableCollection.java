package org.toryt.util_I.collections.lockable;


import java.util.Collection;
import java.util.Iterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.AbstractCollectionBackedCollection;


/**
 * <p>Generalization of common code for {@link SetBackedLockableSet}
 *   and {@link ListBackedLockableList}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractCollectionBackedLockableCollection<_ElementType_>
    extends AbstractCollectionBackedCollection<_ElementType_>
    implements LockableCollection<_ElementType_> {

  protected AbstractCollectionBackedLockableCollection(boolean nullAllowed) {
    $nullAllowed = nullAllowed;
  }



  /* <property name="null allowed"> */
  //------------------------------------------------------------------

  public final boolean isNullAllowed() {
    return $nullAllowed;
  }

  private boolean $nullAllowed;

  /*</property>*/



  /* <property name="locked"> */
  //------------------------------------------------------------------

  public final boolean isLocked() {
    return $locked;
  }

  public final void lock() {
    $locked = true;
  }

  private boolean $locked;

  /*</property>*/



  /* <section name="Inspectors"> */
  //------------------------------------------------------------------

  public abstract class CollectionBackedLockIterator
      implements LockIterator<_ElementType_> {

    protected abstract Iterator<_ElementType_> getIterator();

    public final boolean hasNext() {
      return getIterator().hasNext();
    }

    public final _ElementType_ next() {
      return getIterator().next();
    }

    public final void remove() {
      if (isLocked()) {
        throw new UnsupportedOperationException("Collection is locked");
      }
      getIterator().remove();
    }

  }

  /*</section>*/



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  public final boolean add(_ElementType_ o)
      throws NullPointerException, UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    if ((! isNullAllowed()) && (o == null)) {
      throw new NullPointerException("Null is not allowed");
    }
    return getBackingCollection().add(o);
  }

  public final boolean remove(Object o) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    return getBackingCollection().remove(o);
  }

  public final boolean addAll(Collection<? extends _ElementType_> c)
      throws NullPointerException, UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    if ((! isNullAllowed()) && (c != null) && c.contains(null)) {
      throw new NullPointerException("Null is not allowed");
    }
    return getBackingCollection().addAll(c);
  }

  public final boolean retainAll(Collection<?> c) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    return getBackingCollection().retainAll(c);
  }

  public final boolean removeAll(Collection<?> c) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    return getBackingCollection().removeAll(c);
  }

  public final void clear() throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    getBackingCollection().clear();
  }

  /*</section>*/

}