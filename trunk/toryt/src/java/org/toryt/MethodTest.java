package org.toryt;


import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
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


  public final static String SUBJECT_KEY = "this'";
  public final static String RESULT_KEY = "org.toryt.MethodTest.RESULT";
  public final static String EXCEPTION_KEY = "org.toryt.MethodTest.EXCEPTION";

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
   * @return getContext().get(MethodTest.SUBJECT_KEY);
   */
  public final Object getSubject() {
    return getContext().get(MethodContract.SUBJECT_KEY);
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
   * @return getContext().get(EXCEPTIOn_KEY);
   *
   * @idea move to constructor and inspector test
   */
  public final Throwable getException() {
    return (Throwable)getContext().get(EXCEPTION_KEY);
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
    return hasRun() && getFailedConditions().isEmpty();
  }

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
   * The contract is asked to record the pre-state on this with
   * {@link MethodContract#recordState(MethodTest)}. Then the method-under-test
   * is called. Afterwards, if the method ended nominally, we validate the
   * {@link MethodContract#getPostconditions() postconditions}, the
   * inertia axiom, and the
   * {@link TypeContract#getTypeInvariantConditions() type invariants}.
   * If the method does not end nominally, we validate the
   * {@link MethodContract#getExceptionConditions() exception conditions}.
   *
   * @post new.hasRun();
   * @throws TorytException
   *         hasRun();
   */
  public final void test() throws TorytException {
    if (hasRun()) {
      throw new TorytException(getMethodContract(), null);
    }
    try {
      getMethodContract().recordState(this);
      methodCall();
      validateConditionSet(getMethodContract().getPostconditions());
      validateConditionSet(getMethodContract().getTypeContract().getTypeInvariantConditions());
      validateInertiaAxiom();
//      validateMore();
    }
    catch (InvocationTargetException e) {
      e.printStackTrace();
      getContext().put(EXCEPTION_KEY, e.getCause());
      validateConditionSet(getMethodContract().getExceptionConditions());
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
    setRun();
  }

  private void validateInertiaAxiom() {
    // MUDO
  }

  private void validateConditionSet(Set conditionSet) {
    Iterator iter = conditionSet.iterator();
    while (iter.hasNext()) {
      Condition c = (Condition)iter.next();
      if (c.validate(getContext())) {
        $passedConditions.add(c);
      }
      else {
        $failedConditions.add(c);
      }
    }
  }

  public Set getPassedConditions() {
    return Collections.unmodifiableSet($passedConditions);
  }

  private Set $passedConditions = new HashSet();

  public Set getFailedConditions() {
    return Collections.unmodifiableSet($failedConditions);
  }

  private Set $failedConditions = new HashSet();

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

  public final void report(PrintStream out) {
    out.println((isSuccessful() ? "success" : "FAILURE")
                + ": "
                + ((getSubject() != null) ? getSubject().getClass() : getMethodContract().getTypeContract().getType()).getName()
                + " # "
                + getMethodContract().getMember().toString());
    out.println(repeat("-", PAGE_WIDTH));
    reportContext(out);
    out.println("Passed Conditions:");
    reportConditions(getPassedConditions(), out);
    out.println("Failed Conditions:");
    reportConditions(getFailedConditions(), out);
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

  private void reportConditions(Set conditions, PrintStream out) {
    Iterator iter = conditions.iterator();
    while (iter.hasNext()) {
      out.println(repeat(" ", 2) + iter.next());
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

  public final String toString() {
    return "test for " + getMethodContract().getMember()
           + " (" + (hasRun() ? "run" : "not run") + ")";
  }

}