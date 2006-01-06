package org.toryt_II;


import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.toryt_II.testmodel.ClassInspectorTestModel;
import org.toryt_II.testmodel.ClassMutatorTestModel;
import org.toryt_II.testmodel.ClassTestModel;
import org.toryt_II.testmodel.ConstructorTestModel;
import org.toryt_II.testmodel.InnerClassTestModel;
import org.toryt_II.testmodel.InstanceInspectorTestModel;
import org.toryt_II.testmodel.InstanceMutatorTestModel;
import org.toryt_II.testmodel.MethodTestModel;
import org.toryt_II.testmodel.PackageTestModel;
import org.toryt_II.testmodel.ProjectTestModel;


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
   * @pre constructor != null;
   */
  ConstructorTestModel createConstructorTestModel(Constructor constructor);

  /**
   * @pre instanceMutator != null;
   */
  InstanceMutatorTestModel createInstanceMutatorTestModel(Method instanceMutator);

  /**
   * @pre instanceInspector != null;
   */
  InstanceInspectorTestModel createInstanceInspectorTestModel(Method instanceInspector);

  /**
   * @pre classMutator != null;
   */
  ClassMutatorTestModel createClassMutatorTestModel(Method classMutator);

  /**
   * @pre classInspector != null;
   */
  ClassInspectorTestModel createClassInspectorTestModel(Method classInspector);

  MethodTestModel createMethodTestModel(Object method);

  /**
   * @pre innerClazz != null;
   */
  InnerClassTestModel createInnerClassTestModel(Class innerClazz);

  /**
   * Create a {@link ClassTestModel} for <code>clazz</code>.
   * Using reflection, method test models are added for all
   * <code><b>public</b></code> methods (that are not basic inspectors),
   * inner classes and static nested classes.
   *
   * @pre clazz != null;
   * @throws SecurityException
   *         Access to the reflection information was denied.
   *
   * @idea This should be extended to include non-public members.
   */
  ClassTestModel createClassTestModel(Class clazz) throws SecurityException;

  /**
   * Create a {@link PackageTestModel} for <code>clazz</code>.
   * Inspecting the class tree, test models are added for all
   * <code><b>public</b></code> classes and subpackages.
   *
   * @pre clazz != null;
   *
   * @idea This should be extended to include non-public members.
   * @todo use a classpath instead of single classDirectory
   */
  PackageTestModel createPackageTestModel(File classDirectory, String packageName);

  /**
   * Create a {@link ProjectTestModel} for <code>sourceDirectory</code>.
   * Inspecting the class tree, test models are added for all
   * packages, i.e., subdirectories with at least 1 type defined in them.
   *
   * @pre sourceDirectory != null;
   * @todo use a classpath instead of single classDirectory
   */
  ProjectTestModel createProjectTestModel(File classDirectory, String projectName);

}