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


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Implementation of {@link BeanFinder} that caches
 *    found beans. If a call of {@link #findFor(Object)}
 *    does not produce a hit in the cache, {@link #getActualBeanFinder()}
 *    is used to produce a bean. The result of this {@link BeanFinder}
 *    is then cached for future use, and returned.</p>
 *
 * @author Jan Dockx
 *
 * @invar getActualBeanFinder() != null;
 * @invar getBeanType().isAssignableFrom(getActualBeanFinder.getBeanType());
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class CacheBeanFinder<_Argument_>
    extends FixedBeanTypeBeanFinder<_Argument_> {


  private final static Log _LOG = LogFactory.getLog(CacheBeanFinder.class);

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre beanType != null;
   * @pre actualBeanFinder != null;
   * @pre beanType.isAssignableFrom(actualBeanFinder.getBeanType());
   * @post getBeanType() != beanType;
   * @post getActualBeanFinder() == actualBeanFinder;
   */
  public CacheBeanFinder(Class<?> beanType, BeanFinder<_Argument_> actualBeanFinder) {
    super(beanType);
    assert actualBeanFinder != null;
    assert beanType.isAssignableFrom(actualBeanFinder.getBeanType());
    $actualBeanFinder = actualBeanFinder;
  }

  /*</property>*/



  /*<property name="actual bean finder">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final BeanFinder<_Argument_> getActualBeanFinder() {
    return $actualBeanFinder;
  }

  /**
   * @invar $actualBeanFinder != null;
   */
  private BeanFinder<_Argument_> $actualBeanFinder;

  /*</property>*/



  public final Object findFor(_Argument_ argument)
      throws BeanFinderConfigurationException, NoBeanFoundException {
    Object bean = $cache.get(argument);
    if (bean != null) {
      _LOG.debug("bean found in cache for argument \"" + argument + "\":" +
            bean);
    }
    else {
      _LOG.debug("no bean found in cache for argument \"" + argument + "\";" +
            " trying actual bean finder");
      bean = getActualBeanFinder().findFor(argument);
        // throws BeanFinderConfigurationException, NoBeanFoundException
      _LOG.debug("actual bean finder found bean \"" + bean + "\"" +
            " for argument \"" + argument + "\"; adding to cache");
      $cache.put(argument, bean);
    }
    return bean;
  }



  /*<property name="cache">*/
  //------------------------------------------------------------------

  /**
   * Unmodifiable version of the cache.
   *
   * @basic
   */
  public final Map<_Argument_, Object> getCache() {
    return Collections.unmodifiableMap($cache);
  }

  /**
   * @invar $map != null;
   * @invar (forall Object o; $cache.values(); getBeanType().isInstance(o));
   */
  private Map<_Argument_, Object> $cache = new HashMap<_Argument_, Object>();

  /*</property>*/

}