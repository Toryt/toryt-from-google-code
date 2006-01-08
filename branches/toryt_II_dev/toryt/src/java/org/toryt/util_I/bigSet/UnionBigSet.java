package org.toryt.util_I.bigSet;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Iterator;

import org.apache.commons.lang.ArrayUtils;
import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;


/**
 * <p>A lazy big set, that is the union of all component BigSets.</p>
 * <p>Because big sets can be so large (and {@link #contains(Object)}
 *   and <code>containsAll(Collection)</code> are deprecated to signal this),
 *   it is not sensible to try to avoid {@link Object#equals(Object) equal}
 *   elements from different component sets in this implementation.
 *   Therefor, it is a precondition in the constructor for all
 *   component sets to be <em>disjunct</em>.</p>
 */
public class UnionBigSet extends AbstractLockedBigSet implements LazyBigSet {

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
   *        components[i].getElementType() == getElementType());
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        (forall int j; (j >= 0) && (j < components.length) && (j != i);
   *          (forall Object o; $components[i].contains(o);
   *             ! $components[j].contains(o)));
   *      component sets must be disjunct (not checked with an assertion
   *      because too expensive)
   * @post Collections.containsAll(components, new.getComponents());
   */
  public UnionBigSet(Class elementType, LockBigSet[] components) {
    super(elementType);
    assert components != null;
    assert Collections.noNull(components);
    assert Collections.forAll(components,
                              new Assertion() {

                                    public boolean isTrueFor(Object o) {
                                      return ((LockBigSet)o).isLocked();
                                    }

                                  });
    $components = (LockBigSet[])ArrayUtils.clone(components);
  }



  /* <property name="components"> */
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final LockBigSet[] getComponents() {
    return (LockBigSet[])ArrayUtils.clone($components);
  }

  /**
   * @invar $components != null;
   * @invar cC:noNull($components);
   * @invar (forall int i; (i >= 0) && (i < $components.length);
   *          $components[i].isLocked());
   */
  private final LockBigSet[] $components;

  /*</property>*/



  /* <property name="size"> */
  //------------------------------------------------------------------

  /**
   * @return (sum int i; (i >=0 ) && (i < getComponents().length);
   *            getComponents()[i].getBigSize());
   */
  public final BigInteger getBigSize() {
    if ($cachedSize ==  null) {
      initCachedSize();
    }
    return $cachedSize;
  }

  private void initCachedSize() {
    $cachedSize = BigInteger.ZERO;
    for (int i = 0; i < $components.length; i++) {
      $cachedSize = $cachedSize.and($components[i].getBigSize());

    }
  }

  private BigInteger $cachedSize;

  /*</property>*/



  /**
   * @deprecated
   */
  public boolean contains(final Object o) {
    return Collections.exists($components,
                              new Assertion() {

      public boolean isTrueFor(Object s) {
        return ((LockBigSet)s).contains(o);
      }

    });
  }

  /**
   * @deprecated
   */
  public Object[] toArray(Object[] a) {
    int size = size();
    Object[] result;
    if (a.length >= size) {
      result = a;
    }
    else {
      result = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
    }
    int insert = 0;
    for (int i = 0; i < $components.length; i++) {
      Object[] lbsTa = $components[i].toArray();
      System.arraycopy(lbsTa, 0, result, insert, lbsTa.length);
      insert += lbsTa.length;
    }
    return result;
  }

  /**
   * @return (forall int i; (i > 0) && (i < getComponents().length);
   *            getComponents()[i].isEmpty());
   */
  public final boolean isEmpty() {
    return Collections.forAll($components,
                              new Assertion() {

                                    public boolean isTrueFor(Object o) {
                                      return ((LockBigSet)o).isEmpty();
                                    }

                                  });
  }

  public Iterator iterator() {
    return new AbstractLockedCollectionIterator() {

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