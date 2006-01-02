package org.toryt_II;



/**
 * Exceptions thrown by Toryt code, or because of faulty test code.
 * <em>Not</em> exceptions thrown by the tested code.
 */
public class TorytException extends Exception {

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
   * @post new.getTest() == test;
   * @post new.getCause() == cause;
   */
  public TorytException(Contract test, Throwable cause) {
    super(cause);
    $test = test;
  }
  
  private Contract $test;
  
  public final Contract getTest() {
    return $test;
  }
  
}