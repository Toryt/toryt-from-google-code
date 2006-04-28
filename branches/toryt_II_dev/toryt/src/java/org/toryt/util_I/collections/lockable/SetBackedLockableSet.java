package org.toryt.util_I.collections.lockable;


import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;



/**
 * <p>Implementation of {@link LockableCollection} and {@link Set},
 * backed by another {@link Set}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class SetBackedLockableSet extends AbstractCollectionBackedLockableCollection
    implements LockableSet {

  /**
   * @pre backingSet != null;
   * @post ! new.isLocked();
   */
  public SetBackedLockableSet(Set backingSet) {
    assert backingSet != null;
    $backingSet = backingSet;
  }

  /**
   * Create an instance backed by a fresh {@link HashSet}
   */
  public SetBackedLockableSet() {
    this(new HashSet());
  }

  protected final Set getBackingSet() {
    return $backingSet;
  }

  protected final Collection getBackingCollection() {
    return $backingSet;
  }

  /**
   * @invar $backingSet != null;
   */
  private Set $backingSet;

  public final Iterator iterator() {
    return new CollectionBackedLockIterator() {

      /**
       * @invar $backingIterator != null;
       */
      private Iterator $iterator = $backingSet.iterator();

      protected Iterator getIterator() {
        return $iterator;
      }

    };
  }

}