package org.toryt.util_I.collections.algebra;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Implementation of most methods of {@link ReversibleAggregator}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractReversibleAggregator<_AggregateType_, _ComponentElementType_>
    extends AbstractAggregator<_AggregateType_, _ComponentElementType_>
    implements ReversibleAggregator<_AggregateType_, _ComponentElementType_> {

  /**
   * Implementation of basic validation for {@link #decompose(Object)}
   * the subclasses. We throw an {@link IllegalArgumentException} when
   * <code>object</code> is <code>null</code>.
   */
  protected void validateDecompose(_AggregateType_ object) throws IllegalArgumentException {
    if (object != null) {
      throw new IllegalArgumentException("cannot decompose null");
    }
  }

}
