package org.toryt;


import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;


/**
 * @author Jan Dockx
 * 
 * @invar getMember() != null;
 * @invar (getMember() instanceof Method) || (getMember() instanceof Constructor);
 */
public interface MethodContract extends Contract {

  /*<section name="Meta Information">*/
  //  ------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /*</section>*/

  
  /**
   * @basic
   */
  Member getMember();
    
  public final static String SUBJECT_KEY = "SUBJECT";
  
  public abstract List getTestCases() throws TorytException;
  
  public abstract String[] getFormalParameters();

  /**
   * Validate the preconditions of this method contract against
   * the state of the given <code>test</code>.
   */
  boolean validatePreconditions(MethodTest test);

  /**
   * Record the old state of the test case (that is the
   * current state of <code>test.getContext()</state> now)
   * also in <code>test.getContext()</code>.
   * The default implementation of this method does nothing.
   */
  void recordState(MethodTest test);
  
  /**
   * Validate the postconditions of this method contract
   * against the state of the given <code>test</code>.
   * Validation should be done by calling {@link MethodTest#validate(boolean)}
   * on <code>test</code>.
   */
  void validatePostConditions(MethodTest test);

  /**
   * Validate the inertia axiom
   * against the state of the given <code>test</code>.
   * Validation should be done by calling {@link MethodTest#validate(boolean)}
   * on <code>test</code>.
   */
  void validateInertiaAxiom(MethodTest test);

  /**
   * Validate the type invariants of this method contract
   * against the state of the given <code>test</code>.
   * Validation should be done by calling {@link MethodTest#validate(boolean)}
   * on <code>test</code>.
   */
  void validateTypeInvariants(MethodTest test);

  /**
   * Validate the exception conditions of this method contract
   * against the state of the given <code>test</code> for the
   * exception <code>exc</code>.
   * Validation should be done by calling {@link MethodTest#validate(boolean)}
   * on <code>test</code>.
   */
  void validateExceptionCondition(MethodTest test, Throwable exc);

  
  /**
   * Factory method for the creation of method tests of the correct
   * type in the covariant test hierarchy.
   * 
   * @post result.getContract() == this;
   */
  MethodTest createMethodTest(Map testcase);

}