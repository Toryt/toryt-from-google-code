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
 * The contract of a type. This features iinvariants, method contracts
 * for all the methods defined in the type, nested class contracts, and extra tests.
 * Furthermore, there are references to all contracts for all direct supertypes.
 *
 * @invar getDirectSuperInterfaceContracts() != null;
 * @invar ! getDirectSuperInterfaceContracts().contains(null);
 * @invar (forall InterfaceContract ic : getDirectSuperInterfaceContracts() {
 *          ic.getSubject() in getSubject().getInterfaces()});
 * @invar getInstanceInvariantConditions() != null;
 * @invar getBasicInspectors() != null;
 * @invar ! getBasicInspectors().contains(null);
 * @invar (forall Method m; getBasicInspectors().contains(m);
 *            m.getDeclaringClass() == getSubject());
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface TypeContract<_Type_> extends Contract<Class<_Type_>> {

  /*<property name="direct super interface contracts">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  Set<InterfaceContract<? super _Type_>> getDirectSuperInterfaceContracts();

  /*</property>*/



  /*<property name="instance invariant conditions">*/
  //------------------------------------------------------------------

  /**
   * Invariants of the instance state for all instances of this type.
   * These invariant conditions need to be valid after all instance
   * metods.
   *
   * @basic
   */
  Set<Condition> getInstanceInvariantConditions();

  /*</property>*/



  /*<property name="basic instance inspectors">*/
  //------------------------------------------------------------------

  /**
   * @basic
   *
   * @mudo shouldn't we have instead basic inspector contracts?
   */
  Set<Method> getBasicInstanceInspectors();

  /*</property>*/

}