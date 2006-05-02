package org.toryt.util_I.collections.lockable;


import java.util.Collection;
import java.util.Iterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.AbstractCollectionBackedCollection;


/**
 * <p>Generalization of common code for {@link SetBackedLockableSet}
 *   and {@link ListBackedLockableList}. The backing collection
 *   needs to be kept private, to ensure correct behavior when
 *   {@link #isLocked()}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractCollectionBackedLockableCollection<_ElementType_, _BackingCollection_ extends Collection<_ElementType_>>
    extends AbstractCollectionBackedCollection<_ElementType_>
    implements LockableCollection<_ElementType_> {

  /**
   * The <code>backingCollection</code> should not be exposed to protect integrity
   * when {@link #isLocked()}.
   *
   * @pre backingCollection != null;
   * @post new.isNullAllowed() == nullAllowed;
   * @post new.getBackingCollection() == backingCollection;
   * @post ! new.isLocked();
   */
  protected AbstractCollectionBackedLockableCollection(_BackingCollection_ backingCollection, boolean nullAllowed) {
    assert backingCollection != null;
    $nullAllowed = nullAllowed;
    $backingCollection = backingCollection;
  }

  @Override
  protected final _BackingCollection_ getBackingCollection() {
    return $backingCollection;
  }

  /**
   * @invar $backingCollection != null;
   */
  private _BackingCollection_ $backingCollection;


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
    extraLock();
    $locked = true;
  }

  /**
   * Extra lock activity.
   * This implementation does nothing, but subclasses might do more.
   */
  protected void extraLock() {
    // NOP
  }

  private boolean $locked;

  /*</property>*/



  /* <section name="Inspectors"> */
  //------------------------------------------------------------------

  public abstract class CollectionBackedLockIterator
      implements LockIterator<_ElementType_> {

    protected abstract Iterator<_ElementType_> getBackingIterator();

    public final boolean hasNext() {
      return getBackingIterator().hasNext();
    }

    /**
     * Not final, because lazy subtypes might want to override this.
     */
    public _ElementType_ next() {
      return getBackingIterator().next();
    }

    public final void remove() {
      if (isLocked()) {
        throw new UnsupportedOperationException("Collection is locked");
      }
      getBackingIterator().remove();
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