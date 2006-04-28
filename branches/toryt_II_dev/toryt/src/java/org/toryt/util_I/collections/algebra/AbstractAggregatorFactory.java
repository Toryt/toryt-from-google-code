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
public abstract class AbstractAggregatorFactory implements AggregatorFactory {

  /**
   * @pre elementType != null;
   * @pre nrOfComponents >= 0;
   * @post new.getElementType() == elementType;
   * @post new.getNrOfComponents() == nrOfComponents;
   */
  protected AbstractAggregatorFactory(Class elementType, int nrOfComponents) {
    assert elementType != null;
    assert nrOfComponents >= 0;
    $elementType = elementType;
    $nrOfComponents = nrOfComponents;
  }

  public final Class getElementType() {
    return $elementType;
  }

  /**
   * @invar $elementType != null;
   */
  private Class $elementType;

  public final int getNrOfComponents() {
    return $nrOfComponents;
  }

  /**
   * @invar $nrOfComponents >= 0;
   */
  private int $nrOfComponents;



  /**
   * <p>Implementation of most methods of {@link Aggregator}, based
   *   on the outer class state for settings.</p>
   */
  public abstract class AbstractAggregator implements Aggregator {

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

  /**
   * Implementation
   */
  public abstract class AbstractReversibleAggregator extends AbstractAggregator implements ReversibleAggregator {

    /**
     * Implementation of basic validation for {@link #decompose(Object)}
     * the subclasses. We throw an {@link IllegalArgumentException} when
     * <code>object</code> is <code>null</code> or not an instance of
     * {@link #getElementType()}.
     */
    protected void validateDecompose(Object object) throws IllegalArgumentException {
      if (object != null) {
        throw new IllegalArgumentException("cannot decompose null");
      }
      if (getElementType().isInstance(object)) {
        throw new IllegalArgumentException("incompatible type");
      }
    }

  }

}