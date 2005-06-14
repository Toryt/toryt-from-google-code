package org.toryt;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.toryt.support.straightlist.ArrayStraightList;
import org.toryt.support.straightlist.StraightList;

import be.peopleware.bean_IV.Beans;


/**
 * <p>Static methods to retrieve {@link TestObjectList} instances.</p>
 *
 * @author    Jan Dockx
 * @author    PeopleWare n.v.
 */
public final class Cases {

  /*<section name="Meta Information">*/
  //------------------------------------------------------------------

  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";

  /*</section>*/


  private final static StraightList TOL_JAVA_LANG_INTEGER
      = new ArrayStraightList(
                new Integer[] {new Integer(0),
                               new Integer(1),
                               new Integer(-1),
                               new Integer(Integer.MAX_VALUE),
                               new Integer(Integer.MIN_VALUE),
//                               new Integer(-Integer.MIN_VALUE),
//                               new Integer(Integer.MAX_VALUE - 1),
//                               new Integer(Integer.MIN_VALUE + 1),
//                               new Integer(5),
//                               new Integer(45),
//                               new Integer(511),
//                               new Integer(512),
//                               new Integer(513),
//                               new Integer(1023),
//                               new Integer(1024),
//                               new Integer(1025),
//                               new Integer(-5),
//                               new Integer(-45),
//                               new Integer(-511),
//                               new Integer(-512),
//                               new Integer(-513),
//                               new Integer(-1023),
//                               new Integer(-1024),
                                 new Integer(-1025)});

  private final static StraightList TOL_JAVA_LANG_LONG
      = new ArrayStraightList(
            new Long[] {new Long(0),
                        new Long(1),
                        new Long(-1),
                        new Long(Long.MAX_VALUE),
                        new Long(Long.MIN_VALUE),
//                        new Long(-Long.MIN_VALUE),
//                        new Long(Long.MAX_VALUE - 1),
//                        new Long(Long.MIN_VALUE + 1),
//                        new Long(-Integer.MIN_VALUE),
//                        new Long(Integer.MAX_VALUE),
//                        new Long(Integer.MAX_VALUE - 1),
//                        new Long(Integer.MIN_VALUE + 1),
//                        new Long(5),
//                        new Long(45),
//                        new Long(511),
//                        new Long(512),
//                        new Long(513),
//                        new Long(1023),
//                        new Long(1024),
//                        new Long(1025),
//                        new Long(4 * Integer.MAX_VALUE),
//                        new Long(10 * Integer.MAX_VALUE),
//                        new Long(100000 * Integer.MAX_VALUE),
//                        new Long(Long.MAX_VALUE / 4),
//                        new Long(Long.MAX_VALUE / 2),
//                        new Long(-5),
//                        new Long(-45),
//                        new Long(-511),
//                        new Long(-512),
//                        new Long(-513),
//                        new Long(-1023),
//                        new Long(-1024),
//                        new Long(-1025),
//                        new Long(Integer.MIN_VALUE),
//                        new Long(4 * Integer.MIN_VALUE),
//                        new Long(10 * Integer.MIN_VALUE),
//                        new Long(100000 * Integer.MIN_VALUE),
//                        new Long(Long.MIN_VALUE / 4),
                          new Long(Long.MIN_VALUE / 2)});


  private final static StraightList TOL_JAVA_LANG_OBJECT
      = new ArrayStraightList(new Object[] {new Object()});

//  private final static TestObjectList TOL_JAVA_LANG_THROWABLE
//      = new TOL_Throwable(true);

  private final static StraightList TOL_JAVA_LANG_STRING
      = new ArrayStraightList(
            new String[] {"",
                      "Jan Dockx",
//                      "this is a test sentence with more then 1024 characters"
//                        + "*0 12345678901234567890123456789012345678901234567890"
//                        +    "12345678901234567890123456789012345678901234567890"
//                        + "*1 12345678901234567890123456789012345678901234567890"
//                        +    "12345678901234567890123456789012345678901234567890"
//                        + "*2 12345678901234567890123456789012345678901234567890"
//                        +    "12345678901234567890123456789012345678901234567890"
//                        + "*3 12345678901234567890123456789012345678901234567890"
//                        +    "12345678901234567890123456789012345678901234567890"
//                        + "*4 12345678901234567890123456789012345678901234567890"
//                        +    "12345678901234567890123456789012345678901234567890"
//                        + "*5 12345678901234567890123456789012345678901234567890"
//                        +    "12345678901234567890123456789012345678901234567890"
//                        + "*6 12345678901234567890123456789012345678901234567890"
//                        +    "12345678901234567890123456789012345678901234567890"
//                        + "*7 12345678901234567890123456789012345678901234567890"
//                        +    "12345678901234567890123456789012345678901234567890"
//                        + "*8 12345678901234567890123456789012345678901234567890"
//                        +    "12345678901234567890123456789012345678901234567890"
//                        + "*9 12345678901234567890123456789012345678901234567890"
//                        +    "12345678901234567890123456789012345678901234567890",
//                      "J",
//                      "Jan",
//                      "JanD",
//                      " JanD",
//                      "JanD ",
//                      " Jan Dockx",
//                      "Jan Dockx ",
//                      " Jan Dockx ",
                      " JanD "});

