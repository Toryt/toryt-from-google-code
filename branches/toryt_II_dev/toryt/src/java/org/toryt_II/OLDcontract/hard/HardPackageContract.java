package org.toryt_II.OLDcontract.hard;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.toryt_II.OLDTorytException;
import org.toryt_II.OLDcontract.AbstractPackageContract;
import org.toryt_II.OLDcontract.ClassContract;


/**
 * @author Jan Dockx
 * 
 * @deprecated
 */
public class HardPackageContract
    extends AbstractPackageContract {

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


  
  public HardPackageContract(Package p) {
    super(p);
  }

  public final Set getSubPackageContracts() {
    return Collections.unmodifiableSet($subPackageContracts);
  }
  
  /**
   * 
   * @pre    pc != null;
   * @throws OLDTorytException
   *         isClosed();
   * @throws OLDTorytException
   *         ! pc.isClosed();
   */
  public final void addSubPackageContract(org.toryt_II.OLDcontract.PackageContract pc)
      throws OLDTorytException {
    assert pc != null;
    if (isClosed()) {
      throw new OLDTorytException(this, null);
    }
    if (! pc.isClosed()) {
      throw new OLDTorytException(this, null);
    }
    $subPackageContracts.add(pc);
  }
  
  private Set $subPackageContracts = new HashSet();
  
  public final Set getClassContracts() {
    return Collections.unmodifiableSet($classContracts);
  }
  
  /**
   * 
   * @pre    cc != null;
   * @throws OLDTorytException
   *         isClosed();
   * @throws OLDTorytException
   *         ! cc.isClosed();
   */
  public final void addClassContract(ClassContract cc) throws OLDTorytException {
    assert cc != null;
    if (isClosed()) {
      throw new OLDTorytException(this, null);
    }
    if (! cc.isClosed()) {
      throw new OLDTorytException(this, null);
    }
    $classContracts.add(cc);
  }
  
  private Set $classContracts = new HashSet();
  
}