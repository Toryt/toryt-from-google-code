package org.toryt_II.testcase;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.TorytException;


/**
 * An exception thrown by a test case.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestCaseException extends TorytException {

  /**
   * @pre testCase != null;
   * @post new.getTestCase() == testCase;
   * @post new.getMessage() == null;
   * @post new.getCause() == null;
   */
  public TestCaseException(TestCase testCase) {
    this(testCase, null, null);
  }

  /**
   * @pre testCase != null;
   * @post new.getTestCase() == testCase;
   * @post (message == null) ? new.getMessage() == null : message.equals(new.getMessage());
   * @post new.getCause() == null;
   */
  public TestCaseException(TestCase testCase, String message) {
    this(testCase, message, null);
  }

  /**
   * @pre testCase != null;
   * @post new.getTestCase() == testCase;
   * @post new.getMessage() == null;
   * @post new.getCause() == cause;
   */
  public TestCaseException(TestCase testCase, Throwable cause) {
    this(testCase, null, cause);
  }

  /**
   * @pre testCase != null;
   * @post new.getTestCase() == testCase;
   * @post (message == null) ? new.getMessage() == null : message.equals(new.getMessage());
   * @post new.getCause() == cause;
   */
  public TestCaseException(TestCase testCase, String message, Throwable cause) {
    super(message, cause);
    assert testCase != null;
    $testCase = testCase;
  }

  public final TestCase getTestCase() {
    return $testCase;
  }

  /**
   * @invar $testCase != null;
   */
  private TestCase $testCase;

}