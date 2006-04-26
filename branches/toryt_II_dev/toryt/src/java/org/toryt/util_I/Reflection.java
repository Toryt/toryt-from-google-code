package org.toryt.util_I;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.toryt_II.Contract;
import org.toryt_II.TorytException;


/**
 * @mudo (jand) most methods are also in ppw-bean; consolidate
 *
 * @author    Jan Dockx
 */
public class Reflection {

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



  public static Method findMethod(Class type, String signature, Contract excSource)
      throws TorytException {
    assert type != null;
    assert signature != null;
    String search = type.getName() + "." + signature;
    Method[] methods = type.getDeclaredMethods();
    for (int i = 0; i < methods.length; i++) {
      if (methods[i].toString().indexOf(search) > -1) {
        return methods[i];
      }
    }
    throw new TorytException(excSource, null);
  }

  public static Constructor findConstructor(Class type, String signature, Contract excSource)
  throws TorytException {
    assert type != null;
    assert signature != null;
    Constructor[] constructors = type.getConstructors();
    for (int i = 0; i < constructors.length; i++) {
      if (constructors[i].toString().indexOf(signature) > -1) {
        return constructors[i];
      }
    }
    throw new TorytException(excSource, null);
  }

  public static Class loadForName(String fqn) throws TorytException {
    assert fqn != null;
    try {
      return Class.forName(fqn);
    }
    catch (ClassNotFoundException e) {
      throw new TorytException(null, e);
    }
  }

  /**
   * Instantiate an object of a type
   * <code>prefixedFqcn(prefix, fqcn)</code>.
   *
   * @param cl
   *        The class-loader from which we should create
   *        the bean. If this is null, then the system class-loader
   *        is used.
   * @param prefix
   *        The prefix to add before the class name.
   * @param fqcn
   *        The original fully qualified class name to derive
   *        the prefixed class name from.
   * @throws ClassNotFoundException
   * @throws IOException
   */
  public static Object instantiatePrefixed(ClassLoader cl,
                                           final String prefix,
                                           final String fqcn)
      throws IOException, ClassNotFoundException {
    return java.beans.Beans.instantiate(cl, prefixedFqcn(prefix, fqcn));
  }

  private final static String PREFIXED_FQCN_PATTERN = "\\.";

  private final static String EMPTY = "";

  private final static String DOT = ".";

  /**
   * Return a fully qualified class name that is in the same package
   * as <code>fqcn</code>, and has as class name
   * <code>prefix + <var>ClassName</var></code>.
   *
   * @param prefix
   *        The prefix to add before the class name.
   * @param fqcn
   *        The fully qualified class name to start from.
   */
  public static String prefixedFqcn(final String prefix,
                                    final String fqcn) {
    String[] parts = fqcn.split(PREFIXED_FQCN_PATTERN);
    String prefixedName = prefix + parts[parts.length - 1];
    String result = EMPTY;
    for (int i =0; i < parts.length - 1; i++) {
      result = result + parts[i] + DOT;
    }
    result = result + prefixedName;
    return result;
  }

  /**
   * Returns the constant(public final static) with the given fully qualified
   * name.
   *
   * @param     fqClassName
   *            The fully qualified class name of the type to look in
   *            for the constant.
   * @param     constantName
   *            The name of the constant whose value to return.
   * @return    Object
   *            The value of the field named <code>constantName</code>
   *            in class <code>fqClassName</code>.
   * @throws    LinkageError
   *            Error retrieving value.
   * @throws    ClassNotFoundException
   *            Could not find class <code>fqClassName</code>.
   * @throws    NoSuchFieldException
   *            Could not find a field named <code>constantName</code>
   *            in class <code>fqClassName</code>.
   * @throws    NullPointerException
   *            Error retrieving value.
   * @throws    SecurityException
   *            Not allowed to read the value of the field named
   *            <code>constantName</code>
   *            in class <code>fqClassName</code>.
   * @throws    IllegalAccessException
   *            The field named
   *            <code>constantName</code>
   *            in class <code>fqClassName</code> is not public.
   * @throws    IllegalArgumentException
   *            Error retrieving value.
   */
  public static Object constant(final String fqClassName,
                                final String constantName)
      throws LinkageError,
             ClassNotFoundException,
             NoSuchFieldException,
             NullPointerException,
             SecurityException,
             IllegalAccessException,
             IllegalArgumentException {
    Class clazz = Class.forName(fqClassName); // LinkageError, ClassNotFoundException
    return constant(clazz, constantName);
  }

  /**
   * Returns the constant(public final static) with the given fully qualified
   * name.
   *
   * @param     clazz
   *            The type to look in for the constant.
   * @param     constantName
   *            The name of the constant whose value to return.
   * @return    Object
   *            The value of the field named <code>constantName</code>
   *            in class <code>clazz</code>.
   * @throws    NoSuchFieldException
   *            Could not find a field named <code>constantName</code>
   *            in class <code>fqClassName</code>.
   * @throws    NullPointerException
   *            Error retrieving value.
   * @throws    SecurityException
   *            Not allowed to read the value of the field named
   *            <code>constantName</code>
   *            in class <code>fqClassName</code>.
   * @throws    IllegalAccessException
   *            The field named
   *            <code>constantName</code>
   *            in class <code>fqClassName</code> is not public.
   * @throws    IllegalArgumentException
   *            Error retrieving value.
   */
  public static Object constant(final Class clazz,
                                final String constantName)
      throws NoSuchFieldException,
             NullPointerException,
             SecurityException,
             IllegalAccessException,
             IllegalArgumentException {
    Field field = clazz.getField(constantName); // NoSuchFieldException
                                                // NullPointerException
                                                // SecurityException
    return field.get(null); // IllegalAccessException
                            // IllegalArgumentException
                            // NullPointerException
                            // ExceptionInInitializerError
  }

