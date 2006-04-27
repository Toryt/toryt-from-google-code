package org.toryt_II.testmodel;


/**
 * A model of a project entity that contains packages. This is an
 * abstract class that generalizes some code for subclasses.
 *
 * @author Jan Dockx
 *
 * @invar packageTestModels != null;
 */
public abstract class AbstractPackageTestModelContainer extends CompoundTestModel {

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



  public final TestModelCollectionDelegate<PackageTestModel> packageTestModels =
      new TestModelCollectionDelegate<PackageTestModel>(this);

  {
    addTestModelCollectionDelegate("packages", packageTestModels);
  }

}