package org.toryt_II.testobject;


import org.toryt.util_I.collections.priorityList.PriorityList;


/**
 * A {@link PriorityList} that makes it easy to create a
 * <acronym title="Test Object Factory Priority List">TOF PL</acronym>
 * for classes of which the instances are guaranteed to be immutable.
 * Use the method {@link #addImmutableTestObject(int, Object)} instead of
 * {@link #addPriorityElement(int, Object)}, and {@link #lock()} when ready.
 *
 * @author Jan Dockx
 *
 * @mudo we should be able to define the type of generated test objects
 */
public class ConstantTofPl extends ArrayHashTofPl {

  /* <section name="Meta Information"> */
  //------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /* </section> */



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
  public final void addImmutableTestObject(int priority, Object testObject) {
    assert testObject != null;
    addPriorityElement(priority, new ConstantTestObjectFactory(testObject));
  }

}