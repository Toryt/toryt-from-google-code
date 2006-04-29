package org.toryt_II.testmodel;


import java.lang.reflect.Method;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.PriorityList;
import org.toryt.util_I.reflect.Reflection;
import org.toryt.util_I.reflect.Reflection.MethodKind;


/**
 * A model of a method to test. Instances of this type deliver
 * a {@link PriorityList} of {@link TestFactory TestFactories}
 * for {@link #getSubject()}. This class is abstract: you should use
 * one of the more specific subclasses for different kinds of methods
 * instead.
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getTestFactoryList() != null;
 * @invar getTestFactoryList().getElementType() == TestFactory.class;
 * @invar (getNonConstructorMethod() != null) ?
 *          Reflection.methodKind(getNonConstructorMethod()) == getMethodKind();
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class NonConstructorMethodTestModel extends MethodTestModel<Method> {



  protected NonConstructorMethodTestModel(MethodKind methodKind) {
    $methodKind = methodKind;
  }



  /*<property name="methodKind">*/
  //------------------------------------------------------------------

  public final MethodKind getMethodKind() {
    return $methodKind;
  }

  private final MethodKind $methodKind;

  /*</property>*/



  public void setSubject(Method subject) {
    assert (subject != null) ?
             (Reflection.methodKind(subject) == $methodKind) :
             true;
    super.setSubject(subject);
  }

  public PriorityList getTestFactoryList() {
    // TODO Auto-generated method stub
    return null;
  }

  protected final String getSubjectDisplayNameSave() {
    return getSubject().toGenericString();
  }

}