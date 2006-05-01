package org.toryt.util_I.collections.algebra;


import java.lang.reflect.Array;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>A factory for {@link Aggregator Aggregators} that combines component
 *   elements in an array of component type {@link #getComponentType()}
 *   as aggregation-instance.</p>
 *
 * @author Jan Dockx
 *
 * @invar getComponentType() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ObjectArrayAggregatorFactory<_ComponentElementType_>
    extends AbstractAggregatorFactory<_ComponentElementType_[], _ComponentElementType_> {

  /**
   * @pre nrOfComponents >= 0;
   * @pre componentType != null;
   * @post new.getNrOfComponents() == nrOfComponents;
   */
  public ObjectArrayAggregatorFactory(Class<_ComponentElementType_> componentType,
                                      int nrOfComponents) {
    super(nrOfComponents);
    $componentType = componentType;
  }

  public final Class<_ComponentElementType_> getComponentType() {
    return $componentType;
  }

  private final Class<_ComponentElementType_> $componentType;

  public final Aggregator<_ComponentElementType_[], _ComponentElementType_> create() {
    return new ObjectArrayAggregator<_ComponentElementType_>();
  }

  public class ObjectArrayAggregator<_AggregatorComponentElementType_>
      extends AbstractReversibleAggregator<_AggregatorComponentElementType_[], _AggregatorComponentElementType_> {

    public int getNrOfComponents() {
      return ObjectArrayAggregator.this.getNrOfComponents();
    }

    /**
     * Generate a combination-instance, based on the component elements
     * set using {@link #setComponentElement(int, Object)}.
     * The result might be <code>null</code> in some special cases.
     */
    public final _AggregatorComponentElementType_[] aggregate() {
      @SuppressWarnings("unchecked") _AggregatorComponentElementType_[] result =
        (_AggregatorComponentElementType_[])Array.newInstance(getComponentType(), getNrOfComponents());
      /* unchecked cast ok */
      System.arraycopy($componentElements, 0, result, 0, getNrOfComponents());
      return result;
    }

    public final void decompose(_AggregatorComponentElementType_[] array) throws IllegalArgumentException {
      validateDecompose(array);
      if (array.length != getNrOfComponents()) {
        throw new IllegalArgumentException("incompatible length");
      }
      for (int i = 0; i < array.length; i++) {
        setComponentElement(i, array[i]);
      }
    }

  }

}