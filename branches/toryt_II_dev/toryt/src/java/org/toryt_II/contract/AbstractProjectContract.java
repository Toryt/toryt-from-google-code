package org.toryt_II.contract;


import java.util.Iterator;
import java.util.Set;

import org.toryt.support.straightlist.ConcatStraightList;
import org.toryt.support.straightlist.StraightList;
import org.toryt_II.AbstractContract;
import org.toryt_II.TorytException;



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
  
  public final StraightList getMethodTests() throws TorytException {
    StraightList[] lists = new StraightList[getPackageContracts().size()];
    Iterator iter = getPackageContracts().iterator();
    int i = 0;
    while (iter.hasNext()) {
      PackageContract pc = (PackageContract)iter.next();
      lists[i] = pc.getMethodTests();
      i++;
    }
    return new ConcatStraightList(lists);
  }
  
  public final Set getSubContracts() {
    return getPackageContracts();
  }

}