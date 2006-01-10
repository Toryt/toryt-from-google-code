package org.toryt.util_I.priorityList;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Iterator;

import org.apache.commons.lang.ArrayUtils;
import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.collections.LazyList;


/**
 * <p>A lazy {@link PriorityList}, build from component PriorityLists.</p>
 * <p>This class contains common implementations for subtypes.</p>
 *
 * @author Jan Dockx
 *
 * @invar getComponents() != null;
 * @invar cC:noNull(getComponents());
 * @invar (forall int i; (i >= 0) && (i < getComponents().length);
 *          getComponents()[i].isLocked());
 */
public abstract class AbstractComponentPriorityList extends AbstractLockedPriorityList
    implements LazyList {

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
   * @pre priorityElementType != null;
   * @pre bigSize != null;
   * @pre bigSize >= 0;
   * @pre components != null;
   * @pre cC:noNull(components);
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        components[i].isLocked());
   * @post Collections.containsAll(components, new.getComponents());
   */
  public AbstractComponentPriorityList(Class priorityElementType, BigInteger bigSize,
                                       PriorityList[] components) {
    super(priorityElementType, bigSize);
    assert components != null;
    assert Collections.noNull(components);
    assert Collections.forAll(components,
                              new Assertion() {

                                    public boolean isTrueFor(Object o) {
                                      return ((PriorityList)o).isLocked();
                                    }

                                  });
    $components = (PriorityList[])ArrayUtils.clone(components);
  }



  /* <property name="components"> */
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final PriorityList[] getComponents() {
    return (PriorityList[])ArrayUtils.clone($components);
  }

  /**
   * @invar $components != null;
   * @invar cC:noNull($components);
   * @invar (forall int i; (i >= 0) && (i < $components.length);
   *          $components[i].isLocked());
   */
  private final PriorityList[] $components;

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