package org.toryt_II.testobject;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * A {@link PriorityList} that has {@link TestObjectFactory TestObjectFactories}
 * for <code>_TestObjectType_</code> as priority elements.
 *
 * @author Jan Dockx
 *
 * @invar getPriorityElementType() == TestObjectFactory.class;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface TestObjectFactoryPriorityList<_TestObject_>
    extends PriorityList<TestObjectFactory<_TestObject_>> {

  // NOP

}