  // MUDO (dvankeer): This needs to be moved elsewhere

  /** Number of millisecongs in a day. */
  private static final long DAY     = 86400000L;
  /** Number of milliseconds in a week. */
  private static final long WEEK    = 604800000L;
  /** Number of milliseconds in a month. */
  private static final long MONTH   = 2629743830L;
  /** Number of milliseconds in a year. */
  private static final long YEAR    = 31556926000L;
  /** Number of milliseconds in a decade. */
  private static final long DECADE  = 3155692597470L;

  private final static StraightList TOL_JAVA_UTIL_DATE
    = new ArrayStraightList(
            new Date[] {new Date(),                                    // Today
                        new Date(System.currentTimeMillis() - DAY),    // Yesterday
                        new Date(System.currentTimeMillis() + DAY),    // Tommorow
//                        new Date(System.currentTimeMillis() - WEEK),   // Last Week
//                        new Date(System.currentTimeMillis() + WEEK),   // Next Week
//                        new Date(System.currentTimeMillis() - MONTH),  // Last Month
//                        new Date(System.currentTimeMillis() + MONTH),  // Next Month
//                        new Date(System.currentTimeMillis() - YEAR),   // Last year
//                        new Date(System.currentTimeMillis() + YEAR),   // Next year
//                        new Date(System.currentTimeMillis() - DECADE), // Last decade
//                        new Date(System.currentTimeMillis() + DECADE), // Next decade
                        new Date(0)});                                  // Unix Epoch

  private final static StraightList TOL_JAVA_LANG_BOOLEAN
      = new ArrayStraightList(new Boolean[] {Boolean.FALSE, Boolean.TRUE});

  private final static StraightList TOL_JAVA_LANG_DOUBLE
  = new ArrayStraightList(
            new Double[] { new Double(0.0),
                           new Double(1.0),
                           new Double(-1.0),
                           new Double(1.0 - Double.MIN_VALUE),
                           new Double(-1.0 + Double.MIN_VALUE),
                           new Double(0.5),
                           new Double(-0.5),
                           new Double(1.0E-2d),
                           new Double(-1.0E-2d),
                           new Double(Double.MAX_VALUE),
                           new Double(Double.MIN_VALUE),
                           new Double(Double.NEGATIVE_INFINITY),
                           new Double(Double.POSITIVE_INFINITY),
                           new Double(-Double.MIN_VALUE),
                           new Double(Double.MAX_VALUE - Double.MIN_VALUE),
                           new Double(Double.MIN_VALUE + Double.MIN_VALUE),
                           new Double(1.0E5d),
                           new Double(1.0E45),
                           new Double(1.0E-5),
                           new Double(1.0E-45),
                           new Double(-1.0E5d),
                           new Double(-1.0E45),
                           new Double(-1.0E-5),
                           new Double(-1.0E-45),
                           new Double(3.333333),
                           new Double(512.512),
                           new Double(-3.333333),
                           new Double(-512.512),
                           new Double(1000.01),
                           new Double(-1000.01)});

  private final static Map TEST_OBJECT_LISTS = new HashMap();

  static {
    addTol(Integer.class, TOL_JAVA_LANG_INTEGER);
    addTol(Long.class, TOL_JAVA_LANG_LONG);
    addTol(Object.class, TOL_JAVA_LANG_OBJECT);
    addTol(Boolean.class, TOL_JAVA_LANG_BOOLEAN);
//    addTol(Throwable.class, TOL_JAVA_LANG_THROWABLE);
    addTol(String.class, TOL_JAVA_LANG_STRING);
    addTol(Date.class, TOL_JAVA_UTIL_DATE);
    addTol(Double.class, TOL_JAVA_LANG_DOUBLE);
  }

  public static void addTol(Class forClass, StraightList tol) {
    assert forClass != null;
    assert tol != null;
    TEST_OBJECT_LISTS.put(forClass.getName(), tol);
  }

