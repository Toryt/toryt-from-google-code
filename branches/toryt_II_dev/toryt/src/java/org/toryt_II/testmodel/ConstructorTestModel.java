package org.toryt_II.testmodel;

import java.io.PrintStream;
import java.lang.reflect.Constructor;

import org.toryt.support.priorityList.PriorityList;
import org.toryt_II.testmodel.AbstractTestModel.IndentPrinter;




/**
 * A model of a constructor to test.
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt_II.contract.Collections;
 * @invar getTestFactoryList() != null;
 * @invar getTestFactoryList().getElementType() == TestFactory.class;
 */
public class ConstructorTestModel extends AbstractTestModel {

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


  /*<property name="constructor">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init null;
   */
  public final Constructor getConstructor() {
    return $constructor;
  }

  /**
   * @post new.getConstructor() == method;
   */
  public final void setConstructor(Constructor constructor) {
    $constructor = constructor;
  }

  private Constructor $constructor;

  /*</property>*/



  public PriorityList getTestFactoryList() {
    // TODO Auto-generated method stub
    return null;
  }


  public String toString() {
    return getClass().getName() + "[" + getConstructor() + "]";
  }

  void printStructure(IndentPrinter out) {
    assert out != null;
    out.println(getConstructor());
  }

}