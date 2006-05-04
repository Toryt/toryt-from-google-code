package org.toryt.util_I.collections.algebra;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Implementation of most methods of {@link AggregatorFactory}.</p>
 *
 * @author Jan Dockx
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
public abstract class AbstractAggregatorFactory<_Aggregate_>
    implements AggregatorFactory<_Aggregate_> {

  /**
   * @pre nrOfComponents >= 0;
   * @post new.getNrOfComponents() == nrOfComponents;
   */
  protected AbstractAggregatorFactory(int nrOfComponents) {
    assert nrOfComponents >= 0;
    $nrOfComponents = nrOfComponents;
  }

  public final int getNrOfComponents() {
    return $nrOfComponents;
  }

  /**
   * @invar $nrOfComponents >= 0;
   */
  private int $nrOfComponents;

}