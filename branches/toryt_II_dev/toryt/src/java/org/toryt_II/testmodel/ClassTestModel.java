package org.toryt_II.testmodel;


import org.toryt.util_I.annotations.vcs.CvsInfo;
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
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class ClassTestModel<_TypeToTest_> extends CompoundTestModel<Class<_TypeToTest_>> {



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



  public void setSubject(Class<_TypeToTest_> subject) {
    assert (subject != null) ?
             (Reflection.typeKind(subject) == $typeKind) :
             true;
    super.setSubject(subject);
  }



  public final TestModelCollectionDelegate<ConstructorTestModel<?>> constructorTestModels =
    new TestModelCollectionDelegate<ConstructorTestModel<?>>(this);

  public final TestModelCollectionDelegate<InstanceMutatorTestModel> instanceMutatorTestModels =
    new TestModelCollectionDelegate<InstanceMutatorTestModel>(this);

  public final TestModelCollectionDelegate<InstanceInspectorTestModel> instanceInspectorTestModels =
    new TestModelCollectionDelegate<InstanceInspectorTestModel>(this);

  public final TestModelCollectionDelegate<ClassMutatorTestModel> classMutatorTestModels =
    new TestModelCollectionDelegate<ClassMutatorTestModel>(this);

  public final TestModelCollectionDelegate<ClassInspectorTestModel> classInspectorTestModels =
    new TestModelCollectionDelegate<ClassInspectorTestModel>(this);

  public final TestModelCollectionDelegate<InnerClassTestModel<?>> innerClassTestModels =
    new TestModelCollectionDelegate<InnerClassTestModel<?>>(this);

  {
    addTestModelCollectionDelegate("constructors", constructorTestModels);
    addTestModelCollectionDelegate("instance mutators", instanceMutatorTestModels);
    addTestModelCollectionDelegate("instance inspectors", instanceInspectorTestModels);
    addTestModelCollectionDelegate("class mutators", classMutatorTestModels);
    addTestModelCollectionDelegate("class inspectors", classInspectorTestModels);
    addTestModelCollectionDelegate("inner classes", innerClassTestModels);
  }

  protected String getSubjectDisplayNameSave() {
    return getSubject().getName();
  }

}