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
import java.util.Map;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Implementation of common methods of {@link MethodContract}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractMethodContract<_Subject_ extends Member>
    extends AbstractContract<_Subject_>
    implements MethodContract<_Subject_> {

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre subject != null;
   * @pre typeContract != null;
   * @post new.getSubject() == subject;
   * @post new.getTypeContract() == typeContract;
   */
  public AbstractMethodContract(_Subject_ subject) {
    super(subject);
  }

  /*</construction>*/



  private final static String[] EMPTY = new String[] {};

  /**
   * Default implementation for methods without formal parameters.
   * Subclasses should override.
   *
   * @protected-result result.length == 0;
   *
   * @mudo problem: now it's no longer a bean
   */
  public String[] getFormalParameters() {
    return EMPTY;
  }

  /**
   * Default implementation does noting.
   */
  public void recordState(Map<String, Object> context) {
    // NOP
  }

}