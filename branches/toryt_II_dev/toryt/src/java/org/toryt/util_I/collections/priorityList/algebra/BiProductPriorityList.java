package org.toryt.util_I.collections.priorityList.algebra;


import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.collections.bigSet.algebra.ProductBigSet;
import org.toryt.util_I.collections.bigSet.algebra.UnionBigSet;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.lockable.LazyList;
import org.toryt.util_I.collections.priorityList.AbstractLockedPriorityList;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 *
 * @author Jan Dockx
 *
 */
public class BiProductPriorityList extends AbstractLockedPriorityList
    implements LazyList {

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



  private BiProductPriorityList(PriorityList leftFactor, PriorityList rightFactor, int fromIndex, int toIndex) {
    super(buildPriorityElementType(leftFactor, rightFactor),
          calculateCardinality(leftFactor, rightFactor));
    $leftFactor = leftFactor;
    $rightFactor = rightFactor;
    $fromIndex = fromIndex;
    $toIndex = toIndex;
    $baseSize = calculateBaseSize(leftFactor, rightFactor);
  }

  public BiProductPriorityList(PriorityList leftFactor, PriorityList rightFactor) {
    this(leftFactor, rightFactor, 0, calculateBaseSize(leftFactor, rightFactor));
  }

  private int $fromIndex;
  private int $toIndex;

  private static Class buildPriorityElementType(PriorityList leftFactor, PriorityList rightFactor) {
    return Object[].class;
    // IDEA more specific: closest supertype of left PETand right PET.
  }

  private static BigInteger calculateCardinality(PriorityList leftFactor, PriorityList rightFactor) {
    return ((leftFactor == null) || (rightFactor == null)) ?
             BigInteger.ZERO :
             leftFactor.getCardinality().multiply(rightFactor.getCardinality());
  }

  public final PriorityList getLeftFactor() {
    return $leftFactor;
  }

  private PriorityList $leftFactor;

  public final PriorityList getRightFactor() {
    return $rightFactor;
  }

  private PriorityList $rightFactor;

  /**
   * @return Math.min(getLeftFactor().size(), getRightFactor().size()) - 1;
   */
  public final int getPlateauLeftPriority() {
    return (($leftFactor == null) || ($rightFactor == null)) ?
             0 :
             Math.min($leftFactor.size(), $rightFactor.size()) - 1;
  }

  /**
   * @return getBaseSize() - (2 * getPlateauLeftPriority());
   */
  public final int getPlateauSize() {
    return getBaseSize() - (2 * getPlateauLeftPriority());
  }

  /**
   * @return return getPlateauLeftPriority() + getPlateauSize() - 1;
   */
  public final int getPlateauRightPriority() {
    return getPlateauLeftPriority() + getPlateauSize() - 1;
  }

  public int size() {
    return $toIndex - $fromIndex;
  }

  private static int calculateBaseSize(PriorityList leftFactor, PriorityList rightFactor) {
    return ((leftFactor == null) || (rightFactor == null)) ?
              0 :
              (leftFactor.size() - 1) + (rightFactor.size() - 1) + 1;

  }

  public final int getBaseSize() {
    return $baseSize;
  }
  
  private int $baseSize;

  public final boolean isEmpty() {
    return size() == 0;
  }

  public Object get(int index) throws IndexOutOfBoundsException {
    if (index < 0) {
      throw new IndexOutOfBoundsException();
    }
    int priority = index + $fromIndex;
    int leftStartPriority = Math.min(0, priority - $rightFactor.size() + 1);
    int nrOfCombinationsInBucket = getNrOfCombinationsInBucket(priority);
    LockableBigSet[] components = new LockableBigSet[nrOfCombinationsInBucket];
    for (int i = 0; i < nrOfCombinationsInBucket; i++) {
      int leftPriority = leftStartPriority + i;
      int rightPriority = priority - leftPriority;
      components[i] = new ProductBigSet(Object[].class,
                                        new LockableBigSet[] {(LockableBigSet)$leftFactor.get(leftPriority),
                                                              (LockableBigSet)$rightFactor.get(rightPriority)});
    }
    return new UnionBigSet(getPriorityElementType(), true, components);
  }

  public final int getNrOfCombinationsInBucket(int priority) {
    return (priority <= getPlateauLeftPriority()) ?
              priority + 1 :
              ((priority >= getPlateauRightPriority()) ?
                 getBaseSize() - priority :
                 getPlateauLeftPriority() + 1);
  }

  public final List subList(int fromIndex, int toIndex) {
    return new BiProductPriorityList($leftFactor, $rightFactor, fromIndex, toIndex);
  }

  public final ListIterator listIterator(final int index) {
    return new AbstractLockedListIterator() {

      private int $cursor = index;

      public final boolean hasNext() {
        return $cursor < size();
      }

      public Object next() {
        Object result = get($cursor);
        $cursor++;
        return result;
      }

      public boolean hasPrevious() {
        return $cursor > 0;
      }

      public Object previous() {
        $cursor--;
        return get($cursor);
      }

      public int nextIndex() {
        return $cursor;
      }

      public int previousIndex() {
        return $cursor - 1;
      }

    };
  }

  public Iterator priorityElementIterator() {
    return new AbstractLockedCollectionIterator() {
            
                  Iterator $listIterator = iterator();
      
                  /**
                   * @invar ($lbsIterator == null) || ($lbsIterator.hasNext());
                   */
                  Iterator $lbsIterator;
                  
                  {
                    prepareNextLbsIterator();
                  }
                  
                  private void prepareNextLbsIterator() {
                    $lbsIterator = null;
                    while (($lbsIterator == null) && $listIterator.hasNext()) {
                      LockableBigSet lbs = (LockableBigSet)$listIterator.next();
                      if (! lbs.isEmpty()) {
                        $lbsIterator = lbs.iterator();
                      }
                    }
                  }
      
                  public final boolean hasNext() {
                    return ($lbsIterator != null);
                  }
            
                  public final Object next() {
                    Object result = $lbsIterator.next();
                    if (! $lbsIterator.hasNext()) {
                      prepareNextLbsIterator();
                    }
                    return result;
                  }
            
                };
  }

  /**
   * Contains an equal {@link LockableBigSet}.
   * 
   * @deprecated This method is very costly, as it
   *             effectively creates buckets one after the
   *             other
   */
  public boolean contains(final Object o) {
    return Collections.exists(this, new Assertion() {
                                          public boolean isTrueFor(Object lbs) {
                                            assert lbs != null;
                                            return lbs.equals(o);
                                          }
                                        });
  }

  /**
   * Find an equal {@link LockableBigSet}.
   * 
   * @deprecated This method is very costly, as it
   *             effectively creates buckets one after the
   *             other
   */
  public final int indexOf(Object o) {
    ListIterator iter = listIterator();
    while (iter.hasNext()) {
      if (iter.next().equals(o)) {
        return iter.previousIndex();
      }
    }
    return -1;
  }

  /**
   * Find an equal {@link LockableBigSet}.
   * 
   * @deprecated This method is very costly, as it
   *             effectively creates buckets one after the
   *             other
   */
  public final int lastIndexOf(Object o) {
    ListIterator iter = listIterator(size());
    while (iter.hasPrevious()) {
      if (iter.previous().equals(o)) {
        return iter.nextIndex();
      }
    }
    return -1;
  }

  /**
   * Find an equal <code>Object[]</code>.
   */
  public final boolean containsPriorityElement(final Object o) {
    Object[] element = (Object[])o;
    return $leftFactor.contains(element[0]) && $rightFactor.contains(element[1]);
  }

}