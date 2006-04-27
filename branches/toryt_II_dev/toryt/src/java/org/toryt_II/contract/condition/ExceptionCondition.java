package org.toryt_II.contract.condition;

import java.util.Map;

import org.toryt_II.OLDTorytException;
import org.toryt_II.test.MethodTest;


/**
 * An ExceptionCondition is used to validate boolean expressions during a method test,
 * to validate thrown exceptions. Instances of this type know the
 * {@link #getExceptionType() type of exception} for which this condition applies.
 * 
 * @author Jan Dockx
 * 
 * @invar getExceptionType() != null;
 * @invar Throwable.class.isAssignableFrom(getExceptionType());
 */
public abstract class ExceptionCondition implements Condition {

  /*<section name="Meta Information">*/
  //  ------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";


  /**
   * @throws OLDTorytException
   *         (exceptionType == null) || (
   */
  public ExceptionCondition(Class exceptionType) throws OLDTorytException {
    if ((exceptionType == null) || (! Throwable.class.isAssignableFrom(exceptionType))) {
      throw new OLDTorytException(null, null);
    }
    $exceptionType = exceptionType;
  }
  
  /**
   * The type of exception for which this is a condition.
   */
  public final Class getExceptionType() {
    return $exceptionType;
  }

  /**
   * @invar $exceptionType != null;
   * @invar Throwable.class.isAssignableFrom($exceptionType);
   */
  private Class $exceptionType;
  
  /**
   * @see MethodTest#EXCEPTION_KEY
   */
  public final static String EXCEPTION_KEY = MethodTest.EXCEPTION_KEY;
  
  /**
   * Retrieve the exception from the map.
   * 
   * @pre context != null;
   * @return context.get(EXCEPTION_KEY);
   */
  protected final Throwable getException(Map m) {
    return (Throwable)m.get(EXCEPTION_KEY);
  }

  /**
   * @pre context != null;
   * @pre context.get(EXCEPTION_KEY) != null;
   */
  public abstract boolean validate(Map context);
  
}