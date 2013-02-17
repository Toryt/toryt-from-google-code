package org.toryt_II.test;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Enumeration type for possible results of tests.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public enum TestOutcome {

  /*<values>*/
  //------------------------------------------------------------------

  /**
   * Result not available. Test has not run yet.
   */
  NOT_RUN_YET,

  /**
   * Result not available. Test is running.
   */
  RUNNING,

  /**
   * Test tried, but an error occured in the testing code,
   * not necessarily in the tested code.
   *
   * @note It is quite difficult to recognize errors from
   *       failures. It is not sure this rest result is
   *       meaningful.
   */
  ERROR,

  /**
   * Test completed, but failed.
   */
  FAILED,

  /**
   * Test completed successfully.
   */
  SUCCESSFUL;

  /*</values>*/



  /**
   * @return this.compareTo(NOT_RUN_YET) > 0;
   */
  public final boolean hasStarted() {
    return this.compareTo(NOT_RUN_YET) > 0;
  }

  /**
   * @return this.compareTo(RUNNING) > 0;
   */
  public final boolean hasRun() {
    return this.compareTo(RUNNING) > 0;
  }

  /**
   * @return hasRun() && this.compareTo(RUNNING) < 0;
   */
  public final boolean hasProblems() {
    return hasRun() && this.compareTo(SUCCESSFUL) < 0;
  }

}