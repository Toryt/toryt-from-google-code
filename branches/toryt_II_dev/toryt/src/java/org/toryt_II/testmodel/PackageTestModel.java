package org.toryt_II.testmodel;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Instances represent a package to test. A <code>PackageTestModel</code>
 *   is an aggregate of {@link PackageTestModel PackageTestModels}
 *   for sub packages and {@link StaticClassTestModel StaticClassTestModels}.</p>
 * <p>We do not use the reflection {@link Package} class to refer to
 *   packages, since this class doesn't help us for modelling the software.
 *   Instances only exist when at least one class of the package has been
 *   loaded (and even then), and you cannot get a list of types in the package
 *   from such instances. We refer to a package just by its name (String).</p>
 *
 * @author Jan Dockx
 *
 * @invar classTestModels != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class PackageTestModel extends AbstractPackageTestModelContainer<String> {

  public final TestModelCollectionDelegate<StaticClassTestModel> classTestModels =
      new TestModelCollectionDelegate<StaticClassTestModel>(this);

  {
    addTestModelCollectionDelegate("classes", classTestModels);
  }

  protected String getSubjectDisplayNameSave() {
    return getSubject();
  }

}