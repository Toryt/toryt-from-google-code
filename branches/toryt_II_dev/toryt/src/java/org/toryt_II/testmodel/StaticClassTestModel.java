package org.toryt_II.testmodel;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;




/**
 * Test model for top level and static nested classes.
 * Apart from the child test models offered by
 * {@link ClassTestModel}, static classes also can have
 * static nested classes
 * ({@link #getStaticNestedClassTestModels()}).
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getStaticNestedClassTestModels() != null;
 * @invar cC:noNull(getStaticNestedClassTestModels());
 * @invar cC:instanceOf(getStaticNestedClassTestModels(), StaticClassTestModel.class);
 */
public class StaticClassTestModel extends ClassTestModel {

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
   * @basic
   * @init new.getStaticNestedClassTestModels().isEmpty();
   */
  public Set getStaticNestedClassTestModels() {
    return Collections.unmodifiableSet($staticNestedClassTestModels);
  }

  /**
   * @pre classTestModel != null;
   * @post new.getStaticNestedClassTestModels().contains(classTestModel);
   */
  public void addClassTestModel(ClassTestModel classTestModel) {
    assert classTestModel != null;
    $staticNestedClassTestModels.add(classTestModel);
    resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @post ! new.getStaticNestedClassTestModels().contains(classTestModel);
   */
  public void removeClassTestModel(ClassTestModel classTestModel) {
    $staticNestedClassTestModels.remove(classTestModel);
    resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @post new.getStaticNestedClassTestModels().isEmpty();
   */
  public void removeAllStaticNestedClassTestModels() {
    $staticNestedClassTestModels = new HashSet();
    resetCachedTestFactoryList();
    // TODO events
  }

  /**
   * @invar cC:noNull($staticNestedClassTestModels);
   * @invar cC:instanceOf($staticNestedClassTestModels, StaticClassTestModel.class);
   */
  private Set $staticNestedClassTestModels = new HashSet();

  /*</property>*/

}