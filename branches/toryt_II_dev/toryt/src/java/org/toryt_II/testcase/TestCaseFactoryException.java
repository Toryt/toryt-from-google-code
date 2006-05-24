package org.toryt_II.testcase;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * An exception thrown by a test case.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestCaseFactoryException extends TorytException {

  /**
   * @post new.getMessage() == null;
   * @post new.getCause() == null;
   */
  public TestCaseFactoryException() {
    super();
  }

  /**
   * @post (message == null) ? new.getMessage() == null : message.equals(new.getMessage());
   * @post new.getCause() == null;
   */
  public TestCaseFactoryException(String message) {
    super(message);
  }

  /**
   * @post new.getMessage() == null;
   * @post new.getCause() == cause;
   */
  public TestCaseFactoryException(Throwable cause) {
    super(cause);
  }

  /**
   * @post (message == null) ? new.getMessage() == null : message.equals(new.getMessage());
   * @post new.getCause() == cause;
   */
  public TestCaseFactoryException(String message, Throwable cause) {
    super(message, cause);
  }

}