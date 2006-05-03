package org.toryt.util_I.collections.priorityList.algebra;


import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;
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
public abstract class AbstractComponentPriorityList<_Element_>
    extends AbstractLockedPriorityList<_Element_> {

  /**
   * @pre size >= 0;
   * @pre components != null;
   * @pre cC:noNull(components);
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        components[i].isLocked());
   * @post Collections.containsAll(components, new.getComponents());
   */
  protected AbstractComponentPriorityList(int size,
                                          PriorityList<? extends _Element_>[] components) {
    super(calculateCardinality(components));
    assert size >= 0;
    assert components != null;
    assert Collections.noNull(components);
    assert Collections.forAll(components,
                              new Assertion<PriorityList<? extends _Element_>>() {

                                    public boolean isTrueFor(PriorityList<? extends _Element_> o) {
                                      return o.isLocked();
                                    }

                                  });
    $size = size;
    $components = (PriorityList<? extends _Element_>[])ArrayUtils.clone(components);
  }

  private static BigInteger calculateCardinality(PriorityList<?>[] components) {
    BigInteger result = BigInteger.ZERO;
    for (int i = 0; i < components.length; i++) {
      result = result.add(components[i].getCardinality());
    }
    return result;
  }



  /* <property name="components"> */
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final PriorityList<? extends _Element_>[] getComponents() {
    return (PriorityList<? extends _Element_>[])ArrayUtils.clone($components);
  }

  /**
   * @invar $components != null;
   * @invar cC:noNull($components);
   * @invar (forall int i; (i >= 0) && (i < $components.length);
   *          $components[i].isLocked());
   */
  private final PriorityList<? extends _Element_>[] $components;

  /*</property>*/



  public final boolean containsPriorityElement(final Object o) {
    return Collections.exists(getComponents(),
                              new Assertion<PriorityList<? extends _Element_>>() {
                                    public boolean isTrueFor(PriorityList<? extends _Element_> s) {
                                      return s.containsPriorityElement(o);
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

  /**
   * @return (forall int i; (i > 0) && (i < getComponents().length);
   *            getComponents()[i].isEmpty());
   */
  public final boolean isEmpty() {
    return $size == 0;
  }

  /**
   * If this method is used with
   * <code>! o instanceof AbstractComponentPriorityList</code>,
   * it is possibly very expensive, as it uses
   * {@link #containsAll(Collection)} on this and <code>o</code>:
   * if {@link #containsAll(Collection)} is expensive, so is this
   * method if <code>! o instanceof AbstractComponentBigSet</code>.
   */
  @Override
  public boolean equals(Object o) {
    if ((o == null) || (! (o instanceof PriorityList<?>))) {
      return false;
    }
    else if (o instanceof AbstractComponentPriorityList<?>) {
      PriorityList<?>[] otherComponents = ((AbstractComponentPriorityList<?>)o).getComponents();
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
      return (size() == ((List<?>)o).size()) &&
             containsAll((List<?>)o) &&
             ((List<?>)o).containsAll(this);
    }
  }

  @Override
  public final int hashCode() {
    int acc = 0;
    for (int i =0; i < $components.length; i++) {
      acc += ($components[i] == null) ? 0 : $components[i].hashCode();
    }
    return acc;
  }

}