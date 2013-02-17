package org.toryt;


import java.util.Map;


/**
 * A Condition is used to validate boolean expressions during a method test.
 * Conditions are used as preconditions, postconditions, type invariants and
 * exception conditions.
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
   * Validate a context.
   */
  boolean validate(Map context);

}