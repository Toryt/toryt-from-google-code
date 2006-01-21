package org.toryt.util_I.collections.priorityList;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.bigSet.lockable.SetBackedLockableBigSet;


/**
 * <p>A {@link PriorityList} backed by an
 *   {@link ArrayList} of {@link SetBackedLockableBigSet},
 *   backed by a {@link HashSet}, although users should not
 *   depend on this knowledge. The buckets can contain <code>null</code>.
 *   Mutation methods of the list itself are not supported. Use
 *   {@link #addPriorityElement(int, Object)} to add priority
 *   elements directly.</p>
 * <p>Instances are intended to be used in the following way:</p>
 * <pre>
 *   ArrayHashPriorityList pl = new ArrayHashPriorityList();
 *   pl.addPriorityElement(0, element1);
 *   pl.addPriorityElement(0, element2);
 *   pl.addPriorityElement(1, element3);
 *   pl.addPriorityElement(1, element4);
 *   pl.addPriorityElement(1, element5);
 *   pl.addPriorityElement(1, element6);
 *   pl.addPriorityElement(2, element7);
 *   ...
 *   pl.lock();
 *
 * @author Jan Dockx
 */
public class ArrayHashPriorityList implements PriorityList {

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
   * This method is introduced for use in {@link #subList(int, int)}.
   *
   * @pre backingList != null;
   * @pre priorityPriorityElementType != null;
   * @post ! new.isLocked();
   */
  private ArrayHashPriorityList(List backingList, Class priorityPriorityElementType)
      throws IndexOutOfBoundsException {
    assert backingList != null;
    assert priorityPriorityElementType != null;
    $backingList = backingList;
    $priorityPriorityElementType = priorityPriorityElementType;
  }

  /**
   * Create an instance backed by a fresh {@link ArrayList}
   *
   * @pre priorityPriorityElementType != null;
   * @post ! new.isLocked();
   */
  public ArrayHashPriorityList(Class priorityPriorityElementType) {
    this(new ArrayList(), priorityPriorityElementType);
  }

  /**
   * @invar $backingList != null;
   */
  private List $backingList;



  /* <property name="element type"> */
  //------------------------------------------------------------------

  public final Class getElementType() {
    return SetBackedLockableBigSet.class;
  }

  /*</property>*/



  /* <property name="priority element type"> */
  //------------------------------------------------------------------

  public final Class getPriorityElementType() {
    return $priorityPriorityElementType;
  }

  /**
   * @invar $priorityPriorityElementType != null;
   */
  private Class $priorityPriorityElementType;

  /*</property>*/



  /* <property name="locked"> */
  //------------------------------------------------------------------

  public final boolean isLocked() {
    return $locked;
  }

  public final void lock() {
    $locked = true;
    Iterator iter = iterator();
    while (iter.hasNext()) {
      SetBackedLockableBigSet sblbs = (SetBackedLockableBigSet)iter.next();
      sblbs.lock();
    }
  }

  private boolean $locked;

  /*</property>*/



  public final boolean isNullAllowed() {
    return false;
  }

  public final BigInteger getCardinality() {
    if (! isLocked()) {
      $cachedCardinality = BigInteger.ZERO;
      Iterator iter = iterator();
      while (iter.hasNext()) {
        LockableBigSet lbs = (LockableBigSet)iter.next();
        $cachedCardinality = $cachedCardinality.add(lbs.getBigSize());
      }
    }
    // otherwise, we remembered the last calculation
    return $cachedCardinality;
  }

  private BigInteger $cachedCardinality;


  /**
   * @pre priority >= 0;
   * @post get(priority).contains(o);
   * @throws ClassCastException
   *         (o != null) ? (! getPriorityElementType().isInstance(o));
   * @throws UnsupportedOperationException
   *         isLocked();
   * @throws UnsupportedOperationException
   *         get(priority).isLocked();
   */
  public final void addPriorityElement(int priority, Object o)
      throws ClassCastException, UnsupportedOperationException {
    assert priority >= 0;
    if (isLocked()) {
      throw new UnsupportedOperationException("priority list is locked");
    }
    if ((o != null) && (! getPriorityElementType().isInstance(o))) {
      throw new ClassCastException(o + " is not of type " + getPriorityElementType());
    }
    LockableBigSet lbsP = null;
    if (priority < size()) {
      lbsP = (LockableBigSet)get(priority);
    }
    if (lbsP == null) {
      lbsP = createFreshBucket(priority);
    }
    assert lbsP != null;
    lbsP.add(o);
  }

  public final boolean containsPriorityElement(final Object o) {
    return Collections.exists(this,
                               new Assertion() {
                                      public boolean isTrueFor(Object pb) {
                                        LockableBigSet lbs = (LockableBigSet)pb;
                                        return lbs.contains(o);
                                      }
                                    });
  }

  public final Iterator priorityElementIterator() {
    return new Iterator() {

                  /**
                   * @invar $listIter != null;
                   */
                  private Iterator $listIter = iterator();

                  private Iterator $bucketIter;

                  {
                    prepareNext();
                  }

                  private void prepareNext() {
                    assert ($bucketIter == null) || (! $bucketIter.hasNext());
                    $bucketIter = null;
                    assert $bucketIter == null;
                    while (($bucketIter == null) && ($listIter.hasNext())) {
                      LockableBigSet nextBucket = (LockableBigSet)$listIter.next();
                      if (! nextBucket.isEmpty()) {
                        $bucketIter = nextBucket.iterator();
                      }
                    }
                    assert ($bucketIter == null) ? (! $listIter.hasNext()) : $bucketIter.hasNext();
                  }

                  public boolean hasNext() {
                    return $bucketIter != null;
                  }

                  public Object next() {
                    Object result = $bucketIter.next();
                    if (! $bucketIter.hasNext()) {
                      prepareNext();
                    }
                    return result;
                  }

                  public final void remove()  throws UnsupportedOperationException {
                    throw new UnsupportedOperationException("Set is locked");
                  }

                };
  }


  private SetBackedLockableBigSet createFreshBucket() {
    SetBackedLockableBigSet lbsP = new SetBackedLockableBigSet(getPriorityElementType(), true);
    if (isLocked()) {
      lbsP.lock();
    }
    return lbsP;
  }

  private SetBackedLockableBigSet createFreshBucket(int priority) {
    SetBackedLockableBigSet lbsP = createFreshBucket();
    set(priority, lbsP);
    return lbsP;
  }

  private SetBackedLockableBigSet createFreshBucket(ListIterator li) {
    SetBackedLockableBigSet lbsP = createFreshBucket();
    li.set(lbsP);
    return lbsP;
  }


  /* <section name="inspectors"> */
  //------------------------------------------------------------------

  public final Object get(int index) {
    SetBackedLockableBigSet lbs = (SetBackedLockableBigSet)$backingList.get(index);
      // IndexOutOfBoundsException
    if (lbs == null) {
      lbs = createFreshBucket(index);
    }
    return lbs;
  }

  public final int indexOf(Object o) {
    return (o != null) ? $backingList.indexOf(o) : -1;
  }

  public final int lastIndexOf(Object o) {
    return (o != null) ? $backingList.lastIndexOf(o) : -1;
  }

  public final int size() {
    return $backingList.size();
  }

  public final boolean isEmpty() {
    return $backingList.isEmpty();
  }

  public final boolean contains(Object o) {
    return (o != null) && $backingList.contains(o);
  }

  public final boolean containsAll(Collection c) {
    return (! c.contains(null)) && $backingList.containsAll(c);
  }

  public final Object[] toArray() {
    return toArray(new Object[size()]);
  }

  public final Object[] toArray(Object[] a) {
    int size = size();
    Object[] result;
    if (a.length >= size) {
      result = a;
      result[size] = null;
    }
    else {
      result = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
    }
    Iterator iter = iterator();
    int i = 0;
    while (iter.hasNext()) {
      result[i] = iter.next();
      i++;
    }
    return result;
  }

  public final Iterator iterator() {
    return listIterator();
  }

  public final ListIterator listIterator() {
    return listIterator(0);
  }

  public final ListIterator listIterator(int index) {
    return new ArrayHashListIterator(index);
  }

  public final List subList(int fromIndex, int toIndex) {
    ArrayHashPriorityList result =
        new ArrayHashPriorityList($backingList.subList(fromIndex, toIndex),
                                  getPriorityElementType());
    if (isLocked()) {
      result.lock();
    }
    return result;
  }

  public final boolean equals(Object o) {
    return $backingList.equals(o);
  }

  public final int hashCode() {
    return $backingList.hashCode();
  }

  /*</section>*/



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  public boolean add(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, Object element) instead");
  }

