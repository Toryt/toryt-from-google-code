package org.toryt.util_I.collections;


import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * <p>Implementation of {@link NoNullCollection} and {@link Set}
 *   through a backing set.</p>
 */
public class SetBackedNoNullSet implements NoNullSet {

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
   */
  public SetBackedNoNullSet(Set backingSet) {
    assert backingSet != null;
    $backingSet = backingSet;
  }

  /**
   * Create an instance backed by a fresh {@link HashSet}.
   */
  public SetBackedNoNullSet() {
    this(new HashSet());
  }

  protected final Set getBackingSet() {
    return $backingSet;
  }

  /**
   * @invar $backingSet != null;
   */
  private Set $backingSet;



  /* <section name="Inspectors"> */
  //------------------------------------------------------------------

  public int size() {
    return $backingSet.size();
  }

  public boolean isEmpty() {
    return $backingSet.isEmpty();
  }

  public boolean contains(Object o) {
    return (o != null) && $backingSet.contains(o);
  }

  public boolean containsAll(Collection c) {
    return $backingSet.containsAll(c);
  }

  public Iterator iterator() {
    return $backingSet.iterator();
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

  public boolean add(Object o) throws NullPointerException {
    if (o == null) {
      throw new NullPointerException("Null is not allowed");
    }
    return $backingSet.add(o);
  }

  public boolean remove(Object o) {
    return $backingSet.remove(o);
  }

  public boolean addAll(Collection c) throws NullPointerException {
    if ((c != null) && c.contains(null)) {
      throw new NullPointerException("Null is not allowed");
    }
    return $backingSet.addAll(c);
  }

  public boolean retainAll(Collection c) {
    return $backingSet.retainAll(c);
  }

  public boolean removeAll(Collection c) {
    return $backingSet.removeAll(c);
  }

  public void clear() {
    $backingSet.clear();
  }

  /*</section>*/

}