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
 * Implementation of common functionality for {@link Contract Contracts}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractContract<_Subject_> implements Contract<_Subject_> {


  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * @pre subject != null;
   * @post new.getSubject() == subject;
   */
  public AbstractContract(_Subject_ subject) {
    assert subject != null;
    $subject = subject;
  }

  /*</construction>*/



  /*<property name="subject">*/
  //------------------------------------------------------------------

  public final _Subject_ getSubject() {
    return $subject;
  }

  /**
   * @invar $subject != null;
   */
  private _Subject_ $subject;

  /*</property>*/




  /*<property name="closed">*/
  //------------------------------------------------------------------

  public final boolean isClosed() {
    return $closed;
  }

  /**
   * @post new.isClosed();
   */
  public void close() {
    $closed = true;
  }

  private boolean $closed;

  /*</property>*/

}