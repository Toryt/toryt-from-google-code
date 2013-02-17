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
 * Signals that we tried to change a contract that
 * is {@link Contract#isClosed() closed}.
 *
 * @author Jan Dockx
 *
 * @invar getContract() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ContractIsClosedException extends ContractException {

  /**
   * @param contract
   *        The contract that is closed.
   * @param change
   *        What we tried to add or set.
   * @param message
   *        Where we tried to change things.
   * @pre contract != null;
   * @pre contract.isClosed();
   * @pre message != null;
   * @pre message.length() > 0;
   * @post new.getContract() == contract;
   * @post new.getChange() == change;
   * @post new.getMessage().equals(message);
   */
  public ContractIsClosedException(Contract<?> contract, Object change, String message) {
    super(contract, message);
    assert contract.isClosed();
    assert message != null;
    assert message.length() > 0;
    $change = change;
  }



  /*<property name="change">*/
  //------------------------------------------------------------------

  /**
   * What we tried to add or set.
   *
   * @basic
   */
  public final Object getChange() {
    return $change;
  }

  private final Object $change;

  /*</property>*/

}