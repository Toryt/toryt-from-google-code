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
import org.toryt_II.TorytException;


/**
 * Exceptions thrown by {@link Contract contracts}.
 *
 * @author Jan Dockx
 *
 * @invar getContract() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ContractException extends TorytException {

  /*<construction>*/
  //  ------------------------------------------------------------------

  /**
   * @pre contract != null;
   * @post new.getContract() == contract;
   * @post (message == null) ? (new.getMessage() == null) : new.getMessage().equals(message);
   * @post new.getCause() == null;
   */
  public ContractException(Contract<?> contract, String message) {
    super(message);
    assert contract != null;
    $contract = contract;
  }



  /**
   * @pre contract != null;
   * @post new.getContract() == contract;
   * @post new.getMessage() == null;
   * @post new.getCause() == null;
   */
  public ContractException(Contract<?> contract) {
    this(contract, null);
  }

  /*</construction>*/


  /*<property name="contract">*/
  //------------------------------------------------------------------

  public final Contract<?> getContract() {
    return $contract;
  }

  /**
   * @invar $contract != null;
   */
  private final Contract<?> $contract;

  /*</property>*/

}