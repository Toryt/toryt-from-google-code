package org.toryt.util_I.collections.algebra;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Implementation of most methods of {@link Aggregator}.</p>
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
public abstract class AbstractAggregator<_Aggregate_> implements Aggregator<_Aggregate_> {

  public final Object getComponentElement(int componentIndex)
      throws IndexOutOfBoundsException {
    if ((componentIndex < 0) || (componentIndex >= getNrOfComponents())) {
      throw new IndexOutOfBoundsException();
    }
    return $componentElements[componentIndex];
  }

  public final void setComponentElement(int componentIndex, Object componentElement)
      throws IndexOutOfBoundsException, IllegalArgumentException {
    if ((componentIndex < 0) || (componentIndex >= getNrOfComponents())) {
      throw new IndexOutOfBoundsException();
    }
    validateComponentElement(componentIndex, componentElement); // IllegalArgumentException
    $componentElements[componentIndex] = componentElement;
  }

  /**
   * NOP method, that throws an exception if <code>componentElement</code>
   * cannot be accepted as component element for index <code>componentIndex</code>.
   * This default implementation does not do anything.
   *
   * @throws IllegalArgumentException
   */
  protected void validateComponentElement(int componentIndex, Object componentElement)
      throws IllegalArgumentException {
    // NOP
  }

  /**
   * @invar $componentElements != null;
   */
  protected final Object[] $componentElements = new Object[getNrOfComponents()];

}
