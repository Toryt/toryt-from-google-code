package org.toryt.util_I.collections.bigSet;


import java.math.BigInteger;
import java.util.Iterator;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;


/**
 * <p>A lazy big set, that is the union of all component BigSets.</p>
 * <p>Because big sets can be so large (and <code>toArray()</code>
 *   and <code>toArray(Object[])</code> are deprecated to signal this),
 *   it is not sensible to try to avoid {@link Object#equals(Object) equal}
 *   elements from different component sets in this implementation.
 *   Therefor, it is a precondition in the constructor for all
 *   component sets to be <em>disjunct</em>.</p>
 * <p>If one of the {@link #getComponents()} is <code>null</code>,
 *   it behaves as an empty set with
 *   {@link BigSet#getElementType() <var>component</var>.getElementType()}<code> == </code>{@link #getElementType()}.
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
public class UnionBigSet extends AbstractComponentBigSet {

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



  /**
   * @pre elementType != null;
   * @pre components != null;
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        (components[i] != null) ? components[i].isLocked());
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        (components[i] != null) ? getElementType().isAssignableFrom(components[i].getElementType()));
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        (forall int j; (j >= 0) && (j < components.length) && (j != i);
   *          (components[i] != null) && (components[j] != null) ?
   *            (forall Object o; components[i].contains(o);
   *              ! components[j].contains(o)));
   *      component sets must be disjunct (not checked with an assertion
   *      because too expensive)
   * @post Collections.containsAll(components, new.getComponents());
   */
  public UnionBigSet(Class elementType, LockableBigSet[] components) {
    super(elementType, calculateSize(components), components);
    assert Collections.forAll(components,
                              new Assertion() {

                                    public boolean isTrueFor(Object o) {
                                      return (o != null) ?
                                              getElementType().
                                                isAssignableFrom(((LockableBigSet)o).
                                                                 getElementType()) :
                                              true;
                                    }

                                  });
  }

  private static BigInteger calculateSize(LockableBigSet[] components) {
    BigInteger result = BigInteger.ZERO;
    for (int i = 0; i < components.length; i++) {
      if (components[i] != null) {
        result = result.add(components[i].getBigSize());
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

  public final boolean contains(final Object o) {
    return Collections.exists(getComponents(),
                              new Assertion() {

                                    public boolean isTrueFor(Object s) {
                                      return (s != null) &&
                                             ((LockableBigSet)s).contains(o);
                                    }

                                  });
  }

  /**
   * @return (forall int i; (i > 0) && (i < getComponents().length);
   *            getComponents()[i].isEmpty());
   */
  public final boolean isEmpty() {
    return Collections.forAll(getComponents(),
                              new Assertion() {

                                    public boolean isTrueFor(Object o) {
                                      return (o == null) ||
                                             ((LockableBigSet)o).isEmpty();
                                    }

                                  });
  }

  public Iterator iterator() {
    return new AbstractLockedCollectionIterator() {

      private final LockableBigSet[] $components = getComponents();

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

      private Iterator $componentIterator;

      {
        nextComponentIterator();
      }

      public final boolean hasNext() {
        return $componentIterator != null;
      }

      public final Object next() {
        Object result = $componentIterator.next();
        if (! $componentIterator.hasNext()) {
          nextComponentIterator();
        }
        return result;
      }

    };
  }

}