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
import org.toryt.util_I.beanfinder.InstantiatingFqcnBeanFinder;
import org.toryt.util_I.reflect.Classes;
import org.toryt_II.testobject.TestObjectFactoryPriorityList;


/**
 * <p>Implementation of {@link TofPlFinder} that looks a class in the same
 *   package as the argument with a special name, which
 *   can be instantiated using bean instantiation, to return the requested
 *   instance.</p>
 *
 * @author Jan Dockx
 *
 * @invar ! isFqcnFinal();
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class SamePackageTofPlFinder
    extends InstantiatingFqcnBeanFinder<Class<?>>
    implements TofPlFinder {

  public SamePackageTofPlFinder() {
    super(TestObjectFactoryPriorityList.class, false);
  }

  /**
   * No NoBeanFoundException. We will try any FQCN.
   * ({@code argument} certainly exists).
   */
  @Override
  public String fqcn(Class<?> argument) {
    assert argument != null;
    return Classes.prefixedFqcn(FQCN_PREFIX, argument.getName());
  }

}