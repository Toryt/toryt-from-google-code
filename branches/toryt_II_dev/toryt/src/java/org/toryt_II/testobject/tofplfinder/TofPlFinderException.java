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

package org.toryt_II.testobject.tofplfinder;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.TorytException;


/**
 * Exceptions thrown by {@link TofPlFinder} instances.
 *
 * @author Jan Dockx
 *
 * @invar getTofPlFinder() != null;
 * @invar getForClass() != null;
 * @invar getMessage() == null;
 * @invar getCause() == null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TofPlFinderException extends TorytException {

  /**
   * @pre tofPlFinder != null;
   * @pre forClass != null;
   * @post new.getTofPlFinder() == tofPlFinder;
   * @post new.getForClass() == forClass;
   */
  public TofPlFinderException(TofPlFinder tofPlFinder, Class<?> forClass) {
    super();
    assert tofPlFinder != null;
    assert forClass != null;
    $tofPlFinder = tofPlFinder;
    $forClass = forClass;
  }

  /**
   * @pre tofPlFinder != null;
   * @pre forClass != null;
   * @post new.getTofPlFinder() == tofPlFinder;
   * @post new.getForClass() == forClass;
   * @post new.getForClass() == forClass;
   * @post (message != null) ? new.getMessage().equals(message) : new.getMessage() == message;
   */
  public TofPlFinderException(TofPlFinder tofPlFinder, Class<?> forClass, String message) {
    super(message);
    assert tofPlFinder != null;
    assert forClass != null;
    $tofPlFinder = tofPlFinder;
    $forClass = forClass;
  }

  /**
   * @basic
   */
  public TofPlFinder getTofPlFinder() {
    return $tofPlFinder;
  }

  /**
   * @invar $tofPlFinder != null;
   */
  private final TofPlFinder $tofPlFinder;

  /**
   * @basic
   */
  public Class<?> getForClass() {
    return $forClass;
  }

  /**
   * @invar $forClass != null;
   */
  private final Class<?> $forClass;

}