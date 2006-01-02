package org.toryt_II.testmodel;

import java.io.File;

import org.toryt.example.Bookmark;
import org.toryt_II.main.TestModelFactory;

import junit.framework.TestCase;



public class TestTestModelFactory extends TestCase {

  protected void setUp() throws Exception {
    super.setUp();
    $testModelFactory = new TestModelFactory();
  }

  protected void tearDown() throws Exception {
    $testModelFactory = null;
    super.tearDown();
  }

  private TestModelFactory $testModelFactory;

  public static final Class CLASS_UNDER_TEST = Bookmark.class;

  public static final String PACKAGE_NAME_UNDER_TEST_1 = "org.toryt.example";

  public static final Package PACKAGE_UNDER_TEST_1 = Package.getPackage(PACKAGE_NAME_UNDER_TEST_1);

  public static final String PACKAGE_NAME_UNDER_TEST_2 = "org";

  public static final Package PACKAGE_UNDER_TEST_2 = Package.getPackage(PACKAGE_NAME_UNDER_TEST_2);

  public static final File SOURCE_DIRECTORY_UNDER_TEST_1 = new File("/Users/jand/Documents/eclipse/workspace/toryt/target/classes");

  public static final String PROJECT_NAME_UNDER_TEST_1 = "Toryt Example";

  public static final File SOURCE_DIRECTORY_UNDER_TEST_2 = new File("/Users/jand/Documents/eclipse/workspace/toryt/target/classes");

  public static final String PROJECT_NAME_UNDER_TEST_2 = "Toryt";

//  /*
//   * Test method for 'org.toryt_II.testmodel.TestModelFactory.createConstructorTestModel(Constructor)'
//   */
//  public void testCreateConstructorTestModel() {
//
//  }
//
//  /*
//   * Test method for 'org.toryt_II.testmodel.TestModelFactory.createInstanceMutatorTestModel(Method)'
//   */
//  public void testCreateInstanceMutatorTestModel() {
//
//  }
//
//  /*
//   * Test method for 'org.toryt_II.testmodel.TestModelFactory.createInstanceInspectorTestModel(Method)'
//   */
//  public void testCreateInstanceInspectorTestModel() {
//
//  }
//
//  /*
//   * Test method for 'org.toryt_II.testmodel.TestModelFactory.createClassMutatorTestModel(Method)'
//   */
//  public void testCreateClassMutatorTestModel() {
//
//  }
//
//  /*
//   * Test method for 'org.toryt_II.testmodel.TestModelFactory.createClassInspectorTestModel(Method)'
//   */
//  public void testCreateClassInspectorTestModel() {
//
//  }
//
//  /*
//   * Test method for 'org.toryt_II.testmodel.TestModelFactory.createInnerClassTestModel(Class)'
//   */
//  public void testCreateInnerClassTestModel() {
//
//  }

  /*
   * Test method for 'org.toryt_II.testmodel.TestModelFactory.createClassTestModel(Class)'
   */
  public void testCreateClassTestModel() {
    ClassTestModel result = $testModelFactory.createClassTestModel(CLASS_UNDER_TEST);
    result.printStructure(System.out);
    assertEquals(CLASS_UNDER_TEST, result.getClazz());
    assertTrue(result.getChildTestModels().containsAll(result.getConstructorTestModels()));
    assertTrue(result.getChildTestModels().containsAll(result.getInstanceMutatorTestModels()));
    assertTrue(result.getChildTestModels().containsAll(result.getInstanceInspectorTestModels()));
    assertTrue(result.getChildTestModels().containsAll(result.getClassMutatorTestModels()));
    assertTrue(result.getChildTestModels().containsAll(result.getClassInspectorTestModels()));
    assertTrue(result.getChildTestModels().containsAll(result.getInnerClassTestModels()));
    assertTrue(result.getChildTestModels().containsAll(result.getClassTestModels()));
  }

  /*
   * Test method for 'org.toryt_II.testmodel.TestModelFactory.createPackageTestModel(File[], Package)'
   */
  public void testCreatePackageTestModel1() {
    PackageTestModel result = $testModelFactory.createPackageTestModel(SOURCE_DIRECTORY_UNDER_TEST_1, PACKAGE_NAME_UNDER_TEST_1);
    result.printStructure(System.out);
    assertEquals(PACKAGE_UNDER_TEST_1, result.getPackage());
    assertTrue(result.getChildTestModels().containsAll(result.getPackageTestModels()));
    assertTrue(result.getChildTestModels().containsAll(result.getClassTestModels()));
  }

  /*
   * Test method for 'org.toryt_II.testmodel.TestModelFactory.createPackageTestModel(File[], Package)'
   */
  public void testCreatePackageTestModel2() {
    PackageTestModel result = $testModelFactory.createPackageTestModel(SOURCE_DIRECTORY_UNDER_TEST_1, PACKAGE_NAME_UNDER_TEST_2);
    result.printStructure(System.out);
    assertEquals(PACKAGE_UNDER_TEST_2, result.getPackage());
    assertTrue(result.getChildTestModels().containsAll(result.getPackageTestModels()));
    assertTrue(result.getChildTestModels().containsAll(result.getClassTestModels()));
  }

  /*
   * Test method for 'org.toryt_II.testmodel.TestModelFactory.createProjectTestModel(File[], Package)'
   */
  public void testCreateProjectTestModel1() {
    ProjectTestModel result = $testModelFactory.createProjectTestModel(SOURCE_DIRECTORY_UNDER_TEST_1, PROJECT_NAME_UNDER_TEST_1);
    result.printStructure(System.out);
    assertEquals(PROJECT_NAME_UNDER_TEST_1, result.getProjectName());
    assertTrue(result.getChildTestModels().containsAll(result.getPackageTestModels()));
  }

  /*
   * Test method for 'org.toryt_II.testmodel.TestModelFactory.createProjectTestModel(File[], Package)'
   */
  public void testCreateProjectTestModel2() {
    ProjectTestModel result = $testModelFactory.createProjectTestModel(SOURCE_DIRECTORY_UNDER_TEST_2, PROJECT_NAME_UNDER_TEST_2);
    result.printStructure(System.out);
    assertEquals(PROJECT_NAME_UNDER_TEST_2, result.getProjectName());
    assertTrue(result.getChildTestModels().containsAll(result.getPackageTestModels()));
  }

}

