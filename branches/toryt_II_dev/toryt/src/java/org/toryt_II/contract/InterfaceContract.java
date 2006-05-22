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
import org.toryt_II.OLDcontract.hard.HardTypeContract;


/**
 * The contract of an interface. A {@link HardTypeContract} for types that
 * are interfaces.
 *
 * @invar getSubject().isInterface();
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface InterfaceContract<_Interface_> extends TypeContract<_Interface_> {

  // NOP

}