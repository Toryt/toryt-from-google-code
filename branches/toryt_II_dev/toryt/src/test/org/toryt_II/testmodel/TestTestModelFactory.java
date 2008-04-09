package org.toryt_II.testmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.toryt_II.example.Bookmark;



public class TestTestModelFactory {

  @Before
  public void setUp() throws Exception {
    $testModelFactory = new DefaultTestModelFactory();
  }

  @After
  public void tearDown() throws Exception {
    $testModelFactory = null;
  }

  private TestModelFactory $testModelFactory;

  public static final Class<Bookmark> CLASS_UNDER_TEST = Bookmark.class;

  public static final String PACKAGE_NAME_UNDER_TEST_1 = "org.toryt_II.example";

  public static final String PACKAGE_NAME_UNDER_TEST_2 = "org";

  public static final File CLASS_DIRECTORY_UNDER_TEST_1 = new File("target/example-classes");

  public static final String PROJECT_NAME_UNDER_TEST_1 = "Toryt Example";

//  public static final File CLASS_DIRECTORY_UNDER_TEST_2 = new File("target/classes");

//  public static final String PROJECT_NAME_UNDER_TEST_2 = "Toryt";

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

  @Test
  public void testCreateStaticClassTestModel() throws TestModelCreationException {
    StaticClassTestModel<Bookmark> result = $testModelFactory.createStaticClassTestModel(CLASS_UNDER_TEST);
    result.printStructure(System.out);
    assertEquals(CLASS_UNDER_TEST, result.getSubject());
    assertTrue(result.getChildTestModels().containsAll(result.constructorTestModels.getSet()));
    assertTrue(result.getChildTestModels().containsAll(result.instanceMutatorTestModels.getSet()));
    assertTrue(result.getChildTestModels().containsAll(result.instanceInspectorTestModels.getSet()));
    assertTrue(result.getChildTestModels().containsAll(result.classMutatorTestModels.getSet()));
    assertTrue(result.getChildTestModels().containsAll(result.classInspectorTestModels.getSet()));
    assertTrue(result.getChildTestModels().containsAll(result.innerClassTestModels.getSet()));
    assertTrue(result.getChildTestModels().containsAll(result.staticNestedClassTestModels.getSet()));
  }

  @Test
  public void testCreatePackageTestModel1() throws TestModelCreationException {
    PackageTestModel result = $testModelFactory.createPackageTestModel(CLASS_DIRECTORY_UNDER_TEST_1, PACKAGE_NAME_UNDER_TEST_1);
    result.printStructure(System.out);
    assertEquals(PACKAGE_NAME_UNDER_TEST_1, result.getSubject());
    assertTrue(result.getChildTestModels().containsAll(result.packageTestModels.getSet()));
    assertTrue(result.getChildTestModels().containsAll(result.classTestModels.getSet()));
  }

  @Test
  public void testCreatePackageTestModel2() throws TestModelCreationException {
    PackageTestModel result = $testModelFactory.createPackageTestModel(CLASS_DIRECTORY_UNDER_TEST_1, PACKAGE_NAME_UNDER_TEST_2);
    result.printStructure(System.out);
    assertEquals(PACKAGE_NAME_UNDER_TEST_2, result.getSubject());
    assertTrue(result.getChildTestModels().containsAll(result.packageTestModels.getSet()));
    assertTrue(result.getChildTestModels().containsAll(result.classTestModels.getSet()));
  }

  @Test
  public void testCreateProjectTestModel1() throws TestModelCreationException {
    ProjectTestModel result = $testModelFactory.createProjectTestModel(CLASS_DIRECTORY_UNDER_TEST_1, PROJECT_NAME_UNDER_TEST_1);
    result.printStructure(System.out);
    assertEquals(PROJECT_NAME_UNDER_TEST_1, result.getSubject());
    assertTrue(result.getChildTestModels().containsAll(result.packageTestModels.getSet()));
  }

//  /*
//   * Test method for 'org.toryt_II.testmodel.TestModelFactory.createProjectTestModel(File[], Package)'
//   */
//  public void testCreateProjectTestModel2() throws TestModelCreationException {
  // fails as long as this project has compilation problems
//    ProjectTestModel result = $testModelFactory.createProjectTestModel(CLASS_DIRECTORY_UNDER_TEST_2, PROJECT_NAME_UNDER_TEST_2);
//    result.printStructure(System.out);
//    assertEquals(PROJECT_NAME_UNDER_TEST_2, result.getSubject());
//    assertTrue(result.getChildTestModels().containsAll(result.packageTestModels.getSet()));
//  }

}

