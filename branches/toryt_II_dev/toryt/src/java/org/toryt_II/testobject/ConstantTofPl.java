package org.toryt_II.testobject;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * A {@link PriorityList} that makes it easy to create a
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for classes of which the instances are guaranteed to be immutable.
 * Use the method {@link #addImmutableTestObject(int, _TestObjectType_)} instead of
 * {@link #addPriorityElement(int, Object)}, and {@link #lock()} when ready.
 *
 * @author Jan Dockx
 *
 * @mudo we should be able to define the type of generated test objects
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ConstantTofPl<_TestObjectType_> extends ArrayHashTofPl<_TestObjectType_> {

  /**
   * @pre priority >= 0;
   * @pre testObject != null;
   * @post (exists TestObjectFactory tof; get(priority).contains(tof);
   *            tof.generate() == testObject);
   * @throws UnsupportedOperationException
   *         isLocked();
   * @throws UnsupportedOperationException
   *         get(priority).isLocked();
   */
  public final void addImmutableTestObject(int priority, _TestObjectType_ testObject) {
    assert testObject != null;
    addPriorityElement(priority, new ConstantTestObjectFactory<_TestObjectType_>(testObject));
  }

}