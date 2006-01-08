package org.toryt.util_I.collections;


import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * <p>Implementation of {@link LockCollection} and {@link Set},
 * backed by another {@link Set}.</p>
 */
public class SetBackedLockSet implements LockSet {

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
  public SetBackedLockSet(Set backingSet) {
    assert backingSet != null;
    $backingSet = backingSet;
  }

  /**
   * Create an instance backed by a fresh {@link HashSet}
   */
  public SetBackedLockSet() {
    this(new HashSet());
  }

  protected final Set getBackingSet() {
    return $backingSet;
  }

  /**
   * @invar $backingSet != null;
   */
  private Set $backingSet;



  /* <property name="locked"> */
  //------------------------------------------------------------------

  public boolean isLocked() {
    return $locked;
  }

  public void lock() {
    $locked = true;
  }

  private boolean $locked;

  /*</property>*/



  /* <section name="Inspectors"> */
  //------------------------------------------------------------------

  public int size() {
    return $backingSet.size();
  }

  public boolean isEmpty() {
    return $backingSet.isEmpty();
  }

  public boolean contains(Object o) {
    return $backingSet.contains(o);
  }

  public boolean containsAll(Collection c) {
    return $backingSet.containsAll(c);
  }

  public Iterator iterator() {
    return new LockIterator() {

                 /**
                  * @invar $backingSetIterator != null;
                  */
                 private Iterator $backingSetIterator = $backingSet.iterator();

                 public boolean hasNext() {
                   return $backingSetIterator.hasNext();
                 }

                 public Object next() {
                   return $backingSetIterator.next();
                 }

                 public void remove() {
                   if (isLocked()) {
                     throw new UnsupportedOperationException("Set is locked");
                   }
                   $backingSetIterator.remove();
                 }

               };
  }

  public Object[] toArray() {
    return $backingSet.toArray();
  }

  public Object[] toArray(Object[] a) {
    return $backingSet.toArray(a);
  }

  /*</section>*/



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  public boolean add(Object o) {
    if (isLocked()) {
      throw new UnsupportedOperationException("Set is locked");
    }
    return $backingSet.add(o);
  }

  public boolean remove(Object o) {
    if (isLocked()) {
      throw new UnsupportedOperationException("Set is locked");
    }
    return $backingSet.remove(o);
  }

  public boolean addAll(Collection c) {
    if (isLocked()) {
      throw new UnsupportedOperationException("Set is locked");
    }
    return $backingSet.addAll(c);
  }

  public boolean retainAll(Collection c) {
    if (isLocked()) {
      throw new UnsupportedOperationException("Set is locked");
    }
    return $backingSet.retainAll(c);
  }

  public boolean removeAll(Collection c) {
    if (isLocked()) {
      throw new UnsupportedOperationException("Set is locked");
    }
    return $backingSet.removeAll(c);
  }

  public void clear() {
    if (isLocked()) {
      throw new UnsupportedOperationException("Set is locked");
    }
    $backingSet.clear();
  }

  /*</section>*/

}