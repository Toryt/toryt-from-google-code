package org.toryt_II;


/**
 * Toryt code can throw exceptions of any kind. When we need to communicate a
 * Toryt-related issue, an instance of this class or a subclass is used.
 * Normal execution of tests should never throw an exception.
 */
public class TorytException extends Exception {

  /*<section name="Meta Information">*/
  //  ------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /*</section>*/


  /**
   * @post new.getMessage() == null;
   * @post new.getCause() == null;
   */
  public TorytException() {
    super();
  }

  /**
   * @post (message == null) ? new.getMessage() == null : message.equals(new.getMessage());
   * @post new.getCause() == null;
   */
  public TorytException(String message) {
    super(message);
  }

  /**
   * @post new.getMessage() == null;
   * @post new.getCause() == cause;
   */
  public TorytException(Throwable cause) {
    super(cause);
  }

  /**
   * @post (message == null) ? new.getMessage() == null : message.equals(new.getMessage());
   * @post new.getCause() == cause;
   */
  public TorytException(String message, Throwable cause) {
    super(message, cause);
  }

}