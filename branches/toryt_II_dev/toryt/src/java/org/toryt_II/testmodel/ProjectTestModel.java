package org.toryt_II.testmodel;


import java.util.Set;


/**
 * Instances represent a project to test. Projects have
 * a {@link #getProjectName() name}, and are an aggregate
 * of {@link #getPackageTestModels() PackageTestModels}.
 *
 * @author Jan Dockx
 */
public class ProjectTestModel extends AbstractPackageTestModelContainer {

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
    // TODO events
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