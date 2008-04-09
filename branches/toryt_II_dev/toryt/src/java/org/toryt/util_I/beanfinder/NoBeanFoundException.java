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

package org.toryt.util_I.beanfinder;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Signals that no bean could be found or instantiated for
 * argument {@link #getArgument()} by bean finder {@link #getBeanFinder()}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class NoBeanFoundException extends BeanFinderException {

  /**
   * @pre beanFinder != null;
   * @pre argument != null;
   * @post new.getBeanFinder() == beanFinder;
   * @post new.getArgument() == argument;
   */
  public NoBeanFoundException(BeanFinder<?> beanFinder, Object argument) {
    super(beanFinder, argument);
  }

}