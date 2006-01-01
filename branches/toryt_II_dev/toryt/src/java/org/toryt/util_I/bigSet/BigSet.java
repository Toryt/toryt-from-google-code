package org.toryt.util_I.bigSet;


import java.math.BigInteger;
import java.util.List;
import java.util.Set;


/**
 * <p>A {@link Set} of which the size might exceed
 *   {@link Integer#MAX_VALUE}. Therefor, {@link #getBigSize()}
 *   should be used instead of {@link #size()}.</p>
 * <p><code>BigSets</code> are always immutable.
 *   Combination-<code>BigSets</code> should use their
 *   component-<code>BigSets</code> by reference always.</p>
 * <p><code>BigSets</code> are almost always lazy,
 *   and generative. Therefor, operations like {@link #contains(Object)}
 *   are to be considered very expensive.</p>
 *
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getElementType() !=  null;
 * @invar cC:noNull(this);
 * @invar cC:instanceOf(this, getElementType());
 * @invar getBigSize() >= 0;
 */
public interface BigSet extends List {

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
   * The type of the elements in the set.
   * No other types of objects are allowed.
   *
   * @basic
   *
   * @note When moving to Java 5, replace this with a generic implementation.
   */
  Class getElementType();

  /**
   * The number of elements as a {@link BigInteger}.
   * This method should be used instead of (@link #size()},
   * because <code>BigSets</code> can become very large.
   *
   * @basic
   */
  BigInteger getBigSize();

  /**
   * The number of elements, if it is smaller than
   * {@link Integer#MAX_VALUE}. Otherwise, {@link Integer#MAX_VALUE}.
   * <strong>Use {@link #getBigSize()} instead of this method.</strong>
   * The method is made deprecated to signal this in the compiler.
   *
   *  @deprecated
   *
   *  @return (getBigSize() <= Integer.MAX_VALUE) ? getBigSize() : Integer.MAX_VALUE;
   */
  int size();

}