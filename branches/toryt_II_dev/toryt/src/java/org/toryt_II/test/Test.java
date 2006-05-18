package org.toryt_II.test;


import java.io.PrintStream;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * An actual instance of a test. A test can only be run once.
 * {@link #getOutcome()} returns the status of the test.
 *
 * @invar getResult() != null;
 * @invar getResult().hasStarted() ? isReady() == new.isReady();
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface Test<_Subject_> {

  /**
   * The object under test (a project, package, class, method, ...).
   *
   * @basic
   */
  _Subject_ getSubject();

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
   * During execution of the test, {@link #getOutcome()} must return
   * {@link TestOutcome#RUNNING}. When the test has run, {@link #getOutcome()}
   * must be set to either {@link TestOutcome#ERROR}, {@link TestOutcome#FAILED}
   * or {@link TestOutcome#SUCCESSFUL}. This method can only be called once.
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
   * The outcome of the test.
   *
   * @basic
   * @init TestOutcome.NOT_RUN_YET
   */
  TestOutcome getOutcome();

  /**
   * Structured output on an output stream.
   *
   * @deprecated
   * @mudo not sure about deprecation
   */
  @Deprecated
  void report(PrintStream out);

}