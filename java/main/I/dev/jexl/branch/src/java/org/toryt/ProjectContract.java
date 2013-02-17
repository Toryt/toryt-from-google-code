package org.toryt;


import java.util.Set;

import org.toryt.support.straightlist.StraightList;


/**
 * The contract of a project. This is an aggregate of package contracts,
 * and extra tests.
 * 
 * @author Jan Dockx
 * 
 * @invar getProjectName() != null;
 * @invar getPackageContracts() != null;
 * @invar ! getPackageContracts().contains(null);
 * @invar (forall Object o; getPackageContracts().contains(o);
 *            o instanceof PackageContract);
 */
public interface ProjectContract extends Contract {

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
  
  
  String getProjectName();
  
  /**
   * @basic
   */
  Set getPackageContracts();
  
  /**
   * The union of the method tests of all packages.
   */
  StraightList getMethodTests() throws TorytException;

}