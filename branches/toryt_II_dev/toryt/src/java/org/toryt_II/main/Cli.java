package org.toryt_II.main;


import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.toryt.util_I.reflect.CannotGetMethodException;
import org.toryt.util_I.reflect.Classes;
import org.toryt.util_I.reflect.Methods;
import org.toryt_II.testmodel.ClassTestModel;
import org.toryt_II.testmodel.DefaultTestModelFactory;
import org.toryt_II.testmodel.NonConstructorMethodTestModel;
import org.toryt_II.testmodel.TestModel;
import org.toryt_II.testmodel.TestModelCreationException;
import org.toryt_II.testmodel.TestModelFactory;


/**
 * @author Jan Dockx
 */
public class Cli {

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


  public static void main(String[] args) throws SecurityException, TestModelCreationException, CannotGetMethodException {
    CommandLine cl = null;
    { // Parsing
      CommandLineParser clParser = new PosixParser();
      try {
        cl = clParser.parse(_CL_OPTIONS, args);
      }
      catch (ParseException e) {
        System.err.println("Parsing command line arguments failed.  Reason: " + e.getMessage());
        printHelp(-1, System.err);  // exit
      }
    }
    if (cl.hasOption("h")) { // help option
      printHelp(0, System.out); // exit
    }
    if (cl.hasOption(SHORT_VERSION_OPTION)) {
      System.out.println(VERSION);
      System.exit(0);
    }
    TestModelFactory testModelFactory = createTestModelFactory(cl);
    TestModel<?> testModel = createTestModel(cl, testModelFactory);
    testModel.printStructure(System.out);
    System.out.println("Starting tests.");
    System.out.println(new Date());
    System.out.println();

    //TESTING HERE

    System.out.println();
    System.out.println(new Date());
    System.out.println("Tests done.");
  }

  private static String DIRECTORY_REQUIRED = "The --directory option is required.";

  private static String EXPLANATION = "If called without the --help option, either the " +
                                      "--project option, the --package option, the " +
                                      "--class option or the --method option is required. " +
                                      "For the --project and the --package option, the " +
                                      "--directory option is required too.";

  public static String SHORT_VERSION_OPTION = "v";

  private static Option createVersionOption() {
    OptionBuilder.withLongOpt("version");
    OptionBuilder.withDescription("Version of this runtime");
// include version of Toryt from pom.xml if possible
    return OptionBuilder.create(SHORT_VERSION_OPTION);
  }

  public static Class<DefaultTestModelFactory> DEFAULT_TEST_MODEL_FACTORY = DefaultTestModelFactory.class;

  public static String SHORT_TEST_MODEL_FACTORY_OPTION = "f";

  private static Option createTestModelFactoryOption() {
    OptionBuilder.withLongOpt("factory");
    OptionBuilder.withArgName("TestModelFactory");
    OptionBuilder.hasArg();
    OptionBuilder.withDescription("Fully qualified class name of TestModelFactory implementation to use. " +
                                  "The default is " + DEFAULT_TEST_MODEL_FACTORY);
    return OptionBuilder.create(SHORT_TEST_MODEL_FACTORY_OPTION);
  }

  public static String SHORT_DIRECTORY_OPTION = "d";

  /**
   * @idea change to real classpath, that can take many directories and jars
   */
  private static Option createClassDirectoryOption() throws IllegalArgumentException {
    OptionBuilder.withLongOpt("directory");
    OptionBuilder.withArgName("class directory");
    OptionBuilder.hasArg();
    OptionBuilder.withDescription("Path to class directory that contains code to be tested.");
    return OptionBuilder.create(SHORT_DIRECTORY_OPTION);
  }

  public static String SHORT_PROJECT_OPTION = "p";

  private static Option createProjectOption() throws IllegalArgumentException {
    OptionBuilder.withLongOpt("project");
    OptionBuilder.withArgName("project name");
    OptionBuilder.hasArg();
    OptionBuilder.withDescription("Test a project, found at <class directory>, with this name. " +
                                  "Toryt will automatically find the packages in the project " +
                                  "to test. " +
                                  DIRECTORY_REQUIRED);
    return OptionBuilder.create(SHORT_PROJECT_OPTION);
  }

  public static String SHORT_PACKAGE_OPTION = "k";

