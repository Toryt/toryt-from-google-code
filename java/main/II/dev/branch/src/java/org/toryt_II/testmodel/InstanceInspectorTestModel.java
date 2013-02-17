package org.toryt_II.testmodel;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.MethodKind;


/**
 * A model of a constructor to test.
 *
 * @author Jan Dockx
 *
 * @invar getMethodKind() == MethodKind.INSTANCE_INSPECTOR;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class InstanceInspectorTestModel extends NonConstructorMethodTestModel {

  public InstanceInspectorTestModel() {
    super(MethodKind.INSTANCE_INSPECTOR);
  }

}