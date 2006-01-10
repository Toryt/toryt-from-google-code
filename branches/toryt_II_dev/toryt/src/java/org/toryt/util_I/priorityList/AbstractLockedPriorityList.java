package org.toryt.util_I.priorityList;


import java.math.BigInteger;
import java.util.Collection;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.collections.AbstractLockedList;
import org.toryt.util_I.collections.bigSet.LockableBigSet;


/**
 * <p>Implementation of some methods for a locked {@link PriorityList}.</p>
 *
 * @author Jan Dockx
 */
public abstract class AbstractLockedPriorityList extends AbstractLockedList
    implements PriorityList {

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
   * @pre priorityPriorityElementType != null;
   * @pre cardinality != null;
   * @pre cardinality >= 0;
   */
  protected AbstractLockedPriorityList(Class priorityPriorityElementType, BigInteger cardinality) {
    assert priorityPriorityElementType != null;
    assert cardinality != null;
    assert cardinality.compareTo(BigInteger.ZERO) >= 0;
    $priorityPriorityElementType = priorityPriorityElementType;
    $cardinality = cardinality;
  }



  /* <property name="element type"> */
  //------------------------------------------------------------------

  public Class getElementType() {
    return LockableBigSet.class;
  }

  /*</property>*/

  

  /* <property name="priority element type"> */
  //------------------------------------------------------------------

  public final Class getPriorityElementType() {
    return $priorityPriorityElementType;
  }

  /**
   * @invar $priorityPriorityElementType != null;
   */
  private Class $priorityPriorityElementType;

  /*</property>*/



  /* <property name="size"> */
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final BigInteger getCardinality() {
    return $cardinality;
  }

  /**
   * @invar $cardinality != null;
   */
  private BigInteger $cardinality;

  /*</property>*/


  /* this implementation is too expensive
  public boolean contains(final Object o) {
    Iterator iter = iterator();
    while (iter.hasNext()) {
      if (iter.next().equals(o)) {
        return true;
      }
    }
    return false;
  }
  */

  public final boolean containsAll(Collection c) {
    return Collections.forAll(c,
                              new Assertion() {

                                    public boolean isTrueFor(Object o) {
                                      return contains(o);
                                    }

                                  });
  }

}