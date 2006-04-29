package org.toryt_II.testobject;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.ArrayHashPriorityList;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * A {@link PriorityList} that has {@link TestObjectFactory TestObjectFactories}
 * for <code>_TestObjectType_</code>
 * as priority elements.
 *
 * @author Jan Dockx
 *
 * @invar getPriorityElementType() == TestObjectFactory.class;
 *
 * @mudo this class makes no sense anymore with generics
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ArrayHashTofPl<_TestObjectType_>
    extends ArrayHashPriorityList<TestObjectFactory<_TestObjectType_>> {

  /**
   * @post new.getPriorityElementType() == TestObjectFactory.class;
   * @post ! new.isLocked();
   */
  public ArrayHashTofPl() {
    super(TestObjectFactory.class);
  }

}