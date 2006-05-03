package org.toryt.util_I.reflect;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Signals that we could not get the value of a constant with name
 * {@link #getMemberName()} from class {@link #getClazz()}.
 * The {@link #getCause() cause} gives more detail.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class CannotGetValueException extends NamedMemberException {

  /**
   * @pre clazz != null;
   * @pre constantName != null;
   * @post new.getClazz() == clazz;
   * @post new.getMemberName().equals(constantName);
   * @post new.getMessage() == null;
   * @post new.getCause() == cause;
   */
  public CannotGetValueException(Class<?> clazz, String constantName, Throwable cause) {
    super(clazz, constantName, cause);
  }

}