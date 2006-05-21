package org.toryt.util_I.collections.bigSet;


import java.math.BigInteger;
import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>A {@link Set} of which the size might exceed
 *   {@link Integer#MAX_VALUE}. Therefor, {@link #getBigSize()}
 *   should be used instead of <code>size()</code>.</p>
 * <p><code>BigSets</code> are almost always lazy,
 *   and generative. Therefor, operations like {@link #toArray()}
 *   and {@link #toArray(Object[])}
 *   are to be considered very expensive.</p>
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getBigSize() >= 0;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface BigSet<_Element_> extends Set<_Element_>, Cloneable {

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
  @Deprecated
  int size();

}