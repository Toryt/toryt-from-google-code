package org.toryt_II.testobject;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.ArrayHashPriorityList;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * A {@link PriorityList} that has {@link TestObjectFactory TestObjectFactories}
 * as priority elements.
 *
 * @author Jan Dockx
 *
 * @invar getPriorityElementType() == TestObjectFactory.class;
 *
 * @mudo we should be able to define the type of generated test objects
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ArrayHashTofPl extends ArrayHashPriorityList {

  /**
   * @post new.getPriorityElementType() == TestObjectFactory.class;
   * @post ! new.isLocked();
   */
  public ArrayHashTofPl() {
    super(TestObjectFactory.class);
  }

}