  /**
   * Check whether <code>beanClass</code> has a no-arguments getter for
   * <code>propertyName</code>. This method only finds implemented methods,
   * thus not methods in interfaces or abstract methods.
   *
   * @todo check that This method only finds implemented methods,
   * thus not methods in interfaces or abstract methods, and fix.
   *
   * @param     beanClass
   *            The bean class to get the property read method of
   * @param     propertyName
   *            The programmatic name of the property we want to read
   * @pre       beanClass != null;
   * @throws    IntrospectionException
   *            Cannot get the <code>BeanInfo</code> of <code>beanClass</code>,
   *            cannot find a property descriptor.
   */
  public static boolean hasPropertyReadMethod(final Class beanClass,
                                              final String propertyName)
      throws IntrospectionException {
    assert beanClass != null;
    return getPropertyDescriptor(beanClass, propertyName).getReadMethod() != null;
  }

  /**
   * Returns the {@link PropertyDescriptor} of the property with
   * name <code>propertyName</code> of <code>beanClass</code>. If such a
   * property or such an descriptor does not exist, an exception is thrown.
   *
   * @param     beanClass
   *            The bean class to get the property descriptor of
   * @param     propertyName
   *            The programmatic name of the property we want the descriptor for
   * @return    PropertyDescription
   *            result != null;
   * @throws    IntrospectionException
   *            Cannot get the <code>BeanInfo</code> of <code>beanClass</code>,
   *            cannot find a property descriptor.
   *
   * @pre       beanClass != null;
   */
  public static PropertyDescriptor
      getPropertyDescriptor(final Class beanClass, final String propertyName)
      throws IntrospectionException {
    assert beanClass != null;
    BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
                                 // throws IntrospectionException
    PropertyDescriptor[] propertyDescriptors =
        beanInfo.getPropertyDescriptors();
                                 // entries in the array are never null
    PropertyDescriptor descriptor = null;
    for (int i = 0; i < propertyDescriptors.length; i++) {
      if (propertyDescriptors[i].getName().equals(propertyName)) {
                // PropertyDescriptors always return a non-null name
        descriptor = propertyDescriptors[i];
        break;
      }
    }
    if (descriptor == null) {
      throw new IntrospectionException("No property descriptor found for " //$NON-NLS-1$
                                       + propertyName
                                       + " in " //$NON-NLS-1$
                                       + beanClass.getName());
    }
    return descriptor;
  }

  /**
   * The value to be written to the text file, as String.
   *
   * @param     bean
   *            The bean to get the property value of
   * @param     propertyName
   *            The programmatic name of the property we want to read
   * @return    Object
   *            @todo (dvankeer): (JAVADOC) Write description.
   * @throws    NullPointerException
   *            bean == null;
   * @throws    IntrospectionException
   *            Cannot get the <code>BeanInfo</code> of <code>bean</code> class.
   * @throws    NoSuchMethodException
   *            There is no read method for this property in the bean.
   * @throws    IllegalAccessException
   *            This user is not allowed to access the read method of
   *            the <code>propertyName()</code>-property of <code>bean</code>.
   * @throws    InvocationTargetException
   *            The read method of the property <code>propertyName</code>,
   *            applied to <code>bean</code>, has thrown an exception.
   */
  public static Object getPropertyValue(final Object bean,
                                        final String propertyName)
      throws NullPointerException,
             IntrospectionException,
             NoSuchMethodException,
             IllegalAccessException,
             InvocationTargetException {
    Method inspector = getPropertyReadMethod(bean.getClass(), propertyName);
    // != null; throws loads of exceptions
    Object result = null;
    try {
      result = inspector.invoke(bean, null);
    }
    catch (IllegalArgumentException iaExc) {
      assert false : "Should not happen, since there are no " //$NON-NLS-1$
                     + "arguments, and the implicit argument is " //$NON-NLS-1$
                     + "not null and of the correct type"; //$NON-NLS-1$
    }
    /* ExceptionInInitializerError can occur with invoke, but we do not
        take into account errors */
    return result;
  }

  /**
   * Returns the method object of the inspector of the property with
   * name <code>propertyName</code> of <code>beanClass</code>. If such a
   * property or such an inspector does not exist, an exception is thrown.
   * This method only finds implemented methods,
   * thus not methods in interfaces or abstract methods.
   *
   * @todo check that This method only finds implemented methods,
   * thus not methods in interfaces or abstract methods, and fix.
   *
   * @param     beanClass
   *            The bean class to get the property read method of
   * @param     propertyName
   *            The programmatic name of the property we want to read
   * @pre       beanClass != null;
   * @return    result != null;
   * @throws    IntrospectionException
   *            Cannot get the <code>BeanInfo</code> of <code>beanClass</code>.
   * @throws    NoSuchMethodException
   *            There is no read method for this property.
   */
  public static Method getPropertyReadMethod(final Class beanClass,
                                             final String propertyName)
      throws IntrospectionException, NoSuchMethodException {
    assert beanClass != null;
    Method inspector
        = getPropertyDescriptor(beanClass, propertyName).getReadMethod();
        // this can be null for an read-protected property
    if (inspector == null) {
      throw new NoSuchMethodException("No read method for property " //$NON-NLS-1$
                                      + propertyName);
    }
    return inspector;
  }

}