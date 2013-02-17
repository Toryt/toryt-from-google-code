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


import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Implementation of {@link BeanFinder} that looks for a system property
 *   with a given name, whose value should be the FQCN of a class, which
 *   can be instantiated using bean instantiation, to return the requested
 *   instance.</p>
 *
 * @author Jan Dockx
 * @deprecated this class is not IoC
 */
@Deprecated
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class SystemPropertyFqcnBeanFinder<_Argument_>
    extends InstantiatingFqcnBeanFinder<_Argument_> {


  private final static Log _LOG = LogFactory.getLog(SystemPropertyFqcnBeanFinder.class);

  public final static String EMPTY = "";



  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre beanType != null;
   * @post getBeanType() == beanType;
   * @post isFqcnFinal() == fqcnFinal;
   */
  protected SystemPropertyFqcnBeanFinder(Class<?> beanType, boolean fqcnFinal) {
    super(beanType, fqcnFinal);
  }

  /*</property>*/



  /*<property name="system property key">*/
  //------------------------------------------------------------------

  /**
   * <p>The system property key of the system property that may hold the FQCN of a
   *   Class to instantiate for <code>argument</code>.</p>
   *
   * @pre argument != null;
   * @result result != null;
   * @result ! EMPTY.equals(result);
   */
  public abstract String systemPropertyKey(_Argument_ argument);

  /*</property>*/



  /*<property name="fqcn">*/
  //------------------------------------------------------------------

  /**
   * @pre argument != null;
   * @basic
   * @throws NoBeanFoundException
   *         System.getProperties().get(systemPropertyKey(argument)) == null;
   */
  @Override
  public final String fqcn(_Argument_ argument) throws NoBeanFoundException {
    assert argument != null;
    _LOG.debug("trying to find fqcn for argument " + argument + " in system property");
    String key = systemPropertyKey(argument);
    assert key != null;
    assert ! EMPTY.equals(key);
    Properties properties = System.getProperties();
    _LOG.debug("  system property key: \"" + key + "\"");
    String fqcn = properties.getProperty(key);
    _LOG.debug("  value of system property: \"" + fqcn + "\"");
    if (fqcn == null) {
      // no entry
      _LOG.debug("there is no entry for " + argument + " in the system properties");
      throw new NoBeanFoundException(this, argument);
    }
    return fqcn;
  }

  /*</property>*/

}