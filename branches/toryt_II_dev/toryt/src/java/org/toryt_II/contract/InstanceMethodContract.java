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


import java.lang.reflect.Member;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>The <code>_ImplicitArgument_</code> of <dfn>instance methods</dfn>
 *   are regular objects of that type. Constructors are considered instance methods
 *   (because they also need acces to their {@link #getTypeContract()}.</p>
 * <p>An instance method contract needs a {@link #getTypeContract() type contract},
 *   so it can access applicable {@link TypeContract#getDeclaredInstanceInvariantConditions()
 *   type invariants}.</p>
 *
 * @param <_ImplicitArgument_>
 *        The type of the implicit argument of the method we are testing.
 *        For instance methods, this is the type of the <code>this</code>
 *        instance. For class methods and contructors, this is the the
 *        class in which they are defined.
 *
 * @author Jan Dockx
 *
 * @invar (getSubject() instanceof Method) ? Reflection.methodKind(getSubject()).isInstanceMethod();
 * @invar getTypeContract() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface InstanceMethodContract<_ImplicitArgument_, _Subject_ extends Member>
    extends MethodContract<_Subject_> {

  /*<property name="type contract">*/
  //------------------------------------------------------------------

  /**
   * The contract of the type in which this method is defined.
   *
   * @basic
   */
  TypeContract<_ImplicitArgument_> getTypeContract();

  /*</property>*/

}