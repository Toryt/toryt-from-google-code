package org.toryt_II.testmodel;


import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.CouldNotLoadClassException;
import org.toryt.util_I.reflect.Reflection;
import org.toryt.util_I.reflect.Reflection.MethodKind;
import org.toryt.util_I.reflect.Reflection.TypeKind;


/**
 * A collection of methods that creates initialized
 * {@link TestModel} instances via reflection or project inspection.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class DefaultTestModelFactory implements TestModelFactory {



  private final static Log LOG = LogFactory.getLog(DefaultTestModelFactory.class);



  public ConstructorTestModel createConstructorTestModel(Constructor constructor) {
    assert constructor != null;
    ConstructorTestModel result = new ConstructorTestModel();
    result.setSubject(constructor);
    return result;
  }

  public InstanceMutatorTestModel createInstanceMutatorTestModel(Method instanceMutator) {
    assert instanceMutator != null;
    assert Reflection.methodKind(instanceMutator) == MethodKind.INSTANCE_MUTATOR;
    InstanceMutatorTestModel result = new InstanceMutatorTestModel();
    result.setSubject(instanceMutator);
    return result;
  }

  public InstanceInspectorTestModel createInstanceInspectorTestModel(Method instanceInspector) {
    assert instanceInspector != null;
    assert Reflection.methodKind(instanceInspector) == MethodKind.INSTANCE_INSPECTOR;
    InstanceInspectorTestModel result = new InstanceInspectorTestModel();
    result.setSubject(instanceInspector);
    return result;
  }

  public ClassMutatorTestModel createClassMutatorTestModel(Method classMutator) {
    assert classMutator != null;
    assert Reflection.methodKind(classMutator) == MethodKind.CLASS_MUTATOR;
    ClassMutatorTestModel result = new ClassMutatorTestModel();
    result.setSubject(classMutator);
    return result;
  }

  public ClassInspectorTestModel createClassInspectorTestModel(Method classInspector) {
    assert classInspector != null;
    assert Reflection.methodKind(classInspector) == MethodKind.CLASS_INSPECTOR;
    ClassInspectorTestModel result = new ClassInspectorTestModel();
    result.setSubject(classInspector);
    return result;
  }

  public InnerClassTestModel createInnerClassTestModel(Class innerClazz) throws TestModelCreationException {
    assert innerClazz != null;
    assert Reflection.typeKind(innerClazz) == TypeKind.INNER;
    LOG.debug("Creating InnerClassTestModel for class " + innerClazz);
    InnerClassTestModel result = new InnerClassTestModel();
    initClassTestModel(innerClazz, result);
    return result;
  }

  public StaticClassTestModel createStaticClassTestModel(Class clazz) throws TestModelCreationException {
    assert clazz != null;
    assert Reflection.typeKind(clazz) == TypeKind.STATIC;
    LOG.debug("Creating StaticClassTestModel for class " + clazz);
    StaticClassTestModel result = new StaticClassTestModel();
    initClassTestModel(clazz, result);
    return result;
  }

  private void initClassTestModel(Class clazz, ClassTestModel result) throws TestModelCreationException {
    result.setSubject(clazz);
    addConstructors(clazz, result);
    addMethods(clazz, result);
    addNestedClasses(clazz, result);
  }

  private void addConstructors(Class clazz, ClassTestModel result) throws TestModelCreationException {
    try {
      LOG.debug("  adding ConstructorTestModels for class " + clazz);
      Constructor[] constructors = clazz.getConstructors();
          // only public constructors
      LOG.debug("  there are " + constructors.length + " constructors");
      for (int i = 0; i < constructors.length; i++) {
        result.constructorTestModels.add(createConstructorTestModel(constructors[i]));
      }
    }
    catch (SecurityException sExc) {
      throw new TestModelCreationException(result, null, sExc);
    }
  }

  private void addMethods(Class clazz, ClassTestModel result)
      throws TestModelCreationException {
    try {
      LOG.debug("  adding MethodTestModels for class " + clazz);
      Method[] methods = clazz.getMethods(); // SecurityException
          // only public methods, but also inherited
      LOG.debug("  there are " + methods.length + " methods");
      for (int i = 0; i < methods.length; i++) {
        switch (Reflection.methodKind(methods[i])) {
          case INSTANCE_MUTATOR:
            LOG.debug("    " + methods[i] + " is an instance mutator");
            result.instanceMutatorTestModels.add(createInstanceMutatorTestModel(methods[i]));
            break;
          case INSTANCE_INSPECTOR:
            LOG.debug("    " + methods[i] + " is an instance inspector");
            // MUDO remove basic inspectors
            result.instanceInspectorTestModels.add(createInstanceInspectorTestModel(methods[i]));
            break;
          case CLASS_MUTATOR:
            LOG.debug("    " + methods[i] + " is an class mutator");
            result.classMutatorTestModels.add(createClassMutatorTestModel(methods[i]));
            break;
          case CLASS_INSPECTOR:
            LOG.debug("    " + methods[i] + " is an class inspector");
            // MUDO remove basic inspectors
            result.classInspectorTestModels.add(createClassInspectorTestModel(methods[i]));
            break;
          default:
            assert false;
        }
      }
    }
    catch (SecurityException sExc) {
      throw new TestModelCreationException(result, null, sExc);
    }
  }

  private void addNestedClasses(Class clazz, ClassTestModel result)
      throws TestModelCreationException {
    try {
      LOG.debug("  adding TestModels for nested classes in class  " + clazz);
      Class[] clazzes = clazz.getClasses(); // SecurityException
          // only public nested types, but also inherited and interfaces
      for (int i = 0; i < clazzes.length; i++) {
        if (! clazzes[i].isInterface()) { // interfaces cannot be tested
          switch (Reflection.typeKind(clazz)) {
            case INNER:
              LOG.debug("    " + clazzes[i] + " is an inner class");
              result.innerClassTestModels.add(createInnerClassTestModel(clazzes[i]));
              break;
            case STATIC:
              if (clazzes[i].getDeclaringClass() == clazz) {
                // static nested classes only need to be added to the declaring class test model
                LOG.debug("    " + clazzes[i] + " is an static nested class");
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
    catch (SecurityException sExc) {
      throw new TestModelCreationException(result, null, sExc);
    }
  }

  public PackageTestModel createPackageTestModel(File classDirectory, String packageName)
      throws TestModelCreationException {
    assert classDirectory != null;
    assert classDirectory.exists();
    assert classDirectory.isDirectory();
    assert packageName != null;
    LOG.debug("Creating PackageTestModel for package " + packageName +
              " from class directory " + classDirectory.getPath());
    PackageTestModel result = new PackageTestModel();
    result.setSubject(packageName);
    File packageDirectory = new File(classDirectory, packageName.replace('.', '/'));
    LOG.debug("  package directory should be " + packageDirectory.getPath());
    assert packageDirectory.exists();
    assert packageDirectory.isDirectory();
    addClasses(packageDirectory, packageName, result);
    addPackages(packageDirectory, packageName, classDirectory, result);
    return result;
  }

  private void addClasses(File packageDirectory, String packageName,
                          PackageTestModel result)
      throws TestModelCreationException {
    LOG.debug("  finding classes in package " + packageName);
    File[] packageContents = packageDirectory.listFiles(JAVA_CLASS_FILTER);
    for (int i = 0; i < packageContents.length; i++) {
      String fileName = packageContents[i].getName();
      String simpleClassName = fileName.substring(0, fileName.lastIndexOf(JAVA_CLASS_SUFFIX));
      String fqcn = packageName + "." + simpleClassName;
      LOG.debug("    " + fqcn + " should be an ok class");
      Class clazz;
      try {
        clazz = Reflection.loadForName(fqcn);
        LOG.debug("    " + clazz + " loaded; creating StaticClassTestModel for this class");
        result.classTestModels.add(createStaticClassTestModel(clazz));
      }
      catch (CouldNotLoadClassException cnlcExc) {
        LOG.warn("could not load class with fully qualified class name " + fqcn);
        throw new TestModelCreationException(result,
                                             "could not load class with fully qualified class name " + fqcn,
                                             cnlcExc);
      }
    }
  }

  public ProjectTestModel createProjectTestModel(File classDirectory, String projectName)
      throws TestModelCreationException {
    assert classDirectory != null;
    assert classDirectory.exists();
    assert classDirectory.isDirectory();
    LOG.debug("Creating ProjectTestModel for project \"" + projectName +
              "\" from class directory " + classDirectory.getPath());
    ProjectTestModel result = new ProjectTestModel();
    result.setSubject(projectName);
    addPackages(classDirectory, "", classDirectory, result);
    return result;
  }

  private final static String NO_PACKAGE_DIR_NAME = "doc-files";

  private void addPackages(File directory, String parentPackageName, File classDirectory,
                           AbstractPackageTestModelContainer<?> result)
      throws TestModelCreationException {
    LOG.debug("Finding subpackages of package " + parentPackageName +
              " in class directory " + classDirectory.getPath() +
              " (parent package directory: " + directory.getPath() + ")");
    File[] directoryContents = directory.listFiles(JAVA_SUBPACKAGE_FILTER);
    for (int i = 0; i < directoryContents.length; i++) {
      File packageCandidate = directoryContents[i];
      String packageCandidateName = parentPackageName + packageCandidate.getName();
      if (NO_PACKAGE_DIR_NAME.equals(packageCandidate.getName())) {
        LOG.debug("  " + NO_PACKAGE_DIR_NAME + " is a reserved directory name, and not a package");
      }
      else {
        LOG.debug("  candidate package: " + packageCandidateName);
        File[] packageCandidateContents = packageCandidate.listFiles(JAVA_CLASS_FILTER);
        LOG.debug("  only is a real package if it contains Java class files: " + packageCandidateContents.length);
        if (packageCandidateContents.length > 0) { // this is a package
          LOG.debug("  yes: create PackageTestModel for subpackage " + packageCandidateName);
          result.packageTestModels.add(createPackageTestModel(classDirectory, packageCandidateName));
        }
        else { // this is not a package; go deeper
          LOG.debug("  no, but there might be real packages deeper");
          addPackages(packageCandidate, packageCandidateName + ".", classDirectory, result);
        }
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