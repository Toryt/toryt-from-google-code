package org.toryt_II.testmodel;


import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * A model of a project entity that contains packages. This is an
 * abstract class that generalizes some code for subclasses.
 *
 * @author Jan Dockx
 *
 * @invar packageTestModels != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractPackageTestModelContainer<_SubjectType_>
    extends CompoundTestModel<_SubjectType_> {

  public final TestModelCollectionDelegate<PackageTestModel> packageTestModels =
      new TestModelCollectionDelegate<PackageTestModel>(this);

  {
    addTestModelCollectionDelegate("packages", packageTestModels);
  }

}