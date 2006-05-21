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
public class SetBackedLockableSet<_Element_>
    extends AbstractCollectionBackedLockableCollection<_Element_, Set<_Element_>>
    implements LockableSet<_Element_> {

  /**
   * The <code>backingSet</code> should not be exposed to protect integrity
   * when {@link #isLocked()}.
   *
   * @pre backingSet != null;
   * @post new.isNullAllowed() == nullAllowed;
   * @post new.getBackingCollection() == backingSet;
   * @post ! new.isLocked();
   */
  protected SetBackedLockableSet(Set<_Element_> backingSet, boolean nullAllowed) {
    super(backingSet, nullAllowed);
  }

  @Override
  protected Set<_Element_> backingCollectionClone(Set<_Element_> backingCollection) {
    return new HashSet<_Element_>(backingCollection);
  }

  /**
   * Create an instance backed by a fresh {@link HashSet}.
   */
  public SetBackedLockableSet(boolean nullAllowed) {
    this(new HashSet<_Element_>(), nullAllowed);
  }

  public final Iterator<_Element_> iterator() {
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
}