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

package org.toryt_II.contract;


import java.lang.reflect.Method;
import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.contract.condition.Condition;


/**
 * The contract of a class.
 *
 * @invar ! getSubject().isInterface();
 * @invar (getSubject() == Object.class) ?
 *          (getDeclaredSuperClassContract() == null) : (getDeclaredSuperClassContract() != null);
 * @invar (getSubject() != Object.class) ?
 *          (getDeclaredSuperClassContract().getSubject() == getSubject().getSuperclass());
 * @invar getClassInvariantConditions() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface ClassContract<_Class_> extends TypeContract<_Class_> {

  /*<property name="direct super class contract">*/
  //------------------------------------------------------------------

  /**
   * The contract of the direct superclass.
   * All classes have a direct superclass, except {@link Object}, for which
   * this method returns <code>null</code>.
   * Since overridden methods must also adhere to the contract of that method
   * in the superclass, we also need to test that contract.
   *
   * @basic
   */
  ClassContract<? super _Class_> getDeclaredSuperClassContract();

  /*</property>*/



  /*<property name="class invariant conditions">*/
  //------------------------------------------------------------------

  /**
   * Invariants of the class state. These are conditions
   * that do not involve an instance, but only
   * class inspectors. They need to be valid after all
   * methods, both class methods and instance methods.
   *
   * @basic
   */
  Set<Condition> getDeclaredClassInvariantConditions();

  /*</property>*/



  /*<property name="basic class inspectors">*/
  //------------------------------------------------------------------

  /**
   * @basic
   *
   * @mudo shouldn't we have instead basic inspector contracts?
   */
  Set<Method> getBasicClassInspectors();

  /*</property>*/

}