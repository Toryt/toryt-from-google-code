package org.toryt.util_I.collections.lockable;


import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



/**
 * <p>Implementation of {@link LockableCollection} and {@link Set},
 * backed by another {@link Set}.</p>
 *
 * @author Jan Dockx
 */
public class SetBackedLockableSet extends AbstractCollectionBackedLockableCollection
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