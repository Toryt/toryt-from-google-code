package org.toryt_II.testmodel;


import java.lang.reflect.Method;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.Reflection.MethodKind;


/**
 * A model of a constructor to test.
 *
 * @author Jan Dockx
 *
 * @invar getMethodKind() == MethodKind.INSTANCE_MUTATOR;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class InstanceMutatorTestModel extends NonConstructorMethodTestModel {



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