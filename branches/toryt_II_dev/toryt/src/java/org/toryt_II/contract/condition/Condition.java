package org.toryt_II.contract.condition;


import java.util.Map;

import org.toryt_II.test.MethodContractTest;


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
   * @see MethodContractTest#SUBJECT_KEY
   */
  final static String SUBJECT_KEY = MethodContractTest.SUBJECT_KEY;

  /**
   * @see MethodContractTest#RESULT_KEY
   */
  final static String RESULT_KEY = MethodContractTest.RESULT_KEY;

  /**
   * Validate a context.
   */
  boolean validate(Map context);

}