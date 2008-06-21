package org.toryt_II.testmodel;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.reflect.Classes;


/**
 * Instances represent an inner class to test.
 *
 * @author Jan Dockx
 *
 * @invar Classes.isInnerClass(subject);
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class InnerClassTestModel<_Subject_> extends ClassTestModel<_Subject_>  {

  @Override
  public void setSubject(Class<_Subject_> subject) {
    assert Classes.isInnerClass(subject);
    super.setSubject(subject);
  }

}