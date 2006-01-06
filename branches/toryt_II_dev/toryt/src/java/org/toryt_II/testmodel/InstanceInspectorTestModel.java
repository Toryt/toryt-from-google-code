package org.toryt_II.testmodel;

import java.lang.reflect.Method;

import org.toryt.util_I.priorityList.PriorityList;




/**
 * A model of a constructor to test.
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getTestFactoryList() != null;
 * @invar getTestFactoryList().getElementType() == TestFactory.class;
 */
public class InstanceInspectorTestModel extends NonConstructorMethodTestModel {

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



  /*<property name="instance inspector">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init null;
   */
  public final Method getInstanceInspector() {
    return getNonConstructorMethod();
  }

  /**
   * @post new.getMethod() == method;
   */
  public final void setInstanceInspector(Method instanceInspector) {
    setNonConstructorMethod(instanceInspector);
  }

  /*</property>*/



  public PriorityList getTestFactoryList() {
    // TODO Auto-generated method stub
    return null;
  }

}