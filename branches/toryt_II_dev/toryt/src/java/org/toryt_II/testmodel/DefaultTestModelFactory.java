package org.toryt_II.testmodel;


import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.toryt.util_I.Reflection;
import org.toryt.util_I.Reflection.MethodKind;
import org.toryt.util_I.Reflection.TypeKind;


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



  public ConstructorTestModel createConstructorTestModel(Constructor constructor) {
    assert constructor != null;
    ConstructorTestModel result = new ConstructorTestModel();
    result.setConstructor(constructor);
    return result;
  }

  public InstanceMutatorTestModel createInstanceMutatorTestModel(Method instanceMutator) {
    assert instanceMutator != null;
    assert Reflection.methodKind(instanceMutator) == MethodKind.INSTANCE_MUTATOR;
    InstanceMutatorTestModel result = new InstanceMutatorTestModel();
    result.setInstanceMutator(instanceMutator);
    return result;
  }

  public InstanceInspectorTestModel createInstanceInspectorTestModel(Method instanceInspector) {
    assert instanceInspector != null;
    assert Reflection.methodKind(instanceInspector) == MethodKind.INSTANCE_INSPECTOR;
    InstanceInspectorTestModel result = new InstanceInspectorTestModel();
    result.setInstanceInspector(instanceInspector);
    return result;
  }

  public ClassMutatorTestModel createClassMutatorTestModel(Method classMutator) {
    assert classMutator != null;
    assert Reflection.methodKind(classMutator) == MethodKind.CLASS_MUTATOR;
    ClassMutatorTestModel result = new ClassMutatorTestModel();
    result.setClassMutator(classMutator);
    return result;
  }

  public ClassInspectorTestModel createClassInspectorTestModel(Method classInspector) {
    assert classInspector != null;
    assert Reflection.methodKind(classInspector) == MethodKind.CLASS_INSPECTOR;
    ClassInspectorTestModel result = new ClassInspectorTestModel();
    result.setClassInspector(classInspector);
    return result;
  }

// public MethodTestModel createMethodTestModel(Method method) {
//    assert method != null;
//    switch (Reflection.methodKind(method)) {
//      case CLASS_MUTATOR:
//        return createClassMutatorTestModel(method);
//      case CLASS_INSPECTOR:
//        return createClassInspectorTestModel(method);
//      case INSTANCE_MUTATOR:
//        return createInstanceMutatorTestModel(method);
//      case INSTANCE_INSPECTOR:
//        return createInstanceInspectorTestModel(method);
//      default:
//        assert false;
//        return null; // keep compiler happy
//    }
//  }

  public InnerClassTestModel createInnerClassTestModel(Class innerClazz) throws SecurityException {
    assert innerClazz != null;
    assert Reflection.typeKind(innerClazz) == TypeKind.INNER;
    InnerClassTestModel result = new InnerClassTestModel();
    initClassTestModel(innerClazz, result);
    return result;
  }

  public StaticClassTestModel createStaticClassTestModel(Class clazz) throws SecurityException {
    assert clazz != null;
    assert Reflection.typeKind(clazz) == TypeKind.STATIC;
    StaticClassTestModel result = new StaticClassTestModel();
    initClassTestModel(clazz, result);
    return result;
  }

  private void initClassTestModel(Class clazz, ClassTestModel result) throws SecurityException {
    result.setClazz(clazz);
    addConstructors(clazz, result);
    addMethods(clazz, result);
    addNestedClasses(clazz, result);
  }

  private void addConstructors(Class clazz, ClassTestModel result) throws SecurityException {
    Constructor[] constructors = clazz.getConstructors();
        // only public constructors
    for (int i = 0; i < constructors.length; i++) {
      result.constructorTestModels.add(createConstructorTestModel(constructors[i]));
    }
  }

  private void addMethods(Class clazz, ClassTestModel result) throws SecurityException {
    Method[] methods = clazz.getMethods();
        // only public methods, but also inherited
    for (int i = 0; i < methods.length; i++) {
      switch (Reflection.methodKind(methods[i])) {
        case INSTANCE_MUTATOR:
          result.instanceMutatorTestModels.add(createInstanceMutatorTestModel(methods[i]));
          break;
        case INSTANCE_INSPECTOR:
          // MUDO remove basic inspectors
          result.instanceInspectorTestModels.add(createInstanceInspectorTestModel(methods[i]));
          break;
        case CLASS_MUTATOR:
          result.classMutatorTestModels.add(createClassMutatorTestModel(methods[i]));
          break;
        case CLASS_INSPECTOR:
          // MUDO remove basic inspectors
          result.classInspectorTestModels.add(createClassInspectorTestModel(methods[i]));
          break;
        default:
          assert false;
      }
    }
  }

  private void addNestedClasses(Class clazz, ClassTestModel result) throws SecurityException {
    Class[] clazzes = clazz.getClasses();
        // only public nested types, but also inherited and interfaces
    for (int i = 0; i < clazzes.length; i++) {
      if (! clazzes[i].isInterface()) { // interfaces cannot be tested
        switch (Reflection.typeKind(clazz)) {
          case INNER:
            result.innerClassTestModels.add(createInnerClassTestModel(clazzes[i]));
            break;
          case STATIC:
            if (clazzes[i].getDeclaringClass() == clazz) {
              // static nested classes only need to be added to the declaring class test model
              try {
                ((StaticClassTestModel)result).staticNestedClassTestModels.add(createStaticClassTestModel(clazzes[i]));
              }
              catch (ClassCastException ccExc) {
                // static nested classses can only be nested in static classes
                assert false : "static nested classses can only be nested in static classes";
              }
            }
            break;
          default:
            assert false;
        }
      }
    }
  }

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
        result.classTestModels.add(createStaticClassTestModel(clazz));
      }
      // MUDO more exceptions, and what to do??
      catch (ClassNotFoundException e) {
        assert false : "ClassNotFoundExceptionshould not happen: " + e;
      }
    }
    packageContents = packageDirectory.listFiles(JAVA_SUBPACKAGE_FILTER);
    for (int i = 0; i < packageContents.length; i++) {
      String subpackageName = packageName + "." + packageContents[i].getName();
      result.packageTestModels.add(createPackageTestModel(classDirectory, subpackageName));
    }
    return result;
  }

  public ProjectTestModel createProjectTestModel(File classDirectory, String projectName) {
    assert classDirectory != null;
    assert classDirectory.exists();
    assert classDirectory.isDirectory();
    ProjectTestModel result = new ProjectTestModel();
    result.setProjectName(projectName);
    checkForCandidatePackageDirs(classDirectory, "", classDirectory, result);
    return result;
  }

  private void checkForCandidatePackageDirs(File directory,
                                            String parentPackageName,
                                            File classDirectory,
                                            ProjectTestModel result) {
    File[] directoryContents = directory.listFiles(JAVA_SUBPACKAGE_FILTER);
    for (int i = 0; i < directoryContents.length; i++) {
      File packageCandidate = directoryContents[i];
      String packageCandidateName = parentPackageName + packageCandidate.getName();
      File[] packageCandidateContents = packageCandidate.listFiles(JAVA_CLASS_FILTER);
      if (packageCandidateContents.length > 0) { // this is a package
        result.packageTestModels.add(createPackageTestModel(classDirectory, packageCandidateName));
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