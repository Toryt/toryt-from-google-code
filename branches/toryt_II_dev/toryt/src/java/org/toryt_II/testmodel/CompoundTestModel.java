package org.toryt_II.testmodel;


import java.util.Iterator;
import java.util.Set;

import org.toryt.util_I.priorityList.PriorityList;
import org.toryt.util_I.priorityList.UnionPriorityList;
import org.toryt_II.TestModel;


/**
 * {@link TestModel} that is a compound of
 * {@link #getChildTestModels child test models}.
 * The {@link #getTestFactoryList()}
 * is the union of the test factory lists of the children, and
 * possibly extra test factories added here.
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getChildTestModels() != null;
 * @invar cC:noNull(getChildTestModels());
 * @invar cC:instanceOf(getChildTestModels(), TestModel);
 * @invar cC:noNull(getPackageTestModels());
 * @invar cC:instanceOf(getPackageTestModels(), PackageTestModel);
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