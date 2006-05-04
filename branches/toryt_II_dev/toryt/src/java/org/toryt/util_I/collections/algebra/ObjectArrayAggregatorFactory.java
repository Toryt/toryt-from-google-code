package org.toryt.util_I.collections.algebra;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>A factory for {@link Aggregator Aggregators} that combines component
 *   elements in an array of component type {@link Object}
 *   as aggregation-instance.</p>
 *
 * @author Jan Dockx
 *
 * @invar getComponentType() != null;
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
public class ObjectArrayAggregatorFactory extends AbstractAggregatorFactory<Object[]> {

  /**
   * @pre nrOfComponents >= 0;
   * @pre componentType != null;
   * @post new.getNrOfComponents() == nrOfComponents;
   */
  public ObjectArrayAggregatorFactory(int nrOfComponents) {
    super(nrOfComponents);
  }

  public final Aggregator<Object[]> create() {
    return new ObjectArrayAggregator();
  }

 /**
  * @deprecated These classes where only here for the old ProductBigSet,
  *             which were array based. This wasn't very nice, and is now
  *             replace by a Map base product.
  *             These classes aren't very nice either, so the are going
  *             away too.
  */
 @Deprecated
  public class ObjectArrayAggregator
      extends AbstractReversibleAggregator<Object[]> {

    public int getNrOfComponents() {
      return ObjectArrayAggregatorFactory.this.getNrOfComponents();
    }

    /**
     * Generate a combination-instance, based on the component elements
     * set using {@link #setComponentElement(int, Object)}.
     * The result might be <code>null</code> in some special cases.
     */
    public final Object[] aggregate() {
      Object[] result = new Object[getNrOfComponents()];
      System.arraycopy($componentElements, 0, result, 0, getNrOfComponents());
      return result;
    }

    public final void decompose(Object array) throws IllegalArgumentException {
      try {
        Object[] a = (Object[])array;
        validateDecompose(a); // ClassCastException
        if (a.length != getNrOfComponents()) {
          throw new IllegalArgumentException("incompatible length");
        }
        for (int i = 0; i < a.length; i++) {
          setComponentElement(i, a[i]);
        }
      }
      catch (ClassCastException ccExc) {
        throw new IllegalArgumentException(array + " is not of type Object[]");
      }
    }

  }

}