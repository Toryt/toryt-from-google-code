package org.toryt;


import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.toryt.support.straightlist.ConcatStraightList;
import org.toryt.support.straightlist.StraightList;


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
  
  public final StraightList getMethodTests() throws TorytException {
    StraightList[] lists
        = new StraightList[getClassContracts().size() + getSubPackageContracts().size()];
    Iterator iter = getClassContracts().iterator();
    int i = 0;
    while (iter.hasNext()) {
      ClassContract cc = (ClassContract)iter.next();
      lists[i] = cc.getMethodTests();
      i++;
    }
    iter = getSubPackageContracts().iterator();
    while (iter.hasNext()) {
      PackageContract pc = (PackageContract)iter.next();
      lists[i] = pc.getMethodTests();
      i++;
    }
    return new ConcatStraightList(lists);
  }

  public final Set getSubContracts() {
    Set result = new HashSet();
    result.addAll(getSubPackageContracts());
    result.addAll(getClassContracts());
    return Collections.unmodifiableSet(result);
  }
  
}