package org.toryt.util_I.bigSet;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Iterator;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;


/**
 * <p>A lazy big set, that is the cartesian product of all component
 *   BigSets. The elements of this BigSet are arrays of type
 *   {@link #getElementType()} with 1 element per
 *   {@link #getComponents() component}.</p>
 *
 * @author Jan Dockx
 *
 * @invar (forall int i; (i >= 0) && (i < getComponents().length);
 *          getElementType().getComponentType().
 *              isAssignableFrom(getComponents()[i].getElementType()));
 * @invar getElementType().getComponentType() != null;
 */
public class ProductBigSet extends AbstractComponentBigSet {

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
   *        getElementType().getComponentType().
   *            isAssignableFrom(components[i].getElementType()));
   * @pre elementType.getComponentType() != null;
   * @post Collections.containsAll(components, new.getComponents());
   */
  public ProductBigSet(Class elementType, LockableBigSet[] components) {
    super(elementType, calculateSize(components), components);
    assert elementType.getComponentType() != null;
    assert Collections.forAll(components,
                              new Assertion() {

                                    public boolean isTrueFor(Object o) {
                                      return getElementType().getComponentType().
                                              isAssignableFrom(((LockableBigSet)o).
                                                                   getElementType());
                                    }

                                  });
  }

  private static BigInteger calculateSize(LockableBigSet[] components) {
    BigInteger result = BigInteger.ONE;
    for (int i = 0; i < components.length; i++) {
      result = result.multiply(components[i].getBigSize());
    }
    return result;
  }

  /**
   * @return (product int i; (i >=0 ) && (i < getComponents().length);
   *            getComponents()[i].getBigSize());
   *
  public final BigInteger getBigSize();
   */

  public final boolean contains(final Object o) {
    if (o == null) {
      return false;
    }
    Object[] array = (Object[])o;
    LockableBigSet[] components = getComponents();
    if (array.length != components.length) {
      return false;
    }
    for (int i = 0; i < array.length; i++) {
      if (! components[i].contains(array[i])) {
        return false;
      }
    }
    return true;
  }

  /**
   * @return (exists int i; (i > 0) && (i < getComponents().length);
   *            getComponents()[i].isEmpty());
   */
  public final boolean isEmpty() {
    return Collections.exists(getComponents(),
                              new Assertion() {

                                    public boolean isTrueFor(Object o) {
                                      return ((LockableBigSet)o).isEmpty();
                                    }

                                  });
  }

  public Iterator iterator() {
    return new AbstractLockedCollectionIterator() {

      private final Class componentType = getElementType().getComponentType();

      private final LockableBigSet[] $components = getComponents();

      private final int dim = $components.length;

      private final Iterator[] $iterators = new Iterator[dim];

      {
        for (int i = 0; i < dim; i++) {
          $iterators[i] = $components[i].iterator();
        }
      }

      private Object[] $next;

      {
        $next = (Object[])Array.newInstance(componentType, dim);
        for (int j = dim - 1; j >= 0; j--) {
          if ($iterators[j].hasNext()) {
            // we have an empty component
            $next = null;
            assert isEmpty();
            assert ! hasNext();
            break;
          }
          $next[j] = $iterators[j].next();
        }
      }

      private void prepareNext() {
        int i = dim - 1;
        boolean canProceed = false;
        while ((! canProceed) && (i >= 0)) {
          if (! $iterators[i].hasNext()) {
            $iterators[i] = $components[i].iterator();
            i--;
          }
          else {
            canProceed = true;
            // iterator at index i is first not reset
          }
        }
        if (! canProceed) {
          // if canProceed is still false; we are at the end
          $next = null;
        }
        else {
          Object[] previous = $next;
          $next = (Object[])Array.newInstance(componentType, dim);
          // copy entries from previous up until i -1
          System.arraycopy(previous, 0, $next, 0, i);
          // take next at index i, and first elements at later indices
          for (int j = i; j < dim; j++) {
            $next[j] = $iterators[j].next();
          }
        }
      }

      public final boolean hasNext() {
        return $next != null;
      }

      public final Object next() {
        Object[] result = $next;
        prepareNext();
        return result;
      }

    };
  }

}