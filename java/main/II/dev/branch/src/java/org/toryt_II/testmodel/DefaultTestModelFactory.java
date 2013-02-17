package org.toryt_II.testmodel;


import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.CannotGetClassException;
import org.toryt.util_I.reflect.Classes;
import org.toryt.util_I.reflect.MethodKind;
import org.toryt.util_I.reflect.Methods;


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



  public <_Subject_> ConstructorTestModel<_Subject_> createConstructorTestModel(Constructor<_Subject_> constructor) {
    assert constructor != null;
    ConstructorTestModel<_Subject_> result = new ConstructorTestModel<_Subject_>();
    result.setSubject(constructor);
    return result;
  }

  public InstanceMutatorTestModel createInstanceMutatorTestModel(Method instanceMutator) {
    assert instanceMutator != null;
    assert Methods.methodKind(instanceMutator) == MethodKind.INSTANCE_MUTATOR;
    InstanceMutatorTestModel result = new InstanceMutatorTestModel();
    result.setSubject(instanceMutator);
    return result;
  }

  public InstanceInspectorTestModel createInstanceInspectorTestModel(Method instanceInspector) {
    assert instanceInspector != null;
    assert Methods.methodKind(instanceInspector) == MethodKind.INSTANCE_INSPECTOR;
    InstanceInspectorTestModel result = new InstanceInspectorTestModel();
    result.setSubject(instanceInspector);
    return result;
  }

  public ClassMutatorTestModel createClassMutatorTestModel(Method classMutator) {
    assert classMutator != null;
    assert Methods.methodKind(classMutator) == MethodKind.CLASS_MUTATOR;
    ClassMutatorTestModel result = new ClassMutatorTestModel();
    result.setSubject(classMutator);
    return result;
  }

  public ClassInspectorTestModel createClassInspectorTestModel(Method classInspector) {
    assert classInspector != null;
    assert Methods.methodKind(classInspector) == MethodKind.CLASS_INSPECTOR;
    ClassInspectorTestModel result = new ClassInspectorTestModel();
    result.setSubject(classInspector);
    return result;
  }

  public <_Subject_> InnerClassTestModel<_Subject_> createInnerClassTestModel(Class<_Subject_>  innerClazz)
      throws TestModelCreationException {
    assert innerClazz != null;
    assert Classes.isInnerClass(innerClazz);
    LOG.debug("Creating InnerClassTestModel for class " + innerClazz);
    InnerClassTestModel<_Subject_> result = new InnerClassTestModel<_Subject_>();
    initClassTestModel(innerClazz, result);
    return result;
  }

  public <_Subject_> StaticClassTestModel<_Subject_> createStaticClassTestModel(Class<_Subject_>  clazz)
      throws TestModelCreationException {
    assert clazz != null;
    assert ! Classes.isInnerClass(clazz);
    LOG.debug("Creating StaticClassTestModel for class " + clazz);
    StaticClassTestModel<_Subject_> result = new StaticClassTestModel<_Subject_>();
    initClassTestModel(clazz, result);
    return result;
  }

  /**
   * The generic parameter <_Subject_> ensures at <em>compile time</em> that
   * the returned {@link ClassTestModel} <code>result</code> is indeed for the type
   * <code>clazz</code>.
   */
  private <_Subject_> void initClassTestModel(Class<_Subject_> clazz, ClassTestModel<_Subject_> result) throws TestModelCreationException {
    result.setSubject(clazz);
    addConstructors(clazz, result);
    addMethods(clazz, result);
    addNestedClasses(clazz, result);
  }

  /**
   * The generic parameter <_Subject_> ensures at <em>compile time</em> that
   * the returned {@link ClassTestModel} <code>result</code> is indeed for the type
   * <code>clazz</code>, and that constructors for which models are added are
   * for that type.
   */
  private <_Subject_> void addConstructors(Class<_Subject_> clazz, ClassTestModel<_Subject_> result) throws TestModelCreationException {
    try {
      LOG.debug("  adding ConstructorTestModels for class " + clazz);
      @SuppressWarnings("unchecked") Constructor<_Subject_>[] constructors = clazz.getConstructors();
          // only public constructors
          /* warning because we cannot check type safety here (because the
           * Class interface return Constructor[] and not Constructor<T>[];
           * this seems a silly decision of the developers);
           * this is ok, since we get the constructors from <code>clazz</code>,
           * and <code>clazz</code> is of type <code>_TypeToTest</code>
           * guaranteed */
      LOG.debug("  there are " + constructors.length + " constructors");
      for (int i = 0; i < constructors.length; i++) {
        result.constructorTestModels.add(createConstructorTestModel(constructors[i]));
      }
    }
    catch (SecurityException sExc) {
      throw new TestModelCreationException(result, null, sExc);
    }
  }

  private <_Subject_> void addMethods(Class<_Subject_> clazz, ClassTestModel<_Subject_> result)
      throws TestModelCreationException {
    try {
      LOG.debug("  adding MethodTestModels for class " + clazz);
      Method[] methods = clazz.getMethods(); // SecurityException
          // only public methods, but also inherited
      LOG.debug("  there are " + methods.length + " methods");
      for (int i = 0; i < methods.length; i++) {
        switch (Methods.methodKind(methods[i])) {
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

  private <_Subject_> void addNestedClasses(Class<_Subject_> clazz, ClassTestModel<_Subject_> result)
      throws TestModelCreationException {
    try {
      LOG.debug("  adding TestModels for nested classes in class  " + clazz);
      Class<?>[] clazzes = clazz.getClasses(); // SecurityException
          // only public nested types, but also inherited and interfaces
      for (int i = 0; i < clazzes.length; i++) {
        if (! clazzes[i].isInterface()) { // interfaces cannot be tested
          if (Classes.isInnerClass(clazzes[i])) {
            LOG.debug("    " + clazzes[i] + " is an inner class");
            result.innerClassTestModels.add(createInnerClassTestModel(clazzes[i]));
          }
          else if (clazzes[i].getDeclaringClass() == clazz) {
            // static nested classes only need to be added to the declaring class test model
            LOG.debug("    " + clazzes[i] + " is a static nested class");
            try {
              ((StaticClassTestModel<_Subject_>)result).staticNestedClassTestModels.add(createStaticClassTestModel(clazzes[i]));
            }
            catch (ClassCastException ccExc) {
              // static nested classses can only be nested in static classes
              assert false : "static nested classses can only be nested in static classes";
            }
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
      Class<?> clazz;
      try {
        clazz = Classes.loadForName(fqcn);
        LOG.debug("    " + clazz + " loaded; creating StaticClassTestModel for this class");
        result.classTestModels.add(createStaticClassTestModel(clazz));
      }
      catch (CannotGetClassException cnlcExc) {
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
    addPackages(classDirectory, null, classDirectory, result);
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
      if (NO_PACKAGE_DIR_NAME.equals(packageCandidate.getName())) {
        LOG.debug("  " + NO_PACKAGE_DIR_NAME + " is a reserved directory name, and not a package");
      }
      else {
        String packageCandidateName =
            ((parentPackageName == null) ? "" : parentPackageName + ".") +
            packageCandidate.getName();
        LOG.debug("  candidate package: " + packageCandidateName);
        File[] packageCandidateContents = packageCandidate.listFiles(JAVA_CLASS_FILTER);
        LOG.debug("  only is a real package if it contains Java class files: " + packageCandidateContents.length);
        if (packageCandidateContents.length > 0) { // this is a package
          LOG.debug("  yes: create PackageTestModel for subpackage " + packageCandidateName);
          result.packageTestModels.add(createPackageTestModel(classDirectory, packageCandidateName));
        }
        else { // this is not a package; go deeper
          LOG.debug("  no, but there might be real packages deeper");
          addPackages(packageCandidate, packageCandidateName, classDirectory, result);
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
              // IDEA needs more exclusion; add grep filter
            }

          };

  private static FileFilter JAVA_SUBPACKAGE_FILTER =
      new FileFilter() {

            public boolean accept(File file) {
              return file.exists() &&
                     file.canRead() &&
                     file.isDirectory() &&
                     ! file.getName().equals("CVS");
              // IDEA needs more exclusion, for javadoc; add grep filter
            }

          };

}