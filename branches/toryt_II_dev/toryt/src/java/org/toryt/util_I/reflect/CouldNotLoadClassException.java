package org.toryt.util_I.reflect;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Signals that a class with {@link #getFullyQualifiedClassName()}
 * could not be loaded. The {@link #getCause() cause} gives more detail.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class CouldNotLoadClassException extends AbstractFqcnException {

  /**
   * @pre fqcn != null;
   * @post new.getFullyQualifiedClassName().equals(fqcn);
   * @post new.getMessage() == null;
   * @post new.getCause() == cause;
   */
  public CouldNotLoadClassException(String fqcn, Throwable cause) {
    super(fqcn, cause);
  }

}