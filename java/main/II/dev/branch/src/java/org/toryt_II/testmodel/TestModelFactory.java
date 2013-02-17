package org.toryt_II.testmodel;


import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.toryt.util_I.annotations.vcs.CvsInfo;



/**
 * A collection of methods that creates initialized
 * {@link TestModel} instances.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface TestModelFactory {

  /**
   * Create a {@link ConstructorTestModel} for <code>constructor</code>.
   *
   * The generic parameter <_Subject_> ensures at <em>compile time</em> that
   * the returned {@link ConstructorTestModel} is indeed for the same type
   * as the <code>constructor</code>.
   *
   * @pre constructor != null;
   * @throws TestModelCreationException
   *         Could not create the test model for some reason.
   */
  <_Subject_> ConstructorTestModel<_Subject_> createConstructorTestModel(Constructor<_Subject_> constructor)
      throws TestModelCreationException;

  /**
   * Create an {@link InstanceMutatorTestModel} for <code>instanceMutator</code>.
   *
   * @pre instanceMutator != null;
   * @pre Methods.methodKind(instanceMutator) == MethodKind.INSTANCE_MUTATOR;
   * @throws TestModelCreationException
   *         Could not create the test model for some reason.
   */
  InstanceMutatorTestModel createInstanceMutatorTestModel(Method instanceMutator)
      throws TestModelCreationException;

  /**
   * Create an {@link InstanceInspectorTestModel} for <code>instanceInspector</code>.
   *
   * @pre instanceInspector != null;
   * @pre Methods.methodKind(instanceInspector) == MethodKind.INSTANCE_INSPECTOR;
   * @throws TestModelCreationException
   *         Could not create the test model for some reason.
   */
  InstanceInspectorTestModel createInstanceInspectorTestModel(Method instanceInspector)
      throws TestModelCreationException;

  /**
   * Create an {@link ClassMutatorTestModel} for <code>classMutator</code>.
   *
   * @pre classMutator != null;
   * @pre Methods.methodKind(classMutator) == MethodKind.CLASS_MUTATOR;
   * @throws TestModelCreationException
   *         Could not create the test model for some reason.
   */
  ClassMutatorTestModel createClassMutatorTestModel(Method classMutator)
      throws TestModelCreationException;

  /**
   * Create an {@link ClassInspectorTestModel} for <code>classInspector</code>.
   *
   * @pre classInspector != null;
   * @pre Methods.methodKind(classInspector) == MethodKind.CLASS_INSPECTOR;
   * @throws TestModelCreationException
   *         Could not create the test model for some reason.
   */
  ClassInspectorTestModel createClassInspectorTestModel(Method classInspector)
      throws TestModelCreationException;

  /**
   * Create an {@link InnerClassTestModel} for <code>innerClazz</code>.
   * Using reflection, test models are added for all
   * <code><b>public</b></code> methods (that are not basic inspectors),
   * inner classes and static nested classes.
   *
   * The generic parameter <_Subject_> ensures at <em>compile time</em> that
   * the returned {@link InnerClassTestModel} is indeed for the type
   * <code>innerClazz</code>.
   *
   * @pre innerClazz != null;
   * @pre Classes.typeKind(innerClazz) == TypeKind.INNER;
   * @throws TestModelCreationException
   *         Could not create the test model for some reason.
   */
  <_Subject_> InnerClassTestModel<_Subject_> createInnerClassTestModel(Class<_Subject_>  innerClazz)
      throws TestModelCreationException;

  /**
   * Create a {@link StaticClassTestModel} for <code>clazz</code>.
   * Using reflection, test models are added for all
   * <code><b>public</b></code> methods (that are not basic inspectors),
   * inner classes and static nested classes.
   *
   * The generic parameter <_Subject_> ensures at <em>compile time</em> that
   * the returned {@link StaticClassTestModel} is indeed for the type
   * <code>clazz</code>.
   *
   * @pre clazz != null;
   * @pre Classes.typeKind(innerClazz) == TypeKind.STATIC;
   * @throws TestModelCreationException
   *         Could not create the test model for some reason.
   *
   * @idea This should be extended to include non-public members.
   */
  <_Subject_> StaticClassTestModel<_Subject_> createStaticClassTestModel(Class<_Subject_>  clazz)
      throws TestModelCreationException;

  /**
   * Create a {@link PackageTestModel} for <code>clazz</code>.
   * Inspecting the file system, test models are added for all
   * <code><b>public</b></code> classes and subpackages.
   *
   * @pre clazz != null;
   * @throws TestModelCreationException
   *         Could not create the test model for some reason.
   *
   * @idea This should be extended to include non-public members.
   * @todo use a classpath instead of single classDirectory
   */
  PackageTestModel createPackageTestModel(File classDirectory, String packageName)
      throws TestModelCreationException;

  /**
   * Create a {@link ProjectTestModel} for <code>sourceDirectory</code>.
   * Inspecting the file system,, test models are added for all
   * packages, i.e., subdirectories with at least 1 type defined in them.
   *
   * @pre sourceDirectory != null;
   * @throws TestModelCreationException
   *         Could not create the test model for some reason.
   *
   * @todo use a classpath instead of single classDirectory
   */
  ProjectTestModel createProjectTestModel(File classDirectory, String projectName)
      throws TestModelCreationException;

}