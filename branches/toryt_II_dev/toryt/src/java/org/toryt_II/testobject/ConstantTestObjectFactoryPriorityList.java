package org.toryt_II.testobject;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.priorityList.ArrayHashPriorityList;
import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * A {@link PriorityList} that makes it easy to create a
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for classes of which the instances are guaranteed to be immutable.
 * Use the method {@link #addImmutableTestObject(int, Object)} instead of
 * {@link #addPriorityElement(int, Object)}, and {@link #lock()} when ready.
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ConstantTestObjectFactoryPriorityList<_TestObject_>
    extends ArrayHashPriorityList<TestObjectFactory<_TestObject_>>
    implements TestObjectFactoryPriorityList<_TestObject_> {

  public ConstantTestObjectFactoryPriorityList() {
    super(false);
  }


  /**
   * @post (exists TestObjectFactory tof; get(priority).contains(tof);
   *            tof.generate() == testObject);
   * @throws UnsupportedOperationException
   *         isLocked();
   * @throws UnsupportedOperationException
   *         get(priority).isLocked();
   */
  public final void addImmutableTestObject(int priority, _TestObject_ testObject)
      throws UnsupportedOperationException, NullPointerException, IndexOutOfBoundsException {
    addPriorityElement(priority, new ConstantTestObjectFactory<_TestObject_>(testObject));
  }

}