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
import org.toryt.util_I.beanfinder.BeanFinder;
import org.toryt_II.testobject.TestObjectFactoryPriorityList;


/**
 * <p>Instances of this type find (or create) a {@link TestObjectFactoryPriorityList}
 *   for a given type.</p>
 * <p>We would like to say that the type instances of this finder
 *   return are {@code TestObjectFactoryPriorityList<argument.class>.class},
 *   but sadly that is impossible.</p>
 *
 * @author Jan Dockx
 *
 * @invar getBeanType() == TestObjectFactoryPriorityList.class;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface TofPlFinder extends BeanFinder<Class<?>> {

  // NOP

  /**
   * <p>The prefix for class names that are
   *   <acronym title="Test Object Factory Priority List">TOF PL</acronym>s
   *   in the same package as the type they serve.</p>
   *
   * <p><strong>= {@value}</strong></p>
   */
  public static final String FQCN_PREFIX = "_TOF_PL_";

}