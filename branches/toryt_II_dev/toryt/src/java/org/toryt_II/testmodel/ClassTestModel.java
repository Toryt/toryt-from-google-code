package org.toryt_II.testmodel;

import org.toryt.util_I.reflect.Reflection;
import org.toryt.util_I.reflect.Reflection.TypeKind;


/**
 * <p>Instances represent a class to test. A <code>ClassTestModel</code>
 *   is an aggregate of
 *   {@link ConstructorTestModel ConstructorTestModels},
 *   {@link InstanceMutatorTestModel InstanceMutatorTestModels},
 *   {@link InstanceInspectorTestModel InstanceInspectorTestModels},
 *   and {@link ClassMutatorTestModel ClassMutatorTestModels}
 *   and {@link ClassInspectorTestModel ClassInspectorTestModels},
 *   and {@link InnerClassTestModel InnerClassTestModels} for inner classes.</p>
 *
 * @author Jan Dockx
 *
 * @invar constructorTestModels != null;
 * @invar instanceMutatorTestModels != null;
 * @invar instanceInspectorTestModels != null;
 * @invar classMutatorTestModels != null;
 * @invar classInspectorTestModels != null;
 * @invar innerClassTestModels != null;
 * @invar (getClazz() != null) ?
 *          Reflection.typeKind(getClazz()) == getTypeKind();
 */
public abstract class ClassTestModel extends CompoundTestModel {

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


  protected ClassTestModel(TypeKind typeKind) {
    $typeKind = typeKind;
  }



  /*<property name="typeKind">*/
  //------------------------------------------------------------------

  public final TypeKind getTypeKind() {
    return $typeKind;
  }

  private final TypeKind $typeKind;

  /*</property>*/



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
   * @pre (clazz != null) ? Reflection.typeKind(clazz) == getTypeKind();
   * @post new.getClass() == clazz;
   */
  public final void setClazz(Class clazz) {
    assert (clazz != null) ? (Reflection.typeKind(clazz) == getTypeKind()) : true;
    $clazz = clazz;
    // TODO events
  }

  private Class $clazz;

  /*</property>*/



  public final TestModelCollectionDelegate<ConstructorTestModel> constructorTestModels =
    new TestModelCollectionDelegate<ConstructorTestModel>(this);

  public final TestModelCollectionDelegate<InstanceMutatorTestModel> instanceMutatorTestModels =
    new TestModelCollectionDelegate<InstanceMutatorTestModel>(this);

  public final TestModelCollectionDelegate<InstanceInspectorTestModel> instanceInspectorTestModels =
    new TestModelCollectionDelegate<InstanceInspectorTestModel>(this);

  public final TestModelCollectionDelegate<ClassMutatorTestModel> classMutatorTestModels =
    new TestModelCollectionDelegate<ClassMutatorTestModel>(this);

  public final TestModelCollectionDelegate<ClassInspectorTestModel> classInspectorTestModels =
    new TestModelCollectionDelegate<ClassInspectorTestModel>(this);

  public final TestModelCollectionDelegate<InnerClassTestModel> innerClassTestModels =
    new TestModelCollectionDelegate<InnerClassTestModel>(this);

  {
    addTestModelCollectionDelegate("constructors", constructorTestModels);
    addTestModelCollectionDelegate("instance mutators", instanceMutatorTestModels);
    addTestModelCollectionDelegate("instance inspectors", instanceInspectorTestModels);
    addTestModelCollectionDelegate("class mutators", classMutatorTestModels);
    addTestModelCollectionDelegate("class inspectors", classInspectorTestModels);
    addTestModelCollectionDelegate("inner classes", innerClassTestModels);
  }

  public String toString() {
    return getClass().getName() + "[" + getClazz() + "]";
  }

  public String getDisplayName() {
    return getClazz().getName();
  }

}