package org.toryt.util_I.reflect;


/**
 * Signals that a bean initialisation for class with {@link #getFullyQualifiedClassName()}
 * could not be loaded. The {@link #getCause() cause} gives more detail.
 *
 * @author Jan Dockx
 *
 * @invar getFullyQualifiedClassName() != null;
 */
public abstract class AbstractFqcnException extends ReflectionException {

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