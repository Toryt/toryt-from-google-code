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

package org.toryt_II.contract.condition;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Implementation of general methods of {@link ExceptionCondition}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractExceptionCondition<_Exception_ extends Throwable>
    implements ExceptionCondition<_Exception_> {

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre exceptionType != null;
   */
  public AbstractExceptionCondition(Class<_Exception_> exceptionType) {
    assert exceptionType != null;
    $exceptionType = exceptionType;
  }

  /*</construction>*/



  /*<property name="preconditions">*/
  //------------------------------------------------------------------

  public final Class<_Exception_> getExceptionType() {
    return $exceptionType;
  }

  /**
   * @invar $exceptionType != null;
   */
  private final Class<_Exception_> $exceptionType;

  /*</property>*/

}