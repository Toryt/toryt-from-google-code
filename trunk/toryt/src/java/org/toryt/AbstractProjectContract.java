package org.toryt;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/**
 * @author Jan Dockx
 */
public abstract class AbstractProjectContract
    extends AbstractContract
    implements ProjectContract {

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

  protected AbstractProjectContract(String projectName) {
    assert projectName != null;
    $projectName = projectName;
  }
  
  public final String getProjectName() {
    return $projectName;
  }
  
  private String $projectName;
  
  public final List getMethodTests() throws TorytException {
    // MUDO optimize list?
    List result = new ArrayList();
    Iterator i = getPackageContracts().iterator();
    while (i.hasNext()) {
      PackageContract pc = (PackageContract)i.next();
      result.addAll(pc.getMethodTests());
    }
    return result;
  }
  
}