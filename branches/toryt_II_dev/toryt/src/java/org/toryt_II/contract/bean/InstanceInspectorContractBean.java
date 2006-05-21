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

package org.toryt_II.contract.bean;


import static org.toryt.util_I.reflect.Reflection.MethodKind.INSTANCE_INSPECTOR;

import java.lang.reflect.Method;
import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.Reflection;
import org.toryt_II.contract.InstanceInspectorContract;
import org.toryt_II.contract.TypeContract;


/**
 * Build method contracts JavaBean style. Construct an instance,
 * add conditions, and close.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class InstanceInspectorContractBean<_ImplicitArgument_>
    extends NonConstructorInstanceMethodContractBean<_ImplicitArgument_, InstanceInspectorContract<? super _ImplicitArgument_>>
    implements InstanceInspectorContract<_ImplicitArgument_> {

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @param superContracts
   *        If this is <code>null</code> we treat it the same as the empty set.
   * @pre subject != null;
   * @pre typeContract != null;
   * @pre (superContracts != null) ? ! superContracts.contains(null);
   * @pre (forall _SuperContract_ : sc {sc.isClosed()});
   * @pre Reflection.methodKind(subject) == INSTANCE_INSPECTOR;
   * @post new.getSubject() == subject;
   * @post new.getTypeContract() == typeContract;
   * @post (superContracts == null) ?
   *         getSuperContracts().isEmpty() :
   *         getSuperContracts().equals(superContracts);
   * @post new.getPostconditions() == Union over supercontracts;
   */
  public InstanceInspectorContractBean(Method subject,
                                       TypeContract<_ImplicitArgument_> typeContract,
                                       Set<InstanceInspectorContract<? super _ImplicitArgument_>> superContracts) {
    super(subject, typeContract, superContracts);
    assert Reflection.methodKind(subject) == INSTANCE_INSPECTOR;
  }

  /*</construction>*/

}