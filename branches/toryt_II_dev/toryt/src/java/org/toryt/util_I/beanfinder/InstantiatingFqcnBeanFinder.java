/*<license>
Copyright 2006 - $Date$ by the authors mentioned below.

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

package org.toryt.util_I.beanfinder;


import java.beans.Beans;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Implementation of {@link BeanFinder} that instantiates
 *   a fresh bean of a class whose FQCN is given.
 *   The FQCN is based on the {@code argument}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class InstantiatingFqcnBeanFinder<_Argument_>
    extends FixedBeanTypeBeanFinder<_Argument_> {


  private final static Log _LOG = LogFactory.getLog(InstantiatingFqcnBeanFinder.class);

  public final static String EMPTY = "";


  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre beanType != null;
   * @post getBeanType() != beanType;
   * @post isFqcnFinal() == fqcnFinal;
   */
  protected InstantiatingFqcnBeanFinder(Class<?> beanType, boolean fqcnFinal) {
    super(beanType);
    $fqcnFinal = fqcnFinal;
  }

  /*</construction>*/



  /*<property name="fqcn">*/
  //------------------------------------------------------------------

  /**
   * @pre argument != null;
   * @basic
   * @throws NoBeanFoundException
   *         ; No FQCN defined or possible for {@code argument}
   */
  public abstract String fqcn2(_Argument_ argument) throws NoBeanFoundException;

  /*</property>*/



  /*<property name="fqcn is final">*/
  //------------------------------------------------------------------

  /**
   * Returns {@code true} is the {@link #fqcn2(Object)} is final.
   * This means that, if a class with this name is not found, this
   * is a {@link BeanFinderConfigurationException}. If the
   * {@link #fqcn2(Object)} is not final, a {@link NoBeanFoundException}
   * is thrown.
   *
   * @basic
   */
  public final boolean isFqcnFinal() {
    return $fqcnFinal;
  }

  private boolean $fqcnFinal;

  /*</property>*/



  public final Object findFor(_Argument_ argument)
      throws BeanFinderConfigurationException, NoBeanFoundException {
    assert argument != null;
    String fqcn = fqcn2(argument); // throws NoBeanFoundException
    _LOG.debug("  trying to instantiate class with FQCN " + fqcn + " with default constructor");
    Object bean = null;
    try {
      bean = Beans.instantiate(null, fqcn);
      assert bean != null;
      if (! (getBeanType().isInstance(bean))) {
        ClassCastException ccExc = new ClassCastException(bean +
                                                          " cannot be cast to type " +
                                                          getBeanType());
        logInstantiationProblem(argument, fqcn, ccExc);
      }
      _LOG.debug("Created bean for argument " + argument +
                " from class " + fqcn);
    }
    catch (IOException ioExc) {
      logInstantiationProblem(argument, fqcn, ioExc); // throws exception
    }
    catch (ClassNotFoundException cnfExc) {
      if (isFqcnFinal()) {
        logInstantiationProblem(argument, fqcn, cnfExc); // throws exception
      }
      else {
        throw new NoBeanFoundException(this, argument);
      }
    }
    /* it is weird that we cannot get other exceptions here, according to the Beans.instantiate
     * Javadoc: how about exceptions in the constructor, having no default constructor, etc.
     * Solution is found in source code of the instantiate method: such exceptions
     * are all recast to ClassCastException.
     */
    assert bean != null;
    return bean;
  }

  private void logInstantiationProblem(_Argument_ argument,
                                       String fqcn,
                                       Exception exc)
      throws BeanFinderConfigurationException {
    StringBuffer message = new StringBuffer();
    message.append("While trying to find a bean for " + argument);
    message.append(" of type " + getBeanType());
    message.append(" of class with fqcn " + fqcn + ", ");
    message.append("but could not use the FQCN to create an instance.");
    String strMessage = message.toString();
    _LOG.debug(strMessage, exc);
    throw new BeanFinderConfigurationException(this, argument, strMessage, exc);
  }

}