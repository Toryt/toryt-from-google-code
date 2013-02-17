/*<license>
Copyright 2006 - $Date$ by the authors mentioned below.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</license>*/

package org.toryt_II.test;


import static org.toryt_II.test.TestOutcome.ERROR;
import static org.toryt_II.test.TestOutcome.NOT_RUN_YET;
import static org.toryt_II.test.TestOutcome.RUNNING;

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
public abstract class AbstractTest<_Subject_> implements Test<_Subject_> {

  private final static Log LOG = LogFactory.getLog(AbstractTest.class);



  /*<property name="subject">*/
  //------------------------------------------------------------------

  public final _Subject_ getSubject() {
    return $subject;
  }

  /**
   * @post new.getSubject() == subject;
   */
  public void setSubject(_Subject_ subject) {
    $subject = subject;
  }

  /**
   * @invar $subject != null;
   */
  private _Subject_ $subject;

  /*</property>*/



  /*<property name="result">*/
  //------------------------------------------------------------------

  public final TestOutcome getOutcome() {
    return $outcome;
  }

  /**
   * @pre isReady();
   * @pre outcome != null;
   * @post new.getOutcome() == outcome;
   */
  protected final void setOutcome(TestOutcome outcome) {
    assert isReady();
    assert outcome != null;
    $outcome = outcome;
  }

  /**
   * @invar $outcome != null;
   */
  private TestOutcome $outcome = NOT_RUN_YET;

  /*</property>*/



  /*<section name="running">*/
  //------------------------------------------------------------------

  /**
   * This implementation delegates the actual testing to
   * {@link #runImplementation()}. First, we check to see
   * if this test is ready and not started already. If it is, we throw
   * the appropriate exception. Then, we set the {@link #getOutcome()}
   * to {@link TestOutcome#RUNNING}, and call {@link #runImplementation()}.
   * When this method completes normally, the result is used
   * to set {@link #getOutcome()}. If the method throws any exception,
   * {@link #getOutcome()} is set to {@link #ERROR}.
   */
  public final void run() throws TestNotReadyException, TestAlreadyStartedException  {
    if (! isReady()) {
      throw new TestNotReadyException(this);
    }
    synchronized(this) {
      if (getOutcome().hasStarted()) {
        throw new TestAlreadyStartedException(this);
      }
      else {
        setOutcome(RUNNING);
      }
    }
    try {
      TestOutcome testResult = runImplementation();
      assert testResult != null;
      assert testResult.hasRun();
      setOutcome(testResult);
    }
    catch (Throwable t) {
      LOG.warn("Caught exception during test run. This usually means that there is an " +
               "error in the testing code, not the tested code.", t);
      setOutcome(ERROR);
    }
  }

  /**
   * The actual implementation of the test. This method should not throw any
   * kind of exception (but if some do escape, there is a last minute
   * rescue around this call; any exception that does pass through
   * is considered an {@link #ERROR}). When the test concludes without
   * problems, it should return a {@link TestOutcome} appropriately.
   *
   * @pre ! getResult() == RUNNING;
   * @post result != null;
   * @post result.hasRun();
   */
  protected abstract TestOutcome runImplementation();

  /*</section>*/

}