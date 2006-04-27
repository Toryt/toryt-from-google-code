package org.toryt.util_I.reflect;


/**
 * Signals that a bean initialisation for class with {@link #getFullyQualifiedClassName()}
 * could not be loaded. The {@link #getCause() cause} gives more detail.
 *
 * @author Jan Dockx
 */
public class CouldNotInstantiateBeanException extends AbstractFqcnException {

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
   * @pre fqcn != null;
   * @post new.getFullyQualifiedClassName().equals(fqcn);
   * @post new.getMessage() == null;
   * @post new.getCause() == cause;
   */
  public CouldNotInstantiateBeanException(String fqcn, Throwable cause) {
    super(fqcn, cause);
  }

}