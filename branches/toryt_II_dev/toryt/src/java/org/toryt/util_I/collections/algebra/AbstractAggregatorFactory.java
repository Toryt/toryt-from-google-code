package org.toryt.util_I.collections.algebra;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Implementation of most methods of {@link AggregatorFactory}.</p>
 *
 * @author Jan Dockx
 */
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