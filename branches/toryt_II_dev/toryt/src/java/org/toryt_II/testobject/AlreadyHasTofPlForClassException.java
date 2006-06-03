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


/**
 * Signals that there already is a
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for class {@link #getForClass()} in {@link #getTofPlFactory()}.
 *
 * @author Jan Dockx
 *
 * @invar getTofPl() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class AlreadyHasTofPlForClassException /*<_ForClass_>*/ extends TofPlFactoryException {

  /**
   * @pre tofPlFactory != null;
   * @pre forClass != null;
   * @pre tofPl != null;
   * @post new.getTofPlFactory() == tofPlFactory;
   * @post new.getForClass() == forClass;
   * @post new.getTofPl() == tofPl;
   */
  public AlreadyHasTofPlForClassException(TofPlFactory tofPlFactory,
                                          Class<?> forClass,
                                          TestObjectFactoryPriorityList<?> tofPl) {
    super(tofPlFactory, forClass);
    assert tofPl != null;
    $tofPl = tofPl;
  }

  /**
   * @basic
   */
  public TestObjectFactoryPriorityList<?> getTofPl() {
    return $tofPl;
  }

  /**
   * @invar $tofPl != null;
   */
  private final TestObjectFactoryPriorityList<?> $tofPl;

}