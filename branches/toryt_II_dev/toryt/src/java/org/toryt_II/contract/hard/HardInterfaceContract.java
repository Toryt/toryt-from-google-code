package org.toryt_II.contract.hard;

import org.toryt_II.TorytException;




/**
 * @author Jan Dockx
 */
public class HardInterfaceContract
    extends HardTypeContract implements org.toryt_II.contract.InterfaceContract {

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
   * @pre type.isInterface();
   */
  public HardInterfaceContract(Class type) {
    super(type);
    assert type.isInterface();
  }

  /**
   * @pre type.isInterface();
   */
  public HardInterfaceContract(String fqn) throws TorytException {
    super(fqn);
  }

}