package org.toryt_II.testmodel;


import java.lang.reflect.Method;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.Reflection.MethodKind;


/**
 * A model of a constructor to test.
 *
 * @author Jan Dockx
 *
 * @invar getMethodKind() == MethodKind.INSTANCE_INSPECTOR;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class InstanceInspectorTestModel extends NonConstructorMethodTestModel {



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