package org.toryt_II.testobject;


/**
 * <p>A <code>ConstantTestObjectFactory</code>'s {@link #generate()} method
 *   always returns the same instance. This can be used (and may only be used)
 *   for test objects that are guaranteed to be immutable.</p>
 *
 * @author Jan Dockx
 */
public class ConstantTestObjectFactory extends AbstractTestObjectFactory {

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
   * @pre immutableInstance != null;
   * @post new.generate() == immutableInstance;
   * @post getTestObjectClass() == immutableInstance.getClass();
   */
  public ConstantTestObjectFactory(Object immutableInstance) {
    super(immutableInstance.getClass());
    assert immutableInstance != null;
    $immutableInstance = immutableInstance;
  }

  public final Object generate() {
    return $immutableInstance;
  }

  private final Object $immutableInstance;

}