package org.toryt_II.testmodel;


import org.toryt.util_I.annotations.vcs.CvsInfo;


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
 *          Classes.typeKind(getClazz()) == getTypeKind();
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class ClassTestModel<_Subject_> extends CompoundTestModel<Class<_Subject_>> {



//  protected ClassTestModel(TypeKind typeKind) {
//    $typeKind = typeKind;
//  }
//
//
//
//  /*<property name="typeKind">*/
//  //------------------------------------------------------------------
//
//  public final TypeKind getTypeKind() {
//    return $typeKind;
//  }
//
//  private final TypeKind $typeKind;
//
//  /*</property>*/



//  @Override
//  public void setSubject(Class<_Subject_> subject) {
//    assert (subject != null) ?
//             (Classes.typeKind(subject) == $typeKind) :
//             true;
//    super.setSubject(subject);
//  }



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

  @Override
  protected String getSubjectDisplayNameSave() {
    return getSubject().getName();
  }

}