package org.toryt;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author Jan Dockx
 */
public abstract class AbstractPackageContract
    extends AbstractContract
    implements PackageContract {

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

  protected AbstractPackageContract(Package p) {
    assert p != null;
    $package = p;
  }
  
  public final Package getPackage() {
    return $package;
  }
  
  private Package $package;
  
  public final List getMethodTests() throws TorytException {
    // MUDO optimize list?
    List result = new ArrayList();
    Iterator i = getClassContracts().iterator();
    while (i.hasNext()) {
      ClassContract pc = (ClassContract)i.next();
      result.addAll(pc.getMethodTests());
    }
    i = getSubPackageContracts().iterator();
    while (i.hasNext()) {
      PackageContract pc = (PackageContract)i.next();
      result.addAll(pc.getMethodTests());
    }
    return result;
  }
  
}