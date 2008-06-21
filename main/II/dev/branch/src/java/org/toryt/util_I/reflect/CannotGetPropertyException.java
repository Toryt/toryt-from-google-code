package org.toryt.util_I.reflect;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Signals that we could not get the property
 * {@link #getMemberName()} from class {@link #getClazz()}.
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
public class CannotGetPropertyException extends NamedMemberException {

  /**
   * @pre clazz != null;
   * @pre constantName != null;
   * @post new.getClazz() == clazz;
   * @post new.getConstantName().equals(fqcn);
   * @post new.getMessage() == null;
   * @post new.getCause() == cause;
   */
  public CannotGetPropertyException(Class<?> clazz, String memberName, Throwable cause) {
    super(clazz, memberName, cause);
  }

}