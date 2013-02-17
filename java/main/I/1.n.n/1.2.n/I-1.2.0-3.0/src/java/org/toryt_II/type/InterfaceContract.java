package org.toryt_II.type;


/**
 * The contract of an interface. A {@link HardTypeContract} for types that
 * are interfaces.
 * 
 * @invar getType().isInterface();
 */
public interface InterfaceContract extends TypeContract {

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

}