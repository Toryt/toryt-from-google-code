package org.toryt_II.testmodel;


import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.toryt.support.priorityList.PriorityList;


/**
 * A model of a method to test. Instances of this type deliver
 * a {@link PriorityList} of {@link TestFactory TestFactories}
 * for {@link #getMethod()}. This class is abstract: you should use
 * one of the more specific subclasses for different kinds of methods
 * instead.
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt_II.contract.Collections;
 * @invar getTestFactoryList() != null;
 * @invar getTestFactoryList().getElementType() == TestFactory.class;
 */
public abstract class MethodTestModel extends AbstractTestModel {

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



  /*<property name="method">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init null;
   */
  public final Method getMethod() {
    return $method;
  }

  /**
   * @post new.getClass() == method;
   */
  protected final void setMethod(Method method) {
    $method = method;
  }

  private Method $method;

  /*</property>*/




  public String toString() {
    return getClass().getName() + "[" + getMethod() + "]";
  }

  void printStructure(IndentPrinter out) {
    assert out != null;
    out.println(getMethod());
  }

}