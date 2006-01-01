package org.toryt_II.testmodel;


import java.io.PrintStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.toryt.support.priorityList.PriorityList;
import org.toryt_II.testmodel.AbstractTestModel.IndentPrinter;


/**
 * Instances represent a class to test. A <code>ClassTestModel</code>
 * is an aggregate of
 * {@link #getConstructorTestModels() ConstructorTestModels},
 * {@link #getInstanceMutatorTestModels() InstanceMutatorTestModels},
 * {@link #getInstanceInspectorTestModels() InstanceInspectorTestModels},
 * and {@link #getClassMutatorTestModels() ClassMutatorTestModels}
 * and {@link #getClassInspectorTestModels() ClassInspectorTestModels},
 * and {@link #getInnerClassTestModels() InnerClassTestModels} for inner classes
 * and {@link #getClassTestModels() ClassTestModels}
 * for static nested classes.
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt_II.contract.Collections;
 * @invar getClassTestModels() != null;
 * @invar cC:noNull(getClassTestModels());
 * @invar cC:instanceOf(getClassTestModels(), ClassTestModel);
 */
public class ClassTestModel extends ClassTestModelContainer {

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


  /*<property name="clazz">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init null;
   */
  public final Class getClazz() {
    return $clazz;
  }

  /**
   * @post new.getClass() == clazz;
   */
  public final void setClazz(Class clazz) {
    $clazz = clazz;
  }

  private Class $clazz;

  /*</property>*/



  /*<property name="child test models">*/
  //------------------------------------------------------------------

  /**
   * @return getPackageTestModels();
   */
  public Set getChildTestModels() {
    Set result = new HashSet();
    result.addAll($constructorTestModels);
    result.addAll($instanceMutatorTestModels);
    result.addAll($instanceInspectorTestModels);
    result.addAll($classMutatorTestModels);
    result.addAll(getClassTestModels());
    result.addAll($innerClassTestModels);
    return Collections.unmodifiableSet(result);
  }

  /*</property>*/



  /*<property name="constructor test models">*/
  //------------------------------------------------------------------

  /**
   *
   */
  public Set getConstructorTestModels() {
    return Collections.unmodifiableSet($constructorTestModels);
  }

