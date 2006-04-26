package org.toryt_II.testobject;


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
public class ArrayHashTofPl extends ArrayHashPriorityList {

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
   * @post new.getPriorityElementType() == TestObjectFactory.class;
   * @post ! new.isLocked();
   */
  public ArrayHashTofPl() {
    super(TestObjectFactory.class);
  }

}