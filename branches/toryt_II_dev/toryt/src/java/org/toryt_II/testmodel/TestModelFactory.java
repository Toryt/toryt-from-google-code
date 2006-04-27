package org.toryt_II.testmodel;


import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.toryt.util_I.Reflection;
import org.toryt.util_I.Reflection.MethodKind;
import org.toryt.util_I.Reflection.TypeKind;



/**
 * A collection of methods that creates initialized
 * {@link TestModel} instances.
 *
 * @author Jan Dockx
 */
public interface TestModelFactory {

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
   * Create a {@link ConstructorTestModel} for <code>constructor</code>.
   *
   * @pre constructor != null;
   */
  ConstructorTestModel createConstructorTestModel(Constructor constructor);

  /**
   * Create an {@link InstanceMutatorTestModel} for <code>instanceMutator</code>.
   *
   * @pre instanceMutator != null;
   * @pre Reflection.methodKind(instanceMutator) == MethodKind.INSTANCE_MUTATOR;
   */
  InstanceMutatorTestModel createInstanceMutatorTestModel(Method instanceMutator);

  /**
   * Create an {@link InstanceInspectorTestModel} for <code>instanceInspector</code>.
   *
   * @pre instanceInspector != null;
   * @pre Reflection.methodKind(instanceInspector) == MethodKind.INSTANCE_INSPECTOR;
   */
  InstanceInspectorTestModel createInstanceInspectorTestModel(Method instanceInspector);

  /**
   * Create an {@link ClassMutatorTestModel} for <code>classMutator</code>.
   *
   * @pre classMutator != null;
   * @pre Reflection.methodKind(classMutator) == MethodKind.CLASS_MUTATOR;
   */
  ClassMutatorTestModel createClassMutatorTestModel(Method classMutator);

  /**
   * Create an {@link ClassInspectorTestModel} for <code>classInspector</code>.
   *
   * @pre classInspector != null;
   * @pre Reflection.methodKind(classInspector) == MethodKind.CLASS_INSPECTOR;
   */
  ClassInspectorTestModel createClassInspectorTestModel(Method classInspector);

//  /**
//   * Create a {@link MethodTestModel} for <code>method</code>, of the appropriate
//   * subtype ({@link InstanceMutatorTestModel}, {@link InstanceInspectorTestModel},
//   * {@link ClassMutatorTestModel} or {@link ClassInspectorTestModel}). This method
//   * delegates to {@link #createInstanceMutatorTestModel(Method)},
//   * {@link #createInstanceInspectorTestModel(Method)},
//   * {@link #createClassMutatorTestModel(Method)} and
//   * {@link #createClassInspectorTestModel(Method)} respectively.
//   *
//   * @pre method != null;
//   * @pre assert (method instanceof Constructor) || (method instanceof Method);
//   */
//  MethodTestModel createMethodTestModel(Method method);

  /**
   * Create an {@link InnerClassTestModel} for <code>innerClazz</code>.
   * Using reflection, test models are added for all
   * <code><b>public</b></code> methods (that are not basic inspectors),
   * inner classes and static nested classes.
   *
   * @pre innerClazz != null;
   * @pre Reflection.typeKind(innerClazz) == TypeKind.INNER;
   * @throws SecurityException
   *         Access to the reflection information was denied.
   *
   * @todo check whether static nested classes are possible for inner classes;
   *       otherwise, the {@link InnerClassTestModel} interface (and
   *       inheritance hierarchy) must change.
   */
  InnerClassTestModel createInnerClassTestModel(Class innerClazz) throws SecurityException;

  /**
   * Create a {@link StaticClassTestModel} for <code>clazz</code>.
   * Using reflection, test models are added for all
   * <code><b>public</b></code> methods (that are not basic inspectors),
   * inner classes and static nested classes.
   *
   * @pre clazz != null;
   * @pre Reflection.typeKind(innerClazz) == TypeKind.STATIC;
   * @throws SecurityException
   *         Access to the reflection information was denied.
   *
   * @idea This should be extended to include non-public members.
   */
  StaticClassTestModel createStaticClassTestModel(Class clazz) throws SecurityException;

  /**
   * Create a {@link PackageTestModel} for <code>clazz</code>.
   * Inspecting the file system, test models are added for all
   * <code><b>public</b></code> classes and subpackages.
   *
   * @pre clazz != null;
   * @throws SecurityException
   *         Access to the reflection information was denied.
   *
   * @idea This should be extended to include non-public members.
   * @todo use a classpath instead of single classDirectory
   */
  PackageTestModel createPackageTestModel(File classDirectory, String packageName)
      throws SecurityException;

  /**
   * Create a {@link ProjectTestModel} for <code>sourceDirectory</code>.
   * Inspecting the file system,, test models are added for all
   * packages, i.e., subdirectories with at least 1 type defined in them.
   *
   * @pre sourceDirectory != null;
   * @throws SecurityException
   *         Access to the reflection information was denied.
   *
   * @todo use a classpath instead of single classDirectory
   */
  ProjectTestModel createProjectTestModel(File classDirectory, String projectName)
      throws SecurityException;

}