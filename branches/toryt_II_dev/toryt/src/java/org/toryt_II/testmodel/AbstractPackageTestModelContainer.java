package org.toryt_II.testmodel;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * A model of a project entity that contains packages. This is an
 * abstract class that generalizes some code for subclasses.
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getPackageTestModels() != null;
 * @invar cC:noNull(getPackageTestModels());
 * @invar cC:instanceOf(getPackageTestModels(), PackageTestModel.class);
 */
public abstract class AbstractPackageTestModelContainer extends CompoundTestModel {

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



  /*<property name="package test models">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init new.getPackageTestModels().isEmpty();
   */
  public Set getPackageTestModels() {
    return Collections.unmodifiableSet($packageTestModels);
  }

  /**
   * @pre packageTestModel != null;
   * @post getPackageTestModels().contains(packageTestModel);
   */
  public void addPackageTestModel(PackageTestModel packageTestModel) {
    assert packageTestModel != null;
    $packageTestModels.add(packageTestModel);
    resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @post ! getPackageTestModels().contains(packageTestModel);
   */
  public void removePackageTestModel(PackageTestModel packageTestModel) {
    $packageTestModels.remove(packageTestModel);
    resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @post new.getPackageTestModels().isEmpty();
   */
  public void removeAllPackageTestModels() {
    $packageTestModels = new HashSet();
    resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @invar cC:noNull($packageTestModels);
   * @invar cC:instanceOf($packageTestModels, PackageTestModel.class);
   */
  private Set $packageTestModels = new HashSet();

  /*</property>*/

}