package org.toryt_II.testmodel;


import java.lang.reflect.Method;

import org.toryt.util_I.reflect.Reflection.MethodKind;


/**
 * A model of a constructor to test.
 *
 * @author Jan Dockx
 *
 * @invar getMethodKind() == MethodKind.INSTANCE_INSPECTOR;
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



  public InstanceInspectorTestModel() {
    super(MethodKind.INSTANCE_INSPECTOR);
  }



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
   * @pre (instanceInspector != null) ?
   *        Reflection.methodKind(instanceInspector) == getMethodKind();
   * @post new.getMethod() == method;
   */
  public final void setInstanceInspector(Method instanceInspector) {
    setNonConstructorMethod(instanceInspector); // events
  }

  /*</property>*/

}