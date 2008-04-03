package org.toryt.util_I.reflect;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Signals problems parsing a String intended to be a method signature.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class CannotParseSignatureException extends ReflectionException {

  /**
   * @post equalsWithNull(getSignature(), signature);
   * @post getCause() == cause;
   * @post getMessage() == null;
   */
  public CannotParseSignatureException(String signature, Throwable cause) {
    super(cause);
    $signature = signature;
  }

  /**
   * @post equalsWithNull(getSignature(), signature);
   * @post equalsWithNull(message, getMessage());
   * @post getCause() == null;
   */
  public CannotParseSignatureException(String signature, String message) {
    super(message);
    $signature = signature;
  }

  /**
   * @basic
   */
  public String getSignature() {
    return $signature;
  }

  private final String $signature;

}