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
public class ConstructorTestModel<_Subject_> extends MethodTestModel<Constructor<_Subject_>> {

  public PriorityList getTestFactoryList() {
    // MUDO Auto-generated method stub
    return null;
  }

  @Override
  protected final String getSubjectDisplayNameSave() {
    return getSubject().toGenericString();
  }

}