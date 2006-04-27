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
public abstract class AbstractClassTestModelContainer extends CompoundTestModel {

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

  /* duplicated code due to single inheritance
   * used in StaticClassTestModel and PackageTestModel
   */

  /**
   * @basic
   * @init new.getClassTestModels().isEmpty();
   */
  public Set getClassTestModels() {
    return Collections.unmodifiableSet($classTestModels);
  }

  /**
   * @pre packageTestModel != null;
   * @post new.getClassTestModels().contains(classTestModel);
   */
  public void addClassTestModel(ClassTestModel classTestModel) {
    assert classTestModel != null;
    $classTestModels.add(classTestModel);
    resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @post ! new.getClassTestModels().contains(classTestModel);
   */
  public void removeClassTestModel(ClassTestModel classTestModel) {
    $classTestModels.remove(classTestModel);
    resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @post new.getClassTestModels().isEmpty();
   */
  public void removeAllClassTestModels() {
    $classTestModels = new HashSet();
    resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @invar cC:noNull($classTestModels);
   * @invar cC:instanceOf($classTestModels, TestModel.class);
   */
  private Set $classTestModels = new HashSet();

  /*</property>*/

}