package org.toryt_II;


/**
 * @author Jan Dockx
 */
public abstract class AbstractTest implements Test {

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

  
  /**
   * @init true;
   */
  public final boolean hasRun() {
    return $run;
  }
  
  protected final void setRun() {
    $run = true;
  }
  
  private boolean $run;
  
}