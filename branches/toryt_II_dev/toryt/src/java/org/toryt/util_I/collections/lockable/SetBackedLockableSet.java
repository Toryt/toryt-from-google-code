package org.toryt.util_I.collections.lockable;


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
public class SetBackedLockableSet<_ElementType_>
    extends AbstractCollectionBackedLockableCollection<_ElementType_>
    implements LockableSet<_ElementType_> {

  /**
   * @pre backingSet != null;
   * @post ! new.isLocked();
   */
  public SetBackedLockableSet(Set<_ElementType_> backingSet, boolean nullAllowed) {
    super(nullAllowed);
    assert backingSet != null;
    $backingSet = backingSet;
  }

  /**
   * Create an instance backed by a fresh {@link HashSet}
   */
  public SetBackedLockableSet(boolean nullAllowed) {
    this(new HashSet<_ElementType_>(), nullAllowed);
  }

  @Override
  protected final Set<_ElementType_> getBackingCollection() {
    return $backingSet;
  }

  /**
   * @invar $backingSet != null;
   */
  private Set<_ElementType_> $backingSet;

  public final Iterator<_ElementType_> iterator() {
    return new CollectionBackedLockIterator() {

      /**
       * @invar $backingIterator != null;
       */
      private Iterator<_ElementType_> $iterator = $backingSet.iterator();

      @Override
      protected Iterator<_ElementType_> getIterator() {
        return $iterator;
      }

    };
  }

}