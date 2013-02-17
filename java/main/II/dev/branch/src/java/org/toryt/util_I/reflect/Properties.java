/*<license>
Copyright 2006 - $Date$ by Jan Dockx.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</license>*/

package org.toryt.util_I.reflect;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Utility methods for reflection. Use these methods if you are
 *   interested in the result of reflection, and not in a particular
 *   reason why some reflective inspection might have failed.</p>
 *
 * @idea (jand) most methods are also in ppw-bean; consolidate
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class Properties {

  private Properties() {
    // NOP
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
      inspector = Properties.getPropertyDescriptor(beanClass, propertyName).getReadMethod();
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
   * The value to be written to the text file, as String.
   *
   * @throws    CannotGetValueException
   *            Could not get the property read method or got the read method,
   *            but something went wrong reading the value.
   */
  public static <_Value_, _Bean_> _Value_ getPropertyValue(final _Bean_ bean,
                                                           final String propertyName)
      throws CannotGetValueException {
    Method inspector;
    try {
      inspector = getPropertyReadMethod(bean.getClass(), propertyName);
    }
    catch (CannotGetMethodException cgmExc) {
      throw new CannotGetValueException(bean.getClass(), propertyName, cgmExc);
    }
    assert inspector != null;
    try {
      @SuppressWarnings("unchecked") _Value_ result =
          (_Value_)inspector.invoke(bean, (Object[])null);
      return result;
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
    return null;
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

}