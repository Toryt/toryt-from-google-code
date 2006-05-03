package org.toryt_II.testobject;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.ArrayHashPriorityList;


/**
 * Provides default constructor for simple
 * {@link ArrayHashPriorityList} implementation of
 * {@link TestObjectFactoryPriorityList}.
 *
 * @author Jan Dockx
 *
 * @invar getPriorityElementType() == TestObjectFactory.class;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ArrayHashTofPl<_TestObject_>
    extends ArrayHashPriorityList<TestObjectFactory<_TestObject_>>
    implements TestObjectFactoryPriorityList<_TestObject_> {

  public ArrayHashTofPl() {
    super(false);
  }

}