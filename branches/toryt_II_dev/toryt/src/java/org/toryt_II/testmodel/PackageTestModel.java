package org.toryt_II.testmodel;


/**
 * <p>Instances represent a package to test. A <code>PackageTestModel</code>
 *   is an aggregate of {@link PackageTestModel PackageTestModels}
 *   for sub packages and {@link StaticClassTestModel StaticClassTestModels}.</p>
 * <p>We do not use the reflection {@link Package} class to refer to
 *   packages, since this class doesn't help us for modelling the software.
 *   Instances only exist when at least one class of the package has been
 *   loaded (and even then), and you cannot get a list of types in the package
 *   from such instances. We refer to a package just by its name.</p>
 *
 * @author Jan Dockx
 *
 * @invar classTestModels != null;
 */
public class PackageTestModel extends AbstractPackageTestModelContainer {

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


  /*<property name="packageName">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init null;
   */
  public final String getPackageName() {
    return $packageName;
  }

  /**
   * @post new.getPackageName() == packageName;
   */
  public final void setPackageName(String packageName) {
    $packageName = packageName;
    // TODO events
  }

  private String $packageName;

  /*</property>*/



  public final TestModelCollectionDelegate<StaticClassTestModel> classTestModels =
      new TestModelCollectionDelegate<StaticClassTestModel>(this);

  {
    addTestModelCollectionDelegate("classes", classTestModels);
  }



  public String toString() {
    return getClass().getName() + "[" + getPackageName() + "]";
  }

  public String getDisplayName() {
    return getPackageName();
  }

}