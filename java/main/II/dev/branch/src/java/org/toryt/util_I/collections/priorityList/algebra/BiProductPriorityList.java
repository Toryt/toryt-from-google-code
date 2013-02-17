package org.toryt.util_I.collections.priorityList.algebra;


import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.algebra.ProductBigSet;
import org.toryt.util_I.collections.bigSet.algebra.UnionBigSet;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * <p>A lazy priority list, that is the cartesian product of 2 factor
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
 *
 * @author Jan Dockx
 *
 * @invar getFactors().size() == 2;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class BiProductPriorityList<_Label_, _ResultMapElement_>
    extends AbstractProductPriorityList<_Label_, _ResultMapElement_> {

  /* <construction> */
  //------------------------------------------------------------------

  /**
   * @pre factors != null;
   * @pre ! factors.isEmpty();
   * @pre Collections.noNullKey(factors);
   * @pre factors.size() == 2;
   * @pre (forall PriorityList<?> pl : factors.values(); (pl == null) || pl.isLocked());
   * @post new.getFactors().equals(factors);
   */
  public BiProductPriorityList(Map<? extends _Label_, ? extends PriorityList<? extends _ResultMapElement_>> factors,
                                int fromInclusive, int toExclusive) {
    super(factors, fromInclusive, toExclusive);
    assert factors.size() == 2;
  }

  /**
   * @pre factors != null;
   * @pre ! factors.isEmpty();
   * @pre Collections.noNullKey(factors);
   * @pre factors.size() == 2;
   * @pre (forall PriorityList<?> pl : factors.values(); (pl == null) || pl.isLocked());
   * @post new.getFactors().equals(factors);
   */
  public BiProductPriorityList(Map<? extends _Label_, ? extends PriorityList<? extends _ResultMapElement_>> factors) {
    this(factors, 0, baseSize(factors));
  }

  // overriding clone is not necessary: there are no instance variable here that need to be copied

  /*</construction>*/



  /* <section name="bi-structure"> */
  //------------------------------------------------------------------

  /**
   * @return Math.min(getFactors().values().size()) - 1;
   */
  public final int getPlateauLeftPriority() {
    assert $factorPriorityLists.length == 2;
    return (($factorPriorityLists[0] == null) || ($factorPriorityLists[1] == null)) ?
             0 :
             Math.min($factorPriorityLists[0].size(), $factorPriorityLists[1].size()) - 1;
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

  /**
   * This is an {@code int}, and not a {@link BigInteger}, for the product of
   * 2 factors.
   */
  public final int getNrOfCombinationsInBucket(int priority) {
    return (priority <= getPlateauLeftPriority()) ?
              priority + 1 :
              ((priority >= getPlateauRightPriority()) ?
                 getBaseSize() - priority :
                 getPlateauLeftPriority() + 1);
  }

  /*</section>*/



  /* <section name="list methods"> */
  //------------------------------------------------------------------

  public final BiProductPriorityList<_Label_, _ResultMapElement_> subList(int fromIndex, int toIndex)
      throws IndexOutOfBoundsException {
    return new BiProductPriorityList<_Label_, _ResultMapElement_>($factors, fromIndex, toIndex);
  }

  /*</section>*/


  public UnionBigSet<? extends Map<_Label_, _ResultMapElement_>> get(int index) throws IndexOutOfBoundsException {
    if ((index < 0) || (index >= size())) {
      throw new IndexOutOfBoundsException();
    }
    int priority = index + $fromInclusive;
    int leftStartPriority = Math.max(0, priority - $factorPriorityLists[1].size() + 1);
    int nrOfCombinationsInBucket = getNrOfCombinationsInBucket(priority);
    @SuppressWarnings("unchecked") LockableBigSet<? extends Map<_Label_, _ResultMapElement_>>[] products =
        (ProductBigSet<_Label_, _ResultMapElement_>[])new ProductBigSet<?, ?>[nrOfCombinationsInBucket];
    for (int i = 0; i < nrOfCombinationsInBucket; i++) {
      int leftPriority = leftStartPriority + i;
      int rightPriority = priority - leftPriority;
      Map<_Label_, LockableBigSet<? extends _ResultMapElement_>> componentMap =
          new HashMap<_Label_, LockableBigSet<? extends _ResultMapElement_>>(2);
      componentMap.put($factorLabels[0], $factorPriorityLists[0].get(leftPriority));
      componentMap.put($factorLabels[1], $factorPriorityLists[1].get(rightPriority));
      products[i] = new ProductBigSet<_Label_, _ResultMapElement_>(componentMap);
    }
    UnionBigSet<Map<_Label_, _ResultMapElement_>> result =
        new UnionBigSet<Map<_Label_, _ResultMapElement_>>(isNullPriorityElementAllowed(), products);
    return result;
  }

}