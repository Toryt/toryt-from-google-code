package org.toryt_II.testmodel;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Instances represent a project to test. Projects have
 * a {@link #getProjectName() name}, and are an aggregate
 * of {@link PackageTestModel PackageTestModels}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ProjectTestModel extends AbstractPackageTestModelContainer {

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