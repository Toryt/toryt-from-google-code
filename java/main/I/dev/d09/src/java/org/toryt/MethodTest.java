package org.toryt;


import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


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
    $methodContract = methodContract;
    $context = testCase;
  }

  /*</construction>*/


  /*<property name="contract">*/
  //------------------------------------------------------------------

  public final MethodContract getMethodContract() {
    return $methodContract;
  }
  
  public final TypeContract getTypeContract() {
    return $methodContract.getTypeContract();
  }

  /**
   * @invar $methodContract != null;
   */
  private MethodContract $methodContract;

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
    String[] fp = getMethodContract().getFormalParameters();
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
   * {@link TypeContract#validateTypeInvariants(Object, MethodTest) type invariants}.
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
      throw new TorytException(getMethodContract(), null);
    }
    if (getMethodContract().validatePreconditions(this)) {
      try {
        getMethodContract().recordState(this);
        methodCall(); 
        getMethodContract().validatePostConditions(this);
        getMethodContract().validateInertiaAxiom(this);
        validateMore();
      }
      catch (InvocationTargetException e) {
        getMethodContract().validateExceptionCondition(this, e.getCause());
      }
      catch (IllegalArgumentException e) {
        System.out.println(this);
        System.out.println(e);
        throw new TorytException(getMethodContract(), e);
      }
      catch (IllegalAccessException e) {
        System.out.println(this);
        System.out.println(e);
        throw new TorytException(getMethodContract(), e);
      }
      catch (InstantiationException e) {
        System.out.println(this);
        System.out.println(e);
        throw new TorytException(getMethodContract(), e);
      }
      catch (Throwable e) {
        System.out.println(this);
        System.out.println(e);
        throw new TorytException(getMethodContract(), e);
      }
    }
    setRun();
  }

  /**
   * Call the method of the contract with reflection and store
   * the result (if there is one) in the context map.
   * 
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   * @throws InvocationTargetException
   */
  protected abstract void methodCall()
      throws InstantiationException,
             IllegalAccessException,
             IllegalArgumentException,
             InvocationTargetException;
  
  protected abstract void validateMore();
  
  public final void report(PrintStream out) {
    out.println((isSuccessful() ? "success" : "FAILURE")
                + ": "
                + getMethodContract().getMember().toString());
    out.println(repeat("-", PAGE_WIDTH));
    reportContext(out);
  }

  private final static int PAGE_WIDTH = 80; 
  private final static int KEY_WIDTH = 30; 
  
  private String repeat(String c, int times) {
    StringBuffer r = new StringBuffer();
    while (times > 0) {
      r.append(c);
      times--;
    }
    return r.toString();
  }
  
  private void reportContext(PrintStream out) {
    Set map = getContext().entrySet();
    Iterator iter = map.iterator();
    while (iter.hasNext()) {
      Map.Entry e = (Map.Entry)iter.next();
      String key = (String)e.getKey();
      int keyLength = key.length();
      out.println(repeat(" ", KEY_WIDTH - keyLength)
                  + e.getKey()
                  + " :: "
                  + ((e.getValue() == null)
                      ? "null"
                      : new Rtsb(e.getValue(), ToStringStyle.DEFAULT_STYLE).toString()));
    }
  }

  /**
   * Do NOT skip fields that contain a "$".
   */
  private class Rtsb extends ReflectionToStringBuilder {
    
    public Rtsb(Object object, ToStringStyle style) {
      super(object, style);
    }

    protected boolean accept(Field field) {
      return (this.isAppendTransients() || !Modifier.isTransient(field.getModifiers()))
          && (!Modifier.isStatic(field.getModifiers()));
    }

  }

}