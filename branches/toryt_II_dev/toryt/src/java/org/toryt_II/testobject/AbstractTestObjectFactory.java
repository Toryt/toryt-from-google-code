package org.toryt_II.testobject;


/**
 * <p>Supporting code for implementations of {@link TestObjectFactory}.</p>
 *
 * @author Jan Dockx
 */
public abstract class AbstractTestObjectFactory implements TestObjectFactory {

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
   * @pre testObjectClass != null;
   * @post new.getTestObjectClass() == testObjectClass;
   */
  protected AbstractTestObjectFactory(Class testObjectClass) {
    assert testObjectClass != null;
    $testObjectClass = testObjectClass;
  }


  public final Class getTestObjectClass() {
    return $testObjectClass;
  }

  /**
   * @invar $testObjectClass != null;
   */
  private final Class $testObjectClass;

}