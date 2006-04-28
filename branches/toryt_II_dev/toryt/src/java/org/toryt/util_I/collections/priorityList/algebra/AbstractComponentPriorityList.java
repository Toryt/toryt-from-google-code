package org.toryt.util_I.collections.priorityList.algebra;


import java.math.BigInteger;

import org.apache.commons.lang.ArrayUtils;
import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.lockable.LazyList;
import org.toryt.util_I.collections.priorityList.AbstractLockedPriorityList;
import org.toryt.util_I.collections.priorityList.PriorityList;


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
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractComponentPriorityList extends AbstractLockedPriorityList
    implements LazyList {

  /**
   * @pre priorityElementType != null;
   * @pre size >= 0;
   * @pre bigSize != null;
   * @pre bigSize >= 0;
   * @pre components != null;
   * @pre cC:noNull(components);
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        components[i].isLocked());
   * @post Collections.containsAll(components, new.getComponents());
   */
  public AbstractComponentPriorityList(Class priorityElementType,
                                       int size,
                                       PriorityList[] components) {
    super(priorityElementType, calculateCardinality(components));
    assert size >= 0;
    assert components != null;
    assert Collections.noNull(components);
    assert Collections.forAll(components,
                              new Assertion() {

                                    public boolean isTrueFor(Object o) {
                                      return ((PriorityList)o).isLocked();
                                    }

                                  });
    $size = size;
    $components = (PriorityList[])ArrayUtils.clone(components);
  }

  private static BigInteger calculateCardinality(PriorityList[] components) {
    BigInteger result = BigInteger.ZERO;
    for (int i = 0; i < components.length; i++) {
      result = result.add(components[i].getCardinality());
    }
    return result;
  }

  /**
   * This method is very expensive, as it iterates over all buckets
   * (and thus generates them).
   *
   * @deprecated
   */
  public final Object[] toArray() {
    return super.toArray();
  }

  /**
   * This method is very expensive, as it iterates over all buckets
   * (and thus generates them).
   *
   * @deprecated
   */
  public final Object[] toArray(Object[] a) {
    return super.toArray(a);
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
   * @return (forall int i; (i > 0) && (i < getComponents().length);
   *            getComponents()[i].isEmpty());
   */
  public final boolean isEmpty() {
    return $size == 0;
  }

  public final int hashCode() {
    int acc = 0;
    for (int i =0; i < $components.length; i++) {
      acc += ($components[i] == null) ? 0 : $components[i].hashCode();
    }
    return acc;
  }

  public final boolean containsPriorityElement(final Object o) {
    return Collections.exists(getComponents(),
                              new Assertion() {
                                    public boolean isTrueFor(Object s) {
                                      return (s != null) && ((PriorityList)s).containsPriorityElement(o);
                                    }
                                  });
  }

  /**
   * @return (sum int i; (i >=0 ) && (i < getComponents().length);
   *            getComponents()[i].getCardinality());
   *
  public final BigInteger getCardinality();
   */

  public final int size() {
    return $size;
  }

  /**
   * @invar $size >= 0;
   */
  private final int $size;

}