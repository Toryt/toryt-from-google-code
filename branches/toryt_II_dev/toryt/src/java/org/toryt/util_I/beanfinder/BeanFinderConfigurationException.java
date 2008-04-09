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
 * Signals that we could not return a requested bean because
 * of a configuration error. This means that we were committed to a certain
 * strategy to retrieve a bean but could not complete it because some error
 * in configuration. More precise information is given in English in the message.
 * This is a technical exception, from which we cannot recover.
 *
 * @author Jan Dockx
 * @idea Then should'nt this be an Error?
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class BeanFinderConfigurationException extends BeanFinderException {

  /**
   * @pre beanFinder != null;
   * @pre argument != null;
   * @post new.getBeanFinder() == beanFinder;
   * @post new.getArgument() == argument;
   * @post (message != null) ? new.getMessage().equals(message) : new.getMessage() == message;
   * @post new.getCause() == cause;
   */
  public BeanFinderConfigurationException(BeanFinder<?> beanFinder, Object argument, String message, Throwable cause) {
    super(beanFinder, argument, message, cause);
  }

}