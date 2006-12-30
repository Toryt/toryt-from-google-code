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


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Abstract implementation of common code of many
 *   {@link BeanFinder} implementations. The
 *   {@link #getBeanType} is set in the constructor
 *   and fixed.</p>
 *
 * @author Jan Dockx
 *
 * @invar getActualBeanFinder() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class FixedBeanTypeBeanFinder<_Argument_>
    implements BeanFinder<_Argument_> {


//  private final static Log _LOG = LogFactory.getLog(FixedBeanTypeBeanFinder.class);


  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre beanType != null;
   * @post getBeanType() != beanType;
   */
  protected FixedBeanTypeBeanFinder(Class<?> beanType) {
    assert beanType != null;
    $beanType = beanType;
  }

  /*</property>*/



  /*<property name="bean type">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final Class<?> getBeanType() {
    return $beanType;
  }

  /**
   * @invar $beanType != null;
   */
  private Class<?> $beanType;

  /*</property>*/

}