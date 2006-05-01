package org.toryt_II.test;


import static org.toryt_II.test.TestResult.ERROR;
import static org.toryt_II.test.TestResult.NOT_RUN_YET;
import static org.toryt_II.test.TestResult.RUNNING;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Implementation of common methods.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractTest<_SubjectType_> implements Test<_SubjectType_> {

  private final static Log LOG = LogFactory.getLog(AbstractTest.class);



  /*<property name="subject">*/
  //------------------------------------------------------------------

  public final _SubjectType_ getSubject() {
    return $subject;
  }

  /**
   * @post new.getSubject() == subject;
   */
  public void setSubject(_SubjectType_ subject) {
    $subject = subject;
  }

  /**
   * @invar $subject != null;
   */
  private _SubjectType_ $subject;

  /*</property>*/



  /*<property name="result">*/
  //------------------------------------------------------------------

  /**
   * The test result;
   */
  public final TestResult getResult() {
    return $testResult;
  }

  /**
   * @pre isReady();
   * @pre testResult != null;
   * @post new.getResult() == testResult;
   */
  protected final void setResult(TestResult testResult) {
    assert isReady();
    assert testResult != null;
    $testResult = testResult;
  }

  /**
   * @invar $testResult != null;
   */
  private TestResult $testResult = NOT_RUN_YET;

  /*</property>*/



  /*<section name="runnign">*/
  //------------------------------------------------------------------

  /**
   * This implementation delegates the actual testing to
   * {@link #runImplementation()}. First, we check to see
   * if this test is ready and not started already. If it is, we throw
   * the appropriate exception. Then, we set the {@link #getResult()}
   * to {@link TestResult#RUNNING}, and call {@link #runImplementation()}.
   * When this method completes normally, the result is used
   * to set {@link #getResult()}. If the method throws any exception,
   * {@link #getResult()} is set to {@link #ERROR}.
   */
  public final void run() throws TestNotReadyException, TestAlreadyStartedException  {
    if (! isReady()) {
      throw new TestNotReadyException(this);
    }
    synchronized(this) {
      if (getResult().hasStarted()) {
        throw new TestAlreadyStartedException(this);
      }
      else {
        setResult(RUNNING);
      }
    }
    try {
      TestResult testResult = runImplementation();
      assert testResult != null;
      assert testResult.hasRun();
      setResult(testResult);
    }
    catch (Throwable t) {
      LOG.warn("Caught exception during test run. This usually means that there is an " +
               "error in the testing code, not the tested code.", t);
      setResult(ERROR);
    }
  }

  /**
   * The actual implementation of the test. This method should not throw any
   * kind of exception (but if some do escape, there is a last minute
   * result around this call; any exception that does pass through
   * is considered an {@link #ERROR}). When the test concludes without
   * problems, it should return a {@link TestResult} appropriately.
   *
   * @pre ! getResult() == RUNNING;
   * @post result != null;
   * @post result.hasRun();
   */
  protected abstract TestResult runImplementation();

  /*</section>*/

}