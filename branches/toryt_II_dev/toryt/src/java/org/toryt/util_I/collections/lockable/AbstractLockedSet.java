package org.toryt.util_I.collections.lockable;


import java.util.Iterator;
import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;



/**
 * <p>Implementation of modifying methods for a {@link LockableSet}
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
public abstract class AbstractLockedSet
    extends AbstractLockedCollection
    implements LockableSet {

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