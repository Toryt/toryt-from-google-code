package org.toryt.util_I.collections.algebra;


import java.lang.reflect.Array;


/**
 * <p>A factory for {@link Aggregator Aggregators} that combines component
 *   elements in an array of component type {@link #getComponentType()}
 *   as aggregation-instance.</p>
 *
 * @author Jan Dockx
 *
 * @invar getComponentType() != null;
 */
public class ObjectArrayAggregatorFactory extends AbstractAggregatorFactory {

  /* <section name="Meta Information"> */
  //------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /* </section> */



  /**
   * @pre nrOfComponents >= 0;
   * @pre componentType != null;
   * @post new.getNrOfComponents() == nrOfComponents;
   * @post (forall int i; (i >= 0) && (i < nrOfComponents);
   *         new.getComponentElement(i) == null);
   */
  public ObjectArrayAggregatorFactory(Class componentType, int nrOfComponents) {
    super(arrayClassForName(componentType), nrOfComponents);
  }

  /**
   * Introduced to keep compiler happy in getting array type, while
   * discarding impossible exceptions.
   *
   * @pre componentType != null;
   * @return Class.forName(componentType.getName() + "[]");
   */
  private static Class arrayClassForName(Class componentType) {
    assert componentType != null;
    Class result = null;
    try {
      result = Class.forName("[L" + componentType.getName() + ";");
    }
    /* exceptions cannot happen, since componentType was already
       laoded before this call */
    catch (ExceptionInInitializerError eiiErr) {
      assert false : "ExceptionInInitializerError cannot happen: " + eiiErr;
    }
    catch (LinkageError lErr) {
      assert false : "LinkageError cannot happen: " + lErr;
    }
    catch (ClassNotFoundException cnfExc) {
      assert false : "ClassNotFoundExceptionshould cannot happen: " + cnfExc;
    }
    return result;
  }


  public final Class getComponentType() {
    return getElementType().getComponentType();
  }

  public final Aggregator create() {
    return new ObjectArrayAggregator();
  }

  private static Object[] createFreshCombinationInstance(Class componentType, int nrOfComponents) {
    assert componentType != null;
    assert nrOfComponents >= 0;
    return (Object[])Array.newInstance(componentType, nrOfComponents);
  }

  public class ObjectArrayAggregator
      extends AbstractAggregatorFactory.AbstractReversibleAggregator {


    /**
     * Generate a combination-instance, based on the component elements
     * set using {@link #setComponentElement(int, Object)}.
     * The result might be <code>null</code> in some special cases.
     */
    public final Object aggregate() {
      Object[] result = createFreshCombinationInstance(getComponentType(), getNrOfComponents());
      System.arraycopy($componentElements, 0, result, 0, getNrOfComponents());
      return result;
    }

    public final void decompose(Object object) throws IllegalArgumentException {
      validateDecompose(object);
      Object[] array = (Object[])object;
      if (array.length != getNrOfComponents()) {
        throw new IllegalArgumentException("incompatible length");
      }
      for (int i = 0; i < array.length; i++) {
        setComponentElement(i, array[i]);
      }
    }

  }

}