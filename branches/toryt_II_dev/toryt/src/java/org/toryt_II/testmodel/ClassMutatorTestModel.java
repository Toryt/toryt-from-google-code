package org.toryt_II.testmodel;


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

}