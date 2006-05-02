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
    extends AbstractCollectionBackedLockableCollection<_ElementType_, Set<_ElementType_>>
    implements LockableSet<_ElementType_> {

  /**
   * The <code>backingSet</code> should not be exposed to protect integrity
   * when {@link #isLocked()}.
   *
   * @pre backingSet != null;
   * @post new.isNullAllowed() == nullAllowed;
   * @post new.getBackingCollection() == backingSet;
   * @post ! new.isLocked();
   */
  protected SetBackedLockableSet(Set<_ElementType_> backingSet, boolean nullAllowed) {
    super(backingSet, nullAllowed);
  }

  /**
   * Create an instance backed by a fresh {@link HashSet}.
   */
  public SetBackedLockableSet(boolean nullAllowed) {
    this(new HashSet<_ElementType_>(), nullAllowed);
  }

  public final Iterator<_ElementType_> iterator() {
    return new CollectionBackedLockIterator() {

      /**
       * @invar $backingIterator != null;
       */
      private final Iterator<_ElementType_> $iterator = getBackingCollection().iterator();

      @Override
      protected Iterator<_ElementType_> getBackingIterator() {
        return $iterator;
      }

    };
  }

}