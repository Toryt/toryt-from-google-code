package org.toryt_II.testmodel;


import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * <p>Instances represent a package to test. A <code>PackageTestModel</code>
 *   is an aggregate of {@link #getPackageTestModels() PackageTestModels}
 *   for sub packages and {@link #getClassTestModels() ClassTestModels}.</p>
 * <p>We do not use the reflection {@link Package} class to refer to
 *   packages, since this class doesn't help us for modelling the software.
 *   Instances only exist when at least one class of the package has been
 *   loaded (and even then), and you cannot get a list of types in the package
 *   from such instances. We refer to a package just by its name.</p>
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getClassTestModels() != null;
 * @invar cC:noNull(getClassTestModels());
 * @invar cC:instanceOf(getClassTestModels(), ClassTestModel);
 */
public class PackageTestModel extends PackageTestModelContainer {

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
  }

  private String $packageName;

  /*</property>*/



  /*<property name="child test models">*/
  //------------------------------------------------------------------

  /**
   * @return getPackageTestModels();
   */
  public Set getChildTestModels() {
    Set result = new HashSet();
    result.addAll(getPackageTestModels());
    result.addAll(getClassTestModels());
    return Collections.unmodifiableSet(result);
  }

  /*</property>*/



  public String toString() {
    return getClass().getName() + "[" + getPackageName() + "]";
  }

  void printStructure(IndentPrinter out) {
    assert out != null;
    out.println(getPackageName());
    IndentPrinter sections = new IndentPrinter(out, 2);
    sections.printChildren("classes:", getClassTestModels());
    sections.printChildren("subpackages:", getPackageTestModels());
  }

}