package org.toryt_II.testmodel;


import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.toryt_II.TestModel;
import org.toryt_II.TestModelFactory;


/**
 * A collection of methods that creates initialized
 * {@link TestModel} instances via reflection or project inspection.
 *
 * @author Jan Dockx
 */
public class DefaultTestModelFactory implements TestModelFactory {

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
  public ConstructorTestModel createConstructorTestModel(Constructor constructor) {
    assert constructor != null;
    ConstructorTestModel result = new ConstructorTestModel();
    result.setConstructor(constructor);
    return result;
  }

  /**
   * @pre instanceMutator != null;
   */
  public InstanceMutatorTestModel createInstanceMutatorTestModel(Method instanceMutator) {
    assert instanceMutator != null;
    InstanceMutatorTestModel result = new InstanceMutatorTestModel();
    result.setInstanceMutator(instanceMutator);
    return result;
  }

  /**
   * @pre instanceInspector != null;
   */
  public InstanceInspectorTestModel createInstanceInspectorTestModel(Method instanceInspector) {
    assert instanceInspector != null;
    InstanceInspectorTestModel result = new InstanceInspectorTestModel();
    result.setInstanceInspector(instanceInspector);
    return result;
  }

  /**
   * @pre classMutator != null;
   */
  public ClassMutatorTestModel createClassMutatorTestModel(Method classMutator) {
    assert classMutator != null;
    ClassMutatorTestModel result = new ClassMutatorTestModel();
    result.setClassMutator(classMutator);
    return result;
  }

  /**
   * @pre classInspector != null;
   */
  public ClassInspectorTestModel createClassInspectorTestModel(Method classInspector) {
    assert classInspector != null;
    ClassInspectorTestModel result = new ClassInspectorTestModel();
    result.setClassInspector(classInspector);
    return result;
  }

  public MethodTestModel createMethodTestModel(Object method) {
    assert (method instanceof Constructor) || (method instanceof Method);
    if (method instanceof Constructor) {
      return createConstructorTestModel((Constructor)method);
    }
    else {
      Method m = (Method)method;
      if (Modifier.isStatic(m.getModifiers())) { // class
        if (m.getReturnType().equals(Void.TYPE)) { // mutator
          return createClassMutatorTestModel(m);
        }
        else { // inspector
          return createClassInspectorTestModel(m);
        }
      }
      else { // instance
        if (m.getReturnType().equals(Void.TYPE)) { // mutator
          return createInstanceMutatorTestModel(m);
        }
        else { // inspector
          return createInstanceInspectorTestModel(m);
        }
      }
    }
  }

  /**
   * @pre innerClazz != null;
   */
  public InnerClassTestModel createInnerClassTestModel(Class innerClazz) {
    assert innerClazz != null;
    InnerClassTestModel result = new InnerClassTestModel();
    result.setClazz(innerClazz);
    return result;
  }

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
  public ClassTestModel createClassTestModel(Class clazz) throws SecurityException {
    assert clazz != null;
    ClassTestModel result = new ClassTestModel();
    result.setClazz(clazz);
    addConstructors(clazz, result);
    addMethods(clazz, result);
    addNestedClasses(clazz, result);
    return result;
  }

  private void addConstructors(Class clazz, ClassTestModel result) throws SecurityException {
    Constructor[] constructors = clazz.getConstructors();
        // only public constructors
    for (int i = 0; i < constructors.length; i++) {
      result.addConstructorTestModel(createConstructorTestModel(constructors[i]));
    }
  }

  private void addMethods(Class clazz, ClassTestModel result) throws SecurityException {
    Method[] methods = clazz.getMethods();
        // only public methods, but also inherited
    for (int i = 0; i < methods.length; i++) {
      if (! Modifier.isStatic(methods[i].getModifiers())) { // instance
        if (methods[i].getReturnType() == Void.TYPE) { // mutator
          result.addInstanceMutatorTestModel(createInstanceMutatorTestModel(methods[i]));
        }
        else { // inspector
// MUDO remove basic inspectors
          result.addInstanceInspectorTestModel(createInstanceInspectorTestModel(methods[i]));
        }
      }
      else if (methods[i].getDeclaringClass() == clazz) { // class (static)
        // class methods only need to be tested in declaring class
        if (methods[i].getReturnType() == Void.TYPE) { // mutator
          result.addClassMutatorTestModel(createClassMutatorTestModel(methods[i]));
        }
        else { // inspector
// MUDO remove basic inspectors
          result.addClassInspectorTestModel(createClassInspectorTestModel(methods[i]));
        }
      }
    }
  }

