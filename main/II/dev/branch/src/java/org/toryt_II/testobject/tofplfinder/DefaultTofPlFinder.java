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


import java.util.List;
import java.util.Map;

import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.beanfinder.CacheBeanFinder;
import org.toryt.util_I.beanfinder.ChainedBeanFinder;
import org.toryt.util_I.beanfinder.FqcnMapBeanFinder;
import org.toryt_II.testobject.TestObjectFactoryPriorityList;


/**
 * <p>The default {@code DefaultTofPlFinder} is a {@link CacheBeanFinder}
 *   The {@link #getActualBeanFinder() actual bean finder} behind
 *   the cache has the following strategy:</p>
 * <ol>
 *   <li>use {@link SystemPropertyTofPlFinder}</li>
 *   <li>try to find an appropriately named class in
 *     the package of the argument</li>
 *   <li>try to find an appropriately named class in the package structure
 *     nested in one of the given packages;
 *     the given packages are
 *     <ol>
 *       <li>the package structure nested in org.toryt_II.testobject</li>
 *       <li>the package structure nested in one of the packages, found
 *         in a comma-separated list found in a system property</li>
 *     </ol>
 *   </li>
 *   <li>try to build a TOF PL automatically, as a
 *     combination priority list of all properties of the
 *     {@code argument}.</li>
 * </ol>
 *
 * @author Jan Dockx
 *
 * @invar getBeanType() == TestObjectFactoryPriorityList.class;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class DefaultTofPlFinder extends CacheBeanFinder<Class<?>> implements TofPlFinder {

  /**
   * <p>The prefix for system properties that have the FQCN of a
   *   <acronym title="Test Object Factory Priority List">TOF PL</acronym>
   *   for a given class as value.</p>
   *
   * <p><strong>= {@value}</strong></p>
   */
  public final static String SYSTEM_PROPERTY_KEY_PREFIX = "org.toryt.tofpl";

  public DefaultTofPlFinder() {
    super(TestObjectFactoryPriorityList.class);
    ChainedBeanFinder<Class<?>> chainedBeanFinder =
      new ChainedBeanFinder<Class<?>>(TestObjectFactoryPriorityList.class);
    chainedBeanFinder.addActualBeanFinder(new SystemPropertyTofPlFinder()); // TODO deprecated; replace with following with IoC
    chainedBeanFinder.addActualBeanFinder($fqcnMapBeanFinder);
    chainedBeanFinder.addActualBeanFinder(new SamePackageTofPlFinder());
    chainedBeanFinder.addActualBeanFinder($packagesLookupBeanFinder);
    chainedBeanFinder.addActualBeanFinder(new TorytPackageTofPlFinder()); // TODO in packages lookup ...
    setActualBeanFinder(chainedBeanFinder);
  }


  /*<property name="fqcn map">*/
  //------------------------------------------------------------------

  // this should be filled (IoC) from config file and/or prefixed system properties

  /**
   * @basic
   */
  public final Map<Class<?>, String> getFqcnMap() {
    return $fqcnMapBeanFinder.getFqcnMap();
  }

  /**
   * @pre fqcnMap != null ? Collections.noNull(fqcnMap);
   * @pre fqcnMap != null ? ! fqcnMap.containsValue(EMPTY); // MUDO problem: what if, afterwards, this is added to the map?
   * @post getFqcnMap() == fqcnMap;
   */
  public final void setFqcnMap(Map<Class<?>, String> fqcnMap) {
    $fqcnMapBeanFinder.setFqcnMap(fqcnMap);
  }

  /**
   * @invar $fqcnMapBeanFinder != null;
   */
  private FqcnMapBeanFinder<Class<?>> $fqcnMapBeanFinder =
    new FqcnMapBeanFinder<Class<?>>(TestObjectFactoryPriorityList.class);

  /*</property>*/



  /*<property name="lookup package list">*/
  //------------------------------------------------------------------

  // this should be filled (IoC) from config file and/or prefixed system properties

  /**
   * @basic
   */
  public final List<String> getLookupPackageList() {
    return $packagesLookupBeanFinder.getLookupPackageList();
  }

  /**
   * @pre lookupPackageName != null;
   * @pre ! EMPTY.equals(lookupPackageName);
   * @post getLookupPackageList().size() = getLookupPackageList()'size() + 1;
   * @post getLookupPackageList().get(getLookupPackageList().size() - 1).equals(lookupPackageName);
   */
  public final void addLookupPackage(String lookupPackageName) {
    $packagesLookupBeanFinder.addLookupPackage(lookupPackageName);
  }

  /**
   * @invar $packagesLookupBeanFinder != null;
   */
  private PackagesLookupBeanFinder<Class<?>> $packagesLookupBeanFinder =
    new PackagesLookupBeanFinder<Class<?>>(TestObjectFactoryPriorityList.class);

  /*</property>*/

}