  private static Option createPackageOption() throws IllegalArgumentException {
    OptionBuilder.withLongOpt("package");
    OptionBuilder.withArgName("fqpn");
    OptionBuilder.hasArg();
    OptionBuilder.withDescription("Test a package, found in <class directory>, with fully qualified " +
                                  "package name <fqpn>. " +
                                  "Toryt will automatically find the class and subpackages " +
                                  "to test. " +
                                  DIRECTORY_REQUIRED);
    return OptionBuilder.create(SHORT_PACKAGE_OPTION);
  }

  public static String SHORT_CLASS_OPTION = "c";

  private static Option createClassOption() throws IllegalArgumentException {
    OptionBuilder.withLongOpt("class");
    OptionBuilder.withArgName("fqcn");
    OptionBuilder.hasArg();
    OptionBuilder.withDescription("Test a class, found in the classpath with the fully qualified " +
                                  "class name <fqcn>. " +
                                  "This can be a nested class. " +
                                  "Toryt will automatically find the methods and nested classes " +
                                  "to test. " +
                                  "The format for nested classes is " +
                                  "<outer class name>#<nested class name> (recursively).");
    return OptionBuilder.create(SHORT_CLASS_OPTION);
  }

  public static String SHORT_METHOD_OPTION = "m";

  private static Option createMethodOption() throws IllegalArgumentException {
    OptionBuilder.withLongOpt("method");
    OptionBuilder.withArgName("signature");
    OptionBuilder.hasArg();
    OptionBuilder.withDescription("Test a method, found in the classpath with qualified name <signature>. " +
                                  "The format is <fully qualified class name>#<method signature>, " +
                                  "where <fully qualified class name> can be a nested class name, with " +
                                  "format <outer class name>#<nested class name> (recursively).");
    return OptionBuilder.create(SHORT_METHOD_OPTION);
  }

  private static Options _CL_OPTIONS = createOptions();

  private static Options createOptions() throws IllegalArgumentException {
    Options clOptions = new Options();
    clOptions.addOption(new Option("h", "help", false, "print this message" ));
    clOptions.addOption(createVersionOption());
    clOptions.addOption(createTestModelFactoryOption());
    clOptions.addOption(createClassDirectoryOption());
    clOptions.addOption(createProjectOption());
    clOptions.addOption(createPackageOption());
    clOptions.addOption(createClassOption());
    clOptions.addOption(createMethodOption());
    return clOptions;
  }

  private static String VERSION = "Toryt CLI " + CVS_REVISION + ", " + CVS_DATE;

  private static void printHelp(int exitCode, PrintStream ps) {
    printHelp(exitCode, new PrintWriter(ps));
  }

  private static void printHelp(int exitCode, PrintWriter pw) {
    HelpFormatter hf = new HelpFormatter();
    hf.printHelp(70, "java " + Cli.class.getName(),
                 "\n" + VERSION,
                 _CL_OPTIONS,
                 EXPLANATION,
                 true);
    System.exit(exitCode);
  }

  private static TestModelFactory createTestModelFactory(CommandLine cl) {
    Class<? extends TestModelFactory> testModelFactoryClass = loadTestModelFactoryClass(cl);
    TestModelFactory testModelFactory = null;
    try {
      testModelFactory = testModelFactoryClass.newInstance();
    }
    catch (InstantiationException iExc) {
      System.err.println("Cannot instantiate " + testModelFactoryClass + "as TestModelFactory");
      iExc.printStackTrace(System.err);
      printHelp(-2000, System.err);  // exit
    }
    catch (IllegalAccessException iaExc) {
      System.err.println("Cannot instantiate " + testModelFactoryClass + "as TestModelFactory");
      iaExc.printStackTrace(System.err);
      printHelp(-2001, System.err);  // exit
    }
    catch (ExceptionInInitializerError eiiErr) {
      System.err.println("Cannot instantiate " + testModelFactoryClass + "as TestModelFactory");
      eiiErr.printStackTrace(System.err);
      printHelp(-2002, System.err);  // exit
    }
    catch (SecurityException sExc) {
      System.err.println("Cannot instantiate " + testModelFactoryClass + "as TestModelFactory");
      sExc.printStackTrace(System.err);
      printHelp(-2003, System.err);  // exit
    }
    catch (ClassCastException ccExc) {
      System.err.println(testModelFactoryClass + " is not a subtype of TestModelFactory");
      ccExc.printStackTrace(System.err);
      printHelp(-2004, System.err);  // exit
    }
    System.out.println("TestModelFactory class: " + testModelFactory.getClass());
    return testModelFactory;
  }

