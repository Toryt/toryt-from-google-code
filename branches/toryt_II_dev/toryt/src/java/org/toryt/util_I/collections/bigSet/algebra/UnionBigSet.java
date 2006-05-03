package org.toryt.util_I.collections.bigSet.algebra;


import java.math.BigInteger;
import java.util.Iterator;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;


/**
 * <p>A lazy big set, that is the union of all component BigSets.</p>
 * <p>Because big sets can be so large (and <code>toArray()</code>
 *   and <code>toArray(Object[])</code> are deprecated to signal this),
 *   it is not sensible to try to avoid {@link Object#equals(Object) equal}
 *   elements from different component sets in this implementation.
 *   Therefor, it is a precondition in the constructor for all
 *   component sets to be <em>disjunct</em>.</p>
 * <p>If one of the {@link #getComponents()} is <code>null</code>,
 *   it behaves as an empty set.</p>
 *
 * @author Jan Dockx
 *
 * @invar (forall int i; (i >= 0) && (i < getComponents().length);
 *          getElementType().isAssignableFrom(getComponents()[i].getElementType()));
 * @invar (forall int i; (i >= 0) && (i < getComponents().length);
 *          (forall int j; (j >= 0) && (j < getComponents().length) && (j != i);
 *            (forall Object o; getComponents()[i].contains(o);
 *              ! getComponents()[j].contains(o)));
 *        component sets are disjunct
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class UnionBigSet<_ElementType_> extends AbstractComponentBigSet<_ElementType_, _ElementType_> {

  /**
   * @pre components != null;
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        (components[i] != null) ? components[i].isLocked());
   * @pre (! nullAllowed) ?
   *        (forall int i; (i >= 0) && (i < components.length);
   *          (components[i] != null) ? (! components[i].contains(null)));
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        (forall int j; (j >= 0) && (j < components.length) && (j != i);
   *          (components[i] != null) && (components[j] != null) ?
   *            (forall Object o; components[i].contains(o);
   *              ! components[j].contains(o)));
   *      component sets must be disjunct (not checked with an assertion
   *      because too expensive)
   * @post Collections.containsAll(components, new.getComponents());
   */
  public UnionBigSet(boolean nullAllowed, LockableBigSet<? extends _ElementType_>... component) {
    super(nullAllowed, calculateSize(component), component);
  }

  private static BigInteger calculateSize(LockableBigSet<?>[] component) {
    BigInteger result = BigInteger.ZERO;
    for (LockableBigSet<?> c : component) {
      if (c != null) {
        result = result.add(c.getBigSize());
      }
    }
    return result;
  }

  /**
   * @return (sum int i; (i >=0 ) && (i < getComponents().length);
   *            getComponents()[i].getBigSize());
   *
  public final BigInteger getBigSize();
   */

  @Override
  public final boolean contains(final Object o) {
    return Collections.exists(getComponents(),
                              new Assertion<LockableBigSet<? extends _ElementType_>>() {

                                    public boolean isTrueFor(LockableBigSet<? extends _ElementType_> s) {
                                      return (s != null) && s.contains(o);
                                    }

                                  });
  }

  /**
   * @return (forall int i; (i > 0) && (i < getComponents().length);
   *            getComponents()[i].isEmpty());
   */
  public final boolean isEmpty() {
    return Collections.forAll(getComponents(),
                              new Assertion<LockableBigSet<? extends _ElementType_>>() {

                                    public boolean isTrueFor(LockableBigSet<? extends _ElementType_> o) {
                                      return (o == null) || o.isEmpty();
                                    }

                                  });
  }

  public Iterator<_ElementType_> iterator() {
    return new AbstractLockedCollectionIterator() {

      private final LockableBigSet<? extends _ElementType_>[] $components = getComponents();

      private int $componentIndex = -1;

      private void nextComponentIterator() {
        do {
          $componentIndex++;
        } while (($componentIndex < $components.length) &&
                 (($components[$componentIndex] == null) ||
                  $components[$componentIndex].isEmpty()));
        if ($componentIndex < $components.length) {
          assert ($components[$componentIndex] != null) &&
                 (! $components[$componentIndex].isEmpty());
          $componentIterator = $components[$componentIndex].iterator();
        }
        else {
          $componentIterator = null;
        }
      }

      private Iterator<? extends _ElementType_> $componentIterator;

      {
        nextComponentIterator();
      }

      public final boolean hasNext() {
        return $componentIterator != null;
      }

      public final _ElementType_ next() {
        _ElementType_ result = $componentIterator.next();
        if (! $componentIterator.hasNext()) {
          nextComponentIterator();
        }
        return result;
      }

    };
  }

  /**
   * This method is very expensive, as it iterates over all
   * elements (and thus generates them).
   *
   * @deprecated This method is very expensive, as it iterates over all
   *             elements (and thus generates them).
   */
  @Deprecated
  @Override
  public final Object[] toArray() {
    return super.toArray();
  }

  /**
   * This method is very expensive, as it iterates over all
   * elements (and thus generates them).
   *
   * @deprecated This method is very expensive, as it iterates over all
   *             elements (and thus generates them).
   */
  @Deprecated
  @Override
  public final <_ComponentType_> _ComponentType_[] toArray(_ComponentType_[] a) {
    return super.toArray(a);
  }

}