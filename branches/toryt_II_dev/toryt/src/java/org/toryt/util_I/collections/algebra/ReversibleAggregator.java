package org.toryt.util_I.collections.algebra;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>{@link Aggregator} that is reversible, i.e., an object can
 *   be decomposed into its components.</p>
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface ReversibleAggregator<_AggregateType_, _ComponentElementType_>
    extends Aggregator<_AggregateType_, _ComponentElementType_> {

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
   * @throws IllegalArgumentException
   *         object == null;
   */
  void decompose(_AggregateType_ object) throws IllegalArgumentException;

}