  private static Class<? extends TestModelFactory> loadTestModelFactoryClass(CommandLine cl) {
    Class<? extends TestModelFactory> testModelFactoryClass = null;
    if (cl.hasOption(SHORT_TEST_MODEL_FACTORY_OPTION)) {
      String testModelFactoryClassName = cl.getOptionValue(SHORT_TEST_MODEL_FACTORY_OPTION);
      @SuppressWarnings("unchecked")
      Class<? extends TestModelFactory> loadSimpleClass = (Class<? extends TestModelFactory>)loadSimpleClass(testModelFactoryClassName);
      testModelFactoryClass = loadSimpleClass;
    }
    else {
      testModelFactoryClass = DEFAULT_TEST_MODEL_FACTORY;
    }
    assert TestModelFactory.class.isAssignableFrom(testModelFactoryClass);
    assert testModelFactoryClass != null;
    return testModelFactoryClass;
  }

  private static TestModel<?> createTestModel(CommandLine cl, TestModelFactory testModelFactory) throws SecurityException, TestModelCreationException, CannotGetMethodException {
    if ((! cl.hasOption(SHORT_PROJECT_OPTION)) &&
        (! cl.hasOption(SHORT_PACKAGE_OPTION)) &&
        (! cl.hasOption(SHORT_CLASS_OPTION)) &&
        (! cl.hasOption(SHORT_METHOD_OPTION))) {
      System.err.println("A project name (--project), package name (--package), " +
                         "class name (--class) or method name (--method) is required " +
                          "(except when called with option --help)\n");
      printHelp(-100, System.err); // exit
    }
    TestModel<?> testModel = null;
    if (cl.hasOption(SHORT_PROJECT_OPTION) || cl.hasOption(SHORT_PACKAGE_OPTION)) {
      // we need a class directory
      if (! cl.hasOption(SHORT_DIRECTORY_OPTION)) {
        System.err.println("The class directory option is required for --project and -- package\n");
        printHelp(-200, System.err); // exit
      }
      File classDirectory = new File(cl.getOptionValue(SHORT_DIRECTORY_OPTION));
      if (! classDirectory.exists()) {
        System.err.println(cl.getOptionValue(SHORT_DIRECTORY_OPTION) + " does not exist");
        printHelp(-210, System.err); // exit
      }
      if (! classDirectory.isDirectory()) {
        System.err.println(cl.getOptionValue(SHORT_DIRECTORY_OPTION) + " is not a directory");
        printHelp(-211, System.err); // exit
      }
      System.out.println("Class directory: " + classDirectory);
      if (cl.hasOption(SHORT_PROJECT_OPTION)) {
        String projectName = cl.getOptionValue(SHORT_PROJECT_OPTION);
        System.out.println("Project name: " + projectName);
        testModel = testModelFactory.createProjectTestModel(classDirectory, projectName);
      }
      else if (cl.hasOption(SHORT_PACKAGE_OPTION)) {
        String packageName = cl.getOptionValue(SHORT_PACKAGE_OPTION);
        // MUDO check existence; -230 exit values
        System.out.println("Package name: " + packageName);
        testModel = testModelFactory.createPackageTestModel(classDirectory, packageName);
      }
      else {
        assert false;
      }
    }
    else {
      // we do not need a class directory; reflection does the job
      if (cl.hasOption(SHORT_CLASS_OPTION)) {
        String className = cl.getOptionValue(SHORT_CLASS_OPTION);
        Class<?> classToTest = loadClass(className.split(MEMBER_SEPARATOR));
        // MUDO different for nested classes
        testModel = createClassTestModel(testModelFactory, classToTest);
      }
      else if (cl.hasOption(SHORT_METHOD_OPTION)) {
        String methodName = cl.getOptionValue(SHORT_METHOD_OPTION);
        String[] names = methodName.split(MEMBER_SEPARATOR);
        // last part is method signature
        if (names.length < 2) {
          System.err.println("--method argument value \"" +
                             methodName +
                             "\" does not contain # separator");
          printHelp(-420, System.err); // exit
        }
        String[] className = new String[names.length - 1];
        System.arraycopy(names, 0, className, 0, className.length);
        Class<?> methodHolder = loadClass(className);
        Method method = Methods.findMethod(methodHolder, names[names.length - 1]);
        if (method != null) {
          System.out.println("Method: " + method);
          testModel = createNonConstructorMethodTestModel(testModelFactory, method);
        }
        else {
          Constructor<?> constructor = Methods.findConstructor(methodHolder, names[names.length - 1]);
          if (method == null) {
            System.err.println("no method " + methodName + " found");
            printHelp(-421, System.err); // exit
          }
          System.out.println("Method: " + constructor);
          testModel = testModelFactory.createConstructorTestModel(constructor);
        }
      }
    }
    assert testModel != null;
    return testModel;
  }

