package org.toryt.util_I.collections.bigSet.algebra;


import java.math.BigInteger;
import java.util.Collection;

import org.apache.commons.lang.ArrayUtils;
import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.BigSet;
import org.toryt.util_I.collections.bigSet.lockable.AbstractLockedBigSet;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;


/**
 * <p>A lazy big set, build from component BigSets.</p>
 * <p>This class contains common implementations for subtypes.</p>
 * <p>If one of the {@link #getComponents()} is <code>null</code>,
 *   the implementation in the subclass will behave as if it
 *   refers to an empty set with an appropriate <code>_ElementType_</code>.</p>
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
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractComponentBigSet<_ResultElement_, _ComponentElement_>
    extends AbstractLockedBigSet<_ResultElement_> {

  /**
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
  protected AbstractComponentBigSet(boolean nullAllowed,
                                    BigInteger bigSize,
                                    LockableBigSet<? extends _ComponentElement_>[] components) {
    super(nullAllowed, bigSize);
    assert components != null;
    assert Collections.forAll(components,
                              new Assertion<LockableBigSet<? extends _ComponentElement_>>() {
                                    public boolean isTrueFor(LockableBigSet<? extends _ComponentElement_> o) {
                                      return (o == null) || o.isLocked();
                                    }
                                  });
    assert nullAllowed ||
             Collections.forAll(components,
                                new Assertion<LockableBigSet<? extends _ComponentElement_>>() {
                                      public boolean isTrueFor(LockableBigSet<? extends _ComponentElement_> o) {
                                        return (o == null) || (! o.contains(null));
                                      }
                                    });
    $components = (LockableBigSet<? extends _ComponentElement_>[])ArrayUtils.clone(components);
  }



  /* <property name="components"> */
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final LockableBigSet<? extends _ComponentElement_>[] getComponents() {
    return (LockableBigSet<? extends _ComponentElement_>[])ArrayUtils.clone($components);
  }

  /**
   * @invar $components != null;
   * @invar (forall int i; (i >= 0) && (i < $components.length);
   *          ($components[i] != null) ? $components[i].isLocked());
   */
  private final LockableBigSet<? extends _ComponentElement_>[] $components;

  /*</property>*/

  /**
   * If this method is used with
   * <code>! o instanceof AbstractComponentBigSet</code>,
   * it is possibly very expensive, as it uses
   * {@link #containsAll(Collection)} on this and <code>o</code>:
   * if {@link #containsAll(Collection)} is expensive, so is this
   * method if <code>! o instanceof AbstractComponentBigSet</code>.
   */
  @Override
  public boolean equals(Object o) {
    if ((o == null) || (! (o instanceof BigSet<?>))) {
      return false;
    }
    else if (o instanceof AbstractComponentBigSet<?, ?>) {
      LockableBigSet<?>[] otherComponents = ((AbstractComponentBigSet<?, ?>)o).getComponents();
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
      return super.equals(o);
    }
  }

  @Override
  public int hashCode() {
    int acc = 0;
    for (int i =0; i < $components.length; i++) {
      acc += ($components[i] == null) ? 0 : $components[i].hashCode();
    }
    return acc;
  }

}