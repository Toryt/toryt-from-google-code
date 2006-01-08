package org.toryt.util_I.collections;


import java.util.Collection;
import java.util.Iterator;


/**
 * <p>Implementation of modifying methods for a {@link LockCollection}
 *   that is locked always.</p>
 *
 * @invar isLocked();
 */
public abstract class AbstractLockedCollection implements LockCollection {

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

  public boolean add(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public boolean remove(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public boolean addAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public boolean retainAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public boolean removeAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public void clear() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /*</section>*/


  public abstract class AbstractLockedCollectionIterator implements Iterator {

    public final void remove()  throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Set is locked");
    }

  }

}