  /**
   * @pre constructorTestModel != null;
   * @post new.getConstructorTestModels().contains(constructorTestModel);
   */
  public void addConstructorTestModel(ConstructorTestModel constructorTestModel) {
    assert constructorTestModel != null;
    $constructorTestModels.add(constructorTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @post ! new.getConstructorTestModels().contains(getConstructorTestModels);
   */
  public void removeConstructorTestModel(ConstructorTestModel constructorTestModel) {
    $constructorTestModels.remove(constructorTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @invar cC:noNull($constructorTestModels);
   * @invar cC:instanceOf($constructorTestModels, TestModel.class);
   */
  private Set $constructorTestModels = new HashSet();

  /*</property>*/



  /*<property name="instance mutator test models">*/
  //------------------------------------------------------------------

  /**
   *
   */
  public Set getInstanceMutatorTestModels() {
    return Collections.unmodifiableSet($instanceMutatorTestModels);
  }

  /**
   * @pre instanceMutatorTestModel != null;
   * @post new.getInstanceMutatorTestModels().contains(instanceMutatorTestModel);
   */
  public void addInstanceMutatorTestModel(InstanceMutatorTestModel instanceMutatorTestModel) {
    assert instanceMutatorTestModel != null;
    $instanceMutatorTestModels.add(instanceMutatorTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @post ! new.getInstanceMutatorTestModels().contains(getInstanceMutatorTestModels);
   */
  public void removeInstanceMutatorTestModel(InstanceMutatorTestModel instanceMutatorTestModel) {
    $instanceMutatorTestModels.remove(instanceMutatorTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @invar cC:noNull($instanceMutatorTestModels);
   * @invar cC:instanceOf($instanceMutatorTestModels, TestModel.class);
   */
  private Set $instanceMutatorTestModels = new HashSet();

  /*</property>*/



  /*<property name="instance inspector test models">*/
  //------------------------------------------------------------------

  /**
   *
   */
  public Set getInstanceInspectorTestModels() {
    return Collections.unmodifiableSet($instanceInspectorTestModels);
  }

  /**
   * @pre instanceInspectorTestModel != null;
   * @post new.getInstanceInspectorTestModels().contains(instanceInspectorTestModel);
   */
  public void addInstanceInspectorTestModel(InstanceInspectorTestModel instanceInspectorTestModel) {
    assert instanceInspectorTestModel != null;
    $instanceInspectorTestModels.add(instanceInspectorTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @post ! new.getInstanceInspectorTestModels().contains(getInstanceInspectorTestModels);
   */
  public void removeInstanceInspectorTestModel(InstanceInspectorTestModel instanceInspectorTestModel) {
    $instanceInspectorTestModels.remove(instanceInspectorTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @invar cC:noNull($instanceInspectorTestModels);
   * @invar cC:instanceOf($instanceInspectorTestModels, TestModel.class);
   */
  private Set $instanceInspectorTestModels = new HashSet();

  /*</property>*/



  /*<property name="class mutator test models">*/
  //------------------------------------------------------------------

  /**
   *
   */
  public Set getClassMutatorTestModels() {
    return Collections.unmodifiableSet($classMutatorTestModels);
  }

  /**
   * @pre classMutatorTestModel != null;
   * @post new.getClassMutatorTestModels().contains(classMutatorTestModel);
   */
  public void addClassMutatorTestModel(ClassMutatorTestModel classMutatorTestModel) {
    assert classMutatorTestModel != null;
    $classMutatorTestModels.add(classMutatorTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @post ! new.getClassMutatorTestModels().contains(getClassMutatorTestModels);
   */
  public void removeClassMutatorTestModel(ClassMutatorTestModel classMutatorTestModel) {
    $classMutatorTestModels.remove(classMutatorTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @invar cC:noNull($classMutatorTestModels);
   * @invar cC:instanceOf($classMutatorTestModels, TestModel.class);
   */
  private Set $classMutatorTestModels = new HashSet();

  /*</property>*/



  /*<property name="class inspector test models">*/
  //------------------------------------------------------------------

  /**
   *
   */
  public Set getClassInspectorTestModels() {
    return Collections.unmodifiableSet($classInspectorTestModels);
  }

  /**
   * @pre classInspectorTestModel != null;
   * @post new.getClassInspectorTestModels().contains(classInspectorTestModel);
   */
  public void addClassInspectorTestModel(ClassInspectorTestModel classInspectorTestModel) {
    assert classInspectorTestModel != null;
    $classInspectorTestModels.add(classInspectorTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @post ! new.getClassInspectorTestModels().contains(getClassInspectorTestModels);
   */
  public void removeClassInspectorTestModel(ClassInspectorTestModel classInspectorTestModel) {
    $classInspectorTestModels.remove(classInspectorTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @invar cC:noNull($classInspectorTestModels);
   * @invar cC:instanceOf($classInspectorTestModels, TestModel.class);
   */
  private Set $classInspectorTestModels = new HashSet();

  /*</property>*/



  /*<property name="inner clazz test models">*/
  //------------------------------------------------------------------

  /**
   *
   */
  public Set getInnerClassTestModels() {
    return Collections.unmodifiableSet($innerClassTestModels);
  }

  /**
   * @pre innerClassTestModel != null;
   * @post new.getInnerClassTestModels().contains(innerClassTestModel);
   */
  public void addInnerClassTestModel(InnerClassTestModel innerClassTestModel) {
    assert innerClassTestModel != null;
    $innerClassTestModels.add(innerClassTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @post ! new.getInnerClassTestModels().contains(getInnerClassTestModels);
   */
  public void removeInnerClassTestModel(InnerClassTestModel innerClassTestModel) {
    $innerClassTestModels.remove(innerClassTestModel);
    resetCachedTestFactoryList();
    // MUDO events
  }

  /**
   * @invar cC:noNull($innerClassTestModels);
   * @invar cC:instanceOf($innerClassTestModels, TestModel.class);
   */
  private Set $innerClassTestModels = new HashSet();

  /*</property>*/



  public String toString() {
    return getClass().getName() + "[" + getClazz() + "]";
  }

  void printStructure(IndentPrinter out) {
    assert out != null;
    out.println((getClazz() == null) ? "null" : getClazz().getName());
    IndentPrinter sections = new IndentPrinter(out, 7);
    sections.printChildren("constructors:", getConstructorTestModels());
    sections.printChildren("instance mutators:", getInstanceMutatorTestModels());
    sections.printChildren("instance inspectors:", getInstanceInspectorTestModels());
    sections.printChildren("class mutators:", getClassMutatorTestModels());
    sections.printChildren("class inspectors:", getClassInspectorTestModels());
    sections.printChildren("inner classes:", getInnerClassTestModels());
    sections.printChildren("static nested classes:", getClassTestModels());
  }

}