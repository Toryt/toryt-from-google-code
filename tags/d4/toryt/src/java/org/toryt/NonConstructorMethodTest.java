package org.toryt;


import java.lang.reflect.InvocationTargetException;
import java.util.Map;


/**
 * Test class for non-constructor methods, i.e., instance methods
 * and non-constructor class methods.
 * 
 * @invar getContract() instanceof NonConstructorMethodContract;
 */
public class NonConstructorMethodTest extends MethodTest {

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

  public NonConstructorMethodTest(NonConstructorMethodContract methodContract, Map testCase) {
    super(methodContract, testCase);
  }

  /* </construction> */

  

  /* <property name="contract"> */
  //------------------------------------------------------------------

  public final NonConstructorMethodContract getNonConstructorMethodContract() {
    return (NonConstructorMethodContract)getMethodContract();
  }

  /* </property> */


  
  protected final Object methodCall()
      throws IllegalAccessException,
             IllegalArgumentException,
             InvocationTargetException {
    return getNonConstructorMethodContract().getMethod()
              .invoke(getContext().get(MethodContract.SUBJECT_KEY),
                      getActualParameters());
  }
  
  protected final void validateMore() {
    getTypeContract().validateTypeInvariants(getContext().get(MethodContract.SUBJECT_KEY), this);
    // MUDO type invars of all objects
  }

}