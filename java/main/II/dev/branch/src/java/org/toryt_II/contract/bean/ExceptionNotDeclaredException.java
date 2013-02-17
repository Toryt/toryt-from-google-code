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


import org.toryt_II.contract.Contract;
import org.toryt_II.contract.ContractException;


/**
 * Signals that we tried to add an exception condition for an
 * {@link #getExceptionType() exception type} that the
 * method of the {@link #getContract()} has not declared can be thrown..
 *
 * @author Jan Dockx
 *
 * @invar getExceptionType() != null;
 */
public class ExceptionNotDeclaredException extends ContractException {

  /**
   * @pre contract != null;
   * @pre exceptionType != null;
   * @post new.getContract() == contract;
   * @post new.getExceptionType() == exceptionType;
   * @post new.getMessage() == null;
   * @post new.getCause() == null;
   */
  public ExceptionNotDeclaredException(Contract<?> contract, Class<? extends Throwable> exceptionType) {
    super(contract);
    assert exceptionType != null;
    $exceptionType = exceptionType;
  }



  /*<property name="exception type">*/
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final Class<? extends Throwable> getExceptionType() {
    return $exceptionType;
  }

  private final Class<? extends Throwable> $exceptionType;

  /*</property>*/

}

