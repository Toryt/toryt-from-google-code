package org.toryt.util_I.collections.lockable;


import java.util.Collection;
import java.util.Iterator;



/**
 * <p>Implementation of modifying methods for a {@link LockableCollection}
 *   that is locked always.</p>
 *
 * @author Jan Dockx
 *
 * @invar isLocked();
 */
public abstract class AbstractLockedCollection implements LockableCollection {

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
    return true;
  }

  /*</property>*/



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  public final boolean add(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public final boolean remove(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public final boolean addAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public final boolean retainAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public final boolean removeAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public final void clear() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /*</section>*/


  public abstract class AbstractLockedCollectionIterator implements Iterator {

    public final void remove()  throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Set is locked");
    }

  }

}