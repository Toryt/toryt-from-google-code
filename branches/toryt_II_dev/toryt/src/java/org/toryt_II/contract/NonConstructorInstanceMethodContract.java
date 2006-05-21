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


/**
 * <p>A contract for a method that is not a constructor.
 *   non-constructor instance method contracts need access
 *   to their super-specifications, if they override or implement
 *   a method from a super type. The methods {@link #getPreconditions()},
 *   {@link #getPostconditions()} and {@link #getExceptionConditions()}
 *   return the complete set of conditions that apply to the
 *   contract subject. Implementations should take care that,
 *   while gathering the conditions from super contracts, the transitive
 *   closure is taken, so that no duplicate conditions are added when
 *   a contract is reached via multiple inheritance paths.</p>
 * <p>This differentiation is also needed, because the Java API,
 *   sadly, makes it in its reflection types.</p>
 * <p>In subtypes, {@code _SuperContract_} should become something like
 *   <code><var>ThisType</var>&lt;? super _ImplicitArgument_&gt;}</code>.</p>
 *
 * @author Jan Dockx
 *
 * @invar (forall NonConstructorInstanceMethodContract<? super _ImplicitArgument_> ncimc : getSuperContracts()
 *           {ncimc is for this method});
 * @mudo above invar not ok
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface NonConstructorInstanceMethodContract<_ImplicitArgument_,
                                                      _SuperContract_ extends NonConstructorInstanceMethodContract<?, ?>>
    extends InstanceMethodContract<_ImplicitArgument_, Method> {
  /* Considered "_SuperContract_ extends NonConstructorInstanceMethodContract<? super _ImplicitArgument_, ?>>"
   * instead of "_SuperContract_ extends NonConstructorInstanceMethodContract<?, ?>>", but the _ImplicitArgument_
   * of the super contracts in the set getDirectSuperContracts() is possibly different for each element of that set.
   * They all have to be super of this _ImplicitArgument_, but not all the same. That we cannot express with
   * generic parameter bounds. The set might contain, e.g., an InstanceInspectorContract<Object, ?>> and an
   * InstanceInspectorContract<Person, ?>>.
   */


  /*<property name="super contracts">*/
  //------------------------------------------------------------------

  /**
   * The contracts of specifications of this method in
   * super types.
   *
   * @basic
   */
  Set<_SuperContract_> getDirectSuperContracts();

  /*</property>*/

}