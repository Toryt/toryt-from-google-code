package org.toryt_II.contractest;


import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.OLDTorytException;
import org.toryt_II.contract.MethodContract;
import org.toryt_II.contract.TypeContract;
import org.toryt_II.contract.condition.Condition;
import org.toryt_II.contract.condition.ExceptionCondition;
import org.toryt_II.contract.hard.HardTypeContract;
import org.toryt_II.test.AbstractTest;
import org.toryt_II.test.TestOutcome;


/**
 * <p>An actual instance of a test of a method. This is a test with actual
 *   test arguments. To be ready, the test needs to have a {@link #getSubject() subject},
 *   a {@link #getContract() contract} and a {@link #getCase() case}.</p>
 * <p>This class is abstract: you should use
 *   one of the more specific subclasses for different kinds of methods
 *   instead. <code>_Subject_</code> is still unbound, because in Java
 *   reflection {@link java.lang.reflect.Method} and
 *   {@link java.lang.reflect.Constructor} are unrelated types.</p>
 *
 * @invar getMethodContract() != null;
 * @invar getContext() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class MethodContractTest<_Subject_ extends Member> extends AbstractTest<_Subject_> {

  private final static Log LOG = LogFactory.getLog(MethodContractTest.class);



  /*<property name="methodContract">*/
  //------------------------------------------------------------------

  public final MethodContract<_Subject_> getMethodContract() {
    return $methodContract;
  }

  public final TypeContract getTypeContract() {
    return $methodContract.getTypeContract();
  }

  /**
   * @invar $methodContract != null;
   */
  private MethodContract<_Subject_> $methodContract;

  /*</property>*/



  /*<property name="context">*/
  //------------------------------------------------------------------


  public final static String SUBJECT_KEY = "this";
  public final static String RESULT_KEY = "org.toryt_II.MethodTest.RESULT";
  public final static String EXCEPTION_KEY = "org.toryt_II.MethodTest.EXCEPTION";

  /**
   * This method gives direct access to the map that holds the context
   * of the test. This is the map that is passed in the constructor as test case.
   * The map is filled with context information, apart from the initial
   * implicit and explicit arguments, by the {@link MethodContract#recordState(MethodContractTest)}
   * method. In this map, there are 2 specific keys: a key for the implicit
   * object (the subject) and a key for the result of an inspector.
   */
  public Map getCase() {
    return $context;
  }

  private Map $context;

