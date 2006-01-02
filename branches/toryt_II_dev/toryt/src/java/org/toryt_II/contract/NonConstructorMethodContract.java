package org.toryt_II.contract;


import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;



/**
 * Glue between the Java reflection {@link Member} hierarchy, and the method hierarchy
 * of Toryt.
 * In Java reflection, there is a strict distinction between {@link Method}
 * and {@link Constructor}. In this framework, we make a different distinction,
 * between instance and class methods. Constructors are a kind of class methods.
 * This interface is used to patch those differences.
 * 
 * @author Jan Dockx
 * 
 * @invar getMember() instanceof Method
 */
public interface NonConstructorMethodContract extends MethodContract {

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
  /*</section>*/

  
  /**
   * @return getMember();
   */
  Method getMethod();

}