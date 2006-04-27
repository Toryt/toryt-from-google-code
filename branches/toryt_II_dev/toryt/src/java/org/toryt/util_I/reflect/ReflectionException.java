package org.toryt.util_I.reflect;


import org.toryt_II.TorytException;


/**
 * Signals problems with reflection.
 *
 * @author Jan Dockx
 */
public abstract class ReflectionException extends TorytException {

  /* <section name="Meta Information"> */
  //------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /* </section> */


  /**
   * @post new.getMessage() == null;
   * @post new.getCause() == cause;
   */
  public ReflectionException(Throwable cause) {
    super(null, cause);
  }

}