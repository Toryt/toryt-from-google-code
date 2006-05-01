package org.toryt.util_I.collections.algebra;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Implementation of most methods of {@link Aggregator}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractAggregator<_AggregateType_, _ComponentElementType_>
    implements Aggregator<_AggregateType_, _ComponentElementType_> {

  public final _ComponentElementType_ getComponentElement(int componentIndex)
      throws IndexOutOfBoundsException {
    if ((componentIndex < 0) || (componentIndex >= getNrOfComponents())) {
      throw new IndexOutOfBoundsException();
    }
    @SuppressWarnings("unchecked") _ComponentElementType_ result =
        (_ComponentElementType_)$componentElements[componentIndex];
    return result;
  }

  public final void setComponentElement(int componentIndex, _ComponentElementType_ componentElement)
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
  protected void validateComponentElement(int componentIndex, _ComponentElementType_ componentElement)
      throws IllegalArgumentException {
    // NOP
  }

  /**
   * @invar $componentElements != null;
   */
  protected final Object[] $componentElements = new Object[getNrOfComponents()];

}
