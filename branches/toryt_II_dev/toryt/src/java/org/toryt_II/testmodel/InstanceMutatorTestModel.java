package org.toryt_II.testmodel;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.MethodKind;


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

}