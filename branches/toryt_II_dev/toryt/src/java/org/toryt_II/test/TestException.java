package org.toryt_II.test;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.TorytException;


/**
 * Signals a problem with a {@link Test}.
 *
 * @author Jan Dockx
 *
 * @invar getTest() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestException extends TorytException {

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre test != null;
   * @post new.getTest() == test;
   * @post new.getMessage() == null;
   * @post new.getCause() == null;
   */
  public TestException(Test test) {
    super();
    assert test != null;
    $test = test;
  }

  /**
   * @pre test != null;
   * @post new.getTest() == test;
   * @post (message == null) ? new.getMessage() == null : message.equals(new.getMessage());
   * @post new.getCause() == cause;
   */
  public TestException(Test test, String message, Throwable cause) {
    super(message, cause);
    assert test != null;
    $test = test;
  }

  /*</construction>*/



  /*<property name="test">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public Test getTest() {
    return $test;
  }

  /**
   * @invar $test != null;
   */
  private final Test $test;

  /*</property>*/

}