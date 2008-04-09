package org.toryt.util_I.collections.priorityList.algebra;


import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.priorityList.AbstractLockedPriorityList;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * <p>A lazy priority list, that is the cartesian product of all factor
 *   {@link PriorityList PriorityLists}. The factor {@link PriorityList PriorityLists} have
 *   to be locked, and are presented at construction time, in a
 *   {@link Map}. Factors are labelled, and the priority elements in this
 *   {@link PriorityList} are {@link Map Maps} with the same labels, and
 *   for each label an element from the corresponding factor
 *   {@link PriorityList}. The labels can be of any type, but they
 *   will often be {@code Strings}.</p>
 * <p>If one of the factor {@link PriorityList PriorityLists} is <code>null</code>,
 *   it behaves as an empty list, making this an empty list too.
 *   This might become {@link #isEmpty()}, but will never contain
 *   <code>null</code>.</p>
 * <p>This class implements methods that are implemented independent of
 *   the number of factors. {@link BiProductPriorityList} is a
 *   non-abstract class that implements the algorithms for 2 factors.
 *   {@link ProductPriorityList} is a
 *   non-abstract class that implements the algorithms for n factors.</p>
 *
 * @author Jan Dockx
 *
 * @invar ! isNullPriorityElementAllowed();
 * @invar getFactors() != null;
 * @invar ! getFactors().isEmpty();
 * @invar Collections.noNullKey(getFactors());
 * @invar (forall PriorityList<?> pl : getFactors().values(); (pl == null) || pl.isLocked());
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractProductPriorityList<_Label_, _ResultMapElement_>
    extends AbstractLockedPriorityList<Map<_Label_, _ResultMapElement_>> {

  /* elements are of type
   * LockableBigSet<? extends Map<? extends _Label_, ? extends _ResultMapElement_>>
   *
   * priority elements are of type
   * Map<_Label_, _ResultMapElement_>
   * (we create them, so we know the exact type)
   */

  /* <construction> */
  //------------------------------------------------------------------

  /**
   * @pre factors != null;
   * @pre ! factors.isEmpty();
   * @pre Collections.noNullKey(factors);
   * @pre (forall PriorityList<?> pl : factors.values(); (pl == null) || pl.isLocked());
   * @post new.getFactors().equals(factors);
   */
  public AbstractProductPriorityList(Map<? extends _Label_, ? extends PriorityList<? extends _ResultMapElement_>> factors,
                                int fromInclusive, int toExclusive) {
    super(cardinality(factors), false);
    assert factors != null;
    assert ! factors.isEmpty();
    assert Collections.noNullKey(factors);
    assert Collections.forAllValues(factors,
                                    new Assertion<PriorityList<?>>() {
                                      public boolean isTrueFor(PriorityList<?> o) {
                                        return (o != null) ? o.isLocked() : true;
                                      }
                                    });
    $fromInclusive = fromInclusive;
    $toExclusive = toExclusive;
    $baseSize = baseSize(factors);
    { // initFactors
      $factors = new HashMap<_Label_, PriorityList<? extends _ResultMapElement_>>(factors);
      @SuppressWarnings("unchecked") _Label_[] labels = (_Label_[])new Object[factors.size()];
      $factorLabels = labels;
      @SuppressWarnings("unchecked") PriorityList<? extends _ResultMapElement_>[] priorityLists =
          (PriorityList<? extends _ResultMapElement_>[])new PriorityList<?>[factors.size()];
      $factorPriorityLists = priorityLists;
      int i = 0;
      for (Map.Entry<? extends _Label_, ? extends PriorityList<? extends _ResultMapElement_>> entry : factors.entrySet()) {
        /* actual order in arrays is not important, but must be kept
         once decided on */
        $factorLabels[i] = entry.getKey();
        $factorPriorityLists[i] = entry.getValue();
        i++;
      }
    }
  }

  private static BigInteger cardinality(Map<?, ? extends PriorityList<?>> factors) {
    assert factors != null;
    BigInteger result = BigInteger.ONE;
    for (PriorityList<?> pl : factors.values()) {
      if (pl == null) {
        return BigInteger.ZERO;
      }
      result = result.multiply(pl.getCardinality());
    }
    return result;
  }

  protected static int baseSize(Map<?, ? extends PriorityList<?>> factors) {
    assert factors != null;
    int result = 1;
    for (PriorityList<?> pl : factors.values()) {
      if (pl == null) {
        return 0;
      }
      result += pl.size() - 1;
    }
    return result;
  }

  /* overriding clone is not necessary: there are no instance variable here that need to be copied;
   * the component structure (factors) can be share, because they will not change after construction!
   */

  /*</construction>*/



  /* <property name="factors"> */
  //------------------------------------------------------------------

  /* internally, we store labels and factors in 1 parallel arrays for sequential access
   * through the iterator
   */

  /**
   * @basic
   */
  public final Map<_Label_, ? extends PriorityList<? extends _ResultMapElement_>> getFactors() {
    return java.util.Collections.unmodifiableMap($factors);
  }

  /**
   * @invar $factors != null;
   * @invar Collections.noNullKey($factors);
   * @invar (forall PriorityList<?> pl : $factors.values(); (pl == null) || pl.isLocked());
   * @invar (forall _Label_ l : $factors.keySet(); Collections.contains($factorLabels, l));
   */
  protected final Map<_Label_, ? extends PriorityList<? extends _ResultMapElement_>> $factors;

  /**
   * @invar $factorLabels != null;
   * @invar Collections.noNull($factorLabels);
   * @invar (forall _Label_ l : $factorLabels; $factors.containsKey(l));
   */
  protected final _Label_[] $factorLabels;

  /**
   * @invar $factorPriorityLists != null;
   * @invar (forall int i; (i >= 0) && (i < $factorPriorityLists.length);
   *           $factorPriorityListst[i] = $factor.get($factorLabels[i]);
   */
  protected final PriorityList<? extends _ResultMapElement_>[] $factorPriorityLists;

  /*</property>*/



  /* <section name="size"> */
  //------------------------------------------------------------------

  public int size() {
    return $toExclusive - $fromInclusive;
  }

  public final int getBaseSize() {
    return $baseSize;
  }

  public final boolean isEmpty() {
    return size() == 0;
  }

  /**
   * @invar $baseSize >= 0;
   */
  private final int $baseSize;

  /**
   * @invar $fromInclusive >= 0;
   * @invar $fromInclusive < $baseSize;
   * @invar $fromInclusive <= $toExclusive;
   */
  protected final int $fromInclusive;

  /**
   * @invar $toExclusive >= 0;
   * @invar $toExclusive <= $baseSize;
   */
  protected final int $toExclusive;

  /*</section>*/



  /* <section name="list methods"> */
  //------------------------------------------------------------------

  /**
   * If this method is used with
   * <code>! o instanceof MapProductPriorityList</code>,
   * it is possibly very expensive, as it uses
   * {@link #containsAll(Collection)} on <code>this</code> and <code>o</code>:
   * if {@link #containsAll(Collection)} is expensive, so is this
   * method if <code>! o instanceof MapProductPriorityList</code>.
   */
  @Override
  public final boolean equals(Object o) {
    if ((o == null) || (! (o instanceof PriorityList<?>))) {
      return false;
    }
    else if (o instanceof AbstractProductPriorityList<?, ?>) {   // MUDO change if move up
      Map<? , ?> otherFactors = ((AbstractProductPriorityList<?, ?>)o).getFactors();
      return $factors.equals(otherFactors);
    }
    else {
      // fallback; very possibly expensive, and stupid for a lazy set
      return (size() == ((Collection<?>)o).size()) &&
             getCardinality().equals(((PriorityList<?>)o).getCardinality()) &&
             containsAll((Collection<?>)o) &&
             ((Collection<?>)o).containsAll(this);
    }
  }

  @Override
  public final int hashCode() {
    return $factors.hashCode();
  }

  /**
   * Find an equal {@link LockableBigSet}.
   *
   * @deprecated This method is very costly, as it
   *             effectively creates buckets one after the
   *             other
   */
  @Deprecated
  @Override
  public final int indexOf(Object o) {
    return super.indexOf(o);
  }

  /**
   * Find an equal {@link LockableBigSet}.
   *
   * @deprecated This method is very costly, as it
   *             effectively creates buckets one after the
   *             other
   */
  @Deprecated
  @Override
  public final int lastIndexOf(Object o) {
    return super.lastIndexOf(o);
  }

  /**
   * Contains an equal {@link LockableBigSet}.
   *
   * @deprecated This method is very costly, as it
   *             effectively creates buckets one after the
   *             other
   *
   * @note A somewhat more performant algorithm seems possible, but isn't
   *       worth the effort. This method will not be used anyway.
   */
  @Deprecated
  @Override
  public final boolean contains(final Object o) {
    return super.contains(o);
  }

  /**
   * @deprecated This method is very expensive, as it iterates over all buckets
   *             (and thus generates them).
   */
  @Deprecated
  @Override
  public final Object[] toArray() {
    return super.toArray();
  }

  /**
   * @deprecated This method is very expensive, as it iterates over all buckets
   *             (and thus generates them).
   */
  @Deprecated
  @Override
  public final <_Base_> _Base_[] toArray(_Base_[] a) {
    return super.toArray(a);
  }

  /*</section>*/



  public final ListIterator<LockableBigSet<? extends Map<_Label_, _ResultMapElement_>>> listIterator(final int index) {
    return new AbstractLockedListIterator() {

                /* standard, index based implementation */

                private int $cursor = index;

                public final boolean hasNext() {
                  return $cursor < size();
                }

                public LockableBigSet<? extends Map<_Label_, _ResultMapElement_>> next() {
                  LockableBigSet<? extends Map<_Label_, _ResultMapElement_>> result = get($cursor);
                  $cursor++;
                  return result;
                }

                public boolean hasPrevious() {
                  return $cursor > 0;
                }

                public LockableBigSet<? extends Map<_Label_, _ResultMapElement_>> previous() {
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

  public final boolean containsPriorityElement(final Object o) throws ClassCastException {
    if (o == null) {
      return false;
    }
    @SuppressWarnings("unchecked") Map<? extends _Label_, ? extends _ResultMapElement_> object =
        (Map<? extends _Label_, ? extends _ResultMapElement_>)o;
      // this ClassCastException should not be caught, but just thrown
    for (Map.Entry<? extends _Label_, ? extends _ResultMapElement_> entry : object.entrySet()) {
      PriorityList<? extends _ResultMapElement_> pl = $factors.get(entry.getKey());
      if ((pl == null) || (! pl.containsPriorityElement(entry.getValue()))) {
        // if (lbs == null), this is empty, so nothing can match
        return false;
      }
    }
    // if we get here, than such an element exists in the product
    return true;
  }

}