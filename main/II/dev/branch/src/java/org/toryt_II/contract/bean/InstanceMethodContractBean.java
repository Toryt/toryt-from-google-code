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


import java.lang.reflect.Member;
import java.lang.reflect.Method;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.Methods;
import org.toryt_II.contract.InstanceMethodContract;
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
public abstract class InstanceMethodContractBean<_ImplicitArgument_, _Subject_ extends Member>
    extends MethodContractBean<_Subject_>
    implements InstanceMethodContract<_ImplicitArgument_, _Subject_> {

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre subject != null;
   * @pre (_Subject_ == Method) ? Methods.methodKind(subject).isInstanceMethod();
   * @pre typeContract != null;
   * @post new.getSubject() == subject;
   * @post new.getTypeContract() == typeContract;
   */
  public InstanceMethodContractBean(_Subject_ subject, TypeContract<_ImplicitArgument_> typeContract) {
    super(subject);
    assert (subject instanceof Method) ? Methods.methodKind((Method)subject).isInstanceMethod() : true;
    assert typeContract != null;
    $typeContract = typeContract;
  }

  /*</construction>*/



  /*<property name="type contract">*/
  //------------------------------------------------------------------

  public final TypeContract<_ImplicitArgument_> getTypeContract() {
    return $typeContract;
  }

  /**
   * @invar $typeContract != null;
   */
  private final TypeContract<_ImplicitArgument_> $typeContract;

  /*</property>*/

}