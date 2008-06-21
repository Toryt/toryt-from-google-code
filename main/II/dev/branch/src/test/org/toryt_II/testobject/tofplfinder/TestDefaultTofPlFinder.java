package org.toryt_II.testobject.tofplfinder;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.toryt.util_I.beanfinder.BeanFinderConfigurationException;
import org.toryt.util_I.beanfinder.NoBeanFoundException;
import org.toryt_II.testobject.TestObjectFactoryPriorityList;
import org.toryt_II.testobject.java.lang._TOF_PL_Boolean;


public class TestDefaultTofPlFinder {

  private DefaultTofPlFinder $subject;

  @Before
  public void setUp() throws Exception {
    $subject = new DefaultTofPlFinder();
  }

  @After
  public void tearDown() throws Exception {
    $subject = null;
  }

  @Test
  public final void testGetBeanType() {
    assertEquals(TestObjectFactoryPriorityList.class, $subject.getBeanType());
  }

  /**
   * Test whether a system property defined TOF PL is found.
   */
  @Test
  public final void testFindFor1() {
    Class<_DummyTestObject1> dummyClass = _DummyTestObject1.class;
    String TofPlClassName = "org.toryt_II.testobject.tofplfinder._DummyTestObject1_TOF_PL";
    String systemPropertyKey = SystemPropertyTofPlFinder.SYSTEM_PROPERTY_KEY_PREFIX +
        "." + dummyClass.getName();
    System.getProperties().put(systemPropertyKey, TofPlClassName);
    TestObjectFactoryPriorityList<?> pl;
    try {
      pl = (TestObjectFactoryPriorityList<?>)$subject.findFor(dummyClass);
      assertEquals(_DummyTestObject1_TOF_PL.class, pl.getClass());
      assertEquals(pl, $subject.getCache().get(dummyClass));
    }
    catch (BeanFinderConfigurationException e) {
      fail("BeanFinderConfigurationException should not happen: " + e);
    }
    catch (NoBeanFoundException e) {
      fail("NoBeanFoundException should not happen: " + e);
    }
    finally {
      System.getProperties().remove(systemPropertyKey);
    }
  }

  /**
   * Test whether a system property defined TOF PL is found.
   */
  @Test
  public final void testFindFor1b() {
    Class<_DummyTestObject1> dummyClass = _DummyTestObject1.class;
    String TofPlClassName = "org.toryt_II.testobject.tofplfinder._DummyTestObject1_TOF_PL";
    Map<Class<?>, String> fqcnMap = new HashMap<Class<?>, String>(1);
    fqcnMap.put(dummyClass, TofPlClassName);
    $subject.setFqcnMap(fqcnMap);
    TestObjectFactoryPriorityList<?> pl;
    try {
      pl = (TestObjectFactoryPriorityList<?>)$subject.findFor(dummyClass);
      assertEquals(_DummyTestObject1_TOF_PL.class, pl.getClass());
      assertEquals(pl, $subject.getCache().get(dummyClass));
    }
    catch (BeanFinderConfigurationException e) {
      fail("BeanFinderConfigurationException should not happen: " + e);
    }
    catch (NoBeanFoundException e) {
      fail("NoBeanFoundException should not happen: " + e);
    }
  }

  /**
   * Test whether a TOF PL is found in the same package as the
   * base type.
   */
  @Test
  public final void testFindFor2() {
    Class<_DummyTestObject1> dummyClass = _DummyTestObject1.class;
    TestObjectFactoryPriorityList<?> pl;
    try {
      pl = (TestObjectFactoryPriorityList<?>)$subject.findFor(dummyClass);
      assertEquals(_TOF_PL__DummyTestObject1.class, pl.getClass());
      assertEquals(pl, $subject.getCache().get(dummyClass));
    }
    catch (BeanFinderConfigurationException e) {
      fail("BeanFinderConfigurationException should not happen: " + e);
    }
    catch (NoBeanFoundException e) {
      fail("NoBeanFoundException should not happen: " + e);
    }
  }

  /**
   * Test whether a TOF PL is found in the default toryt package.
   */
  @Test
  public final void testFindFor3() {
    TestObjectFactoryPriorityList<?> pl;
    try {
      pl = (TestObjectFactoryPriorityList<?>)$subject.findFor(Boolean.class);
      assertEquals(_TOF_PL_Boolean.class, pl.getClass());
      assertEquals(pl, $subject.getCache().get(Boolean.class));
    }
    catch (BeanFinderConfigurationException e) {
      fail("BeanFinderConfigurationException should not happen: " + e);
    }
    catch (NoBeanFoundException e) {
      fail("NoBeanFoundException should not happen: " + e);
    }
  }

  /**
   * Test whether a TOF PL is found in a package
   * listed in a system property.
   */
  @Test
  public final void testFindFor4() {
    // TODO
    fail("not implemented yet");
//    TestObjectFactoryPriorityList<?> pl;
//    try {
//      pl = (TestObjectFactoryPriorityList<?>)$subject.findFor(Boolean.class);
//      assertEquals(_TOF_PL_Boolean.class, pl.getClass());
//      assertEquals(pl, $subject.getCache().get(Boolean.class));
//    }
//    catch (BeanFinderConfigurationException e) {
//      fail("BeanFinderConfigurationException should not happen: " + e);
//    }
//    catch (NoBeanFoundException e) {
//      fail("NoBeanFoundException should not happen: " + e);
//    }
  }

  /**
   * Test what happens if no TOF PL is found.
   */
  @Test
  public final void testFindFor5() {
    TestObjectFactoryPriorityList<?> pl = null;
    try {
      pl = (TestObjectFactoryPriorityList<?>)$subject.findFor(_DummyTestObject2.class);
      fail("we expect a NoBeanFoundException");
    }
    catch (BeanFinderConfigurationException e) {
      fail("BeanFinderConfigurationException should not happen: " + e);
    }
    catch (NoBeanFoundException e) {
      assertNull(pl);
      assertEquals($subject.getActualBeanFinder(), e.getBeanFinder());
      /* TODO this is stupid, and suggest we better not have a bean finder in this exception
       * maybe it is interesting to store all failed attempts? a structure
       * of NoBeanFoundExceptions, or a list of all BeanFinders that gave up?
       */
      assertEquals(_DummyTestObject2.class, e.getArgument());
    }
  }

}

