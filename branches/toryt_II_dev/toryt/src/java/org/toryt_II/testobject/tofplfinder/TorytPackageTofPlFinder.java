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
import org.toryt.util_I.reflect.Reflection;
import org.toryt_II.testobject.TestObjectFactoryPriorityList;


/**
 * <p>Implementation of {@link TofPlFinder} that looks for
 *   a class in the nested package structure, rooted in
 *   , which
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
public class TorytPackageTofPlFinder
    extends InstantiatingFqcnBeanFinder<Class<?>>
    implements TofPlFinder {

  /**
   * <p>The name of the package in which we look for a nested structure.</p>
   *   prefix for class names that are
   *   <acronym title="Test Object Factory Priority List">TOF PL</acronym>s
   *   in the same package as the type they serve.</p>
   *
   * <p><strong>= {@value}</strong></p>
   */
  public static final String TORYT_TOF_PL_PACKAGE = "org.toryt_II.testobject";

  public TorytPackageTofPlFinder() {
    super(TestObjectFactoryPriorityList.class, false);
  }

  private final static String DOT = ".";

  /**
   * No NoBeanFoundException. We will try any FQCN.
   * ({@code argument} certainly exists).
   */
  @Override
  public String fqcn2(Class<?> argument) {
    assert argument != null;
    return TORYT_TOF_PL_PACKAGE + DOT +
           Reflection.prefixedFqcn(FQCN_PREFIX, argument.getName());
  }

}