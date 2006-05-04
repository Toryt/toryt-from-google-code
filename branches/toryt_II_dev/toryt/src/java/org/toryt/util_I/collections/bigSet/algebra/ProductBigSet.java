package org.toryt.util_I.collections.bigSet.algebra;


import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.BigSet;
import org.toryt.util_I.collections.bigSet.lockable.AbstractLockedBigSet;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;


/**
 * <p>A lazy big set, that is the cartesian product of all factor
 *   {@link BigSet BigSets}. The factor {@link BigSet BigSets} have
 *   to be locked, and are presented at construction time, in a
 *   {@link Map}. Factors are labelled, and the elements in this
 *   {@link BigSet} are {@link Map Maps} with the same labels, and
 *   for each label an element from the corresponding factor
 *   {@link BigSet}. The labels can be of any type, but they
 *   will often be {@code Strings}.</p>
 * <p>If one of the factor {@link BigSet BigSets} is <code>null</code>,
 *   it behaves as an empty set, making this an empty set too.
 *   This might become {@link #isEmpty()}, but will never contain
 *   <code>null</code>.</p>
 *
 * @author Jan Dockx
 *
 * @invar ! isNullAllowed();
 * @invar getFactors() != null;
 * @invar ! getFactors().isEmpty();
 * @invar Collections.noNullKey(getFactors());
 * @invar (forall LockableBigSet<?> lbs : getFactors().values(); (lbs == null) || lbs.isLocked());
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ProductBigSet<_Label_, _ResultMapElement_>
    extends AbstractLockedBigSet<Map<_Label_, _ResultMapElement_>> {

  /**
   * @pre factors != null;
   * @pre ! factors.isEmpty();
   * @pre Collections.noNullKey(factors);
   * @pre (forall LockableBigSet<?> lbs : factors.values(); (lbs == null) || lbs.isLocked());
   * @post new.getFactors().equals(factors);
   */
  public ProductBigSet(Map<? extends _Label_, ? extends LockableBigSet<? extends _ResultMapElement_>> factors) {
    super(false, calculateSize(factors));
    assert ! factors.isEmpty();
    assert Collections.noNullKey(factors);
    assert Collections.forAllValues(factors,
                                    new Assertion<LockableBigSet<?>>() {
                                          public boolean isTrueFor(LockableBigSet<?> o) {
                                            return (o != null) ? o.isLocked() : true;
                                          }
                                        });
    { // initFactors
      $factors = new HashMap<_Label_, LockableBigSet<? extends _ResultMapElement_>>(factors);
      @SuppressWarnings("unchecked") _Label_[] labels = (_Label_[])new Object[factors.size()];
      $factorLabels = labels;
      @SuppressWarnings("unchecked") LockableBigSet<? extends _ResultMapElement_>[] lockableBigSets =
          (LockableBigSet<? extends _ResultMapElement_>[])new LockableBigSet<?>[factors.size()];
      $factorBigSets = lockableBigSets;
      int i = 0;
      for (Map.Entry<? extends _Label_, ? extends LockableBigSet<? extends _ResultMapElement_>> entry : factors.entrySet()) {
        /* actual order in arrays is not important, but must be kept
         once decided on */
        $factorLabels[i] = entry.getKey();
        $factorBigSets[i] = entry.getValue();
        i++;
      }
    }
  }

  private static BigInteger calculateSize(Map<?, ? extends LockableBigSet<?>> factors) {
    assert factors != null;
    BigInteger result = BigInteger.ONE;
    for (LockableBigSet<?> lbs : factors.values()) {
      if (lbs == null) {
        return BigInteger.ZERO;
      }
      result = result.multiply(lbs.getBigSize());
    }
    return result;
  }

  /**
   * @return (product getFactors().getBigSize());
   *
  public final BigInteger getBigSize();
   */



  /* <property name="factors"> */
  //------------------------------------------------------------------

  /* internally, we store labels and factors in 1 parallel arrays for sequential access
   * through the iterator
   */

  /**
   * @basic
   */
  public final Map<_Label_, ? extends LockableBigSet<? extends _ResultMapElement_>> getFactors() {
    return java.util.Collections.unmodifiableMap($factors);
  }

  /**
   * @invar $factors != null;
   * @invar Collections.noNullKey($factors);
   * @invar (forall LockableBigSet<?> lbs : $factors.values(); (lbs == null) || lbs.isLocked());
   * @invar (forall _Label_ l : $factors.keySet(); Collections.contains($factorLabels, l));
   */
  private Map<_Label_, ? extends LockableBigSet<? extends _ResultMapElement_>> $factors;

  /**
   * @invar $factorLabels != null;
   * @invar Collections.noNull($factorLabels);
   * @invar (forall _Label_ l : $factorLabels; $factors.containsKey(l));
   */
  private final _Label_[] $factorLabels;

  /**
   * @invar $factorBigSets != null;
   * @invar (forall int i; (i >= 0) && (i < $factorBigSets.length);
   *           $factorBigSet[i] = $factor.get($factorLabels[i]);
   */
  private final LockableBigSet<? extends _ResultMapElement_>[] $factorBigSets;


  /*</property>*/



  /**
   * If this method is used with
   * <code>! o instanceof MapProductBigSet</code>,
   * it is possibly very expensive, as it uses
   * {@link #containsAll(Collection)} on <code>this</code> and <code>o</code>:
   * if {@link #containsAll(Collection)} is expensive, so is this
   * method if <code>! o instanceof MapProductBigSet</code>.
   */
  @Override
  public final boolean equals(Object o) {
    if ((o == null) || (! (o instanceof BigSet<?>))) {
      return false;
    }
    else if (o instanceof ProductBigSet<?, ?>) {
      Map<? , ?> otherFactors = ((ProductBigSet<?, ?>)o).getFactors();
      return $factors.equals(otherFactors);
    }
    else {
      // fallback; very possibly expensive, and stupid for a lazy set
      return getBigSize().equals(((BigSet<?>)o).getBigSize()) &&
             containsAll((BigSet<?>)o) &&
             ((BigSet<?>)o).containsAll(this);
    }
  }

  @Override
  public final int hashCode() {
    return $factors.hashCode();
  }

  @Override
  public final boolean contains(final Object o) throws ClassCastException {
    if (o == null) {
      return false;
    }
    @SuppressWarnings("unchecked") Map<? extends _Label_, ? extends _ResultMapElement_> object =
        (Map<? extends _Label_, ? extends _ResultMapElement_>)o;
      // this ClassCastException should not be caught, but just thrown
    for (Map.Entry<? extends _Label_, ? extends _ResultMapElement_> entry : object.entrySet()) {
      LockableBigSet<? extends _ResultMapElement_> lbs = $factors.get(entry.getKey());
      if ((lbs == null) || (! lbs.contains(entry.getValue()))) {
        // if (lbs == null), this is empty, so nothing can match
        return false;
      }
    }
    // if we get here, than such an element exists in the product
    return true;
  }

  /**
   * @return getBigSize() == null;
   */
  @SuppressWarnings("deprecation")
  public final boolean isEmpty() {
    return size() == 0;
  }

  public Iterator<Map<_Label_, _ResultMapElement_>> iterator() {
    return new AbstractLockedCollectionIterator() {

      private final int dim = $factorBigSets.length;

      /**
       * This is null if there is no next.
       *
       * @invar (exists Iterator<?> iter : $iterators; iter.hasNext());
       */
      private Iterator<? extends _ResultMapElement_>[] $iterators;

      private _ResultMapElement_[] $elements;

      {
        if (! isEmpty()) {
          // prepare iterators
          @SuppressWarnings("unchecked") Iterator<? extends _ResultMapElement_>[] iterators =
              (Iterator<? extends _ResultMapElement_>[])new Iterator<?>[dim];
          $iterators = iterators;
          for (int i = 0; i < dim; i++) {
            assert $factorBigSets[i] != null : "cannot be null because we are not empty";
            $iterators[i] = $factorBigSets[i].iterator();
            assert $iterators[i].hasNext() : "at least 1 element, because we are not empty";
          }
          // prepare first element
          @SuppressWarnings("unchecked") _ResultMapElement_[] elements = (_ResultMapElement_[])new Object[dim];
          $elements = elements;
          for (int j = dim - 1; j >= 0; j--) {
            assert ($iterators[j] != null) && ($iterators[j].hasNext());
            $elements[j] = $iterators[j].next();
          }
        }
        // else, $iterators and $elements stays null (! hasNext())
      }


      /**
       * From back to front, try to overwrite $elements[i] with next from $iterators[i],
       * until this succeeds for an i. $elements[j] with j > i are reset t the first
       * element of a new iterator for that factor.
       */
      private void prepareNext() {
        assert $elements != null;
        assert $iterators != null;
        assert Collections.forAll($factorBigSets,
                                  new Assertion<LockableBigSet<? extends _ResultMapElement_>>() {
                                        public boolean isTrueFor(LockableBigSet<? extends _ResultMapElement_> o) {
                                          return (o != null) && (! o.isEmpty());
                                        }
                                      });
        int i = dim - 1; // last index
        // prepare iterators, so they have a next
        while (i >= 0) {
          if (! $iterators[i].hasNext()) {
            /* $iterators[i] is depleted; reset; see if we need to reset $iterators[i] */
            $iterators[i] = $factorBigSets[i].iterator();
            i--;
          }
          else {
            // $iterators[i].hasNext(); previous iterators will not be used; later iterators are reset
            break;
          }
        }
        /* iterator at index i is first not reset;
         * if we did not break, i < 0: we have reset all iterators,
         * and thus we have just returned the last element
         */
        if (i < 0) { // remember this
          $iterators = null;
          $elements = null;
        }
        else {
          // take next at index i, and first elements at later indices
          for (int j = i; j < dim; j++) {
            assert $iterators[j].hasNext();
            $elements[j] = $iterators[j].next();
          }
          // next elements are ready for next call to next
        }
      }

      public final boolean hasNext() {
        return $elements != null;
      }

      public final Map<_Label_, _ResultMapElement_> next() throws NoSuchElementException {
        if ($elements == null) {
          throw new NoSuchElementException();
        }
        Map<_Label_, _ResultMapElement_> result = createEmptyElementMap();
        for (int j = 0; j < $elements.length; j++) {
          result.put($factorLabels[j], $elements[j]);
        }
        prepareNext();
        return result;
      }

    };
  }

  protected Map<_Label_, _ResultMapElement_> createEmptyElementMap() {
    return new HashMap<_Label_, _ResultMapElement_>($factorLabels.length);
  }

}