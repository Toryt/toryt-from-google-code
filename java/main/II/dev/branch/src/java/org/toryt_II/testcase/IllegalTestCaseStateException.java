package org.toryt_II.testcase;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Toryt code can throw exceptions of any kind. When we need to communicate a
 * Toryt-related issue, an instance of this class or a subclass is used.
 * Normal execution of tests should never throw an exception.
 *
 * @invar getState() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class IllegalTestCaseStateException extends TestCaseException {

  /**
   * @pre testCase != null;
   * @pre state != null;
   * @post new.getTestCase() == testCase;
   * @post new.getState() == state;
   * @post new.getMessage() == null;
   * @post new.getCause() == null;
   */
  public IllegalTestCaseStateException(TestCase testCase, TestCaseState state) {
    super(testCase, null, null);
    assert state != null;
    $state = state;
  }

  public final TestCaseState getState() {
    return $state;
  }

  /**
   * @invar $state != null;
   */
  private TestCaseState $state;

}