  private void addNestedClasses(Class clazz, ClassTestModel result) throws SecurityException {
    Class[] clazzes = clazz.getClasses();
        // only public nested types, but also inherited and interfaces
    for (int i = 0; i < clazzes.length; i++) {
      if (! clazzes[i].isInterface()) { // interfaces cannot be tested
        if (! Modifier.isStatic(clazzes[i].getModifiers())) { // instance
          result.addInnerClassTestModel(createInnerClassTestModel(clazzes[i]));
        }
        else if (clazzes[i].getDeclaringClass() == clazz) { // class (static)
          // static nested classes only need to be added to the declaring class test model
          result.addClassTestModel(createClassTestModel(clazzes[i]));
        }
      }
    }
  }

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
  public PackageTestModel createPackageTestModel(File classDirectory, String packageName) {
    assert classDirectory != null;
    assert classDirectory.exists();
    assert classDirectory.isDirectory();
    assert packageName != null;
    PackageTestModel result = new PackageTestModel();
    result.setPackageName(packageName);
    File packageDirectory = new File(classDirectory, packageName.replace('.', '/'));
    assert packageDirectory.exists();
    assert packageDirectory.isDirectory();
    File[] packageContents = packageDirectory.listFiles(JAVA_CLASS_FILTER);
    for (int i = 0; i < packageContents.length; i++) {
      String fileName = packageContents[i].getName();
      String simpleClassName = fileName.substring(0, fileName.lastIndexOf(JAVA_CLASS_SUFFIX));
      Class clazz;
      try {
        clazz = Class.forName(packageName + "." + simpleClassName);
        result.addClassTestModel(createClassTestModel(clazz));
      }
      // MUDO more exceptions, and what to do??
      catch (ClassNotFoundException e) {
        assert false : "ClassNotFoundExceptionshould not happen: " + e;
      }
    }
    packageContents = packageDirectory.listFiles(JAVA_SUBPACKAGE_FILTER);
    for (int i = 0; i < packageContents.length; i++) {
      String subpackageName = packageName + "." + packageContents[i].getName();
      result.addPackageTestModel(createPackageTestModel(classDirectory, subpackageName));
    }
    return result;
  }

  /**
   * Create a {@link ProjectTestModel} for <code>sourceDirectory</code>.
   * Inspecting the class tree, test models are added for all
   * packages, i.e., subdirectories with at least 1 type defined in them.
   *
   * @pre sourceDirectory != null;
   * @todo use a classpath instead of single classDirectory
   */
  public ProjectTestModel createProjectTestModel(File classDirectory, String projectName) {
    assert classDirectory != null;
    assert classDirectory.exists();
    assert classDirectory.isDirectory();
    ProjectTestModel result = new ProjectTestModel();
    result.setProjectName(projectName);
    checkForCandidatePackageDirs(classDirectory, "", classDirectory, result);
    return result;
  }

  private void checkForCandidatePackageDirs(File directory, String parentPackageName, File classDirectory, ProjectTestModel result) {
    File[] directoryContents = directory.listFiles(JAVA_SUBPACKAGE_FILTER);
    for (int i = 0; i < directoryContents.length; i++) {
      File packageCandidate = directoryContents[i];
      String packageCandidateName = parentPackageName + packageCandidate.getName();
      File[] packageCandidateContents = packageCandidate.listFiles(JAVA_CLASS_FILTER);
      if (packageCandidateContents.length > 0) { // this is a package
        result.addPackageTestModel(createPackageTestModel(classDirectory, packageCandidateName));
      }
      else { // this is not a package; go deeper
        checkForCandidatePackageDirs(packageCandidate, packageCandidateName + ".", classDirectory, result);
      }
    }
  }

  private static String JAVA_CLASS_SUFFIX = ".class";

  private static char JAVA_NESTED_CLASS_SEPARATOR = '$';

  private static FileFilter JAVA_CLASS_FILTER =
      new FileFilter() {

            public boolean accept(File file) {
              return file.exists() &&
                     file.canRead() &&
                     file.isFile() &&
                     file.getName().endsWith(JAVA_CLASS_SUFFIX) &&
                     (file.getName().indexOf(JAVA_NESTED_CLASS_SEPARATOR) < 0) &&
                     ! file.getName().startsWith("_");
              // MUDO needs more exclusion; add grep filter
            }

          };

  private static FileFilter JAVA_SUBPACKAGE_FILTER =
      new FileFilter() {

            public boolean accept(File file) {
              return file.exists() &&
                     file.canRead() &&
                     file.isDirectory() &&
                     ! file.getName().equals("CVS");
              // MUDO needs more exclusion, for javadoc; add grep filter
            }

          };

}