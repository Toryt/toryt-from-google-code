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
public abstract class AbstractLockedSet<_Element_>
    extends AbstractLockedCollection<_Element_>
    implements LockableSet<_Element_> {

  protected AbstractLockedSet(boolean nullAllowed) {
    super(nullAllowed);
  }

  @Override
  public boolean equals(Object o) {
    return (o != null) &&
           (o instanceof Set<?>) &&
           (size() == ((Set<?>)o).size()) &&
           containsAll((Set<?>)o) &&
           ((Set<?>)o).containsAll(this);
  }

  @Override
  public int hashCode() {
    int acc = 0;
    Iterator<_Element_> iter = iterator();
    while (iter.hasNext()) {
      _Element_ o = iter.next();
      acc += (o == null) ? 0 : o.hashCode();
    }
    return acc;
  }

}