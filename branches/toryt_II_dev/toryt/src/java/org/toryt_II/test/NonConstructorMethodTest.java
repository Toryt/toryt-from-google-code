package org.toryt_II.test;


import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.toryt_II.contract.MethodContract;
import org.toryt_II.contract.NonConstructorMethodContract;


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


  
  /**
   * Call the method of the contract with reflection.
   * The result (if there is one) is stored in the context map with
   * key {@link #RESULT_KEY}.
   * 
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  protected final void methodCall()
      throws IllegalAccessException,
             IllegalArgumentException,
             InvocationTargetException {
    Object result = getNonConstructorMethodContract().getMethod()
              .invoke(getContext().get(MethodContract.SUBJECT_KEY),
                      getActualParameters());
    getContext().put(RESULT_KEY, result);
  }
  
}