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


import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Implementation of {@link BeanFinder} that carries a map
 *   of {@code argument} -
 *   <acronym title="Fully Qualified Class Name">FQCN</acronym>
 *   combinations. If the {@code argument}
 *   is found in the map, we try to instantiate a bean of the type
 *   of the associated
 *   <acronym title="Fully Qualified Class Name">FQCN</acronym>.
 *   A {@link BeanFinderConfigurationException} is thrown when the
 *   found <acronym title="Fully Qualified Class Name">FQCN</acronym>
 *   cannot be instantiated to an instance of {@link #getBeanType()}.
 *   This means this bean finder is {@link #isFqcnFinal()}.
 *   If the {@code argument} is not found in the map, a
 *   {@link NoBeanFoundException} is thrown.</p>
 * <p>The map is exposed to the user, and can be changed at will.
 *   Initially, there is no map.</p>
 *
 * @author Jan Dockx
 *
 * @invar isFqcnFinal();
 * @invar getFqcnMap() != null ? Collections.noNull(getFqcnMap());
 * @invar getFqcnMap() != null ? ! getFqcnMap().containsValue(EMPTY);
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class FqcnMapBeanFinder<_Argument_>
    extends InstantiatingFqcnBeanFinder<_Argument_> {


  private final static Log _LOG = LogFactory.getLog(FqcnMapBeanFinder.class);

  public final static String EMPTY = "";



  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre beanType != null;
   * @post getBeanType() == beanType;
   * @post getFqcnMap() == null;
   */
  public FqcnMapBeanFinder(Class<?> beanType) {
    super(beanType, true);
  }

  /*</property>*/



  /*<property name="fqcn map">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final Map<_Argument_, String> getFqcnMap() {
    return $fqcnMap;
  }

  /**
   * @pre fqcnMap != null ? Collections.noNull(fqcnMap);
   * @pre fqcnMap != null ? ! fqcnMap.containsValue(EMPTY);
   * @post getFqcnMap() == fqcnMap;
   */
  public final void setFqcnMap(Map<_Argument_, String> fqcnMap) {
    assert fqcnMap != null ? Collections.noNull(fqcnMap) : true;
    assert fqcnMap != null ? ! fqcnMap.containsValue(EMPTY) : true;
    $fqcnMap = fqcnMap;
  }

  /**
   * @invar $fqcnMap != null ? Collections.noNull($fqcnMap);
   * @invar $fqcnMap != null ? ! $fqcnMap.containsValue(EMPTY);
   */
  private Map<_Argument_, String> $fqcnMap;

  /*</property>*/



  /*<property name="fqcn">*/
  //------------------------------------------------------------------

  /**
   * @pre argument != null;
   * @basic
   * @throws NoBeanFoundException
   *         ! getFqcnMap().containsKey(argument);
   */
  @Override
  public final String fqcn2(_Argument_ argument) throws NoBeanFoundException {
    assert argument != null;
    _LOG.debug("trying to find fqcn for argument " + argument + " in map");
    String fqcn = null;
    if (getFqcnMap() != null) {
      fqcn = getFqcnMap().get(argument);
    }
    else {
      _LOG.debug("there is no fqcn map, so we cannot find a bean");
    }
    _LOG.debug("fqcn for " + argument + " is " + fqcn);
    if (fqcn == null) {
      // no entry
      _LOG.debug("there is no entry for " + argument + " in the map");
      throw new NoBeanFoundException(this, argument);
    }
    return fqcn;
  }

  /*</property>*/

}