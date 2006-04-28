package org.toryt_II.testmodel;


import java.lang.reflect.Constructor;

import org.toryt.util_I.annotations.vcs.CvsInfo;
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
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ConstructorTestModel extends MethodTestModel {



  /**
   * @return getConstructor();
   */
  public final Object getMethod() {
    return getConstructor();
  }



  /*<property name="constructor">*/
  //------------------------------------------------------------------

  /**
   * @basic
   * @init null;
   */
  public final Constructor getConstructor() {
    return $constructor;
  }

  /**
   * @post new.getConstructor() == method;
   */
  public final void setConstructor(Constructor constructor) {
    $constructor = constructor;
    // TODO events
  }

  private Constructor $constructor;

  /*</property>*/



  public PriorityList getTestFactoryList() {
    // TODO Auto-generated method stub
    return null;
  }


  public String toString() {
    return getClass().getName() + "[" + getConstructor() + "]";
  }

  void printStructure(IndentPrinter out) {
    assert out != null;
    out.println(getConstructor());
  }

}