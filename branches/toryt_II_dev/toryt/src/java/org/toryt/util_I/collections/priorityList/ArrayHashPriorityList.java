package org.toryt.util_I.collections.priorityList;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.BigSet;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.bigSet.lockable.SetBackedLockableBigSet;
import org.toryt.util_I.collections.lockable.ListBackedLockableList;


/**
 * <p>A {@link PriorityList} backed by an
 *   {@link ArrayList} of {@link SetBackedLockableBigSet}s,
 *   backed by a {@link HashSet} (users should not
 *   depend on this knowledge). The buckets can contain <code>null</code>.
 *   Use {@link #addPriorityElement(int, Object)} to add priority
 *   elements directly. The {@link List} mutators will always throw
 *   an {@link UnsupportedOperationException}. You can use
 *   mutators on element {@link LockableBigSet LockableBigSets} if
 *   they are not locked. Locking this list als locks the
 *   element {@link LockableBigSet LockableBigSets}.</p>
 * <p>Instances are intended to be used in the following way:</p>
 * <pre>
 *   ArrayHashPriorityList<_PriorityElementType_> pl =
 *          new ArrayHashPriorityList<_PriorityElementType_>();
 *   pl.addPriorityElement(0, element1);
 *   pl.addPriorityElement(0, element2);
 *   pl.addPriorityElement(1, element3);
 *   pl.addPriorityElement(1, element4);
 *   pl.addPriorityElement(1, element5);
 *   pl.addPriorityElement(1, element6);
 *   pl.addPriorityElement(2, element7);
 *   ...
 *   pl.lock();
 * </pre>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ArrayHashPriorityList<_PriorityElement_>
    extends ListBackedLockableList<LockableBigSet<? extends _PriorityElement_>>
    implements PriorityList<_PriorityElement_> {

  /* we start with an empty backing list, and we make sure in all setters
   * that, from 0 to size(), there are never nulls, but empty sets instead */

  /**
   * The <code>backingList</code> should not be exposed to protect integrity
   * when {@link #isLocked()}.
   *
   * @pre backingList != null;
   * @pre Collections.noNull(backingList);
   * @post new.isNullAllowed() == false;
   * @post (forall LockableBigSet<_PriorityElementType_> sblb; this.contains(sblb);
   *            sblb.isNullAllowed() == nullAllowed);
   * @post ! new.isLocked();
   * @post (forall LockableBigSet<_PriorityElementType_> sblb; this.contains(sblb);
   *            ! sblb.isLocked());
   * @post new.getBackingCollection() == backingList;
   */
  private ArrayHashPriorityList(List<LockableBigSet<? extends _PriorityElement_>> backingList,
                                  boolean nullAllowed) {
    super(backingList, false);
    assert Collections.noNull(backingList);
    $nullPriorityElementAllowed = nullAllowed;
  }

  /**
   * Create an instance backed by a fresh {@link ArrayList}.
   *
   * @post new.isNullAllowed() == false;
   * @post (forall LockableBigSet<_PriorityElementType_> sblb; this.contains(sblb);
   *            sblb.isNullAllowed() == nullAllowed);
   * @post ! new.isLocked();
   * @post (forall LockableBigSet<_PriorityElementType_> sblb; this.contains(sblb);
   *            ! sblb.isLocked());
   */
  public ArrayHashPriorityList(boolean nullAllowed) {
    super(false);
    $nullPriorityElementAllowed = nullAllowed;
  }



  /* <property name="nullPriorityElementAllowed"> */
  //------------------------------------------------------------------

  public final boolean isNullPriorityElementAllowed() {
    return $nullPriorityElementAllowed;
  }

  private boolean $nullPriorityElementAllowed;

  /*</property>*/



  /* <property name="locked"> */
  //------------------------------------------------------------------

  @Override
  protected final void extraLock() {
    Iterator<LockableBigSet<? extends _PriorityElement_>> iter = iterator();
    while (iter.hasNext()) {
      try {
        SetBackedLockableBigSet<? extends _PriorityElement_> lbs =
            (SetBackedLockableBigSet<? extends _PriorityElement_>)iter.next();
        lbs.lock();
      }
      catch (ClassCastException ccExc) {
        assert false : "cannot happen; see source for explanation";
      /* old style; we cannot enforce that we only use
         SetBackedLockableBigSet<_PriorityElementType_>
         in this list by generics; if we do, we need to
         make the generic definition of PriorityList to allow
         for subtypes of LockableBigSet static typing:
         extends LockableList<? extends LockableBigSet<? extends _PriorityElementType_>>
         instead of
         extends LockableList<LockableBigSet<? extends _PriorityElementType_>>
         but, we cannot use ? in an extends clause of a class
         (don't know why, actually).
         Without that, we must use extends LockableList<LockableBigSet<? extends _PriorityElementType_>>
         here, because we reach LockableList via PriorityList and via
         ListBackedLockableList, and via both paths, we need to get at the same
         generic definition of the _ElementType_ of LockableList,
         which is defined hard via the PriorityList to
         LocableBigSet<_PriorityElementType_>.
         So, although we know that the backing list will always contain
         instances of type SetBackedLockableBigSet<_PriorityElementType_>,
         we cannot say that statically (using generics). This is the only
         place in this file where this is important. We are using
         a cast here to solve this. */
      }
    }
    getCardinality(); // calculate and cache
  }

  /*</property>*/




  public final BigInteger getCardinality() {
    if (! isLocked()) {
      $cachedCardinality = BigInteger.ZERO;
      Iterator<LockableBigSet<? extends _PriorityElement_>> iter = iterator();
      while (iter.hasNext()) {
        LockableBigSet<? extends _PriorityElement_> lbs = iter.next();
        $cachedCardinality = $cachedCardinality.add(lbs.getBigSize());
      }
    }
    // otherwise, we remembered the last calculation
    return $cachedCardinality;
  }

  private BigInteger $cachedCardinality;


  /**
   * Only working modification method for this class.
   *
   * @pre priority >= 0;
   * @post get(priority).contains(o);
   * @throws UnsupportedOperationException
   *         isLocked();
   * @throws UnsupportedOperationException
   *         get(priority).isLocked();
   */
  public final void addPriorityElement(int priority, _PriorityElement_ o)
      throws UnsupportedOperationException, NullPointerException {
    assert priority >= 0;
    // take care to fill intermediate nulls with empty sets
    if (isLocked()) {
      throw new UnsupportedOperationException("priority list is locked");
    }
    SetBackedLockableBigSet<_PriorityElement_> lbs= null;
    if (priority < size()) {
      lbs = (SetBackedLockableBigSet<_PriorityElement_>)get(priority);
      /* ok in this implementation: we know this will only contain
       * SetBackedLockableBigSet<_PriorityElementType_> with that precise
       * type in this class (this isn't true for PriorityLists in general).
       */
    }
    if (lbs == null) {
      lbs = fillWithEmptyBucketsUpUntil(priority);
        // possible UnsupportedOperationException
    }
    assert get(priority) == lbs;
    assert Collections.noNull(getBackingCollection().subList(0, priority + 1));
    lbs.add(o); // possible UnsupportedOperationException, NullPointerException
  }

  private SetBackedLockableBigSet<_PriorityElement_> createFreshBucket() {
    SetBackedLockableBigSet<_PriorityElement_> lbsP =
        new SetBackedLockableBigSet<_PriorityElement_>(isNullPriorityElementAllowed());
    if (isLocked()) {
      lbsP.lock();
    }
    return lbsP;
  }

  /**
   * @return new.get(priority);
   * @result new.size() > priority;
   */
  private SetBackedLockableBigSet<_PriorityElement_> fillWithEmptyBucketsUpUntil(int priority) {
    assert priority >= getBackingCollection().size();
    assert ! isLocked();
    SetBackedLockableBigSet<_PriorityElement_> lbs = null;
    while (getBackingCollection().size() <= priority) {
      // add to the end of the list until it is large enough
      lbs = createFreshBucket();
      getBackingCollection().add(lbs);
    }
    // return last added lbs
    return lbs;
  }



  public final boolean containsPriorityElement(final Object o) {
    return Collections.exists(this,
                               new Assertion<Set<? extends _PriorityElement_>>() {
                                      public boolean isTrueFor(Set<? extends _PriorityElement_> s) {
                                        return s.contains(o);
                                      }
                                    });
  }

  public final Iterator<_PriorityElement_> priorityElementIterator() {
    return new Iterator<_PriorityElement_>() {

                  /**
                   * @invar $listIter != null;
                   */
                  private Iterator<LockableBigSet<? extends _PriorityElement_>> $listIter = iterator();

                  private Iterator<? extends _PriorityElement_> $bucketIter;

                  {
                    prepareNext();
                  }

                  private void prepareNext() {
                    assert ($bucketIter == null) || (! $bucketIter.hasNext());
                    $bucketIter = null;
                    assert $bucketIter == null;
                    while (($bucketIter == null) && ($listIter.hasNext())) {
                      Set<? extends _PriorityElement_> nextBucket = $listIter.next();
                      assert nextBucket != null;
                      if (! nextBucket.isEmpty()) {
                        $bucketIter = nextBucket.iterator();
                      }
                    }
                    assert ($bucketIter == null) ? (! $listIter.hasNext()) : $bucketIter.hasNext();
                  }

                  public boolean hasNext() {
                    return $bucketIter != null;
                  }

                  public _PriorityElement_ next() {
                    _PriorityElement_ result = $bucketIter.next();
                    if (! $bucketIter.hasNext()) {
                      prepareNext();
                    }
                    return result;
                  }

                  public final void remove()  throws UnsupportedOperationException {
                    if (isLocked()) {
                      throw new UnsupportedOperationException("Collection is locked");
                    }
                    $bucketIter.remove();
                  }

                };
  }


  /* <section name="inspectors"> */
  //------------------------------------------------------------------

  @Override
  public final ArrayHashPriorityList<_PriorityElement_> subList(int fromIndex, int toIndex) {
    ArrayHashPriorityList<_PriorityElement_> result =
        new ArrayHashPriorityList<_PriorityElement_>(getBackingCollection().subList(fromIndex, toIndex),
                                                         isNullPriorityElementAllowed());
    if (isLocked()) {
      result.lock();
    }
    return result;
  }

  /*</section>*/



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  // all modifying operations always throw UnsupportedOperationException
  // also, the listIterator modification operations are not allowed

  /**
   * @deprecated use {@link #addPriorityElement(int, Object)} instead
   */
  @Override
  @Deprecated
  public final boolean add(LockableBigSet<? extends _PriorityElement_> o)
      throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, _ElementType o) instead");
  }

  /**
   * @deprecated use {@link #addPriorityElement(int, Object)} instead
   */
  @Override
  @Deprecated
  public final boolean remove(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, _ElementType o) instead");
  }

  /**
   * @deprecated use {@link #addPriorityElement(int, Object)} instead
   */
  @Override
  @Deprecated
  public final boolean addAll(Collection<? extends LockableBigSet<? extends _PriorityElement_>> c)
      throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, _ElementType o) instead");
  }

  /**
   * @deprecated use {@link #addPriorityElement(int, Object)} instead
   */
  @Override
  @Deprecated
  public final boolean retainAll(Collection<?> c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, _ElementType o) instead");
  }

  /**
   * @deprecated use {@link #addPriorityElement(int, Object)} instead
   */
  @Override
  @Deprecated
  public final boolean removeAll(Collection<?> c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, _ElementType o) instead");
  }

  /**
   * @deprecated use {@link #addPriorityElement(int, Object)} instead
   */
  @Override
  @Deprecated
  public final void clear() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, _ElementType o) instead");
  }

  /**
   * @deprecated use {@link #addPriorityElement(int, Object)} instead
   */
  @Override
  @Deprecated
  public final void add(int index, LockableBigSet<? extends _PriorityElement_> o)
      throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, _ElementType o) instead");
  }

  /**
   * @deprecated use {@link #addPriorityElement(int, Object)} instead
   */
  @Override
  @Deprecated
  public boolean addAll(int index, Collection<? extends LockableBigSet<? extends _PriorityElement_>> c)
      throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, _ElementType o) instead");
  }

  /**
   * @deprecated use {@link #addPriorityElement(int, Object)} instead
   */
  @Override
  @Deprecated
  public final LockableBigSet<? extends _PriorityElement_> set(int index, LockableBigSet<? extends _PriorityElement_> o)
      throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, _ElementType o) instead");
  }

  /**
   * @deprecated use {@link #addPriorityElement(int, Object)} instead
   */
  @Override
  @Deprecated
  public final LockableBigSet<? extends _PriorityElement_> remove(int index)
      throws UnsupportedOperationException {
    throw new UnsupportedOperationException("use addPriorityElement(int priority, _ElementType o) instead");
  }

  public class LockedListIterator extends ListBackedLockableListIterator {

    protected LockedListIterator(int index) {
      super(index);
    }

    /**
     * @deprecated use {@link ArrayHashPriorityList#addPriorityElement(int, Object)} instead
     */
    @Override
    @Deprecated
    public final void set(LockableBigSet<? extends _PriorityElement_> o) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("use addPriorityElement(int priority, _ElementType o) instead");
    }

    /**
     * @deprecated use {@link ArrayHashPriorityList#addPriorityElement(int, Object)} instead
     */
    @Override
    @Deprecated
    public final void add(LockableBigSet<? extends _PriorityElement_> o) throws UnsupportedOperationException {
      throw new UnsupportedOperationException("use addPriorityElement(int priority, _ElementType o) instead");
    }

    /**
     * @deprecated use {@link ArrayHashPriorityList#addPriorityElement(int, Object)} instead
     */
    @Override
    @Deprecated
    public void remove() throws UnsupportedOperationException {
      throw new UnsupportedOperationException("use addPriorityElement(int priority, _ElementType o) instead");
    }

  }

  @Override
  public final ListIterator<LockableBigSet<? extends _PriorityElement_>> listIterator(int index) {
    return new LockedListIterator(index);
  }


  /*</section>*/

  @Override
  public String toString() {
    StringBuffer result = new StringBuffer();
    ListIterator<LockableBigSet<? extends _PriorityElement_>> iter = listIterator();
    while (iter.hasNext()) {
      BigSet<? extends _PriorityElement_> bs = iter.next();
      result.append(iter.previousIndex());
      result.append(" (");
      result.append(bs.getBigSize());
      result.append("): ");
      Iterator<? extends _PriorityElement_> bsIter = bs.iterator();
      while (bsIter.hasNext()) {
        result.append(bsIter.next());
        if (bsIter.hasNext()) {
          result.append(", ");
        }
      }
      result.append("\n");
    }
    return result.toString();
  }

}