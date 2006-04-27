package org.toryt_II.testmodel;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;




/**
 * <p>A delegate to hold {@link TestModel test models} of a particular kind
 *   for a {@link CompoundTestModel}.</p>
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getTestModels() != null;
 * @invar cC:noNull(getTestModels());
 */
public class TestModelCollectionDelegate<TestModelType extends TestModel> {

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



  /**
   * @pre compoundTestModel != null;
   */
  public TestModelCollectionDelegate(CompoundTestModel compoundTestModel) {
    assert compoundTestModel != null;
    $compoundTestModel = compoundTestModel;
  }


  /*<property name="class test models">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init new.getTestModels().isEmpty();
   */
  public Set<TestModelType> getSet() {
    return Collections.unmodifiableSet($testModels);
  }

  /**
   * @mudo Remove this annoying dependency once we have events.
   * CTM should listen with events, and reset TFL in response
   * to events.
   *
   * @invar $compoundTestModel != null;
   */
  private final CompoundTestModel $compoundTestModel;

  /**
   * @pre testModel != null;
   * @post new.getTestModels().contains(testModel);
   */
  public void add(TestModelType testModel) {
    assert testModel != null;
    $testModels.add(testModel);
    $compoundTestModel.resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @post ! new.getTestModels().contains(testModel);
   */
  public void remove(TestModelType testModel) {
    $testModels.remove(testModel);
    $compoundTestModel.resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @post new.getTestModels().isEmpty();
   */
  public void removeAll() {
    $testModels = new HashSet<TestModelType>();
    $compoundTestModel.resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @invar cC:noNull($testModels);
   */
  private Set<TestModelType> $testModels = new HashSet<TestModelType>();

  /*</property>*/

}