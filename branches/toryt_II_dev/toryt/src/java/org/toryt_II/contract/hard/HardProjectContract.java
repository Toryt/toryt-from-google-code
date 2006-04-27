package org.toryt_II.contract.hard;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.toryt_II.OLDTorytException;
import org.toryt_II.contract.AbstractProjectContract;
import org.toryt_II.contract.PackageContract;


/**
 * @author Jan Dockx
 * 
 * @deprecated
 */
public class HardProjectContract
    extends AbstractProjectContract {

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

  
  
  public HardProjectContract(String projectName) {
    super(projectName);
  }

  public final Set getPackageContracts() {
    return Collections.unmodifiableSet($packageContracts);
  }
  
  /**
   * @pre    pc != null;
   * @throws OLDTorytException
   *         isClosed();
   * @throws OLDTorytException
   *         ! pc.isClosed();
   */
  public final void addPackageContract(PackageContract pc) throws OLDTorytException {
    assert pc != null;
    if (isClosed()) {
      throw new OLDTorytException(this, null);
    }
    if (! pc.isClosed()) {
      throw new OLDTorytException(this, null);
    }
    $packageContracts.add(pc);
  }
  
  private Set $packageContracts = new HashSet();
  
}