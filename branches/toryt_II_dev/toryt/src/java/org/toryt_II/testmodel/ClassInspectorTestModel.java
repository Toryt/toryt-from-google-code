package org.toryt_II.testmodel;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.MethodKind;


/**
 * A model of a constructor to test.
 *
 * @author Jan Dockx
 *
 * @invar getMethodKind() == MethodKind.CLASS_INSPECTOR;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ClassInspectorTestModel extends NonConstructorMethodTestModel {

  public ClassInspectorTestModel() {
    super(MethodKind.CLASS_INSPECTOR);
  }

}