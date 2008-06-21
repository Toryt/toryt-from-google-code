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
 * The contract of a type. In type contracts, invariants are gathered, and references
 * to the contracts of all supertypes. A type contract also lists which methods
 * are basic inspectors, for which there is no contract and no tests are possible.
 *
 * @invar getDeclaredSuperInterfaceContracts() != null;
 * @invar ! getDeclaredSuperInterfaceContracts().contains(null);
 * @invar (forall InterfaceContract ic : getDeclaredSuperInterfaceContracts() {
 *          ic.getSubject() in getSubject().getInterfaces()});
 * @invar getDeclaredInstanceInvariantConditions() != null;
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

  /*<section name="super type contracts">*/
  //------------------------------------------------------------------

  /**
   * The contracts of the super interfaces which are declared for this type,
   * i.e., the direct super interfaces, retrieved with
   * {@link Class#getInterfaces()}.
   *
   * @basic
   */
  Set<InterfaceContract<? super _Type_>> getDeclaredSuperInterfaceContracts();

  /**
   * The contracts of all interfaces this type implements. This is the
   * transitive closure of applying {@link #getDeclaredInstanceInvariantConditions()}
   * recursively.
   *
   * @result result.containsAll(getDeclaredSuperInterfaceContracts().getSuperInterfaceContracts());
   * @result (forall InterfaceContract ic {
   *            ! getDeclaredSuperInterfaceContracts().getSuperInterfaceContracts().contains(ic) ?
   *              ! result.contains(ic)});
   */
  Set<InterfaceContract<? super _Type_>> getSuperInterfaceContracts();

  /**
   * The contracts of all the super types of this type.
   *
   * @result result.containsAll(getSuperInterfaceContracts());
   */
  Set<? extends TypeContract<? super _Type_>> getSuperTypeContracts();

  /*</section>*/



  /*<section name="instance invariant conditions">*/
  //------------------------------------------------------------------

  /**
   * Invariants of the instance state for all instances of this type.
   * These invariant conditions need to be valid after all instance
   * methods. These are the invariants declared for this type
   * specifically.
   *
   * @basic
   */
  Set<Condition> getDeclaredInstanceInvariantConditions();

  /**
   * Instance invariant conditions declared for this type
   * or any of its super types.
   *
   * @return union(getDeclaredInstanceInvariantConditions(),
   *               union(getSuperTypeContracts()..getDeclaredInstanceInvariantConditions()));
   */
  Set<Condition> getInstanceInvariantConditions();

  /*</section>*/



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