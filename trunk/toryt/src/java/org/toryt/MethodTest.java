package org.toryt;


import java.lang.reflect.InvocationTargetException;
import java.util.Map;


/**
 * An actual instance of a test of a method. This is a test with actual
 * test arguments.
 * 
 * @invar getContract() != null;
 * @invar getContext() != null;
 */
public abstract class MethodTest implements Test {

  /*<section name="Meta Information">*/
  //------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /*</section>*/



  /*<construction>*/
  //------------------------------------------------------------------

  public MethodTest(MethodContract methodContract, Map testCase) {
    $contract = methodContract;
    $context = testCase;
  }

  /*</construction>*/


  /*<property name="contract">*/
  //------------------------------------------------------------------

  public MethodContract getContract() {
    return $contract;
  }

  /**
   * @invar $contract != null;
   */
  private MethodContract $contract;

  /*</property>*/



  /*<property name="context">*/
  //------------------------------------------------------------------

  public final static String RESULT_KEY = "org.toryt.MethodTest.RESULT";
  
  /**
   * This method gives direct access to the map that holds the context
   * of the test. This is the map that is passed in the constructor as test case.
   * The map is filled with context information, apart from the initial
   * implicit and explicit arguments, by the {@link MethodContract#recordState(MethodTest)}
   * method. In this map, there are 2 specific keys: a key for the implicit
   * object (the subject) and a key for the result of an inspector.
   */
  public Map getContext() {
    return $context;
  }

  private Map $context;

  /**
   * @return getContext().get(MethodTest.RESULT_KEY);
   */
  public final Object getSubject() {
    return getContext().get(MethodTest.RESULT_KEY);
  }
  
  /**
   * @return getContext().get(RESULT_KEY);
   * 
   * @idea move to constructor and inspector test
   */
  public final Object getResult() {
    return getContext().get(RESULT_KEY);
  }

  /**
   * Extract the actual arguments from the {@link #getContext() context},
   * with the help of the {@link MethodContract#getFormalParameters() formal parameters}.
   */
  protected final Object[] getActualParameters() {
    String[] fp = getContract().getFormalParameters();
    int n = fp.length;
    Object[] result = new Object[n];
    for (int i = 0; i < n; i++) {
      result[i] = getContext().get(fp[i]);
    }
    return result;
  }

  /*</property>*/

  
  
  /*<property name="success">*/
  //------------------------------------------------------------------

  public boolean isSuccessful() {
    return $nrOfErrors == 0;
  }

  /**
   * This method is called by contract conditions to assert
   * the conditions.
   */
  public final void validate(boolean assertion) {
    if (! assertion) {
      $nrOfErrors++;
    }
  }
  
  private int $nrOfErrors = 0;

  /*</property>*/

  
  
  /*<property name="run">*/
  //------------------------------------------------------------------

  public boolean hasRun() {
    return $run;
  }
  
  protected void setRun() {
    $run = true;
  }
  
  private boolean $run;
  /* </property> */

  
  /**
   * Call the method under test and validate the contract.
   * This method starts with calling {@link MethodContract#validatePreconditions(MethodTest)}
   * on itself. If the preconditions fail, the test is not performed, but it is considered
   * as &quot;run&quot;. Next, the contract is asked to record the pre-state on this with
   * {@link MethodContract#recordState(MethodTest)}. Then the method-under-test
   * is called. Afterwards, if the method ended nominally, we validate the
   * {@link MethodContract#validatePostConditions(MethodTest) postconditions}, the
   * {@link MethodContract#validateInertiaAxiom(MethodTest) inertia axiom}, and the
   * {@link MethodContract#validateTypeInvariants(MethodTest) type invariants}.
   * If the method does not end nominally, we validate the
   * {@link MethodContract#validateExceptionCondition(MethodTest, InvocationTargetException)
   * exception conditions}.
   * 
   * @post new.hasRun();
   * @throws TorytException
   *         hasRun();
   */
  public final void test() throws TorytException {
    if (hasRun()) {
      throw new TorytException(getContract(), null);
    }
    if (getContract().validatePreconditions(this)) {
      try {
        getContract().recordState(this);
        Object result = methodCall(); 
        getContext().put(RESULT_KEY, result);
        getContract().validatePostConditions(this);
        getContract().validateInertiaAxiom(this);
        getContract().validateTypeInvariants(this);
      }
      catch (IllegalArgumentException e) {
        System.out.println(this);
        System.out.println(e);
        throw new TorytException(getContract(), e);
      }
      catch (IllegalAccessException e) {
        System.out.println(this);
        System.out.println(e);
        throw new TorytException(getContract(), e);
      }
      catch (InstantiationException e) {
        System.out.println(this);
        System.out.println(e);
        throw new TorytException(getContract(), e);
      }
      catch (InvocationTargetException e) {
        System.out.println(this);
        System.out.println(e);
        getContract().validateExceptionCondition(this, e.getCause());
      }
      catch (Throwable e) {
        System.out.println(this);
        System.out.println(e);
        throw new TorytException(getContract(), e);
      }
    }
    setRun();
  }

  /**
   * Call the method of the contract with reflection.
   * 
   * @return The result of the method; <code>null</code> for
   *          <code>void</code> method.
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  protected abstract Object methodCall()
      throws InstantiationException,
             IllegalAccessException,
             IllegalArgumentException,
             InvocationTargetException;
  
}