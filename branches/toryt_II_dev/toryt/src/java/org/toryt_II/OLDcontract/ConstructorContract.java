package org.toryt_II.OLDcontract;


import java.lang.reflect.Constructor;


/**
 * @author Jan Dockx
 * 
 * @invar getMember() instanceof Constructor;
 */
public interface ConstructorContract extends MethodContract {

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
  public Constructor getConstructor();

}