  public void add(int index, Object element) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, Object element) instead");
  }

  public Object set(int index, Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, Object element) instead");
  }

  public boolean addAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, Object element) instead");
  }

  public boolean addAll(int index, Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, Object element) instead");
  }

  public boolean remove(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("removal not supported");
  }

  public Object remove(int index) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("removal not supported");
  }

  public boolean removeAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("removal not supported");
  }

  public boolean retainAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("removal not supported");
  }

  public void clear() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("removal not supported");
  }

  public class ArrayHashListIterator
      implements ListIterator {

    private ArrayHashListIterator(int index) {
      $backingIterator = $backingList.listIterator(index);
    }

    private ListIterator $backingIterator;

    public final void add(Object o) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("use addPriorityElement(int priority, Object element) instead");
    }

    public final void set(Object o) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("use addPriorityElement(int priority, Object element) instead");
    }

    public final void remove() throws UnsupportedOperationException {
      throw new UnsupportedOperationException("removal not supported");
    }

    public final boolean hasNext() {
      return $backingIterator.hasNext();
    }

    public final Object next() {
      SetBackedLockableBigSet next = (SetBackedLockableBigSet)$backingIterator.next();
      if (next == null) {
        next = createFreshBucket(this);
      }
      return next;
    }

    public final boolean hasPrevious() {
      return $backingIterator.hasPrevious();
    }

    public final Object previous() {
      return $backingIterator.previous();
    }

    public final int nextIndex() {
      return $backingIterator.nextIndex();
    }

    public final int previousIndex() {
      return $backingIterator.previousIndex();
    }

  }

  /*</section>*/

}