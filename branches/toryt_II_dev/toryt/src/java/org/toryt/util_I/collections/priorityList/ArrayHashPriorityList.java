package org.toryt.util_I.collections.priorityList;


import java.math.BigInteger;
import java.util.ArrayList;
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
 *   elements directly.</p>
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
public class ArrayHashPriorityList<_PriorityElementType_>
    extends ListBackedLockableList<LockableBigSet<_PriorityElementType_>>
    implements PriorityList<_PriorityElementType_> {

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
  protected ArrayHashPriorityList(List<LockableBigSet<_PriorityElementType_>> backingList,
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
    Iterator<LockableBigSet<_PriorityElementType_>> iter = iterator();
    while (iter.hasNext()) {
      try {
        SetBackedLockableBigSet<_PriorityElementType_> lbs =
            (SetBackedLockableBigSet<_PriorityElementType_>)iter.next();
        lbs.lock();
      }
      catch (ClassCastException ccExc) {
        assert false : "cannot happen; see source for explanation";
      /* old style; we cannot enforce that we only use
         SetBackedLockableBigSet<_PriorityElementType_>
         in this list by generics; if we do, we need to
         make the generic definition of PriorityList to allow
         for subtypes of LockableBigSet static typing:
         extends LockableList<? extends LockableBigSet<_PriorityElementType_>>
         instead of
         extends LockableList<LockableBigSet<_PriorityElementType_>>
         but, we cannot use ? in an extends clause of a class
         (don't know why, actually).
         Without that, we must use extends LockableList<LockableBigSet<_PriorityElementType_>>
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
      Iterator<LockableBigSet<_PriorityElementType_>> iter = iterator();
      while (iter.hasNext()) {
        BigSet<_PriorityElementType_> lbs = iter.next();
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
   * @throws UnsupportedOperationException
   *         isLocked();
   * @throws UnsupportedOperationException
   *         get(priority).isLocked();
   */
  public final void addPriorityElement(int priority, _PriorityElementType_ o)
      throws UnsupportedOperationException, NullPointerException {
    assert priority >= 0;
    // take care to fill intermediate nulls with empty sets
    if (isLocked()) {
      throw new UnsupportedOperationException("priority list is locked");
    }
    LockableBigSet<_PriorityElementType_> lbs= null;
    if (priority < size()) {
      lbs = get(priority);
    }
    if (lbs == null) {
      lbs = fillWithEmptyBucketsUpUntil(priority);
        // possible UnsupportedOperationException
    }
    assert Collections.noNull(getBackingCollection().subList(0, priority + 1));
    // TODO there is workaround here, that might be solved in eclipse 3.2
    //    lbs.add(o); // possible UnsupportedOperationException, NullPointerException
    /* compilation error in eclipse;
     * "The method add(_PriorityElementType_) is ambiguous for the type
     * LockableBigSet<_PriorityElementType_>"
     *
     * This might be an error in the compiler:
     * https://bugs.eclipse.org/bugs/show_bug.cgi?id=100869
     * Not solved in 3.1, but described to be fixed in 3.2
     *
     * Workaround: static upcast (SetBackedLockableBigSet code executed via
     * dynamic binding anyway).
     */
    ((BigSet<_PriorityElementType_>)lbs).add(o);
    /* END OF WORKAROUND */
  }

  public final boolean containsPriorityElement(final Object o) {
    return Collections.exists(this,
                               new Assertion<Set<_PriorityElementType_>>() {
                                      public boolean isTrueFor(Set<_PriorityElementType_> s) {
                                        return s.contains(o);
                                      }
                                    });
  }

  public final Iterator<_PriorityElementType_> priorityElementIterator() {
    return new Iterator<_PriorityElementType_>() {

                  /**
                   * @invar $listIter != null;
                   */
                  private Iterator<LockableBigSet<_PriorityElementType_>> $listIter = iterator();

                  private Iterator<_PriorityElementType_> $bucketIter;

                  {
                    prepareNext();
                  }

                  private void prepareNext() {
                    assert ($bucketIter == null) || (! $bucketIter.hasNext());
                    $bucketIter = null;
                    assert $bucketIter == null;
                    while (($bucketIter == null) && ($listIter.hasNext())) {
                      Set<_PriorityElementType_> nextBucket = $listIter.next();
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

                  public _PriorityElementType_ next() {
                    _PriorityElementType_ result = $bucketIter.next();
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


  private LockableBigSet<_PriorityElementType_> createFreshBucket() {
    SetBackedLockableBigSet<_PriorityElementType_> lbsP =
        new SetBackedLockableBigSet<_PriorityElementType_>(isNullPriorityElementAllowed());
    if (isLocked()) {
      lbsP.lock();
    }
    return lbsP;
  }

  /**
   * @return new.get(priority);
   * @result new.size() > priority;
   */
  private LockableBigSet<_PriorityElementType_> fillWithEmptyBucketsUpUntil(int priority) {
    assert priority >= getBackingCollection().size();
    assert ! isLocked();
    LockableBigSet<_PriorityElementType_> lbs = null;
    while (getBackingCollection().size() <= priority) {
      // add to the end of the list until it is large enough
      lbs = createFreshBucket();
      getBackingCollection().add(lbs);
    }
    // return last added lbs
    return lbs;
  }



  /* <section name="inspectors"> */
  //------------------------------------------------------------------

  @Override
  public final ArrayHashPriorityList<_PriorityElementType_> subList(int fromIndex, int toIndex) {
    ArrayHashPriorityList<_PriorityElementType_> result =
        new ArrayHashPriorityList<_PriorityElementType_>(getBackingCollection().subList(fromIndex, toIndex),
                                                         isNullPriorityElementAllowed());
    if (isLocked()) {
      result.lock();
    }
    return result;
  }

  /*</section>*/



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  /* don't need to overwrite add(E), addAll(Collection<? extends E>):
   * add won't accept null, and only of correct type, and added to the end,
   * so no need to add intermediate empty sets.
   */

  /* when we are locked, nothing can be added; when we are not locked,
   * adding a locked lbs is ok
   */

  /* No need to override index based mutators: they all throw IndexOutOfBounds when used beyond size().
   */

  /*</section>*/

  @Override
  public String toString() {
    StringBuffer result = new StringBuffer();
    ListIterator<LockableBigSet<_PriorityElementType_>> iter = listIterator();
    while (iter.hasNext()) {
      BigSet<_PriorityElementType_> bs = iter.next();
      result.append(iter.previousIndex());
      result.append(" (");
      result.append(bs.getBigSize());
      result.append("): ");
      Iterator<_PriorityElementType_> bsIter = bs.iterator();
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