  /**
   * Return a {@link TestObjectList} that contains {@link TestObjectFactory}
   * instances that generate test objects of type <code>fqcn</code>. This
   * method first looks in a map for a matching entry. If no match is
   * found, we look for a class in the same package as <code>fqcn</code>,
   * with name <code>Beans.prefixedFqcn({@link #TOL_PREFIX}, fqcn)</code>.
   * If no match is found in that way, we look for a constant with name
   * {@link #TOL_CONSTANT_NAME} in a class in the same package as
   * <code>fqcn</code>, with name
   * <code>Beans.prefixedFqcn({@link #TEST_PREFIX}, fqcn)</code>.
   * If no match is found still, we try to generate an automatic
   * {@link BeanTestObjectList} for type <code>fqcn</code>. If this still
   * fails, we throw an {@link TestObjectListNotFoundException}.<br />
   * If a match is found outside the map, it is added to the map,
   * which so functions as a cache.
   *
   * @param fqcn
   *        The fully qualified class name of the type we want a
   *        {@link TestObjectList} for.
   */
  public static StraightList findTestObjectList(String fqcn)
      throws TorytException {
    StraightList result = null;
    result = (StraightList)TEST_OBJECT_LISTS.get(fqcn);
    if (result != null) {
      return result;
    }
    // not found in map, look for separate class
    result = findTOLClass(fqcn);
    if (result != null) {
      return cached(fqcn, result);
    }
    // not found as separate class; look for class variable
    result = findTOLVariable(fqcn);
    if (result != null) {
      return cached(fqcn, result);
    }
    // not found as class variable; try auto BeanTOL
    result = createBTOL(fqcn);
    if (result != null) {
      return cached(fqcn, result);
    }
    // BeanTOL impossible; give up
    throw new TorytException(null, null);
  }

  private static StraightList cached(String key, StraightList tol) {
    TEST_OBJECT_LISTS.put(key, tol);
    return tol;
  }

  /**
   * <strong>= {@value}</strong>
   */
  public static final String TOL_PREFIX = "_TOL_";

  /**
   * <strong>= {@value}</strong>
   */
  public static final String TEST_PREFIX = "_Test_";

  private static StraightList findTOLClass(String totn)
      throws TorytException {
    try {
      return (StraightList)Beans.
                  instantiatePrefixed(null, TOL_PREFIX, totn);
    }
    catch (IOException e) {
      throw new TorytException(null, null);
//                               "class "
//                          + Beans.prefixedFqcn(TOL_PREFIX, totn)
//                          + " found, but failed to read",
//                          e);
    }
    catch (ClassNotFoundException e) {
      return null;
    }
  }

  /**
   * <strong>= {@value}</strong>
   */
  public static final String TOL_CONSTANT_NAME = "TEST_OBJECT_LIST";

  private static StraightList findTOLVariable(String totn)
      throws TorytException {
    try {
      return (StraightList)Beans.constant(
                 Beans.prefixedFqcn(TEST_PREFIX, TOL_CONSTANT_NAME),
                 totn);
    }
    catch (NullPointerException e) {
      throw new TorytException(null, e);
    }
    catch (SecurityException e) {
      throw new TorytException(null, e);
    }
    catch (IllegalArgumentException e) {
      throw new TorytException(null, e);
    }
    catch (LinkageError e) {
      throw new TorytException(null, e);
    }
    catch (ClassNotFoundException e) {
      return null;
    }
    catch (NoSuchFieldException e) {
      return null;
    }
    catch (IllegalAccessException e) {
      throw new TorytException(null, e);
    }
  }

  private static /*Bean*/StraightList  createBTOL(String totn)
      throws TorytException {
    Object createAWarning;
    return null; // MUDO stub
  }

  /**
   * Convenience method to look for {@link TestObjectList} instances
   * based on a type, instead of on a FQCN.
   *
   * @see #findTestObjectList(String)
   *
   * @pre type != null;
   * @return findTestObjectList(type.getName());
   * @throws TestObjectListNotFoundException
   * @throws TestFault
   */
  public static StraightList findTestObjectList(Class type)
      throws TorytException {
    assert type != null;
    return findTestObjectList(type.getName());
  }

//  /**
//   * Convenience method to look for {@link TestObjectList} instances,
//   * and immediately return a list iterator of that instance.
//   *
//   * @see #findTestObjectList(String)
//   *
//   * @return findTestObjectList(fqcn).listIterator();
//   * @throws TestObjectListNotFoundException
//   * @throws TestFault
//   */
//  public static ListIterator findTestObjectListIterator(String fqcn)
//      throws TestObjectListNotFoundException, TestFault {
//    return findTestObjectList(fqcn).listIterator();
//  }
//
//  /**
//   * Convenience method to look for {@link TestObjectList} instances,
//   * and immediately return a list iterator of that instance, given
//   * a start index.
//   *
//   * @see #findTestObjectList(String)
//   *
//   * @return findTestObjectList(fqcn).listIterator();
//   * @throws TestObjectListNotFoundException
//   * @throws TestFault
//   * @throws IndexOutOfBoundsException
//   */
//  public static ListIterator findTestObjectListIterator(String fqcn, int startIndex)
//      throws TestObjectListNotFoundException, TestFault, IndexOutOfBoundsException {
//    return findTestObjectList(fqcn).listIterator(startIndex);
//  }

}
