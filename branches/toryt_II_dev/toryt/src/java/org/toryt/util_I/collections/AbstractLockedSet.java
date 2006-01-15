package org.toryt.util_I.collections;


import java.util.Iterator;
import java.util.Set;


/**
 * <p>Implementation of modifying methods for a {@link LockableSet}
 *   that is locked always.</p>
 *
 * @author Jan Dockx
 *
 * @invar isLocked();
 */
public abstract class AbstractLockedSet
    extends AbstractLockedCollection
    implements LockableSet {

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



  public boolean equals(Object o) {
    return (o != null) &&
           (o instanceof Set) &&
           (size() == ((Set)o).size()) &&
           containsAll((Set)o) &&
           ((Set)o).containsAll(this);
  }

  public int hashCode() {
    int acc = 0;
    Iterator iter = iterator();
    while (iter.hasNext()) {
      Object o = iter.next();
      acc += (o == null) ? 0 : o.hashCode();
    }
    return acc;
  }

}