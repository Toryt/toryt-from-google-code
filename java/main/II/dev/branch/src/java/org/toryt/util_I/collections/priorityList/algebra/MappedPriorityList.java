package org.toryt.util_I.collections.priorityList.algebra;


import java.util.Iterator;
import java.util.ListIterator;

import org.toryt.patterns_I.Comparisons;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.algebra.Mapping;
import org.toryt.util_I.collections.bigSet.algebra.MappedBigSet;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.priorityList.AbstractLockedPriorityList;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * <p>A lazy {@link PriorityList}, wrapped around another {@link PriorityList},
 *   that represents the priority elements of the wrapped {@link PriorityList}
 *   mapped using a {@link Mapping}.</p>
 * <p>
 *
 * Since a mapping can do anything, we are not sure the result is a
 *   set, nor that it will or will not contain {@code null}. In general,
 *   the result is a collection, and could always contain {@code null}.</p>
 *
 * @mudo The elements of this should be a collection, and not a set.
 *       This means that priority lists should use collections, not sets
 *       as elements, or that we need to be able to force the mapping to
 *       be an injection (f: X --> Y is an injection if
 *       forall (X x1 : X) {forall (X x2 : X) {f(x1) == f(x2) ? x1 == x2}}.
 *       Since tests have reference equality, this is okay with a lazy set
 *       anyway, but we need to think a bit whether this bothers us or not.
 *       In practice, it doesn't.
 *
 * @author Jan Dockx
 *
 * @invar getMapping() != null;
 * @invar getMapping().isInjection();
 * @invar getMapping().isNoNewNullMapping();
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class MappedPriorityList<_WrappedPriorityElement_, _PriorityElement_>
    extends AbstractLockedPriorityList<_PriorityElement_> {

  /**
   * @pre wrapped != null;
   * @pre wrapped.isLocked() != null;
   * @pre mapping != null;
   * @pre mapping.isInjection();
   * @pre mapping.isNoNewNullMapping();
   * @post new.getWrapped() == wrapped;
   * @post new.getMapping() == mapping;
   */
  public MappedPriorityList(PriorityList<? extends _WrappedPriorityElement_> wrapped,
                            Mapping<_WrappedPriorityElement_, _PriorityElement_> mapping) {
    super(wrapped.getCardinality(), wrapped.isNullPriorityElementAllowed());
    assert wrapped != null;
    assert wrapped.isLocked();
    assert mapping != null;
    assert mapping.isInjection();
    assert mapping.isNoNewNullMapping();
    $wrapped = wrapped;
    $mapping = mapping;
  }

  /**
   * @basic
   */
  public final PriorityList<? extends _WrappedPriorityElement_> getWrapped() {
    return $wrapped; // is save: it is locked and thus immutable
  }

  /**
   * @invar $wrapped != null;
   * @invar $wrapped.isLocked();
   */
  public final PriorityList<? extends _WrappedPriorityElement_> $wrapped;

  /**
   * @basic
   */
  public final Mapping<_WrappedPriorityElement_, _PriorityElement_> getMapping() {
    return $mapping;
  }

  /**
   * @invar $mapping != null;
   */
  public final Mapping<_WrappedPriorityElement_, _PriorityElement_> $mapping;

  /**
   * If this method is used with
   * {@code (! o instanceof PriorityList) && (! o instanceof MappedPriorityList)},
   * it is possibly very expensive, all element big sets are compared.
   */
  @Override
  public final boolean equals(Object o) {
    if ((o == null) || (! (o instanceof PriorityList<?>))) {
      return false;
    }
    else if (o instanceof MappedPriorityList<?, ?>) {
      PriorityList<?> otherWrapped = ((MappedPriorityList<?, ?>)o).getWrapped();
      return $wrapped.equals(otherWrapped);
    }
    else {
      // fallback; very possibly expensive, and stupid for a lazy set
      return super.equals(o);
    }
  }

  @Override
  public final int hashCode() {
    return getWrapped().hashCode();
  }

  public final boolean isEmpty() {
    return $wrapped.isEmpty();
  }

  /**
   * Most often, calling this method does not makes sense,
   * since most {@link Mapping mappings} are in fact factories of objects
   * with reference equality. This method is very expensive.
   */
  public boolean containsPriorityElement(Object o) {
    Iterator<? extends _PriorityElement_> iter = priorityElementIterator();
    while (iter.hasNext()) {
      _PriorityElement_ element = iter.next();
      if (Comparisons.equalsWithNull(o, element)) {
        return true;
      }
    }
    return false;
  }

  public final PriorityList<_PriorityElement_> subList(int fromIndexInclusive, int toIndexExclusive) {
    return new MappedPriorityList<_WrappedPriorityElement_, _PriorityElement_>(
                  $wrapped.subList(fromIndexInclusive, toIndexExclusive),
                  $mapping);
  }

  public final int size() {
    return $wrapped.size();
  }

  public final LockableBigSet<? extends _PriorityElement_> get(int index) {
    return new MappedBigSet<_WrappedPriorityElement_, _PriorityElement_>($wrapped.get(index), $mapping);
  }

  public final ListIterator<LockableBigSet<? extends _PriorityElement_>> listIterator(int index) {
    return new GetBasedListIterator(index);
  }

}