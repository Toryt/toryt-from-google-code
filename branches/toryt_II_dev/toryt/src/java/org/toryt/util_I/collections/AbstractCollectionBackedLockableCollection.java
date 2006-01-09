package org.toryt.util_I.collections;


import java.util.Collection;
import java.util.Iterator;


/**
 * <p>Generalization of common code for {@link SetBackedLockableSet}
 *   and {@link ListBackedLockableList}.</p>
 *
 * @author Jan Dockx
 */
public abstract class AbstractCollectionBackedLockableCollection
    extends AbstractCollectionBackedCollection
    implements LockableCollection {

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

  public abstract class CollectionBackedLockIterator implements LockIterator {

    protected abstract Iterator getIterator();
    
    public final boolean hasNext() {
      return getIterator().hasNext();
    }

    public final Object next() {
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

  public final boolean add(Object o) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    return getBackingCollection().add(o);
  }

  public final boolean remove(Object o) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    return getBackingCollection().remove(o);
  }

  public final boolean addAll(Collection c) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    return getBackingCollection().addAll(c);
  }

  public final boolean retainAll(Collection c) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Collection is locked");
    }
    return getBackingCollection().retainAll(c);
  }

  public final boolean removeAll(Collection c) throws UnsupportedOperationException {
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