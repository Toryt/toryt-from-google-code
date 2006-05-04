package org.toryt.util_I.collections.algebra;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.algebra.BiArrayProductPriorityList;


  /**
 * <p>Instances are used to aggregate elements from different components
 *   in product-collections (see, e.g., ArrayProductBigSet,
 *   {@link BiArrayProductPriorityList}).</p>
 * <p>Component elements are set with an index. <code>_ComponentElementType_</code>
 *   is a supertype of the type of the components. Most often, you should
 *   use <code>Object</code> as actual generic parameter, but this makes
 *   a little bit of more control possible. When component instances are
 *   set to your liking, call {@link #aggregate()} to generate a combination-instance,
 *   based on the current component elements.</p>
 * <p>An aggregator is stateful. We need a separate entity to hold the setup of
 *   the aggregators for a given product collection: see {@link AggregatorFactory}.</p>
 * <p>Implementations might benefit from {@link AbstractAggregator}.</p>
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
public interface Aggregator<_Aggregate_> {

  /**
   * The number of components used to generate an aggregate.
   *
   * @basic
   */
  int getNrOfComponents();

  /**
   * Get the component instance for index <code>componentIndex</code>,
   * that would be used to build a combination-instance by {@link #aggregate()}.
   * If no component element was set for index <code>componentIndex</code>
   * on this instance before this call, <code>null</code> is returned.
   *
   * @basic
   * @post componentIndex() < getNrOfComponents();
   * @throws IndexOutOfBoundsException
   *         (componentIndex < 0) || (componentIndex() >= getNrOfComponents());
   */
  Object getComponentElement(int componentIndex)
      throws IndexOutOfBoundsException;

  /**
   * Set the component instance for index <code>componentIndex</code>
   * to <code>componentElement</code>. This instance will be used to
   * build a combination-instance by {@link #aggregate()}.
   *
   * @post new.getComponentElement(componentIndex) == componentElement;
   * @post 0 <= componentIndex();
   * @post componentIndex() < getNrOfComponents();
   * @throws IndexOutOfBoundsException
   *         (componentIndex < 0) || (componentIndex() >= getNrOfComponents());
   * @throws IllegalArgumentException
   *         <code>componentElement</code> cannot be accepted as a component element
   *         for index <code>componentIndex</code> (e.g., because it is <code>null</code>,
   *         or it is not of the correct type).
   *
   */
  void setComponentElement(int componentIndex, Object componentElement)
      throws IndexOutOfBoundsException, IllegalArgumentException;

  /**
   * Generate a aggregate-instance, based on the component elements
   * {@link #getComponentElement(int)}.
   * The result might be <code>null</code> in some special cases.
   * This method cannot throw any exceptions. Implementations need to avoid
   * bad component elements in some way.
   *
   * @result getElementType().isInstance(result);
   * @throws IllegalArgumentException
   *         We cannot create an aggregation given the current component
   *         elements.
   */
  _Aggregate_ aggregate();

}
