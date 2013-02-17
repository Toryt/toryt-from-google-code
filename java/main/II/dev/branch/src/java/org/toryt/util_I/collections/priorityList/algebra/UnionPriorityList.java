package org.toryt.util_I.collections.priorityList.algebra;


import java.util.ListIterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.algebra.UnionBigSet;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * <p>A {@link PriorityList}, that is the union of all component PriorityLists.
 *   This is a {@link PriorityList} that contains buckets that are the unions
 *   of the buckets with that priority in all component priority lists. If a
 *   bucket in a component priority list is <code>null</code>, it is considered
 *   the same as an empty component priority list.</p>
 * <p>Because big sets can be so large (and <code>contains(Object)</code>
 *   and <code>containsAll(Collection)</code> are deprecated to signal this),
 *   it is not sensible to try to avoid {@link Object#equals(Object) equal}
 *   priority elements from different component priority lists in this implementation.
 *   Therefor, it is a precondition in the constructor for all
 *   component priority lists to be <em>disjunct</em> on the level of
 *   the priority elements.</p>
 *
 * @author Jan Dockx
 *
 * @invar (forall int i; (i >= 0) && (i < getComponents().length);
 *          (forall int j; (j >= 0) && (j < getComponents().length) && (j != i);
 *            (forall Object o; getComponents()[i].containsPriorityElement(o);
 *              ! getComponents()[j].containsPriorityElement(o)));
 *        component priority lists are disjunct on the level of the priority elements
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class UnionPriorityList<_PriorityElement_>
    extends AbstractComponentPriorityList<_PriorityElement_> {

  /**
   * @pre component != null;
   * @pre cC:noNull(components);
   * @pre (forall int i; (i >= 0) && (i < $components.length);
   *        $components[i].isLocked());
   * @pre (forall int i; (i >= 0) && (i < $components.length);
   *        (forall int j; (j >= 0) && (j < $components.length) && (j != i);
   *          (forall Object o; $components[i].containsPriorityElement(o);
   *             ! $components[j].containsPriorityElement(o)));
   *      component priority lists must be disjunct on the level of the priority
   *      elements (not checked with an assertion because too expensive)
   * @pre (! nullAllowed) ? (forall int i; (i >= 0) && (i < components.length);
   *          ! components[i].containsPriorityElement(null));
   * @post new.isNullPriorityElementAllowed() == nullPriorityElementAllowed;
   * @post Collections.containsAll(components, new.getComponents());
   */
  public UnionPriorityList(boolean nullPriorityElementAllowed,
                           PriorityList<? extends _PriorityElement_>... component) {
    super(calculateSize(component), nullPriorityElementAllowed, component);
    // no assertion for being disjunct
  }

  private static int calculateSize(PriorityList<?>[] components) {
    assert components != null;
    int max = 0;
    for (PriorityList<?> pl : components) {
      max = Math.max(pl.size(), max);
    }
    return max;
  }

  // overriding clone is not necessary: there are no instance variable here that need to be copied

  /**
   * Contains an equal {@link LockableBigSet}.
   *
   * @deprecated This method is very costly, as it
   *             effectively creates buckets one after the
   *             other
   */
  @Override
  @Deprecated
  public final boolean contains(final Object o) {
    return super.contains(o);
  }

  public final UnionPriorityList<_PriorityElement_> subList(int fromInclusive, int toExclusive)
      throws IndexOutOfBoundsException {
    if ((fromInclusive < 0) || (toExclusive < 0) || (fromInclusive > toExclusive) || (toExclusive > size())) {
      throw new IndexOutOfBoundsException();
    }
    PriorityList<? extends _PriorityElement_>[] components = getComponents();
    @SuppressWarnings("unchecked") PriorityList<? extends _PriorityElement_>[] subcomponents =
      (PriorityList<? extends _PriorityElement_>[])new PriorityList<?>[getComponents().length];
    for (int i = 0; i < subcomponents.length; i++) {
      PriorityList<? extends _PriorityElement_> pl = components[i];
      int maxPriority = Math.min(toExclusive, pl.size());
      subcomponents[i] = pl.subList(fromInclusive, maxPriority);
    }
    return new UnionPriorityList<_PriorityElement_>(isNullPriorityElementAllowed(), subcomponents);
  }

  public final UnionBigSet<? extends _PriorityElement_> get(int index) {
    PriorityList<? extends _PriorityElement_>[] components = getComponents();
    @SuppressWarnings("unchecked") LockableBigSet<? extends _PriorityElement_>[] result =
        (LockableBigSet<? extends _PriorityElement_>[])new LockableBigSet<?>[components.length];
    for (int i = 0; i < components.length; i++) {
      result[i] = (index < components[i].size()) ? components[i].get(index) : null;
    }
    return new UnionBigSet<_PriorityElement_>(isNullPriorityElementAllowed(), result);
  }

   public final ListIterator<LockableBigSet<? extends _PriorityElement_>> listIterator(final int index)
       throws IndexOutOfBoundsException {
     return new UnionListIterator(index);
   }

  private class UnionListIterator extends AbstractLockedListIterator {

    public UnionListIterator(int index) throws IndexOutOfBoundsException {
      if ((index < 0) || (index > size())) {
        throw new IndexOutOfBoundsException();
      }
      PriorityList<? extends _PriorityElement_>[] components = getComponents();
      @SuppressWarnings("unchecked")
      ListIterator<? extends LockableBigSet<? extends _PriorityElement_>>[] iterators =
          (ListIterator<? extends LockableBigSet<? extends _PriorityElement_>>[])new ListIterator<?>[components.length];
      $componentIterators = iterators;
      for (int i = 0; i < components.length; i++) {
        assert components[i] != null;
        $componentIterators[i] = (index <= components[i].size()) ?
                                   components[i].listIterator(index) :
                                   components[i].listIterator(components[i].size());
      }
      $currentNextIndex = index;
    }

    /**
     * $componentIterators[i] are past the last position for a component that is depleted;
     *
     * @invar Collections.noNull($componentIterators);
     */
    private final ListIterator<? extends LockableBigSet<? extends _PriorityElement_>>[] $componentIterators;

    /**
     * @invar $currentNextIndex >= 0;
     */
    private int $currentNextIndex;

    public int nextIndex() {
      return $currentNextIndex;
    }

    public int previousIndex() {
      return $currentNextIndex - 1;
    }

    public boolean hasNext() {
      return $currentNextIndex < size();
    }

    public boolean hasPrevious() {
      return $currentNextIndex > 0;
    }

    public LockableBigSet<? extends _PriorityElement_> next() {
      @SuppressWarnings("unchecked") LockableBigSet<? extends _PriorityElement_>[] resultComponents =
          (LockableBigSet<? extends _PriorityElement_>[])new LockableBigSet<?>[$componentIterators.length];
      for (int i = 0; i < $componentIterators.length; i++) {
        resultComponents[i] = $componentIterators[i].hasNext() ?
                                $componentIterators[i].next() :
                                null;
      }
      $currentNextIndex++;
      return new UnionBigSet<_PriorityElement_>(isNullPriorityElementAllowed(), resultComponents);
    }

    public LockableBigSet<? extends _PriorityElement_> previous() {
      @SuppressWarnings("unchecked") LockableBigSet<? extends _PriorityElement_>[] resultComponents =
        (LockableBigSet<? extends _PriorityElement_>[])new LockableBigSet<?>[$componentIterators.length];
    for (int i = 0; i < $componentIterators.length; i++) {
      resultComponents[i] = $componentIterators[i].hasPrevious() ?
                              $componentIterators[i].previous() :
                              null;
    }
    $currentNextIndex--;
    return new UnionBigSet<_PriorityElement_>(isNullPriorityElementAllowed(), resultComponents);
    }

  }

}