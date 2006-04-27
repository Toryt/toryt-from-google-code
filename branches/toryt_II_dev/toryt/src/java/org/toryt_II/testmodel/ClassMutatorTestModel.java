package org.toryt_II.testmodel;


import java.lang.reflect.Method;

import org.toryt.util_I.Reflection.MethodKind;


/**
 * A model of a constructor to test.
 *
 * @author Jan Dockx
 *
 * @invar getMethodKind() == MethodKind.CLASS_MUTATOR;
 */
public class ClassMutatorTestModel extends NonConstructorMethodTestModel {

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