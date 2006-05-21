package org.toryt.util_I.collections.lockable;


import java.util.ArrayList;
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
public class CollectionBackedLockableCollection<_Element_, _BackingCollection_ extends Collection<_Element_>>
    extends AbstractCollectionBackedCollection<_Element_, _BackingCollection_>
    implements LockableCollection<_Element_> {

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * The <code>backingCollection</code> should not be exposed to protect integrity
   * when {@link #isLocked()}.
   *
   * @pre backingCollection != null;
   * @post new.isNullAllowed() == nullAllowed;
   * @post new.getBackingCollection() == backingCollection;
   * @post ! new.isLocked();
   */
  protected CollectionBackedLockableCollection(_BackingCollection_ backingCollection, boolean nullAllowed) {
    super(backingCollection);
    $nullAllowed = nullAllowed;
  }

  /**
   * @post new.isNullAllowed() == nullAllowed;
   * @post ! new.isLocked();
   */
  public CollectionBackedLockableCollection(boolean nullAllowed) {
    this((_BackingCollection_)new ArrayList<_Element_>(), nullAllowed);
    /* I don't understand this warning */
    $nullAllowed = nullAllowed;
  }

  @Override
  @SuppressWarnings("unchecked")
  protected _BackingCollection_ backingCollectionClone(_BackingCollection_ backingCollection) {
    return (_BackingCollection_)new ArrayList<_Element_>(backingCollection);
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

  public abstract class CollectionBackedLockedIterator
      implements LockIterator<_Element_> {

    protected abstract Iterator<_Element_> getBackingIterator();

    public final boolean hasNext() {
      return getBackingIterator().hasNext();
    }

    /**
     * Not final, because lazy subtypes might want to override this.
     */
    public _Element_ next() {
      return getBackingIterator().next();
    }

    /**
     * Not final, because locked subclasses want to override this.
     */
    public void remove() {
      if (isLocked()) {
        throw new UnsupportedOperationException("Collection is locked");
      }
      getBackingIterator().remove();
    }

  }

  /*</section>*/



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  /**
   * Not final, because locked subclasses want to override this.
   */
  public boolean add(_Element_ o)
      throws NullPointerException, UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    if ((! isNullAllowed()) && (o == null)) {
      throw new NullPointerException("Null is not allowed");
    }
    return getBackingCollection().add(o);
  }

  /**
   * Not final, because locked subclasses want to override this.
   */
  public boolean remove(Object o) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    return getBackingCollection().remove(o);
  }

  /**
   * Not final, because locked subclasses want to override this.
   */
  public boolean addAll(Collection<? extends _Element_> c)
      throws NullPointerException, UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    if ((! isNullAllowed()) && (c != null) && c.contains(null)) {
      throw new NullPointerException("Null is not allowed");
    }
    return getBackingCollection().addAll(c);
  }

  /**
   * Not final, because locked subclasses want to override this.
   */
  public boolean retainAll(Collection<?> c) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    return getBackingCollection().retainAll(c);
  }

  /**
   * Not final, because locked subclasses want to override this.
   */
  public boolean removeAll(Collection<?> c) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    return getBackingCollection().removeAll(c);
  }

  /**
   * Not final, because locked subclasses want to override this.
   */
  public void clear() throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    getBackingCollection().clear();
  }


  public Iterator<_Element_> iterator() {
    return new CollectionBackedLockedIterator() {

      /**
       * @invar $backingIterator != null;
       */
      private final Iterator<_Element_> $iterator = getBackingCollection().iterator();

      @Override
      protected Iterator<_Element_> getBackingIterator() {
        return $iterator;
      }

    };
  }

  /*</section>*/

}