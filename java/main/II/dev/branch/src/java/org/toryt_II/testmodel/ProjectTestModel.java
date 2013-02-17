package org.toryt_II.testmodel;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * Instances represent a project to test. Projects have
 * a {@link #getSubject() name}, and are an aggregate
 * of {@link PackageTestModel PackageTestModels}.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ProjectTestModel extends AbstractPackageTestModelContainer<String> {

  @Override
  protected String getSubjectDisplayNameSave() {
    return getSubject();
  }

}