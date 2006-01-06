package org.toryt_II.testmodel;


import java.lang.reflect.Method;

import org.toryt.util_I.priorityList.PriorityList;


/**
 * A model of a method to test. Instances of this type deliver
 * a {@link PriorityList} of {@link TestFactory TestFactories}
 * for {@link #getMethod()}. This class is abstract: you should use
 * one of the more specific subclasses for different kinds of methods
 * instead.
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getTestFactoryList() != null;
 * @invar getTestFactoryList().getElementType() == TestFactory.class;
 */
public abstract class NonConstructorMethodTestModel extends MethodTestModel {

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



  /**
   * @return getNonConstructorMethod();
   */
  public final Object getMethod() {
    return getNonConstructorMethod();
  }



  /*<property name="nonConstructorMethod">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init null;
   */
  public final Method getNonConstructorMethod() {
    return $nonConstructorMethod;
  }

  /**
   * @post new.getNonConstructorMethod() == nonConstructorMethod;
   */
  protected final void setNonConstructorMethod(Method nonConstructorMethod) {
    $nonConstructorMethod = nonConstructorMethod;
  }

  private Method $nonConstructorMethod;

  /*</property>*/

}