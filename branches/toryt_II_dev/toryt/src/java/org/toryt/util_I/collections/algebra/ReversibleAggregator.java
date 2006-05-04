package org.toryt.util_I.collections.algebra;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>{@link Aggregator} that is reversible, i.e., an object can
 *   be decomposed into its components.</p>
 *
 * @deprecated These classes where only here for the old ProductBigSet,
 *             which were array based. This wasn't very nice, and is now
 *             replace by a Map base product.
 *             These classes aren't very nice either, so the are going
 *             away too.
 * @mudo throw away
 */
@Deprecated
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface ReversibleAggregator<_Aggregate_> extends Aggregator<_Aggregate_> {

  /**
   * <p>Decompose the given <code>object</code> into components.
   *   This is the reverse operation of {@link #aggregate()}.</p>
   * <p>The relationship must hold that
   *   after the execution of <code>decompose(generate())</code>, the components
   *   must be the same:</p>
   * <pre>
   * (forall int i; (i >= 0) && (i < getNrOfComponents());
   *   new{decompose(generate())}.getComponentElement(i).equals(getComponentElement(i)));
   * </pre>
   * <p>For any <code>object</code> of type <code>_AggregateType_</code>
   *   <code>generate(decompose(object))</code> must result in a fresh instance
   *   <em>similar</em> to <code>object</code> (we would like to say
   *   <code>generate(decompose(o)).equals(object)</code>, but we do no
   *   want to demand that the {@link #equals(Object)} method of <code>object</code>
   *   must be overwritten.</p>
   *
   * @note The parameter <code>object</code> should not be of stronger (generic)
   *       type. That doesn't add anything. If this aggregator {@link #aggregate() creates}
   *       instances of a dynamic type stronger than demanded by the static
   *       (generic) type <code>_AggregateType_</code>, we need to test for
   *       the type anyway. And this will occur regularly: often a stronger
   *       {@link Aggregator} will be given when one for a given type is demanded.
   *
   * @throws IllegalArgumentException
   *         object == null;
   */
  void decompose(Object object) throws IllegalArgumentException;

}
