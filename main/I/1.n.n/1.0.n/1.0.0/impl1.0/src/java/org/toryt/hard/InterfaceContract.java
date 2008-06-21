package org.toryt.hard;

import org.toryt.TorytException;




/**
 * @author Jan Dockx
 */
public class InterfaceContract
    extends TypeContract implements org.toryt.InterfaceContract {

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
  public InterfaceContract(Class type) {
    super(type);
    assert type.isInterface();
  }

  /**
   * @pre type.isInterface();
   */
  public InterfaceContract(String fqn) throws TorytException {
    super(fqn);
  }

}