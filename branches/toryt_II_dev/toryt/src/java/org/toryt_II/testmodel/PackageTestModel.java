package org.toryt_II.testmodel;


import java.io.PrintStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.toryt.support.priorityList.PriorityList;
import org.toryt_II.testmodel.AbstractTestModel.IndentPrinter;


/**
 * Instances represent a package to test. A <code>PackageTestModel</code>
 * is an aggregate of
 * {@link #getPackageTestModels() PackageTestModels} for sub packages
 * and {@link #getClassTestModels() ClassTestModels}.
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt_II.contract.Collections;
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


  /*<property name="projectName">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init null;
   */
  public final Package getPackage() {
    return $pack;
  }

  /**
   * @post new.getPackage() == pack;
   */
  public final void setPackage(Package pack) {
    $pack = pack;
  }

  private Package $pack;

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
    return getClass().getName() + "[" + getPackage() + "]";
  }

  void printStructure(IndentPrinter out) {
    assert out != null;
    out.println(getPackage());
    IndentPrinter sections = new IndentPrinter(out, 2);
    sections.printChildren("classes:", getClassTestModels());
    sections.printChildren("subpackages:", getPackageTestModels());
  }

}