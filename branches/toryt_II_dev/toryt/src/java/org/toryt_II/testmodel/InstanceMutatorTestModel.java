package org.toryt_II.testmodel;


import java.lang.reflect.Method;

import org.toryt.util_I.Reflection.MethodKind;


/**
 * A model of a constructor to test.
 *
 * @author Jan Dockx
 *
 * @invar getMethodKind() == MethodKind.INSTANCE_MUTATOR;
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



  public InstanceMutatorTestModel() {
    super(MethodKind.INSTANCE_MUTATOR);
  }


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
   * @pre (instanceMutator != null) ?
   *        Reflection.methodKind(instanceMutator) == getMethodKind();
   * @post new.getMethod() == method;
   */
  public final void setInstanceMutator(Method instanceMutator) {
    setNonConstructorMethod(instanceMutator); // events
  }

  /*</property>*/

}