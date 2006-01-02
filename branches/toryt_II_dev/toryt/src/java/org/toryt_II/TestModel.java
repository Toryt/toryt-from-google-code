package org.toryt_II;


import java.io.PrintStream;

import org.toryt.util_I.priorityList.PriorityList;


/**
 * A model of a project entity. Instances of this type deliver
 * a {@link PriorityList} of {@link TestFactory TestFactories}.
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getTestFactoryList() != null;
 * @invar getTestFactoryList().getElementType() == TestFactory.class;
 */
public interface TestModel {

  /*<section name="Meta Information">*/
  //  ------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /*</section>*/


  /**
   * The union of the method tests of all packages.
   *
   * @basic
   */
  PriorityList getTestFactoryList();

  /**
   * @pre out != null;
   * @post printStructure(out, 0);
   */
  void printStructure(PrintStream out);

}