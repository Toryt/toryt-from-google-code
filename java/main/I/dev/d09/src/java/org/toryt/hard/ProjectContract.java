package org.toryt.hard;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.toryt.AbstractProjectContract;
import org.toryt.PackageContract;
import org.toryt.TorytException;


/**
 * @author Jan Dockx
 */
public class ProjectContract
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

  
  
  public ProjectContract(String projectName) {
    super(projectName);
  }

  public final Set getPackageContracts() {
    return Collections.unmodifiableSet($packageContracts);
  }
  
  /**
   * 
   * @pre    pc != null;
   * @throws TorytException
   *         isClosed();
   * @throws TorytException
   *         ! pc.isClosed();
   */
  public final void addPackageContract(PackageContract pc) throws TorytException {
    assert pc != null;
    if (isClosed()) {
      throw new TorytException(this, null);
    }
    if (! pc.isClosed()) {
      throw new TorytException(this, null);
    }
    $packageContracts.add(pc);
  }
  
  private Set $packageContracts = new HashSet();

}