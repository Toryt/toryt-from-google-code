package org.toryt_II.testobject;


import java.beans.Beans;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.CannotGetValueException;
import org.toryt.util_I.reflect.CannotCreateInstanceException;
import org.toryt.util_I.reflect.Reflection;
import org.toryt_II.contract.ClassContract;
import org.toryt_II.contract.Contract;
import org.toryt_II.main.Contracts;


/**
 * <p>Implementation of {@link TofPlFactory} with the default Toryt strategy.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class DefaultTofPlFactory implements TofPlFactory {


  private final static Log _LOG = LogFactory.getLog(DefaultTofPlFactory.class);


  /**
   * @invar Collections.noNull(TOF_PL_MAP);
   * @invar (forall TestObjectFactoryPriorityList<_TestObjectType_> pl;
   *            TOF_PL_MAP.contains(pl);
   *            pl.getPriorityElementType() == TestObjectFactory.class);
   * @invar (forall Class<?> c; TOF_PL_MAP.containsKey(c);
   *            TOF_PL_MAP.get(c) instanceof TestObjectFactoryPriorityList<c>);
   * @invar (forall Map.Entry e; TOF_PL_MAP.entrySet().contains(e);
   *            (forall TestObjectFactory tof; e.getValue().contains(tof);
   *                e.getKey() == tof.getTestObjectClass()));
   */
  private final Map<Class<?>, TestObjectFactoryPriorityList<?>> TOF_PL_MAP =
      new HashMap<Class<?>, TestObjectFactoryPriorityList<?>>();

  /**
   * <p>The prefix for system properties that have the FQCN of a
   *   <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   *   for a given class as value.</p>
   *
   * <p><strong>= {@value}</strong></p>
   */
  public final static String SYSTEM_PROPERTY_KEY_PREFIX = "org.toryt.TofPl.";

  /**
   * <p>The key for the system property that has a comma-separated list
   *   of package names that will be used as a base to look for a
   *   <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   *   for a given class.</p>
   *
   * <p><strong>= {@value}</strong></p>
   */
  public final static String BASE_PACKAGE_LIST_SYSTEM_PROPERTY_KEY =
      SYSTEM_PROPERTY_KEY_PREFIX + "packages";

  /**
   * <p>The pattern used to separate base package name entries in the
   *   system property with key {@link #BASE_PACKAGE_LIST_SYSTEM_PROPERTY_KEY}.</p>
   *
   * <p><strong>= {@value}</strong></p>
   */
  public final static String BASE_PACKAGE_LIST_SYSTEM_PROPERTY_SEPARATOR_PATTERN =
      "[,\\s]*";

  /**
   * <p>This package is always at the end of the
   *   {@link #getBasePackageNamesList(Class)}.</p>
   *
   * <p><strong>= {@value}</strong></p>
   */
  public final static String DEFAULT_BASE_PACKAGE =
      DefaultTofPlFactory.class.getPackage().getName();

  /**
   * <p>The prefix for the class name that we will use to look for a
   *   <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   *   for a given class in the package of that class.</p>
   *
   * <p><strong>= {@value}</strong></p>
   */
  public final static String CLASS_NAME_PREFIX = "_TOF_PL_";

  /**
   * <p>The name of <code>final static</code> (constant) fields that refer to
   *   a <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   *   for a given class in its {@link Contract}.</p>
   *
   * <p><strong>= {@value}</strong></p>
   */
  public final static String CONTRACT_CONSTANT_NAME = "TOF_PL";

  /**
   * <p>The base package name list for <code>forClass</code>. These are the names of
   *   packages that are used as a starting point to look for
   *   <acronym title="Test Object Factory Priority List">TOF PL</acronym>-implementations
   *   for <code>forClass</code>.
   *   This is {@link #getBasePackageNamesList()}, prepended with the
   *   package name of <code>forClass</code>.</p>
   *
   * @pre forClass != null;
   * @result result != null;
   * @result Collections.noNull(result);
   * @result result.get(0).equals(forClass.getPackage().getName());
   * @result (forall int i; (i >= 0) && (i < getBasePackageNamesList().size());
   *              result.get(i + 1).equals(getBasePackageNamesList().get(i)));
   */
  public final static List<String> getBasePackageNamesList(Class<?> forClass) {
    assert forClass != null;
    List<String> result = new ArrayList<String>(getBasePackageNamesList().size() + 1);
    result.add(forClass.getPackage().getName());
    result.addAll(getBasePackageNamesList());
    if (_LOG.isDebugEnabled()) {
      _LOG.debug("base package name class for class " + forClass + " is " + result);
    }
    return java.util.Collections.unmodifiableList(result);
  }

  /**
   * <p>The base package name list. These are the names of packages that are used
   *   as a starting point to look for
   *   <acronym title="Test Object Factory Priority List">TOF PL</acronym>-implementations
   *   for a given class.</p>
   * <p>The last entry in this list is always {@link #DEFAULT_BASE_PACKAGE}.
   *   We look for a system property with key {@link #BASE_PACKAGE_LIST_SYSTEM_PROPERTY_KEY}.
   *   If such a system property exists, it is treated as a comma-separated list of
   *   base package names, that are added, in order, in front of
   *   {@link #DEFAULT_BASE_PACKAGE}. The separator in this system property value
   *   is the comma ('<code>,</code>'), but wite spacing is allowed (the entries are split
   *   using the regular expression pattern
   *   {@link #BASE_PACKAGE_LIST_SYSTEM_PROPERTY_SEPARATOR_PATTERN}).</p>
   *
   * @result result != null;
   * @result Collections.noNull(result);
   * @result result.get(result.size() - 1).equals(DEFAULT_BASE_PACKAGE);
   * @result Arrays.equals(System.getProperty(BASE_PACKAGE_LIST_SYSTEM_PROPERTY_KEY).
   *                           split(BASE_PACKAGE_LIST_SYSTEM_PROPERTY_SEPARATOR_PATTERN),
   *                       result.subList(0, result.size() - 1).toArray());
   */
  public final static List<String> getBasePackageNamesList() {
    return BASE_PACKAGE_NAMES_LIST;
  }

  /**
   * @invar BASE_PACKAGE_NAMES_LIST != null;
   * @invar Collections.noNull(BASE_PACKAGE_NAMES_LIST);
   */
  private final static List<String> BASE_PACKAGE_NAMES_LIST;

  static {
    _LOG.debug("initializing base package name list");
    List<String> list = null;
    _LOG.debug("  looking for system property with key " + BASE_PACKAGE_LIST_SYSTEM_PROPERTY_KEY);
    String csv = System.getProperty(BASE_PACKAGE_LIST_SYSTEM_PROPERTY_KEY);
    _LOG.debug("  value of system property: \"" + csv + "\"");
    if (csv != null) {
      String[] packageNames = csv.split(BASE_PACKAGE_LIST_SYSTEM_PROPERTY_SEPARATOR_PATTERN);
      if (_LOG.isDebugEnabled()) {
        _LOG.debug("  split system property value:");
        for (int i = 0; i < packageNames.length; i++) {
          _LOG.debug("    " + packageNames[i]);
        }
      }
      list = new ArrayList<String>(packageNames.length + 1);
      list.addAll(Arrays.asList(packageNames));
    }
    else {
      list = new ArrayList<String>(1);
    }
    list.add(DEFAULT_BASE_PACKAGE);
    if (_LOG.isInfoEnabled()) {
      _LOG.info("base package names list initialized to " + list);
    }
    BASE_PACKAGE_NAMES_LIST = java.util.Collections.unmodifiableList(list);
  }

  /**
   * <p>Return the <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   *   for class <code>forClass</code>.</p>
   * <ul>
   *   <li>To find such an <acronym title="Test Object Factory Priority List">TOF PL</acronym>,
   *     first we look in the table that caches
   *     <acronym title="Test Object Factory Priority List">TOF PL</acronym>s.
   *     If {@link #getCachedTofPl(Class)} returns an instance, that result is returned by
   *     this method. If {@link #getCachedTofPl(Class)} returns <code>null</code>,
   *     we try the next step.</li>
   *   <li>Next, we see if a system property with key
   *     <code>SYSTEM_PROPERTY_PREFIX + forClass.getName()</code> exists. The value of this
   *     property should be the FQCN of a
   *     <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   *     with a default constructor with {@link TestObjectFactory TestObjectFactories}
   *     for <code>forClass</code>. We try to instantiate an instance of this class
   *     using JavaBean instantiation using the system class loader. If this succeeds,
   *     that instance is cached, and is returned. If any of this fails, we try the
   *     next step.</li>
   *   <li>Next, we try to find a class in the subpackages of packages listed by
   *     {@link #getBasePackageNamesList(Class) getBasePackageNamesList(forClass)}.
   *     The base class name of <code>forClass</code>
   *     is prefixed with {@link #CLASS_NAME_PREFIX}, the FQ package name of the
   *     package of <code>forClass</code>, and an entry from the base package name list.
   *     This is, with <code><var>basePackageName</var></code> an entry from the base package
   *     name list, <code><var>forClassPackageName</var></code> the FQ package name of the
   *     package of <code>forClass</code>, and <code><var>forClassBaseName</var></code> the
   *     base class name of <code>forClass</code>: <code><var>basePackageName</var> + "."
   *     + <var>forClassPackageName</var> + "." + {@link #CLASS_NAME_PREFIX} +
   *     <var>forClassBaseName</var></code>.
   *     We try to instantiate an object from a class with this name using JavaBean
   *     instantiation. If this succeeds, that instance is cached, and is returned.
   *     If this fails for all entries in {@link #getBasePackageNamesList()},
   *     we try the next step.</li>
   *   <li>Finally, we see if a <code>final static</code> (constant) field exists
   *     in the contract for <code>forClass</code> with name {@link #CONTRACT_CONSTANT_NAME}.
   *     If there is, and its contents is not <code>null</code>, this instance is added to
   *     the cache and returned.</li>
   *   <li>If no <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   *     could be found by the previous steps, we give up, and throw a
   *     {@link NoTofPlFoundException}.
   * </ul>
   *
   * @pre forClass != null;
   * @result != null;
   * @result result instanceof PriorityListTestObjectFactoryPriorityList<forClass>;
   */
  public TestObjectFactoryPriorityList<?> getTofPl(Class<?> forClass) throws NoTofPlFoundException {
    assert forClass != null;
    TestObjectFactoryPriorityList<?> result = getCachedTofPl(forClass);
    if (result == null) {
      result = getTofPlFromSystemProperty(forClass);
    }
    if (result == null) {
      result = getTofPlFromBasePackageList(forClass);
    }
    if (result == null) {
      result = getTofPlFromContractConstant(forClass);
    }
    if (result == null) {
      throw new NoTofPlFoundException(this, forClass);
    }
    assert result != null;
    return result;
  }

  /**
   * @pre forClass != null;
   * @pre getCachedTofPl(forClass) == null;
   */
  private TestObjectFactoryPriorityList<?> getTofPlFromSystemProperty(Class<?> forClass) {
    assert forClass != null;
    assert getCachedTofPl(forClass) == null;
    assert forClass == null;
    _LOG.debug("trying to instantiate TOF PL for class " + forClass.getName() +
              " via FQCN in system property");
    Properties properties = System.getProperties();
    String key = SYSTEM_PROPERTY_KEY_PREFIX + forClass.getName();
    _LOG.debug("  system property key: \"" + key + "\"");
    String tofPlFqcn = properties.getProperty(key);
    _LOG.debug("  value of system property: \"" + tofPlFqcn + "\"");
    _LOG.debug("  trying to instantiate class with FQCN " + tofPlFqcn + " with default constructor");
    TestObjectFactoryPriorityList<?> result = null;
    try {
      result = (TestObjectFactoryPriorityList<?>)Beans.instantiate(null, tofPlFqcn);
      // runtime cannot check against generic type instantiation
      /*
       * TODO how can we do away with this warning?
       */
      _LOG.info("Created TOF PL for class " + forClass +
               " from class " + tofPlFqcn + " (value of system property " +
               key + ")");
      addCachedTofPl(forClass, result);
    }
    catch (AlreadyHasTofPlForClassException e) {
      assert false : "AlreadyHasTofPlForClassException should not happen: " + e;
    }
    catch (IOException ioExc) {
      logTofPlFromSystemPropertyInstantiationProblem(forClass, tofPlFqcn, key, ioExc);
    }
    catch (ClassNotFoundException cnfExc) {
      logTofPlFromSystemPropertyInstantiationProblem(forClass, tofPlFqcn, key, cnfExc);
    }
    catch (ClassCastException ccExc) {
      logTofPlFromSystemPropertyInstantiationProblem(forClass, tofPlFqcn, key, ccExc);
    }
    return result;
  }

  private static void logTofPlFromSystemPropertyInstantiationProblem(Class<?> forClass,
                                                                     String fqcn,
                                                                     String key,
                                                                     Exception exc) {
    if (_LOG.isWarnEnabled()) {
      _LOG.warn("Could not create TOF PL for class " + forClass +
               " using FQCN \"" + fqcn + "\" (FQCN found in system property \"" +
               key + "\")", exc);
    }
  }

  /**
   * @pre forClass != null;
   * @pre getCachedTofPl(forClass) == null;
   */
  private TestObjectFactoryPriorityList<?> getTofPlFromBasePackageList(Class<?> forClass) {
    assert forClass != null;
    assert getCachedTofPl(forClass) == null;
    TestObjectFactoryPriorityList<?> result = null;
    Iterator<String> iter = getBasePackageNamesList(forClass).iterator();
    while (iter.hasNext()) {
      String packageName = iter.next();
      String fqcnToPrefix = packageName + "." + forClass.getName();
      try {
        result = (TestObjectFactoryPriorityList<?>)Reflection.instantiatePrefixed(null, CLASS_NAME_PREFIX, fqcnToPrefix);
        // runtime cannot check against generic type instantiation
        /*
         * TODO how can we do away with this warning?
         */
        assert result != null; // we only get here if this succeeds
        if (_LOG.isInfoEnabled()) {
          _LOG.info("Created TOF PL for class " + forClass +
                   " from class " + Reflection.prefixedFqcn(CLASS_NAME_PREFIX, fqcnToPrefix) +
                   " (using base package list)");
        }
        addCachedTofPl(forClass, result);
        break;
      }
      catch (AlreadyHasTofPlForClassException e) {
        assert false : "AlreadyHasTofPlForClassException should not happen: " + e;
      }
      catch (CannotCreateInstanceException cnibExc) {
        if (_LOG.isDebugEnabled()) {
          _LOG.debug("Could not create TOF PL for class " + forClass +
                    " using FQCN \"" + Reflection.prefixedFqcn(CLASS_NAME_PREFIX, fqcnToPrefix) +
                    "\" (using base packages in base package list)", cnibExc);
        }
      }
    }
    return result; // null if no TOF PL found this way
  }

  /**
   * @pre forClass != null;
   * @pre getCachedTofPl(forClass) == null;
   */
  private TestObjectFactoryPriorityList<?> getTofPlFromContractConstant(Class<?> forClass) {
    assert forClass != null;
    assert getCachedTofPl(forClass) == null;
    TestObjectFactoryPriorityList<?> result = null;
    ClassContract contract = null;
    try {
      contract = Contracts.classContractInstance(forClass);
      if (contract != null) {
        result = (TestObjectFactoryPriorityList<?>)Reflection.constant(contract.getClass(), CONTRACT_CONSTANT_NAME);
        // runtime cannot check against generic type instantiation
        /*
         * TODO how can we do away with this warning?
         */
        if (result != null) {
          if (_LOG.isInfoEnabled()) {
            _LOG.info("Retrieved TOF PL for class " + forClass +
                     " from contract constant " + contract.getClass() + "." +
                     CONTRACT_CONSTANT_NAME);
            addCachedTofPl(forClass, result);
          }
        }
      }
    }
    catch (AlreadyHasTofPlForClassException e) {
      assert false : "AlreadyHasTofPlForClassException should not happen: " + e;
    }
    catch (CannotGetValueException cngcExc) {
      if (_LOG.isDebugEnabled()) {
        _LOG.debug("Could not retrieve TOF PL for class " + forClass +
                  "\" (from contract constant " + CONTRACT_CONSTANT_NAME + ", contract is " +
                  contract + ")", cngcExc);
      }
    }
    catch (CannotCreateInstanceException cnibExc) {
      if (_LOG.isDebugEnabled()) {
        _LOG.debug("Could not retrieve TOF PL for class " + forClass +
                  "\" (from contract constant " + CONTRACT_CONSTANT_NAME + ", contract is " +
                  contract + ")", cnibExc);
      }
    }
    return result;
  }

  /**
   * Return the cached <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   * for class <code>forClass</code>, if there is a cached instance. Return <code>null</code>
   * if not.
   *
   * @basic
   */
  public TestObjectFactoryPriorityList<?> getCachedTofPl(Class<?> forClass) {
    return TOF_PL_MAP.get(forClass);
  }

  /**
   * Add a <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   * to the cached collection of <acronym title="Test Object Factory Priority List">TOF PL</acronym>s,
   * so that it can be retrieved by {@link #getTofPl(Class)}.
   *
   * @pre forClass != null;
   * @pre tofPl != null;
   * @pre tofPl instanceof TestObjectFactoryPriorityList<forClass>;
   * @post new.getCachedTofPl(forClass) == tofPl;
   */
  public void addCachedTofPl(final Class<?> forClass, final TestObjectFactoryPriorityList<?> tofPl)
      throws AlreadyHasTofPlForClassException {
    assert forClass != null;
    assert tofPl != null;
    assert tofPl.getPriorityElementType() == TestObjectFactory.class;
    // cannot test generic type isntantiation at runtime
    if (TOF_PL_MAP.get(forClass) != null) {
      throw new AlreadyHasTofPlForClassException(this, forClass, TOF_PL_MAP.get(forClass));
    }
    TOF_PL_MAP.put(forClass, tofPl);
    _LOG.info("added TOF PL to cache for class " + forClass + " (" + tofPl + ")");
  }

}