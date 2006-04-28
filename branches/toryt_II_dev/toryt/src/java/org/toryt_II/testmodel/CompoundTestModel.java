package org.toryt_II.testmodel;


import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.PriorityList;
import org.toryt.util_I.collections.priorityList.algebra.UnionPriorityList;


/**
 * <p>{@link TestModel} that is a compound of
 *   {@link #getChildTestModels child test models}.
 *   The {@link #getTestFactoryList()}
 *   is the union of the test factory lists of the children, and
 *   possibly extra test factories added here.</p>
 * <p>Subclasses should define a {@link TestModelCollectionDelegate}
 *   for each kind of child test model, using public delegation.
 *   Each of these delegates should be added
 *   using {@link #addTestModelCollectionDelegate(String, TestModelCollectionDelegate)}.</p>
 *
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
 * @invar getTestModelCollectionDelegates() != null;
 * @invar Collections.noNull(getTestModelCollectionDelegates());
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class CompoundTestModel extends AbstractTestModel {

  /*<property name="child test models">*/
  //------------------------------------------------------------------

  /**
   * @return Collections.union([$testModelCollectionDelegates.values()].getTestModels());
   */
  public final Set<TestModel> getChildTestModels() {
    Set<TestModel> result = new HashSet<TestModel>();
    for (TestModelCollectionDelegate<? extends TestModel> delegate : $testModelCollectionDelegates.values()) {
      result.addAll(delegate.getSet());
    }
    return Collections.unmodifiableSet(result);
  }


  protected final void addTestModelCollectionDelegate(String kind, TestModelCollectionDelegate<? extends TestModel> delegate) {
    $testModelCollectionDelegates.put(kind, delegate);
  }

  /**
   * @invar $testModelCollectionDelegates != null;
   * @invar Collections.noNull($testModelCollectionDelegates);
   */
  private Map<String, TestModelCollectionDelegate<? extends TestModel>> $testModelCollectionDelegates =
      new LinkedHashMap<String, TestModelCollectionDelegate<? extends TestModel>>();

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
  final void resetCachedTestFactoryList() {
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
    return null; //new UnionPriorityList(childTestFactoryLists);
  }

  /**
   * @invar ($union != null) ? $union.getElementType() =- TestFactory.class;
   */
  private UnionPriorityList $union;

  /*</property>*/


  protected abstract String getDisplayName();

  final void printStructure(IndentPrinter out) {
    assert out != null;
    out.println(getDisplayName());
    IndentPrinter sections = new IndentPrinter(out, $testModelCollectionDelegates.size());
    for (Map.Entry<String, TestModelCollectionDelegate<? extends TestModel>> entry:
          $testModelCollectionDelegates.entrySet()) {
      sections.printChildren(entry.getKey() + ": ", entry.getValue().getSet());
    }
  }

}