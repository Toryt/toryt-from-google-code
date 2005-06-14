package org.toryt;


import java.lang.reflect.InvocationTargetException;
import java.util.Map;


/**
 * An actual instance of a test of a method. This is a test with actual
 * test arguments.
 * 
 * @invar getContract() instanceof ConstructorContract;
 */
public class ConstructorTest extends MethodTest {

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



  /* <construction> */
  //------------------------------------------------------------------

  public ConstructorTest(ConstructorContract constructorContract, Map testCase) {
    super(constructorContract, testCase);
  }

  /* </construction> */

  

  /* <property name="contract"> */
  //------------------------------------------------------------------

  public final ConstructorContract getConstructorContract() {
    return (ConstructorContract)getMethodContract();
  }

  /* </property> */

  
  /**
   * Call the method of the contract with reflection.
   * The new instance is stored in the context map with
   * key {@link MethodContract#SUBJECT_KEY}.
   * 
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  protected final void methodCall()
      throws InstantiationException,
             IllegalAccessException,
             IllegalArgumentException,
             InvocationTargetException {
    Object instance = getConstructorContract().getConstructor()
              .newInstance(getActualParameters());
    getContext().put(MethodContract.SUBJECT_KEY, instance);
  }
  
}