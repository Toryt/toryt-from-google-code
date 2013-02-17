package org.toryt_II.test;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Signals that we tried to run a test that ran already.
 *
 * @author Jan Dockx
 *
 * @invar getTest() != null;
 * @invar getTest().getResult().hasRun();
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestAlreadyStartedException extends TestException {

  /**
   * @pre test != null;
   * @pre test.getResult().hasRun();
   * @post new.getTest() == test;
   * @post new.getMessage() == null;
   * @post new.getCause() == null;
   */
  public TestAlreadyStartedException(Test test) {
    super(test);
    assert test.getOutcome().hasRun();
  }

}