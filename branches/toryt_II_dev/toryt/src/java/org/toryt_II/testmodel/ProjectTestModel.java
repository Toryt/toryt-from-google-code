package org.toryt_II.testmodel;


/**
 * Instances represent a project to test. Projects have
 * a {@link #getProjectName() name}, and are an aggregate
 * of {@link PackageTestModel PackageTestModels}.
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



  public String toString() {
    return getClass().getName() + "[" + getProjectName() + "]";
  }


  public String getDisplayName() {
    return getProjectName();
  }

}