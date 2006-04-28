package org.toryt_II.testmodel;


import java.io.PrintStream;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * <p>A model of a project entity. Instances of this type deliver
 *   a {@link PriorityList} of {@link TestFactory TestFactories}.</p>
 * <p>Implementations should be developed with the IoC-pattern:
 *   test models should not setup their children themselves on
 *   construction. This allows the use of test models in interactive
 *   applications, because children can be added and removed to
 *   existing test models.<br />
 *   To ease creation of test model instances, implementations of
 *   {@link TestModelFactory} can be used. Different implementations
 *   can implement different strategies to initialize test models.
 *   {@link DefaultTestModelFactory} provides the default Toryt
 *   implementation.</p>
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getTestFactoryList() != null;
 * @invar getTestFactoryList().getElementType() == TestFactory.class;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface TestModel {

  /**
   * The union of the method tests of all packages.
   *
   * @basic
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