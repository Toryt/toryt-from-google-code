package org.toryt_II.testmodel;

import java.lang.reflect.Method;

import org.toryt.util_I.collections.priorityList.PriorityList;




/**
 * A model of a constructor to test.
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getTestFactoryList() != null;
 * @invar getTestFactoryList().getElementType() == TestFactory.class;
 */
public class InstanceMutatorTestModel extends NonConstructorMethodTestModel {

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



  /*<property name="instance mutator">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init null;
   */
  public final Method getInstanceMutator() {
    return getNonConstructorMethod();
  }

  /**
   * @post new.getMethod() == method;
   */
  public final void setInstanceMutator(Method instanceMutator) {
    setNonConstructorMethod(instanceMutator); // events
  }

  /*</property>*/



  public PriorityList getTestFactoryList() {
    // TODO Auto-generated method stub
    return null;
  }

}