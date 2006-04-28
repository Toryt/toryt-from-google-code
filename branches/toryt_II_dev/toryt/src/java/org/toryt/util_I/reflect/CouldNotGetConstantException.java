package org.toryt.util_I.reflect;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Signals that we could not get the value of a constant with name
 * {@link #getConstantName()} from class {@link #getClazz()}.
 * The {@link #getCause() cause} gives more detail.
 *
 * @author Jan Dockx
 *
 * @invar getConstantName() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class CouldNotGetConstantException extends ReflectionException {

  /**
   * @pre clazz != null;
   * @pre constantName != null;
   * @post new.getClazz() == clazz;
   * @post new.getConstantName().equals(fqcn);
   * @post new.getMessage() == null;
   * @post new.getCause() == cause;
   */
  public CouldNotGetConstantException(Class clazz, String constantName, Throwable cause) {
    super(cause);
    $clazz = clazz;
    $constantName = constantName;
  }



  /**
   * @basic
   */
  public Class getClazz() {
    return $clazz;
  }

  /**
   * @invar $clazz != null;
   */
  private final Class $clazz;



  /**
   * @basic
   */
  public String getConstantName() {
    return $constantName;
  }

  /**
   * @invar $constantName != null;
   */
  private final String $constantName;

}