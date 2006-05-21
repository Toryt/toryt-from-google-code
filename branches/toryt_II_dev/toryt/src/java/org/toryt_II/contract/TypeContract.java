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
 * The contract of a type. This features type invariants, method contracts
 * for all the methods defined in the type, nested class contracts, and extra tests.
 * Furthermore, there are references to all contracts for all direct supertypes.
 *
 * @invar getType() != null;
 * @invar getDirectSuperInterfaceContracts() != null;
 * @invar ! getDirectSuperInterfaceContracts().contains(null);
 * @invar getBasicInspectors() != null;
 * @invar ! getBasicInspectors().contains(null);
 * @invar (forall Method m; getBasicInspectors().contains(m);
 *            m.getDeclaringClass() == getType());
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface TypeContract<_Class_> extends Contract<Class<_Class_>> {


  Set<InterfaceContract> getDirectSuperInterfaceContracts();

  /**
   * MUDO nonono! a basic inspector contract!
   *
   * @basic
   */
  Set<Method> getBasicInspectors();



  /*<property name="type invariant conditions">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  Set<Condition> getTypeInvariantConditions();

  /*</property>*/

}