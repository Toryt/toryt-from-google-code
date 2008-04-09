/*<license>
Copyright 2006 - $Date$ by Jan Dockx.

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

package org.toryt_II.testmodel;


import java.io.PrintStream;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.PriorityList;




/**
 * <p>A model of a project entity. Instances of this type deliver
 *   a {@link PriorityList} of {@link TestFactory TestFactories}
 *   for {@link #getSubject()}.</p>
 * <p>Implementations should be developed with the IoC-pattern:
 *   test models should not setup their children themselves on
 *   construction. This allows the use of test models in interactive
 *   applications, because children can be added to and removed from
 *   existing test models.<br />
 *   To ease creation of test model instances, implementations of
 *   {@link TestModelFactory} can be used. Different implementations
 *   can implement different strategies to initialize test models.
 *   {@link DefaultTestModelFactory} provides the default Toryt
 *   implementation.</p>
 *
 * @author Jan Dockx
 *
 * @invar getTestFactoryList() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface TestModel<_Subject_> {

  /**
   * The subject of the tests this instance models.
   *
   * The return type is not bounded, because Java does not offer
   * an overall supertype for reflection types.
   *
   * @basic
   */
  _Subject_ getSubject();

  /**
   * The union of the method tests of all packages.
   *
   * @basic
   * @mudo five
   */
  PriorityList getTestFactoryList();

  /**
   * Method used to generate a structured textual representation
   * of a testmodel on a {@link PrintStream} (such as
   * {@link System#out}. Introduced for testing and debugging.
   *
   * @pre out != null;
   * @post printStructure(out, 0);
   */
  void printStructure(PrintStream out);

}