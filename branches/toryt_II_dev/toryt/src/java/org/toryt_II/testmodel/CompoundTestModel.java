package org.toryt_II.testmodel;


import java.util.Iterator;
import java.util.Set;

import org.toryt.util_I.collections.priorityList.PriorityList;
import org.toryt.util_I.collections.priorityList.algebra.UnionPriorityList;


/**
 * <p>{@link TestModel} that is a compound of
 *   {@link #getChildTestModels child test models}.
 *   The {@link #getTestFactoryList()}
 *   is the union of the test factory lists of the children, and
 *   possibly extra test factories added here.</p>
 * <p>Subclasses should introduce methods to add and remove
 *   test models for different kinds of child test models. For each
 *   kind, there should be a basic inspector to return the set of
 *   test models of that kind, and a mutator to add and remove
 *   a test model of that kind, and a method to remave all
 *   test models of that kind. After construction, the set of child
 *   test models of any kind should be empty.</p>
 * @note This is not an interface, but an abstract
 *   class, because we can implement {@link #getTestFactoryList()}
 *   once and for all here. If it turns out that this is a limitation,
 *   e.g., because multiple polymorphism is needed at some point,
 *   we might separate an interface from the implementation.
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getChildTestModels() != null;
 * @invar cC:noNull(getChildTestModels());
 * @invar cC:instanceOf(getChildTestModels(), TestModel);
 */
public abstract class CompoundTestModel extends AbstractTestModel {

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



  /*<property name="child test models">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public abstract Set getChildTestModels();

  /*</property>*/



  /*<property name="test factory list">*/
  //------------------------------------------------------------------

  /**
   * The union of the test factory lists of all children, and possibly
   * more test factories added here.
   *
   * @protected This method creates a UnionPriorityList lazily from the
   *            {@link TestModel#getTestFactoryList()} of all
   *            {@link #getChildTestModels()}. The union is remade when
   *            child test models are added or removed.
   */
  public final PriorityList getTestFactoryList() {
    if ($union != null) {
      $union = createUnion();
    }
    return $union;
  }

  /**
   * @basic
   */
  public final UnionPriorityList getCachedTestFactoryList() {
    return $union;
  }

  /**
   * @post new.getCachedTestFactoryList() == null;
   */
  protected final void resetCachedTestFactoryList() {
    $union = null;
    // TODO events
  }

  private UnionPriorityList createUnion() {
    PriorityList[] childTestFactoryLists = new PriorityList[getChildTestModels().size()];
    int i = 0;
    Iterator iter = getChildTestModels().iterator();
    while (iter.hasNext()) {
      TestModel childTestModel = (TestModel)iter.next();
      childTestFactoryLists[i] = childTestModel.getTestFactoryList();
      i++;
    }
    return new UnionPriorityList(childTestFactoryLists);
  }

  /**
   * @invar ($union != null) ? $union.getElementType() =- TestFactory.class;
   */
  private UnionPriorityList $union;

  /*</property>*/

}