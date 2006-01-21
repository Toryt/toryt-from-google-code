package org.toryt.util_I.collections.bigSet.algebra;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.ArrayUtils;
import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.collections.bigSet.BigSet;
import org.toryt.util_I.collections.bigSet.lockable.AbstractLockedBigSet;
import org.toryt.util_I.collections.bigSet.lockable.LazyBigSet;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;


/**
 * <p>A lazy big set, build from component BigSets.</p>
 * <p>This class contains common implementations for subtypes.</p>
 * <p>If one of the {@link #getComponents()} is <code>null</code>,
 *   the implementation in the subclass will behave as if it
 *   refers to an empty set with an appropriate
 *   {@link BigSet#getElementType()}.</p>
 *
 * @author Jan Dockx
 *
 * @invar getComponents() != null;
 * @invar (forall int i; (i >= 0) && (i < getComponents().length);
 *          (getComponents()[i] != null) ?getComponents()[i].isLocked());
 * @invar (! isNullAllowed()) ?
 *          (forall int i; (i >= 0) && (i < getComponents().length);
 *            (getComponents()[i] != null) ? (! getComponents()[i].contains(null)));
 */
public abstract class AbstractComponentBigSet extends AbstractLockedBigSet
    implements LazyBigSet {

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
   * @pre bigSize != null;
   * @pre bigSize >= 0;
   * @pre components != null;
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        (components[i] != null) ? components[i].isLocked());
   * @pre (! nullAllowed) ?
   *        (forall int i; (i >= 0) && (i < components.length);
   *          (components[i] != null) ? (! components[i].contains(null)));
   * @post Collections.containsAll(components, new.getComponents());
   */
  public AbstractComponentBigSet(Class elementType,
                                 boolean nullAllowed,
                                 BigInteger bigSize,
                                 LockableBigSet[] components) {
    super(elementType, nullAllowed, bigSize);
    assert components != null;
    assert Collections.forAll(components,
                              new Assertion() {
                                    public boolean isTrueFor(Object o) {
                                      return ((LockableBigSet)o).isLocked();
                                    }
                                  });
    assert nullAllowed ||
             Collections.forAll(components,
                                new Assertion() {
                                      public boolean isTrueFor(Object o) {
                                        return ! ((LockableBigSet)o).contains(null);
                                      }
                                    });
    $components = (LockableBigSet[])ArrayUtils.clone(components);
  }



  /* <property name="components"> */
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final LockableBigSet[] getComponents() {
    return (LockableBigSet[])ArrayUtils.clone($components);
  }

  /**
   * @invar $components != null;
   * @invar (forall int i; (i >= 0) && (i < $components.length);
   *          ($components[i] != null) ? $components[i].isLocked());
   */
  private final LockableBigSet[] $components;

  /*</property>*/

  /**
   * If this method is used with
   * <code>! o instanceof AbstractComponentBigSet</code>,
   * it is possibly very expensive, as it uses
   * {@link #containsAll(Collection)} on this and <code>o</code>:
   * if {@link #containsAll(Collection)} is expensive, so is this
   * method if <code>! o instanceof AbstractComponentBigSet</code>.
   */
  public boolean equals(Object o) {
    if ((o == null) || (! (o instanceof BigSet))) {
      return false;
    }
    else if (o instanceof AbstractComponentBigSet) {
      LockableBigSet[] otherComponents = ((AbstractComponentBigSet)o).getComponents();
      if ($components.length != otherComponents.length) {
        return false;
      }
      for (int i =0; i < $components.length; i++) {
        if (($components[i] != otherComponents[i]) &&
            (($components[i] == null) || (! $components[i].equals(otherComponents[i])))) {
          return false;
        }
      }
      return true;
    }
    else {
      // fallback; very possibly expensive, and stupid for a lazy set
      return getBigSize().equals(((BigSet)o).getBigSize()) &&
             containsAll((BigSet)o) &&
             ((BigSet)o).containsAll(this);
    }
  }

  public int hashCode() {
    int acc = 0;
    for (int i =0; i < $components.length; i++) {
      acc += ($components[i] == null) ? 0 : $components[i].hashCode();
    }
    return acc;
  }


  /**
   * @deprecated
   */
  public final Object[] toArray() {
    return toArray(new Object[size()]);
  }

  /**
   * @deprecated
   */
  public final Object[] toArray(Object[] a) {
    int size = size();
    Object[] result;
    if (a.length >= size) {
      result = a;
      result[size] = null;
    }
    else {
      result = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
    }
    Iterator iter = iterator();
    int i = 0;
    while (iter.hasNext()) {
      result[i] = iter.next();
      i++;
    }
    return result;
  }

}