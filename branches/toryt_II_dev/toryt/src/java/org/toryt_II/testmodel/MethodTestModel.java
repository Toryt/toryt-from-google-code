package org.toryt_II.testmodel;


import java.lang.reflect.Member;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>A model of a method to test.</p>
 * <p>This class is abstract: you should use
 *   one of the more specific subclasses for different kinds of methods
 *   instead. <code>_Subject_</code> is bound by {@link Member}, because in
 *   Java reflection that is the closest common subtype of
 *   {@link java.lang.reflect.Method} and {@link java.lang.reflect.Constructor}.</p>
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getTestFactoryList() != null;
 * @invar getTestFactoryList().getElementType() == TestFactory.class;
 * @invar (getSubject() instanceof Method) || (getSubject() instanceof Constructor);
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class MethodTestModel<_Subject_ extends Member>
    extends AbstractTestModel<_Subject_> {

  @Override
  protected final void printStructure(IndentPrinter out) {
    assert out != null;
    out.println(getSubjectDisplayName());
  }

}