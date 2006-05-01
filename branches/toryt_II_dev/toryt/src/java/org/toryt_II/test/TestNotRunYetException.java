package org.toryt_II.test;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Signals that we called a method on {@link #getTest()}
 * that cannot be called when the test has not yet run.
 *
 * @author Jan Dockx
 *
 * @invar getTest() != null;
 * @invar ! getTest().getResult().hasRun();
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestNotRunYetException extends TestException {

  /**
   * @pre test != null;
   * @pre ! test.getResult().hasRun();
   * @post new.getTest() == test;
   * @post new.getMessage() == null;
   * @post new.getCause() == null;
   */
  public TestNotRunYetException(Test test) {
    super(test);
    assert ! test.getResult().hasRun();
  }

}