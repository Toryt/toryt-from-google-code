package org.toryt;


import java.util.Map;


/**
 * A Condition is used to validate boolean expressions during a method test.
 * Conditions are used as preconditions, postconditions, and type invariants.
 * {@link ExceptionCondition Exception conditions} use a subtype.
 * 
 * @author Jan Dockx
 * 
 */
public interface Condition {

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
   * @see MethodTest#SUBJECT_KEY
   */
  final static String SUBJECT_KEY = MethodTest.SUBJECT_KEY;

  /**
   * @see MethodTest#RESULT_KEY
   */
  final static String RESULT_KEY = MethodTest.RESULT_KEY;

  /**
   * Validate a context.
   */
  boolean validate(Map context);

}