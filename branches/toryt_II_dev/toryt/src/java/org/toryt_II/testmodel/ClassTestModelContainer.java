package org.toryt_II.testmodel;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * A model of a project entity that contains classes. This is an
 * abstract class that generalizes some code for subclasses.
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getClassTestModels() != null;
 * @invar cC:noNull(getClassTestModels());
 * @invar cC:instanceOf(getClassTestModels(), ClassTestModel);
 */
public abstract class ClassTestModelContainer extends CompoundTestModel {

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


  /*<property name="class test models">*/
  //------------------------------------------------------------------

  /**
   *
   */
  public Set getClassTestModels() {
    return Collections.unmodifiableSet($classTestModels);
  }

  /**
   * @pre packageTestModel != null;
   * @post getPackageTestModels().contains(packageTestModel);
   */
  public void addClassTestModel(ClassTestModel classTestModel) {
    assert classTestModel != null;
    $classTestModels.add(classTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @post ! getPackageTestModels().contains(packageTestModel);
   */
  public void removeClassTestModel(ClassTestModel classTestModel) {
    $classTestModels.remove(classTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @invar cC:noNull($classTestModels);
   * @invar cC:instanceOf($classTestModels, TestModel.class);
   */
  private Set $classTestModels = new HashSet();

  /*</property>*/

}