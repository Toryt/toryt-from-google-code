package org.toryt_II.test;


import java.io.PrintStream;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * An actual instance of a test. A test can only be run once.
 * {@link #getResult()} returns the status of the test.
 *
 * @invar getResult() != null;
 * @invar getResult().hasStarted() ? isReady() == new.isReady();
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface Test<_SubjectType_> {

  /**
   * @basic
   */
  _SubjectType_ getSubject();

  /**
   * All resources for running the test are in place.
   * This property cannot change when the test has started.
   *
   * @basic
   */
  boolean isReady();

  /**
   * Execution of the test cannot throw <strong>any</strong>
   * {@link Exception}, {@link Error} or other {@link Throwable}
   * (except for notification of the fact that the test already ran).
   * During execution of the test, {@link #getResult()} must return
   * {@link TestResult#RUNNING}. When the test has run, {@link #getResult()}
   * must be set to either {@link TestResult#ERROR}, {@link TestResult#FAILED}
   * or {@link TestResult#SUCCESSFUL}. This method can only be called once.
   *
   * @post   isReady();
   *         Since we can't change pre-state, if the test is not ready,
   *         we have to throw an exception.
   * @post   ! getResult().hasStarted();
   *         Since we can't change pre-state, if the test has started already,
   *         we have to throw an exception.
   * @post   new.getResult().hasRun();
   * @throws TestNotReadyException
   *         ! isReady();
   * @throws TestAlreadyStartedException
   *         getResult().hasStarted();
   */
  void run() throws TestNotReadyException, TestAlreadyStartedException;

  /**
   * The test result;
   * @basic
   * @init TestResult.NOT_RUN_YET
   */
  TestResult getResult();

  /**
   * Structured output on an output stream.
   *
   * @deprecated
   * @mudo not sure about deprecation
   */
  void report(PrintStream out);

}