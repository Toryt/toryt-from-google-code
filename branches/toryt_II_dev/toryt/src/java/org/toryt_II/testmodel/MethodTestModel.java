package org.toryt_II.testmodel;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * <p>A model of a method to test. Instances of this type deliver
 *   a {@link PriorityList} of {@link TestFactory TestFactories}
 *   for {@link #getSubject()}.</p>
 * <p>This class is abstract: you should use
 *   one of the more specific subclasses for different kinds of methods
 *   instead.<code>_SubjectType_</code> is still unbound, because in Java
 *   reflection {@link java.lang.reflect.Method} and
 *   {@link java.lang.reflect.Constructor} are unrelated types.</p>
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getTestFactoryList() != null;
 * @invar getTestFactoryList().getElementType() == TestFactory.class;
 * @invar (getMethod() instanceof Method) || (getMethod() instanceof Constructor);
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class MethodTestModel<_SubjectType_> extends AbstractTestModel<_SubjectType_> {

  protected final void printStructure(IndentPrinter out) {
    assert out != null;
    out.println(getSubjectDisplayName());
  }

}