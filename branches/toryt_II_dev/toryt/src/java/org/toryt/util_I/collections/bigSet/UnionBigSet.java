package org.toryt.util_I.collections.bigSet;


import java.math.BigInteger;
import java.util.Iterator;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;


/**
 * <p>A lazy big set, that is the union of all component BigSets.</p>
 * <p>Because big sets can be so large (and <code>contains(Object)</code>
 *   and <code>containsAll(Collection)</code> are deprecated to signal this),
 *   it is not sensible to try to avoid {@link Object#equals(Object) equal}
 *   elements from different component sets in this implementation.
 *   Therefor, it is a precondition in the constructor for all
 *   component sets to be <em>disjunct</em>.</p>
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
   * @pre cC:noNull(components);
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        components[i].isLocked());
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        getElementType().isAssignableFrom(components[i].getElementType()));
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        (forall int j; (j >= 0) && (j < components.length) && (j != i);
   *          (forall Object o; components[i].contains(o);
   *             ! components[j].contains(o)));
   *      component sets must be disjunct (not checked with an assertion
   *      because too expensive)
   * @post Collections.containsAll(components, new.getComponents());
   */
  public UnionBigSet(Class elementType, LockableBigSet[] components) {
    super(elementType, calculateSize(components), components);
    assert Collections.forAll(components,
                              new Assertion() {

                                    public boolean isTrueFor(Object o) {
                                      return getElementType().
                                              isAssignableFrom(((LockableBigSet)o).
                                                               getElementType());
                                    }

                                  });
  }

  private static BigInteger calculateSize(LockableBigSet[] components) {
    BigInteger result = BigInteger.ZERO;
    for (int i = 0; i < components.length; i++) {
      result = result.add(components[i].getBigSize());
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
                                      return ((LockableBigSet)s).contains(o);
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
                                      return ((LockableBigSet)o).isEmpty();
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
                  $components[$componentIndex].isEmpty());
        if ($componentIndex < $components.length) {
          assert ! $components[$componentIndex].isEmpty();
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