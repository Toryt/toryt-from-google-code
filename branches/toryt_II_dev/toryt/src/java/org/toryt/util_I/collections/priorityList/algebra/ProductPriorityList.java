package org.toryt.util_I.collections.priorityList.algebra;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.algebra.ProductBigSet;
import org.toryt.util_I.collections.bigSet.algebra.UnionBigSet;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.priorityList.PriorityList;
import org.toryt.util_I.collections.priorityList.algebra.ProductPriorityList.BiCurryTreeNode.BucketInfo;


/**
 * <p>A lazy priority list, that is the cartesian product of factor
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
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ProductPriorityList<_Label_, _ResultMapElement_>
    extends AbstractProductPriorityList<_Label_, _ResultMapElement_> {

  /* <construction> */
  //------------------------------------------------------------------

  /**
   * @pre factors != null;
   * @pre ! factors.isEmpty();
   * @pre Collections.noNullKey(factors);
   * @pre (forall PriorityList<?> pl : factors.values(); (pl == null) || pl.isLocked());
   * @post new.getFactors().equals(factors);
   */
  public ProductPriorityList(Map<? extends _Label_, ? extends PriorityList<? extends _ResultMapElement_>> factors,
                                int fromInclusive, int toExclusive) {
    super(factors, fromInclusive, toExclusive);
  }

  /**
   * @pre factors != null;
   * @pre ! factors.isEmpty();
   * @pre Collections.noNullKey(factors);
   * @pre (forall PriorityList<?> pl : factors.values(); (pl == null) || pl.isLocked());
   * @post new.getFactors().equals(factors);
   */
  public ProductPriorityList(Map<? extends _Label_, ? extends PriorityList<? extends _ResultMapElement_>> factors) {
    this(factors, 0, AbstractProductPriorityList.baseSize(factors));
  }

  // overriding clone is not necessary: there are no instance variable here that need to be copied
  yes there are, but this class is not ok anyway

  /*</construction>*/



  /* <section name="curry tree structure"> */
  //------------------------------------------------------------------

  private CurryTreeNode $factorCurryTree;

  private abstract class CurryTreeNode {

    public abstract LockableBigSet<? extends Map<_Label_, _ResultMapElement_>>[] buildProducts(int priority);

    protected abstract void fillCombinationMap(int leftPriority, int rightPriority,
                                               Map<_Label_, LockableBigSet<? extends _ResultMapElement_>> combinationMap);

  }

  private CurryTreeNode generateCurryTreeNode(Map<_Label_, PriorityList<? extends _ResultMapElement_>> factors) {
    return (factors.size() > 2) ?
             new MultiCurryTreeNode(factors) :
             new BiCurryTreeNode(factors);
  }

  private class MultiCurryTreeNode extends CurryTreeNode {

    public MultiCurryTreeNode(Map<_Label_, PriorityList<? extends _ResultMapElement_>> factors) {
      assert 0 <= factors.size();
      assert factors.size() >= 2;
      int splitPointExclusive = (factors.size() / 2) + 1;
      Map<_Label_, PriorityList<? extends _ResultMapElement_>> leftMap =
        new HashMap<_Label_, PriorityList<? extends _ResultMapElement_>>(splitPointExclusive);
      Map<_Label_, PriorityList<? extends _ResultMapElement_>> rightMap =
        new HashMap<_Label_, PriorityList<? extends _ResultMapElement_>>(factors.size() - splitPointExclusive);
      Iterator<Map.Entry<_Label_, PriorityList<? extends _ResultMapElement_>>> iter =
        factors.entrySet().iterator();
      $nrOfFactors = 0;
      while (iter.hasNext()) {
        Map.Entry<_Label_, PriorityList<? extends _ResultMapElement_>> e = iter.next();
        if ($nrOfFactors < splitPointExclusive) {
          leftMap.put(e.getKey(), e.getValue());
        }
        else {
          rightMap.put(e.getKey(), e.getValue());
        }
        $nrOfFactors++;
      }
      assert $nrOfFactors == factors.size();
      $left = generateCurryTreeNode(leftMap);
      $right = generateCurryTreeNode(rightMap);
    }

    public int $nrOfFactors;
    public final CurryTreeNode $left;
    public final CurryTreeNode $right;

    public LockableBigSet<? extends Map<_Label_, _ResultMapElement_>>[] buildProducts(int priority) {
      BucketInfo bi = new BucketInfo(priority);
      @SuppressWarnings("unchecked") LockableBigSet<? extends Map<_Label_, _ResultMapElement_>>[] products =
          (ProductBigSet<_Label_, _ResultMapElement_>[])new ProductBigSet<?, ?>[bi.$nrOfCombinationsInBucket];

      List<LockableBigSet<? extends Map<_Label_, _ResultMapElement_>>> productList =
          new LinkedList<LockableBigSet<? extends Map<_Label_, _ResultMapElement_>>>();
      for (int i = 0; i < bi.$nrOfCombinationsInBucket; i++) {
        Map<_Label_, LockableBigSet<? extends _ResultMapElement_>> combinationMap =
          new HashMap<_Label_, LockableBigSet<? extends _ResultMapElement_>>($nrOfFactors);
        int leftPriority = bi.$leftStartPriority + i;
        int rightPriority = priority - leftPriority;
        fillCombinationMap(leftPriority, rightPriority, combinationMap);
        products[i] = new ProductBigSet<_Label_, _ResultMapElement_>(combinationMap);
      }
      return products;
    }

    protected void fillCombinationMap(int leftPriority, int rightPriority,
                                      Map<_Label_, LockableBigSet<? extends _ResultMapElement_>> combinationMap) {
      $left.buildProductsOTHER(leftPriority);
      $right.buildProductsOTHER(rightPriority);
      $left.fillCombinationMap(leftPriority, rightPriority, combinationMap);
      $right.fillCombinationMap(leftPriority, rightPriority, combinationMap);
    }

  }

  private class BiCurryTreeNode extends CurryTreeNode {

    public BiCurryTreeNode(Map<_Label_, PriorityList<? extends _ResultMapElement_>> factors) {
      assert factors.size() == 2;
      Iterator<Map.Entry<_Label_, PriorityList<? extends _ResultMapElement_>>> iter =
        factors.entrySet().iterator();
      Map.Entry<_Label_, PriorityList<? extends _ResultMapElement_>> e = iter.next();
      $leftLabel = e.getKey();
      $leftFactor = e.getValue();
      $rightLabel = e.getKey();
      $rightFactor = e.getValue();
      $productSize = (($leftFactor == null) || ($rightFactor == null)) ? 0 :
                       ($leftFactor.size() - 1) + ($rightFactor.size() - 1) + 1;
      $plateauLeftPriorityInclusive = (($leftFactor == null) || ($rightFactor == null)) ? 0 :
                                        Math.min($leftFactor.size(), $rightFactor.size()) - 1;
      $plateauSize = $productSize - (2 * $plateauLeftPriorityInclusive);
      $plateauRightPriorityInclusive = $plateauLeftPriorityInclusive + $plateauSize - 1;
    }

    public final _Label_ $leftLabel;
    public final PriorityList<? extends _ResultMapElement_> $leftFactor;
    public final _Label_ $rightLabel;
    public final PriorityList<? extends _ResultMapElement_> $rightFactor;

    public final int $productSize;

    public final int $plateauLeftPriorityInclusive;
    public final int $plateauRightPriorityInclusive;
    public final int $plateauSize;

    public class BucketInfo {

      public BucketInfo(int priority) {
        $nrOfCombinationsInBucket = (priority <= $plateauLeftPriorityInclusive) ? priority + 1 :
                                      ((priority >= $plateauRightPriorityInclusive) ? $productSize - priority :
                                        $plateauLeftPriorityInclusive + 1);
        $leftStartPriority = Math.max(0, priority - $plateauRightPriorityInclusive + 1);
      }

      public final int $nrOfCombinationsInBucket;
      public final int $leftStartPriority;

    }

    @Override
    public LockableBigSet<? extends Map<_Label_, _ResultMapElement_>>[] buildProducts(int priority) {
      BucketInfo bi = new BucketInfo(priority);
      @SuppressWarnings("unchecked") LockableBigSet<? extends Map<_Label_, _ResultMapElement_>>[] products =
          (ProductBigSet<_Label_, _ResultMapElement_>[])new ProductBigSet<?, ?>[bi.$nrOfCombinationsInBucket];
      for (int i = 0; i < bi.$nrOfCombinationsInBucket; i++) {
        int leftPriority = bi.$leftStartPriority + i;
        int rightPriority = priority - leftPriority;
        Map<_Label_, LockableBigSet<? extends _ResultMapElement_>> combinationMap =
            new HashMap<_Label_, LockableBigSet<? extends _ResultMapElement_>>(2);
        fillCombinationMap(leftPriority, rightPriority, combinationMap);
        products[i] = new ProductBigSet<_Label_, _ResultMapElement_>(combinationMap);
      }
      return products;
    }

    @Override
    protected void fillCombinationMap(int leftPriority, int rightPriority,
                                      Map<_Label_, LockableBigSet<? extends _ResultMapElement_>> combinationMap) {
      combinationMap.put($leftLabel, $leftFactor.get(leftPriority));
      combinationMap.put($rightLabel, $rightFactor.get(rightPriority));
    }

  }

  /*</section>*/



  /* <section name="list methods"> */
  //------------------------------------------------------------------

  public final ProductPriorityList<_Label_, _ResultMapElement_> subList(int fromIndex, int toIndex)
      throws IndexOutOfBoundsException {
    return new ProductPriorityList<_Label_, _ResultMapElement_>($factors, fromIndex, toIndex);
  }

  /*</section>*/


  public UnionBigSet<? extends Map<_Label_, _ResultMapElement_>> get(int index) throws IndexOutOfBoundsException {
    if ((index < 0) || (index >= size())) {
      throw new IndexOutOfBoundsException();
    }
    int priority = index + $fromInclusive;
    LockableBigSet<? extends Map<_Label_, _ResultMapElement_>>[] products = $factorCurryTree.buildProducts(priority);
    UnionBigSet<Map<_Label_, _ResultMapElement_>> result =
        new UnionBigSet<Map<_Label_, _ResultMapElement_>>(isNullPriorityElementAllowed(), products);
    return result;
  }

  /**
   * Independent of number of factors.
   * Makes no sense to make static.
   */
  private Map<_Label_, LockableBigSet<? extends _ResultMapElement_>> build1FactorMap(int priorities[]) {
    assert priorities.length == $factorPriorityLists.length;
    assert Collections.forAll(priorities,
                              new Assertion<Integer>() {
                                    @SuppressWarnings("boxing")
                                    public boolean isTrueFor(Integer o) {
                                      return o >= 0;
                                    }
                                  });
    // assert forAll i: priorities[i] < $factorPriorityLists[i]
    Map<_Label_, LockableBigSet<? extends _ResultMapElement_>> result =
        new HashMap<_Label_, LockableBigSet<? extends _ResultMapElement_>>($factorPriorityLists.length);
    for (int i = 0; i < priorities.length; i++) {
      result.put($factorLabels[i], $factorPriorityLists[i].get(priorities[i]));
    }
    return result;
  }

  /**
   * Not static because we need generic type information.
   * Independent of number of factors.
   */
  private static <_Label_, _ResultMapElement_> LockableBigSet<? extends Map<_Label_, _ResultMapElement_>>
      build1ProductBigSet(Map<_Label_, LockableBigSet<? extends _ResultMapElement_>> factorMap) {
    return new ProductBigSet<_Label_, _ResultMapElement_>(factorMap);
  }

}