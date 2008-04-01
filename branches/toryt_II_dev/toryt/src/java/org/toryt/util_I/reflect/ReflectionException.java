package org.toryt.util_I.reflect;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.TorytException;


/**
 * Signals problems with reflection.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class ReflectionException extends TorytException {

  /**
   * @post new.getMessage() == null;
   * @post new.getCause() == cause;
   */
  public ReflectionException(Throwable cause) {
    super(null, cause);
  }

}