  private static ClassTestModel<?> createClassTestModel(TestModelFactory tmf, Class<?> c) throws TestModelCreationException {
    assert tmf != null;
    assert c != null;
    if (c.isInterface()) {
      System.err.println("interfaces cannot be tested and " + c + " is an interface");
      printHelp(-301, System.err);
    }
    ClassTestModel<?> result = null;
    if (Classes.isInnerClass(c)) {
      result = tmf.createInnerClassTestModel(c);
    }
    else if (c.getDeclaringClass() == c) {
      result = tmf.createStaticClassTestModel(c);
    }
    return result;
  }

  private static NonConstructorMethodTestModel createNonConstructorMethodTestModel(TestModelFactory tmf, Method m) throws TestModelCreationException {
    assert tmf != null;
    assert m != null;
    NonConstructorMethodTestModel result = null;
    switch (Methods.methodKind(m)) {
      case INSTANCE_MUTATOR:
        result = tmf.createInstanceMutatorTestModel(m);
        break;
      case INSTANCE_INSPECTOR:
        // MUDO remove basic inspectors
        result = tmf.createInstanceInspectorTestModel(m);
        break;
      case CLASS_MUTATOR:
        result = tmf.createClassMutatorTestModel(m);
        break;
      case CLASS_INSPECTOR:
        // MUDO remove basic inspectors
        result = tmf.createClassInspectorTestModel(m);
        break;
      default:
        assert false;
    }
    return result;
  }

//  /**
//   * @result (result instanceof Method) || (result instanceof Constructor) || null;
//   * @mudo DOESN"T WORK
//   */
//  private static Object getMethod(Class methodHolder, String methodSig) {
//    assert methodHolder != null;
//    Method[] methods = methodHolder.getMethods();
//    for (int i = 0; i < methods.length; i++) {
//      if (methods[i].toString().equals(methodSig)) {
//        return methods[i];
//      }
//    }
//    Constructor[] constructors = methodHolder.getConstructors();
//    for (int i = 0; i < constructors.length; i++) {
//      if (constructors[i].toString().equals(methodSig)) {
//        return constructors[i];
//      }
//    }
//    return null;
//  }

  /**
   * An array of class names, takes into account nested classes.
   * <code>classNames[0]</code> must be FQCN of top level class.
   *
   * @pre classNames != null;
   * @pre classNames.length > 0;
   * @result != null;
   */
  private static Class<?> loadClass(String[] classNames) {
    assert classNames != null;
    assert classNames.length > 0;
    Class<?> result = loadSimpleClass(classNames[0]);
    for (int i = 1; i < classNames.length; i++) {
      try {
        result = getNestedClass(result, classNames[i]);
      }
      catch (ClassNotFoundException e) {
        System.err.println("Class  " + result + " does not have a nested class " + classNames[i]);
        printHelp(-1013, System.err); // exit
      }
    }
    assert result != null;
    System.out.println("Class: " + result);
    return result;
  }

  private static Class<?> loadSimpleClass(String className) {
    Class<?> result = null;
    try {
      result = Class.forName(className);
    }
    catch (ClassNotFoundException e) {
      System.err.println("Class with name " + className + " does not exist in " +
      "classpath.");
      printHelp(-1010, System.err); // exit
    }
    catch (ExceptionInInitializerError eiiErr) {
      System.err.println("ExceptionInInitializerError loading class with name " + className + ":");
      eiiErr.printStackTrace(System.err);
      printHelp(-1011, System.err); // exit
    }
    catch (LinkageError lErr) {
      System.err.println("LinkageError loading class with name " + className + ":");
      lErr.printStackTrace(System.err);
      printHelp(-1012, System.err); // exit
    }
    assert result != null;
    return result;
  }

  private static Class<?> getNestedClass(Class<?> outerClass, String innerClassName) throws ClassNotFoundException {
    assert outerClass != null;
    Class<?>[] nested = outerClass.getClasses();
    for (int j = 0; j < nested.length; j++) {
      if (nested[j].getName().equals(innerClassName)) {
        return nested[j];
      }
    }
    throw new ClassNotFoundException(outerClass + MEMBER_SEPARATOR + innerClassName);
  }

  public final static String MEMBER_SEPARATOR = "#";

}