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
 * Exceptions thrown by {@link BeanFinder} instances.
 *
 * @author Jan Dockx
 *
 * @invar getBeanFinder() != null;
 * @invar getArgument() != null;
 * @invar getMessage() == null;
 * @invar getCause() == null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class BeanFinderException extends Exception {

  /**
   * @pre beanFinder != null;
   * @pre argument != null;
   * @post new.getBeanFinder() == beanFinder;
   * @post new.getArgument() == argument;
   * @post new.getCause() == null;
   * @post new.getMessage() == null;
   */
  public BeanFinderException(BeanFinder<?> beanFinder, Object argument) {
    super();
    assert beanFinder != null;
    assert argument != null;
    $beanFinder = beanFinder;
    $argument = argument;
  }

  /**
   * @pre beanFinder != null;
   * @pre argument != null;
   * @post new.getBeanFinder() == beanFinder;
   * @post new.getArgument() == argument;
   * @post new.getArgument() == argument;
   * @post (message != null) ? new.getMessage().equals(message) : new.getMessage() == message;
   * @post new.getCause() == cause;
   */
  public BeanFinderException(BeanFinder<?> beanFinder, Object argument, String message, Throwable cause) {
    super(message, cause);
    assert beanFinder != null;
    assert argument != null;
    $beanFinder = beanFinder;
    $argument = argument;
  }

  /**
   * @basic
   */
  public BeanFinder<?> getBeanFinder() {
    return $beanFinder;
  }

  /**
   * @invar $beanFinder != null;
   */
  private final BeanFinder<?> $beanFinder;

  /**
   * @basic
   */
  public Object getArgument() {
    return $argument;
  }

  /**
   * @invar $argument != null;
   */
  private final Object $argument;

}