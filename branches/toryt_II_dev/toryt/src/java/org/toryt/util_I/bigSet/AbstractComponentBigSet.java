package org.toryt.util_I.bigSet;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Iterator;

import org.apache.commons.lang.ArrayUtils;
import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;


/**
 * <p>A lazy big set, build from component BigSets.</p>
 * <p>This class contains common implementations for subtypes.</p>
 *
 * @author Jan Dockx
 *
 * @invar getComponents() != null;
 * @invar cC:noNull(getComponents());
 * @invar (forall int i; (i >= 0) && (i < getComponents().length);
 *          getComponents()[i].isLocked());
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
   * @pre cC:noNull(components);
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        components[i].isLocked());
   * @post Collections.containsAll(components, new.getComponents());
   */
  public AbstractComponentBigSet(Class elementType, BigInteger bigSize, LockableBigSet[] components) {
    super(elementType, bigSize);
    assert components != null;
    assert Collections.noNull(components);
    assert Collections.forAll(components,
                              new Assertion() {

                                    public boolean isTrueFor(Object o) {
                                      return ((LockableBigSet)o).isLocked();
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
   * @invar cC:noNull($components);
   * @invar (forall int i; (i >= 0) && (i < $components.length);
   *          $components[i].isLocked());
   */
  private final LockableBigSet[] $components;

  /*</property>*/

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