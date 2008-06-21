package org.toryt_II.packache;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.toryt_II.TorytException;
import org.toryt_II.type.ClassContract;


/**
 * @author Jan Dockx
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
   * @throws TorytException
   *         isClosed();
   * @throws TorytException
   *         ! pc.isClosed();
   */
  public final void addSubPackageContract(org.toryt_II.packache.PackageContract pc)
      throws TorytException {
    assert pc != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    if (! pc.isClosed()) {
      throw new TorytException(this, null);
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
   * @throws TorytException
   *         isClosed();
   * @throws TorytException
   *         ! cc.isClosed();
   */
  public final void addClassContract(ClassContract cc) throws TorytException {
    assert cc != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    if (! cc.isClosed()) {
      throw new TorytException(this, null);
    }
    $classContracts.add(cc);
  }
  
  private Set $classContracts = new HashSet();
  
}