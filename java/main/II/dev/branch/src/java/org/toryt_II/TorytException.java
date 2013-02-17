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

package org.toryt_II;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Toryt code can throw exceptions of any kind. When we need to communicate a
 * Toryt-related issue, an instance of this class or a subclass is used.
 * Normal execution of tests should never throw an exception.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class TorytException extends Exception {

  /**
   * @post new.getMessage() == null;
   * @post new.getCause() == null;
   */
  public TorytException() {
    super();
  }

  /**
   * @post (message == null) ? new.getMessage() == null : message.equals(new.getMessage());
   * @post new.getCause() == null;
   */
  public TorytException(String message) {
    super(message);
  }

  /**
   * @post new.getMessage() == null;
   * @post new.getCause() == cause;
   */
  public TorytException(Throwable cause) {
    super(cause);
  }

  /**
   * @post (message == null) ? new.getMessage() == null : message.equals(new.getMessage());
   * @post new.getCause() == cause;
   */
  public TorytException(String message, Throwable cause) {
    super(message, cause);
  }

}