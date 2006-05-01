package org.toryt.util_I.reflect;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Utility methods for reflection. Use these methods if you are
 * interested in the result of reflection, and not in a particular
 * reason why some reflective inspection might have failed.
 *
 * @mudo (jand) most methods are also in ppw-bean; consolidate
 * @mudo all methods should have easy 1 or 2 {@link ReflectionException}s
 *
 * @author    Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class Reflection {

  public static Method findMethod(Class<?> type, String signature) throws CannotGetMethodException {
    try {
      String search = type.getName() + "." + signature;
      Method[] methods = type.getDeclaredMethods();
      for (int i = 0; i < methods.length; i++) {
        if (methods[i].toString().indexOf(search) > -1) {
          return methods[i];
        }
      }
      throw new CannotGetMethodException(type, signature, null);
    }
    catch (NullPointerException npExc) {
      throw new CannotGetMethodException(type, signature, npExc);
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> Constructor<T> findConstructor(Class<T> type, String signature) throws CannotGetMethodException {
    try {
      Constructor<T>[] constructors = type.getConstructors();
      // unchecked cast because Class.getConstructors return Constructor[] instead of Constructor<T>[]
      for (int i = 0; i < constructors.length; i++) {
        if (constructors[i].toString().indexOf(signature) > -1) {
          return constructors[i];
        }
      }
      throw new CannotGetMethodException(type, signature, null);
    }
    catch (NullPointerException npExc) {
      throw new CannotGetMethodException(type, signature, npExc);
    }
  }

  public static Class<?> loadForName(String fqn) throws CannotGetClassException {
    try {
      return Class.forName(fqn);
    }
    catch (ClassNotFoundException cnfExc) {
      throw new CannotGetClassException(fqn, cnfExc);
    }
    catch (LinkageError lErr) {
      // also catches ExceptionInInitializerError
      throw new CannotGetClassException(fqn, lErr);
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
   * @throws CannotCreateInstanceException
   */
  @SuppressWarnings("unchecked")
  public static <_InstanceType_> _InstanceType_ instantiatePrefixed(ClassLoader cl,
                                                                    final String prefix,
                                                                    final String fqcn)
      throws CannotCreateInstanceException {
    try {
      String prefixedFqcn = prefixedFqcn(prefix, fqcn);
      try {
        return (_InstanceType_)java.beans.Beans.instantiate(cl, prefixedFqcn);
          /* unchecked cast */
      }
      catch (ClassNotFoundException cnfExc) {
        throw new CannotCreateInstanceException(prefixedFqcn, cnfExc);
      }
      catch (IOException ioExc) {
        throw new CannotCreateInstanceException(prefixedFqcn, ioExc);
      }
    }
    catch (NullPointerException npExc) {
      throw new CannotCreateInstanceException(prefix + "/" + fqcn, npExc);
    }
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
   * @throws NullPointerException
   *         (prefix == null) || (fqcn == null);
   */
  public static String prefixedFqcn(final String prefix,
                                    final String fqcn)
      throws NullPointerException {
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
   * @throws    CannotGetClassException
   *            Could not load class <code>fqClassName</code>.
   * @throws    CannotGetValueException
   *            Error retrieving value.
   */
  public static <_ConstantValueType_> _ConstantValueType_ constant(final String fqClassName,
                                                                   final String constantName)
      throws CannotGetClassException, CannotGetValueException {
    Class clazz = loadForName(fqClassName);
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
   * @throws    CannotGetValueException
   *            Error retrieving value.
   */
  @SuppressWarnings("unchecked")
  public static <_ConstantValueType_> _ConstantValueType_ constant(final Class<?> clazz,
                                                                   final String constantName)
      throws CannotGetValueException {
    try {
      Field field = clazz.getField(constantName); // NoSuchFieldException
                                                  // NullPointerException
                                                  // SecurityException
      return (_ConstantValueType_)field.get(null); // IllegalAccessException
                                                   // IllegalArgumentException
                                                   // NullPointerException
                                                   // ExceptionInInitializerError
          // unchecked cast
    }
    catch (NoSuchFieldException nsfExc) {
      throw new CannotGetValueException(clazz, constantName, nsfExc);
    }
    catch (NullPointerException npExc) {
      throw new CannotGetValueException(clazz, constantName, npExc);
    }
    catch (SecurityException sExc) {
      throw new CannotGetValueException(clazz, constantName, sExc);
    }
    catch (IllegalAccessException iaExc) {
      throw new CannotGetValueException(clazz, constantName, iaExc);
    }
    catch (IllegalArgumentException iaExc) {
      throw new CannotGetValueException(clazz, constantName, iaExc);
    }
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
   * @throws CannotGetPropertyException
   *         Cannot get the <code>BeanInfo</code> of <code>beanClass</code>,
   *         cannot find a property descriptor.
   */
  public static boolean hasPropertyReadMethod(final Class<?> beanClass,
                                              final String propertyName)
      throws CannotGetPropertyException {
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
   * @throws CannotGetPropertyException
   *         Cannot get the <code>BeanInfo</code> of <code>beanClass</code>,
   *         cannot find a property descriptor.
   */
  public static PropertyDescriptor getPropertyDescriptor(final Class<?> beanClass, final String propertyName)
      throws CannotGetPropertyException {
    BeanInfo beanInfo;
    try {
      beanInfo = Introspector.getBeanInfo(beanClass);
    }
    catch (IntrospectionException iExc) {
      throw new CannotGetPropertyException(beanClass, propertyName, iExc);
    }
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
      throw new CannotGetPropertyException(beanClass, propertyName, null);
    }
    return descriptor;
  }

  /**
   * The value to be written to the text file, as String.
   *
   * @throws    CannotGetValueException
   *            Could not get the property read method or got the read method,
   *            but something went wrong reading the value.
   */
  @SuppressWarnings("unchecked")
  public static <_ValueType_, _BeanType_> _ValueType_ getPropertyValue(final _BeanType_ bean,
                                                                       final String propertyName)
      throws CannotGetValueException {
    Method inspector;
    try {
      inspector = getPropertyReadMethod(bean.getClass(), propertyName);
    }
    catch (CannotGetMethodException cgmExc) {
      throw new CannotGetValueException(bean.getClass(), propertyName, cgmExc);
    }
    _ValueType_ result = null;
    assert inspector != null;
    try {
      result = (_ValueType_)inspector.invoke(bean, (Object[])null);
      /* unchecked cast */
    }
    catch (IllegalArgumentException iaExc) {
      assert false : "Should not happen, since there are no " +
                     "arguments, and the implicit argument is " +
                     "not null and of the correct type";
    }
    catch (NullPointerException npExc) {
      throw new CannotGetValueException(bean.getClass(), propertyName, npExc);
    }
    catch (IllegalAccessException iaExc) {
      throw new CannotGetValueException(bean.getClass(), propertyName, iaExc);
    }
    catch (InvocationTargetException itExc) {
      throw new CannotGetValueException(bean.getClass(), propertyName, itExc);
    }
    catch (ExceptionInInitializerError eiiErr) {
      throw new CannotGetValueException(bean.getClass(), propertyName, eiiErr);
    }
    return result;
  }

  /**
   * Returns the method object of the inspector of the property with name <code>propertyName</code>
   * of <code>beanClass</code>. If such a property or such an inspector does not exist, an
   * exception is thrown. This method only finds implemented methods, thus not methods in interfaces
   * or abstract methods.
   *
   * @todo check that This method only finds implemented methods, thus not methods in interfaces or
   *       abstract methods, and fix.
   * @param beanClass
   *        The bean class to get the property read method of
   * @param propertyName
   *        The programmatic name of the property we want to read
   * @pre beanClass != null;
   * @return result != null;
   * @throws CannotGetMethodException
   *         Cannot get the <code>BeanInfo</code> of <code>beanClass</code> or
   *         there is no read method for this property.
   */
  public static Method getPropertyReadMethod(final Class<?> beanClass,
                                             final String propertyName)
      throws CannotGetMethodException {
    Method inspector;
    try {
      inspector = getPropertyDescriptor(beanClass, propertyName).getReadMethod();
      // this can be null for a read-protected property
      if (inspector == null) {
        throw new CannotGetMethodException(beanClass, propertyName, null);
      }
      return inspector;
    }
    catch (CannotGetPropertyException cgpExc) {
      throw new CannotGetMethodException(beanClass, propertyName, cgpExc);
    }
  }

  /**
   * The different kinds of methods that can be instances of
   * {@link Method}.
   */
  public static enum MethodKind {
    INSTANCE_MUTATOR,
    INSTANCE_INSPECTOR,
    CLASS_MUTATOR,
    CLASS_INSPECTOR
  }

  /**
   * @pre method != null;
   */
  public static MethodKind methodKind(Method method) {
    assert method != null;
    if (Modifier.isStatic(method.getModifiers())) { // class
      if (method.getReturnType().equals(Void.TYPE)) { // mutator
        return MethodKind.CLASS_MUTATOR;
      }
      else { // inspector
        return MethodKind.CLASS_INSPECTOR;
      }
    }
    else { // instance
      if (method.getReturnType().equals(Void.TYPE)) { // mutator
        return MethodKind.INSTANCE_MUTATOR;
      }
      else { // inspector
        return MethodKind.INSTANCE_INSPECTOR;
      }
    }
  }

  /**
   * The different kinds of nested types, inner or static.
   * Static types can be top level types or nested.
   * Inner types are always nested.
   */
  public static enum TypeKind {
    INNER,
    STATIC
  }

  /**
   * @pre clazz != null;
   */
  public static TypeKind typeKind(Class<?> clazz) {
    assert clazz != null;
    if ((! clazz.isLocalClass()) || (Modifier.isStatic(clazz.getModifiers()))) {
      return TypeKind.STATIC;
    }
    else {
      return TypeKind.INNER;
    }
  }

  /**
   * Introduced to keep compiler happy in getting array type, while
   * discarding impossible exceptions.
   *
   * @pre componentType != null;
   * @return Class.forName(componentType.getName() + "[]");
   */
  @SuppressWarnings("unchecked")
  public static <_ComponentType_> Class<_ComponentType_[]> arrayClassForName(Class<_ComponentType_> componentType) {
    assert componentType != null;
    String arrayFqcn = "[L" + componentType.getName() + ";";
    Class<_ComponentType_[]> result = null;
    try {
      result = (Class<_ComponentType_[]>)Class.forName(arrayFqcn);
      /* unchecked cast */
    }
    /* exceptions cannot happen, since componentType was already
       laoded before this call */
    catch (ExceptionInInitializerError eiiErr) {
      assert false : "cannot happen";
    }
    catch (LinkageError lErr) {
      assert false : "cannot happen";
    }
    catch (ClassNotFoundException cnfExc) {
      assert false : "cannot happen";
    }
    return result;
  }

}