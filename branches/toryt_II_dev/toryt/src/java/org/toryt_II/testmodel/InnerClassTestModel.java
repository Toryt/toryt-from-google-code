package org.toryt_II.testmodel;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.Reflection.TypeKind;


/**
 * Instances represent an inner class to test.
 *
 * @author Jan Dockx
 *
 * @invar getTypeKind() == TypeKind.INNER;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class InnerClassTestModel<_TypeToTest_>  extends ClassTestModel<_TypeToTest_>  {

  public InnerClassTestModel() {
    super(TypeKind.INNER);
  }

}