package org.toryt_II.main;


import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.toryt_II.TestModel;
import org.toryt_II.contract.PackageContract;
import org.toryt_II.testmodel.ClassInspectorTestModel;
import org.toryt_II.testmodel.ClassMutatorTestModel;
import org.toryt_II.testmodel.ClassTestModel;
import org.toryt_II.testmodel.ConstructorTestModel;
import org.toryt_II.testmodel.InnerClassTestModel;
import org.toryt_II.testmodel.InstanceInspectorTestModel;
import org.toryt_II.testmodel.InstanceMutatorTestModel;
import org.toryt_II.testmodel.PackageTestModel;
import org.toryt_II.testmodel.ProjectTestModel;

import com.sun.rsasign.l;


/**
 * A collection of methods that creates initialized
 * {@link TestModel} instances via reflection or project inspection.
 *
 * @author Jan Dockx
 */
public class TestModelFactory {

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
   * Inspecting the source tree, test models are added for all
   * <code><b>public</b></code> classes and subpackages.
   *
   * @pre clazz != null;
   *
   * @idea This should be extended to include non-public members.
   */
  public PackageTestModel createPackageTestModel(File sourceDirectory, String packageName) {
    assert sourceDirectory != null;
    assert sourceDirectory.exists();
    assert sourceDirectory.isDirectory();
    assert packageName != null;
    PackageTestModel result = new PackageTestModel();
    Package pack = Package.getPackage(packageName);
      // will be null if there are no types in the directory
    result.setPackage(pack);
    File packageDirectory = new File(sourceDirectory, packageName.replace('.', '/'));
    assert packageDirectory.exists();
    assert packageDirectory.isDirectory();
    File[] packageContents = packageDirectory.listFiles(JAVA_SOURCE_FILTER);
    for (int i = 0; i < packageContents.length; i++) {
      String fileName = packageContents[i].getName();
      String simpleClassName = fileName.substring(0, fileName.lastIndexOf(JAVA_SOURCE_SUFFIX));
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
      result.addPackageTestModel(createPackageTestModel(sourceDirectory, subpackageName));
    }
    return result;
  }

  /**
   * Create a {@link ProjectTestModel} for <code>sourceDirectory</code>.
   * Inspecting the source tree, test models are added for all
   * packages, i.e., subdirectories with at least 1 type defined in them.
   *
   * @pre sourceDirectory != null;
   */
  public ProjectTestModel createProjectTestModel(File sourceDirectory, String projectName) {
    assert sourceDirectory != null;
    assert sourceDirectory.exists();
    assert sourceDirectory.isDirectory();
    ProjectTestModel result = new ProjectTestModel();
    result.setProjectName(projectName);
    checkForCandidatePackageDirs(sourceDirectory, "", sourceDirectory, result);
    return result;
  }

  private void checkForCandidatePackageDirs(File directory, String parentPackageName, File sourceDirectory, ProjectTestModel result) {
    File[] directoryContents = directory.listFiles(JAVA_SUBPACKAGE_FILTER);
    for (int i = 0; i < directoryContents.length; i++) {
      File packageCandidate = directoryContents[i];
      String packageCandidateName = parentPackageName + packageCandidate.getName();
      File[] packageCandidateContents = packageCandidate.listFiles(JAVA_SOURCE_FILTER);
      if (packageCandidateContents.length > 0) { // this is a package
        result.addPackageTestModel(createPackageTestModel(sourceDirectory, packageCandidateName));
      }
      else { // this is not a package; go deeper
        checkForCandidatePackageDirs(packageCandidate, packageCandidateName + ".", sourceDirectory, result);
      }
    }
  }

  private static String JAVA_SOURCE_SUFFIX = ".java";

  private static FileFilter JAVA_SOURCE_FILTER =
      new FileFilter() {

            public boolean accept(File file) {
              return file.exists() &&
                     file.canRead() &&
                     file.isFile() &&
                     file.getName().endsWith(JAVA_SOURCE_SUFFIX) &&
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