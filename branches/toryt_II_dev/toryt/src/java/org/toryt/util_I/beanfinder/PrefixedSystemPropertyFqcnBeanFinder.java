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
 * <p>{@link SystemPropertyFqcnBeanFinder} where the
 *   {@link #systemPropertyKey(Object) system property key}
 *   prefix and a dot, followed by a string that is a function
 *   of the {@code argument} ({@link #systemPropertyKeyVariantPart(Object)}).</p>
 *
 * @author Jan Dockx
 * @deprecated this class is not IoC
 */
@Deprecated
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class PrefixedSystemPropertyFqcnBeanFinder<_Argument_>
    extends SystemPropertyFqcnBeanFinder<_Argument_> {


//  private final static Log _LOG = LogFactory.getLog(PrefixedSystemPropertyFqcnBeanFinder.class);


  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre beanType != null;
   * @pre systemPropertyKeyPrefix != null;
   * @pre ! EMPTY.equals(systemPropertyKeyPrefix);
   * @post getBeanType() == beanType;
   * @post systemPropertyKeyPrefix.equals(getSystemPropertyKeyPrefix());
   * @post isFqcnFinal() == fqcnFinal;
   */
  protected PrefixedSystemPropertyFqcnBeanFinder(Class<?> beanType, String systemPropertyKeyPrefix, boolean fqcnFinal) {
    super(beanType, fqcnFinal);
    assert systemPropertyKeyPrefix != null;
    assert ! EMPTY.equals(systemPropertyKeyPrefix);
    $systemPropertyKeyPrefix = systemPropertyKeyPrefix;
  }

  /*</property>*/



  /*<property name="system property key prefix">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public String getSystemPropertyKeyPrefix() {
    return $systemPropertyKeyPrefix;
  }

  private final String $systemPropertyKeyPrefix;

  /*</property>*/



  /*<property name="system property key variant part">*/
  //------------------------------------------------------------------

  /**
   * @pre argument != null;
   * @basic
   */
  public abstract String systemPropertyKeyVariantPart(_Argument_ argument);

  /*</property>*/



  /*<property name="system property key">*/
  //------------------------------------------------------------------

  private final static String DOT = ".";

  /**
   * <p>The system property key of the system property that may hold the FQCN of a
   *   Class to instantiate for <code>argument</code>.</p>
   *
   * @pre argument != null;
   * @result result != null;
   * @result ! EMPTY.equals(result);
   */
  @Override
  public final String systemPropertyKey(_Argument_ argument) {
    return getSystemPropertyKeyPrefix() + DOT + systemPropertyKeyVariantPart(argument);
  }

  /*</property>*/

}