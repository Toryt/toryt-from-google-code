package org.toryt.util_I.collections;


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
           containsAll((List)o) &&
           ((List)o).containsAll(this);
  }

  public int hashCode() {
    int hashCode = 1;
    Iterator i = iterator();
    while (i.hasNext()) {
        Object obj = i.next();
        hashCode = 31*hashCode + (obj==null ? 0 : obj.hashCode());
    }
    return hashCode;
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