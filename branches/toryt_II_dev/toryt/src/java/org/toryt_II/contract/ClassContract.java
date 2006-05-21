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

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * The contract of a class.
 *
 * @invar ! getSubject().isInterface();
 * @invar (getType() == Object.class) ? (getDirectSuperClassContract() == null);
 * @invar (getType() != Object.class) ? (getDirectSuperClassContract() != null);
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface ClassContract<_Class_> extends TypeContract<_Class_> {

  /**
   * The contract of the direct superclass.
   * All classes have a direct superclass, except {@link Object}.
   * Since overridden methods must also adhere to the contract of that method
   * in the superclass, we also need to test that contract. If there is no
   * contract for the superclass, this is logged as a test failure.
   * @mudo or a warning or something
   *
   * @basic
   */
  ClassContract<_Class_> getDirectSuperClassContract();

}