package org.toryt;


import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * An actual instance of a test of a method. This is a test with actual
 * test arguments.
 * 
 * @invar getContract() != null;
 */
public class MethodTest implements Test {

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

  public MethodTest(MethodContract methodContract, Map testCase) {
    $contract = methodContract;
    $testCase = testCase;
  }

  /* </construction> */


  /* <property name="actor"> */
  //------------------------------------------------------------------

  public MethodContract getContract() {
    return $contract;
  }

  /**
   * @invar $contract != null;
   */
  private MethodContract $contract;

  /* </property> */



  /* <property name="actor"> */
  //------------------------------------------------------------------

  public Map getTestCase() {
    return $testCase;
  }

  private Map $testCase;

  /* </property> */



  /* <property name="actor"> */
  //------------------------------------------------------------------

  public Map getOldState() {
    return Collections.unmodifiableMap($oldState);
  }

  private Map $oldState = new HashMap();

  /* </property> */


  
  /* <property name="actor"> */
  //------------------------------------------------------------------

  public Object getResult() {
    return $result;
  }

  private Object $result;

  /* </property> */



  public void test() throws TorytException {
    if (getContract().validatePreconditions(this)) {
      try {
        getContract().recordOldState(this, $oldState);
        $result = $contract.getMethod()
                 .invoke(getTestCase().get(MethodContract.SUBJECT_KEY),
                         getExplicitArguments());
        getContract().validatePostConditions(this);
        getContract().validateInertiaAxiom(this);
        getContract().validateTypeInvariants(this);
      }
      catch (IllegalArgumentException e) {
        throw new TorytException(getContract(), e);
      }
      catch (IllegalAccessException e) {
        throw new TorytException(getContract(), e);
      }
      catch (InvocationTargetException e) {
        getContract().validateExceptionCondition(this, e);
      }
    }
    $run = true;
  }

  private Object[] getExplicitArguments() {
    String[] fp = getContract().getFormalParameters();
    int n = fp.length;
    Object[] result = new Object[n];
    for (int i = 0; i < n; i++) {
      result[i] = getTestCase().get(fp[i]);
    }
    return result;
  }

  public boolean isSuccessful() {
    return $nrOfErrors == 0;
  }
  
  public final void validate(boolean assertion) {
    if (! assertion) {
      $nrOfErrors++;
    }
  }
  
  private int $nrOfErrors = 0;

  public boolean hasRun() {
    return $run;
  }
  
  private boolean $run;
  
}