//  /**
//   * @return getContext().get(MethodTest.SUBJECT_KEY);
//   */
//  public final Object getSubject() {
//    return getContext().get(MethodContract.SUBJECT_KEY);
//  }
//
//  /**
//   * @return getContext().get(RESULT_KEY);
//   */
//  public final Object getResult() {
//    return getContext().get(RESULT_KEY);
//  }
//
//  /**
//   * @return getContext().get(EXCEPTIOn_KEY);
//   */
//  public final Throwable getException() {
//    return (Throwable)getContext().get(EXCEPTION_KEY);
//  }

  /**
   * Extract the actual arguments from the {@link #getContext() context},
   * with the help of the {@link MethodContract#getFormalParameters() formal parameters}.
   */
  protected final Object[] getActualParameters() {
    String[] fp = getMethodContract().getFormalParameters();
    int n = fp.length;
    Object[] result = new Object[n];
    for (int i = 0; i < n; i++) {
      result[i] = getCase().get(fp[i]);
    }
    return result;
  }

  /*</property>*/



  /*<property name="ready">*/
  //------------------------------------------------------------------

  public final boolean isReady() {
    return (getSubject() != null) &&
           ((getMethodContract() != null) && (getMethodContract().getMember() == getSubject()) &&
           (getCase() != null) && hasMatchingCaseAndMethodTypes();  <<---- preconditions instead?
  }

  /*</property>*/



  /**
   * Call the method under test and validate the contract.
   * The contract is asked to record the pre-state on this with
   * {@link MethodContract#recordState(MethodContractTest)}. Then the method-under-test
   * is called. Afterwards, if the method ended nominally, we validate the
   * {@link MethodContract#getPostconditions() postconditions}, the
   * inertia axiom, and the
   * {@link HardTypeContract#getTypeInvariantConditions() type invariants}.
   * If the method does not end nominally, we validate the
   * {@link MethodContract#getExceptionConditions() exception conditions}.
   *
   * @post new.hasRun();
   */
  protected final TestOutcome runImplementation() {
    try {
      getMethodContract().recordState(this); // TODO legacy, until a Case can record state itself
      methodCall();
      validateConditionSet(getMethodContract().getPostconditions());
      validateConditionSet(getMethodContract().getTypeContract().getTypeInvariantConditions());
      validateInertiaAxiom();
//      validateMore();
    }
    catch (InvocationTargetException e) {
      // exceptions from the method itself; might fail the test, but not an error
      validateExceptionConditions(e.getCause());
    }
    catch (IllegalArgumentException iaExc) {
      return handleTestError(iaExc, null);
    }
    catch (IllegalAccessException iaExc) {
      return handleTestError(iaExc, null);
    }
    catch (NullPointerException npExc) {
      return handleTestError(npExc,
                           "This method was called in test on null. " +
                           "Check your cases (no null as test this allowed).");
    }
    catch (ExceptionInInitializerError eiiErr) {
      return handleTestError(eiiErr, null);
    }
    catch (InstantiationException iExc) {
      return handleTestError(iExc, null);
    }
    return $failedConditions.isEmpty() ? TestResult.SUCCESSFUL : TestResult.FAILED;
  }

  private TestResult handleTestError(Throwable t, String extraMessage) {
    LOG.warn("Test failed with exception from calling method (not from the method itself. " +
             "This is considered an error in the test, and should not happen. If this occurs, " +
             "it most often means that the contract has an error.", t);
    return TestResult.ERROR;
  }

  private void validateExceptionConditions(Throwable e) {
    assert e != null;
    Map exceptionConditionsMap = getMethodContract().getExceptionConditions();
    getCase().put(EXCEPTION_KEY, e);
    Iterator iterMap = exceptionConditionsMap.keySet().iterator();
    while (iterMap.hasNext()) {
      Class exceptionType = (Class)iterMap.next();
      if (exceptionType.isAssignableFrom(e.getClass())) {
        Set exceptionConditions =
          (Set)exceptionConditionsMap.get(exceptionType);
        Iterator iterSet = exceptionConditions.iterator();
        while(iterSet.hasNext()) {
          ExceptionCondition exceptionCondition = (ExceptionCondition) iterSet.next();
          if (exceptionCondition.validate(getContext())) {
            $passedConditions.add(new PassedExpectedExceptionCondition(e, exceptionCondition));
          }
        }
      }
    }
    if ($passedConditions.size() == 0) {
      $failedConditions.add(new FailedExceptionCondition(e, null)); //todo
    }
//    assert e != null;
//    Set conditionSet = (Set)getMethodContract().getExceptionConditions().get(e.getClass());
//    if (conditionSet == null) {
//      /* there are no conditions recorded for this exception type;
//       * it is thus unexpected, and thus a failure.
//       */
//      $failedConditions.add(new FailedExceptionCondition(e, null));
//    }
//    else {
//      getContext().put(EXCEPTION_KEY, e);
//      Iterator iter = conditionSet.iterator();
//      while (iter.hasNext()) {
//        ExceptionCondition c = (ExceptionCondition)iter.next();
//        if (c.validate(getContext())) {
//          $passedConditions.add(new PassedExpectedExceptionCondition(e, c));
//          return;
//        }
//      }
//      // if we get here, no condition was valid
//      $failedConditions.add(new FailedExceptionCondition(e, conditionSet));
//    }
  }

  public abstract class ReportExceptionCondition implements Condition {

    public ReportExceptionCondition(Throwable e) {
      assert e != null;
      $exception = e;
    }

    public final Throwable getException() {
      return $exception;
    }

    public final MethodContractTest getMethodTest() {
      return MethodContractTest.this;
    }

    private Throwable $exception;

    public String toString() {
      return "Exception: " + getException().toString();
    }

  }

  public final class FailedExceptionCondition extends ReportExceptionCondition {

    public FailedExceptionCondition(Throwable e,
                                      Set exceptionConditions) {
      super(e);
      $exceptionConditions = exceptionConditions;
    }

    /**
     * The exception conditions for the type of exception
     * that all validated <code>false</code>. If <code>null</code>
     * the exception was wholy unexpected.
     */
    public Set getExceptionCondition() {
      return $exceptionConditions;
    }

    private Set $exceptionConditions;

    public boolean validate(Map context) {
      return false;
    }

  }

  public final class PassedExpectedExceptionCondition extends ReportExceptionCondition {

    public PassedExpectedExceptionCondition(Throwable e,
                                      ExceptionCondition exceptionCondition) {
      super(e);
      assert exceptionCondition != null;
      $exceptionCondition = exceptionCondition;
    }

    /**
     * The exception condition that validated <code>true</code>.
     */
    public ExceptionCondition getExceptionCondition() {
      return $exceptionCondition;
    }

    private ExceptionCondition $exceptionCondition;

    public boolean validate(Map context) {
      return true;
    }

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