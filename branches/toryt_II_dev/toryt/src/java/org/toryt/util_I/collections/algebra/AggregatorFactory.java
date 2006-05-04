package org.toryt.util_I.collections.algebra;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Since instances of {@link Aggregator} are stateful, we need
 *   a separate entity to hold the setup of the aggregators for
 *   a given product collection. {@link #create()} creates a fresh
 *   aggregator instance.</p>
 * <p>Implementations might benefit from {@link AbstractAggregatorFactory}.</p>
 *
 * @author Jan Dockx
 *
 * @invar getNrOfComponents() >= 0;
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
public interface AggregatorFactory<_Aggregate_> {

  /**
   * The number of components that {@link Aggregator Aggregators} generated
   * by this instance need and accept to generate a aggregation-instances.
   *
   * @basic
   */
  int getNrOfComponents();

  /**
   * Create a fresh {@link Aggregator} that generates instances of
   * <code>_AggregateType_</code>
   * based on {@link #getNrOfComponents()} component elements.
   *
   * @result AggregatorFactory.result == this;
   */
  Aggregator<? extends _Aggregate_> create();

}