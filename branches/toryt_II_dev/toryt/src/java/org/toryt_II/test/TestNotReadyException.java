package org.toryt_II.test;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Signals that we called {@link Test#run()} while the
 * test {@link Test#isReady() was not yet ready}.
 * There is no invariant that the test provided is not
 * ready always, since, in concurrent execution,
 * it might get ready after the exception was thrown.
 *
 * @author Jan Dockx
 *
 * @invar getTest() != null;
 * @invar ! test.getResult().hasRun();
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestNotReadyException extends TestException {

  /**
   * @pre test != null;
   * @pre ! test.getResult().hasRun();
   * @post new.getTest() == test;
   * @post new.getMessage() == null;
   * @post new.getCause() == null;
   */
  public TestNotReadyException(Test test) {
    super(test);
    assert ! test.getResult().hasRun();
  }

}