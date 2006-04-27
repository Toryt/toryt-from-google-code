package org.toryt.util_I.reflect;


/**
 * Signals that we could not get the value of a constant with name
 * {@link #getConstantName()} from class {@link #getClazz()}.
 * The {@link #getCause() cause} gives more detail.
 *
 * @author Jan Dockx
 *
 * @invar getConstantName() != null;
 */
public class CouldNotGetConstantException extends ReflectionException {

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