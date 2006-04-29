package org.toryt_II.testmodel;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;


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
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TestModelCollectionDelegate<_TestModelType_ extends TestModel> {

  /**
   * @pre compoundTestModel != null;
   */
  public TestModelCollectionDelegate(CompoundTestModel<?> compoundTestModel) {
    assert compoundTestModel != null;
    $compoundTestModel = compoundTestModel;
  }


  /*<property name="class test models">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init new.getTestModels().isEmpty();
   */
  public Set<_TestModelType_> getSet() {
    return Collections.unmodifiableSet($testModels);
  }

  /**
   * @mudo Remove this annoying dependency once we have events.
   * CTM should listen with events, and reset TFL in response
   * to events.
   *
   * @invar $compoundTestModel != null;
   */
  private final CompoundTestModel<?> $compoundTestModel;

  /**
   * @pre testModel != null;
   * @post new.getTestModels().contains(testModel);
   */
  public void add(_TestModelType_ testModel) {
    assert testModel != null;
    $testModels.add(testModel);
    $compoundTestModel.resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @post ! new.getTestModels().contains(testModel);
   */
  public void remove(_TestModelType_ testModel) {
    $testModels.remove(testModel);
    $compoundTestModel.resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @post new.getTestModels().isEmpty();
   */
  public void removeAll() {
    $testModels = new HashSet<_TestModelType_>();
    $compoundTestModel.resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @invar cC:noNull($testModels);
   */
  private Set<_TestModelType_> $testModels = new HashSet<_TestModelType_>();

  /*</property>*/

}