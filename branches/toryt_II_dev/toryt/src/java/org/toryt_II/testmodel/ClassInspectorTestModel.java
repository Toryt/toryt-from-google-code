package org.toryt_II.testmodel;


import java.lang.reflect.Method;

import org.toryt.util_I.reflect.Reflection.MethodKind;


/**
 * A model of a constructor to test.
 *
 * @author Jan Dockx
 *
 * @invar getMethodKind() == MethodKind.CLASS_INSPECTOR;
 */
public class ClassInspectorTestModel extends NonConstructorMethodTestModel {

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


  public ClassInspectorTestModel() {
    super(MethodKind.CLASS_INSPECTOR);
  }



  /*<property name="class inspector">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init null;
   */
  public final Method getClassInspector() {
    return getNonConstructorMethod();
  }

  /**
   * @pre (classInspector != null) ?
   *        Reflection.methodKind(classInspector) == getMethodKind();
   * @post new.getMethod() == method;
   */
  public final void setClassInspector(Method classInspector) {
    setNonConstructorMethod(classInspector); // events
  }

  /*</property>*/

}