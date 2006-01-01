package org.toryt_II.testmodel;


import java.io.PrintStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.toryt.util_I.priorityList.PriorityList;
import org.toryt_II.testmodel.AbstractTestModel.IndentPrinter;


/**
 * Instances represent a project to test. Projects have
 * a {@link #getProjectName() name}, and are an aggregate
 * of {@link #getPackageTestModels() PackageTestModels}.
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getPackageTestModels() != null;
 * @invar cC:noNull(getPackageTestModels());
 * @invar cC:instanceOf(getPackageTestModels(), PackageTestModel);
 */
public class ProjectTestModel extends PackageTestModelContainer {

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


  /*<property name="projectName">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init null;
   */
  public final String getProjectName() {
    return $projectName;
  }

  /**
   * @post new.getProjectName().equals(projectName);
   */
  public final void setProjectName(String projectName) {
    $projectName = projectName;
  }

  private String $projectName;

  /*</property>*/



  /*<property name="child test models">*/
  //------------------------------------------------------------------

  /**
   * @return getPackageTestModels();
   */
  public Set getChildTestModels() {
    return getPackageTestModels();
  }

  /*</property>*/



  public String toString() {
    return getClass().getName() + "[" + getProjectName() + "]";
  }

  void printStructure(IndentPrinter out) {
    assert out != null;
    out.println(getProjectName());
    IndentPrinter sections = new IndentPrinter(out, 1);
    sections.printChildren("packages:", getPackageTestModels());
  }

}