package org.toryt.util_I.priorityList.experiments;

import java.math.BigInteger;
import java.util.NoSuchElementException;




/**
 * <p>The cartesian product of 2 {@link PriorityList}s, which is itself
 *   a priority list.</p>
 * <p>This implementation is lazy. The user should traverse through this
 *   result using the {@link #priorityListIterator()}. The elements retrieved
 *   from the list are arrays of length 2 <code>Object[2]</code>.</p>
 * 
 * @author Jan Dockx
 * @author PeopleWare n.v.
 * 
 * @invar getFirstList() != null;
 * @invar getSecondList() != null;
 */
public class ProductPriorityList
    extends AbstractSequentialPriorityList {

  /*<section name="Meta Information">*/
  //------------------------------------------------------------------

  /** {@value} */
  public static final String CVS_REVISION = "$Revision$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_DATE = "$Date$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_STATE = "$State$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_TAG = "$Name$"; //$NON-NLS-1$

  /*</section>*/


  /**
   * @param first
   *        The first PriorityList to combine in the cartesian product.
   * @param second
   *        The second PriorityList to combine in the cartesian product.
   * @pre first != null;
   * @pre second != null;
   * @post getFirstList() == first;
   * @post getSecondList() == second;
   */
  public ProductPriorityList(PriorityList first,
                                      PriorityList second) {
    super(first.bigIntegerSize().multiply(second.bigIntegerSize()));
    assert first != null;
    assert second != null;
    $first = first;
    $second = second;
  }

  /**
   * @basic
   */
  public PriorityList getFirstList() {
    return $first;
  }
  
  /**
   * @invar $first != null;
   */
  private PriorityList $first;
  
  /**
   * @basic
   */
  public PriorityList getSecondList() {
    return $second;
  }
  
  /**
   * @invar $second != null;
   */
  private PriorityList $second;
  
  public final PriorityListIterator priorityListIterator() {
    return new X_PLI();
  }
  
  public final PriorityListIterator priorityListIterator(BigInteger index)
      throws IndexOutOfBoundsException {
    assert index != null;
    PriorityListIterator pli = priorityListIterator();
    while (pli.bigIntegerNextIndex().compareTo(index) < 0) {
      try {
        pli.next();
      }
      catch (NoSuchElementException nseExc) {
        throw new IndexOutOfBoundsException();
      }
    }
    return pli;
  }
  
  private class X_PLI extends AbstractPriorityListIterator {
    
    public X_PLI() {
      super(ProductPriorityList.this);
      PriorityListIterator firstPLI = $first.priorityListIterator();
      PriorityListIterator secondPLI = $second.priorityListIterator(); 
      secondPLI.next(); // MUDO problem with list of size 1
      init(null, null,
           firstPLI.clonePriorityListIterator(),
           secondPLI.clonePriorityListIterator(),
           null, null);
    }
    
    private BigInteger $nextIndex = BigInteger.ZERO;

    /**
     * The algorithm requires a deep clone.
     */
    public Object clone() {
      X_PLI result = (X_PLI)super.clone();
      result.init(($elementInBucketIter[0] != null) ? $elementInBucketIter[0].clonePriorityListIterator() : null,
                  ($elementInBucketIter[1] != null) ? $elementInBucketIter[1].clonePriorityListIterator() : null,
                  ($nextBucketStartIter[0] != null) ? $nextBucketStartIter[0].clonePriorityListIterator() : null,
                  ($nextBucketStartIter[1] != null) ? $nextBucketStartIter[1].clonePriorityListIterator() : null,
                  ($previousBucketEndIter[0] != null) ? $previousBucketEndIter[0].clonePriorityListIterator() : null,
                  ($previousBucketEndIter[1] != null) ? $previousBucketEndIter[1].clonePriorityListIterator() : null);
      return result;
    }
    
    private void init(PriorityListIterator elementInBucketIter0,
                 PriorityListIterator elementInBucketIter1,
                 PriorityListIterator nextBucketStartIter0,
                 PriorityListIterator nextBucketStartIter1,
                 PriorityListIterator previousBucketEndIter0,
                 PriorityListIterator previousBucketEndIter1) {
      $elementInBucketIter = new PriorityListIterator[2];
      $elementInBucketIter[0] = elementInBucketIter0;
      $elementInBucketIter[1] = elementInBucketIter1;
      $nextBucketStartIter = new PriorityListIterator[2];
      $nextBucketStartIter[0] = nextBucketStartIter0;
      $nextBucketStartIter[1] = nextBucketStartIter1;
      $previousBucketEndIter = new PriorityListIterator[2];
      $previousBucketEndIter[0] = previousBucketEndIter0;
      $previousBucketEndIter[1] = previousBucketEndIter1;
    }
    
    /**
     * Iterators for the elements within the current bucket.
     * {$elementInBucketIter[0].next(), $elementInBucketIter[0].previous()} is the next couple, and
     * {$elementInBucketIter[0].previous(), $elementInBucketIter[0].next()} is the previous couple, when
     * a bucket is not exhausted.
     * 
     * @invar $elementInBucketIter.length = 2;
     * @invar $elementInBucketIter[0] != null;
     * @invar $elementInBucketIter[1] != null;
     */
    private PriorityListIterator[] $elementInBucketIter;
    
    /**
     * <p>Iterators for the 2 lists that point to the start of the next
     *   bucket.
     *   ($nextBucketStartIter[0].next(), $nextBucketStartIter[1].previous())
     *   is the first element of the next bucket.</p>
     * <p>Initially, $nextBucketStartIter[0].nextIndex() == 0 and
     *   $nextBucketStartIter[1].previousIndex() == 0, and
     *   $nextBucketStartIter[0].previousIndex() == -1 and
     *   $nextBucketStartIter[1].nextIndex() == 1.
     *   ($nextBucketStartIter[0].previous() throws a NoSuchElementException;
     *   ! $nextBucketStartIter[0].hasPrevious()).</p>
     * <p>At the end, after the last element is returned by {@link #next()},
     *   $nextBucketStartIter[0].nextIndex() == $list[0].size() and
     *   $nextBucketStartIter[1] == null
     *   ($nextBucketStartIter[0].next() throws a NoSuchElementException;
     *   ! $nextBucketStartIter[0].hasNext()).</p>
     * <p>Thus, $nextBucketStartIter[0] cannot be null, and
     *   $nextBucketStartIter[1] can be.</p>
     * 
     * @invar $nextBucketStartIter.length = 2;
     * @invar $nextBucketStartIter[0] != null;
     */
    private PriorityListIterator[] $nextBucketStartIter;
    
    /**
     * <p>Iterators for the 2 lists that point to the end of the previous
     *   bucket.
     *   ($previousBucketEndIter[0].previous(), $previousBucketEndIter[1].next())
     *   is the last element of the previous bucket.</p>
     * <p>Initially, previousBucketEndIter[i] == null. The variable is only
     *   initialized when the first bucket is passed (after the first element is
     *   returned).</p>
     * <p>At the end, after the last element is returned by {@link #next()},
     *   $previousBucketEndIter[0].previousIndex() == $list[0].size() - 1 and
     *   $previousBucketEndIter[1].nextIndex() == $list[1].size() - 1
     *   ($previousBucketEndIter[0].next() throws a NoSuchElementException;
     *   ! $previousBucketEndIter[0].hasNext()).</p>
     * 
     * @invar $previousBucketEndIter.length = 2;
     */
    private PriorityListIterator[] $previousBucketEndIter;

    public Object next() throws NoSuchElementException {
      Object[] couple = new Object[2];
      if (($elementInBucketIter[0] == null)
          || (! ($elementInBucketIter[0].hasNext()
                && $elementInBucketIter[1].hasPrevious()))) {
        // bucket is exhausted, go to next bucket, if possible
        /* ($elementInBucketIter[0].previous(), $elementInBucketIter[1].next())
         * is now the last couple of the previous bucket, which we are
         * stepping out */
        if ($elementInBucketIter[0] != null) {
          $previousBucketEndIter[0] = $elementInBucketIter[0].clonePriorityListIterator();
          $previousBucketEndIter[1] = $elementInBucketIter[1].clonePriorityListIterator();
        }
        /* ($nextBucketStartIter[0].next(), $nextBucketStartIter[1].previous())
         * is first element of next bucket */
        $elementInBucketIter[0] = $nextBucketStartIter[0].clonePriorityListIterator();
        $elementInBucketIter[1] = $nextBucketStartIter[1].clonePriorityListIterator();
        /* now we stil need to set
         * ($nextBucketStartIter[0].next(), $nextBucketStartIter[1].previous())
         * to the start of the new next bucket */
        if ($nextBucketStartIter[1].hasNext()) {
          assert $nextBucketStartIter[0].nextIndex() == 0;
          $nextBucketStartIter[1].next();
        }
        else if ($nextBucketStartIter[0].hasNext()) {
          assert $nextBucketStartIter[1].bigIntegerNextIndex()
                  .equals($nextBucketStartIter[1].getPriorityList().bigIntegerSize());
          $nextBucketStartIter[0].next();
        }
        else { // all done
          throw new NoSuchElementException();
        }
      }
      // we can proceed inside the priority bucket
//System.out.println("\n$elementInBucketIter[0].nextIndex(): " + $elementInBucketIter[0].nextIndex());
//System.out.println("$elementInBucketIter[1].previousIndex(): " + $elementInBucketIter[1].previousIndex());
//System.out.println("$nextBucketStartIter[0].nextIndex(): " + $nextBucketStartIter[0].nextIndex());
//System.out.println("$nextBucketStartIter[1].previousIndex(): " + $nextBucketStartIter[1].previousIndex());
//if ($previousBucketEndIter[0] != null) {
//  System.out.println("$previousBucketEndIter[0].nextIndex(): " + $previousBucketEndIter[0].previousIndex());
//  System.out.println("$previousBucketEndIter[1].previousIndex(): " + $previousBucketEndIter[1].nextIndex());
//// FAULT: this is too late for last element; first do couple, then prepare other stuff for next
//// this also changes initial setup; probably easier
//}
      assert $elementInBucketIter[0].bigIntegerNextIndex()
              .add($elementInBucketIter[1].bigIntegerPreviousIndex())
              .equals($nextBucketStartIter[0].bigIntegerNextIndex()
                      .add($nextBucketStartIter[1].bigIntegerPreviousIndex())
                      .subtract(BigInteger.ONE));
      couple[0] = $elementInBucketIter[0].next();
      couple[1] = $elementInBucketIter[1].previous();
      $nextIndex = $nextIndex.add(BigInteger.ONE);
      return couple;
    }

    public Object previous() throws NoSuchElementException {
      Object[] couple = new Object[2];
      if (! ($elementInBucketIter[0].hasPrevious() && $elementInBucketIter[1].hasNext())) {
        // bucket is exhausted, go to previous bucket, if possible
        /* ($elementInBucketIter[0].next(), $elementInBucketIter[1].previous())
         * is now the first couple of the next bucket, which we are
         * stepping out */
        $nextBucketStartIter[0] = $elementInBucketIter[0].clonePriorityListIterator();
        $nextBucketStartIter[1] = $elementInBucketIter[1].clonePriorityListIterator();
        /* ($previousBucketEndIter[0].previous(), $previousBucketEndIter[1].next())
         * is last element of previous bucket */
        $elementInBucketIter[0] = $previousBucketEndIter[0].clonePriorityListIterator();
        $elementInBucketIter[1] = $previousBucketEndIter[1].clonePriorityListIterator();
        /* now we stil need to set
         * ($previousBucketEndIter[0].previous(), $previousBucketEndIter[1].next())
         * to the end of the new previous bucket */
        if ($previousBucketEndIter[1].hasPrevious()) {
          assert $previousBucketEndIter[0].bigIntegerNextIndex()
                  .equals($previousBucketEndIter[0].getPriorityList().bigIntegerSize());
          $previousBucketEndIter[1].previous();
        }
        else if ($previousBucketEndIter[0].hasPrevious()) {
          assert $previousBucketEndIter[1].nextIndex() == 0;
          $previousBucketEndIter[0].previous();
        }
        else { // all done
          throw new NoSuchElementException();
        }
      }
      // we can proceed inside the priority bucket
//System.out.println("\n$elementInBucketIter[0].previousIndex(): " + $elementInBucketIter[0].previousIndex());
//System.out.println("$elementInBucketIter[1].nextIndex(): " + $elementInBucketIter[1].nextIndex());
//System.out.println("$nextBucketStartIter[0].nextIndex(): " + $nextBucketStartIter[0].nextIndex());
//System.out.println("$nextBucketStartIter[1].previousIndex(): " + $nextBucketStartIter[1].previousIndex());
//if ($previousBucketEndIter[0] != null) {
//  System.out.println("$previousBucketEndIter[0].nextIndex(): " + $previousBucketEndIter[0].previousIndex());
//  System.out.println("$previousBucketEndIter[1].previousIndex(): " + $previousBucketEndIter[1].nextIndex());
////             FAULT: this is too late for last element; first do couple, then prepare other stuff for next
////             this also changes initial setup; probably easier
//}
      assert $elementInBucketIter[0].bigIntegerPreviousIndex()
              .add($elementInBucketIter[1].bigIntegerNextIndex())
              .equals((($previousBucketEndIter[0] != null) && ($previousBucketEndIter[1] != null))
                      ? $previousBucketEndIter[0].bigIntegerPreviousIndex()
                         .add($previousBucketEndIter[1].bigIntegerNextIndex())
                         .add(BigInteger.ONE)
                      : BigInteger.ZERO);
      couple[0] = $elementInBucketIter[0].previous();
      couple[1] = $elementInBucketIter[1].next();
      $nextIndex = $nextIndex.subtract(BigInteger.ONE);
      return couple;
    }

    public final boolean hasNext() {
      return ((($elementInBucketIter[0] == null)
                        || $elementInBucketIter[0].hasNext())
                && (($elementInBucketIter[1] == null)
                        || $elementInBucketIter[1].hasPrevious()))
              // bucket is not exhausted
             || ($nextBucketStartIter[1].hasNext()
                   || $nextBucketStartIter[0].hasNext());
              // there are no more buckets
    }

    public final boolean hasPrevious() {
      return ($elementInBucketIter[0].hasPrevious()
                && $elementInBucketIter[1].hasNext())
              // bucket is not exhausted
             || ((($previousBucketEndIter[0] != null) && $previousBucketEndIter[0].hasPrevious())
                   || (($previousBucketEndIter[1] != null) && $previousBucketEndIter[1].hasPrevious()));
              // there are no more buckets
    }

    public final int nextIndex() {
      return bigIntegerNextIndex().intValue();
    }

    public final BigInteger bigIntegerNextIndex() {
      return $nextIndex;
    }

    public final int previousIndex() {
      return bigIntegerPreviousIndex().intValue();
    }

    public final BigInteger bigIntegerPreviousIndex() {
      return $nextIndex.subtract(BigInteger.ONE);
    }
    
  }

}