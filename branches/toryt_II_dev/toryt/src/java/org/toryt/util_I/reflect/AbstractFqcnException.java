package org.toryt.util_I.reflect;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Signals that a bean initialisation for class with {@link #getFullyQualifiedClassName()}
 * could not be loaded. The {@link #getCause() cause} gives more detail.
 *
 * @author Jan Dockx
 *
 * @invar getFullyQualifiedClassName() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractFqcnException extends ReflectionException {

  /**
   * @pre fqcn != null;
   * @post new.getFullyQualifiedClassName().equals(fqcn);
   * @post new.getMessage() == null;
   * @post new.getCause() == cause;
   */
  public AbstractFqcnException(String fqcn, Throwable cause) {
    super(cause);
    assert fqcn != null;
    $fqcn = fqcn;
  }

  /**
   * @basic
   */
  public String getFullyQualifiedClassName() {
    return $fqcn;
  }

  /**
   * @invar $fqcn != null;
   */
  private final String $fqcn;

}