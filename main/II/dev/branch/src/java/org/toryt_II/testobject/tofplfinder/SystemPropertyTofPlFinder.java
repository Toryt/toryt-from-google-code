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

package org.toryt_II.testobject.tofplfinder;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.beanfinder.PrefixedSystemPropertyFqcnBeanFinder;
import org.toryt_II.testobject.TestObjectFactoryPriorityList;


/**
 * <p>Implementation of {@link TofPlFinder} that looks for a system property
 *   with a given name, whose value should be the FQCN of a class, which
 *   can be instantiated using bean instantiation, to return the requested
 *   instance.</p>
 *
 * @author Jan Dockx
 *
 * @invar SYSTEM_PROPERTY_KEY_PREFIX.equals(getSystemPropertyKeyPrefix());
 * @invar isFqcnFinal();
 *
 * @deprecated This is not IoC
 */
@Deprecated
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class SystemPropertyTofPlFinder
    extends PrefixedSystemPropertyFqcnBeanFinder<Class<?>>
    implements TofPlFinder {

  public SystemPropertyTofPlFinder() {
    super(TestObjectFactoryPriorityList.class, SYSTEM_PROPERTY_KEY_PREFIX, true);
  }

  /**
   * <p>The prefix for system properties that have the FQCN of a
   *   <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   *   for a given class as value.</p>
   *
   * <p><strong>= {@value}</strong></p>
   */
  public final static String SYSTEM_PROPERTY_KEY_PREFIX = "org.toryt.tofpl";

  /**
   * @pre argument != null;
   * @return argument.getName();
   */
  @Override
  public String systemPropertyKeyVariantPart(Class<?> argument) {
    assert argument != null;
    return argument.getName();
  }

}