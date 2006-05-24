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
 * A contract for {@link #getSubject()}, a <code>_Subject_</code>.
 * A contract must be {@link #isClosed() closed} before it is used
 * in a test: we don't want to take the chance that anybody changes
 * the contract after it is used in a test.
 *
 * @author Jan Dockx
 *
 * @param <_Subject_>
 *        The type of the entity that this is a contract for.
 *
 * @invar getSubject() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface Contract<_Subject_> {

  /**
   * The entity this is a contract for.
   *
   * @basic
   */
  _Subject_ getSubject();

  /**
   * The contract is closed. It can no longer be changed.
   *
   * @basic
   */
  boolean isClosed();

  /**
   * Contracts {@link Object#equals(java.lang.Object) are equal} if
   * they have the same subject.
   *
   * @return getSubject() == other.getSubject();
   */
  boolean equals(Contract<_Subject_> other);

}