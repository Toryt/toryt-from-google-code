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

package org.toryt_II.testobject;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt_II.TorytException;


/**
 * Exceptions thrown by {@link TofPlFactory} instances.
 *
 * @author Jan Dockx
 *
 * @invar getTofPlFactory() != null;
 * @invar getForClass() != null;
 * @invar getMessage() == null;
 * @invar getCause() == null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TofPlFactoryException extends TorytException {

  /**
   * @pre tofPlFactory != null;
   * @pre forClass != null;
   * @post new.getTofPlFactory() == tofPlFactory;
   * @post new.getForClass() == forClass;
   */
  public TofPlFactoryException(TofPlFactory tofPlFactory, Class<?> forClass) {
    super();
    assert tofPlFactory != null;
    assert forClass != null;
    $tofPlFactory = tofPlFactory;
    $forClass = forClass;
  }

  /**
   * @basic
   */
  public TofPlFactory getTofPlFactory() {
    return $tofPlFactory;
  }

  /**
   * @invar $tofPlFactory != null;
   */
  private final TofPlFactory $tofPlFactory;

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