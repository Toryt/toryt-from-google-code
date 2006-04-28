package org.toryt_II;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Toryt code can throw exceptions of any kind. When we need to communicate a
 * Toryt-related issue, an instance of this class or a subclass is used.
 * Normal execution of tests should never throw an exception.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TorytException extends Exception {

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