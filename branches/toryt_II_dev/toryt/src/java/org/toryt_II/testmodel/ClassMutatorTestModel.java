package org.toryt_II.testmodel;


import java.lang.reflect.Method;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.Reflection.MethodKind;


/**
 * A model of a constructor to test.
 *
 * @author Jan Dockx
 *
 * @invar getMethodKind() == MethodKind.CLASS_MUTATOR;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ClassMutatorTestModel extends NonConstructorMethodTestModel {

  public ClassMutatorTestModel() {
    super(MethodKind.CLASS_MUTATOR);
  }



  /*<property name="class mutator">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init null;
   */
  public final Method getClassMutator() {
    return getNonConstructorMethod();
  }

  /**
   * @pre (classMutator != null) ?
   *        Reflection.methodKind(classMutator) == getMethodKind();
   * @post new.getMethod() == method;
   */
  public final void setClassMutator(Method classMutator) {
    setNonConstructorMethod(classMutator); // events
  }

  /*</property>*/

}