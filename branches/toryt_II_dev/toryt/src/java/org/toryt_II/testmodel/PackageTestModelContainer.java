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
 * @invar cC:instanceOf(getPackageTestModels(), PackageTestModel);
 */
public abstract class PackageTestModelContainer extends ClassTestModelContainer {

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
   * @return getChildTestModels();
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
    // MUDO events
  }

  /**
   * @post ! getPackageTestModels().contains(packageTestModel);
   */
  public void removePackageTestModel(PackageTestModel packageTestModel) {
    $packageTestModels.remove(packageTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @invar cC:noNull($packageTestModels);
   * @invar cC:instanceOf($packageTestModels, TestModel.class);
   */
  private Set $packageTestModels = new HashSet();

  /*</property>*/

}