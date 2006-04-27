package org.toryt.util_I.collections.priorityList;


import java.math.BigInteger;
import java.util.Iterator;
import java.util.ListIterator;

import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.lockable.AbstractLockedList;


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

  public final Class getElementType() {
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


  public final boolean isNullAllowed() {
    return false;
  }

  public String toString() {
    StringBuffer result = new StringBuffer();
    ListIterator iter = listIterator();
    while (iter.hasNext()) {
      LockableBigSet lbs = (LockableBigSet)iter.next();
      result.append(iter.previousIndex());
      result.append(" (");
      result.append(lbs.getBigSize());
      result.append("): ");
      Iterator lbsIter = lbs.iterator();
      while (lbsIter.hasNext()) {
        result.append(priorityElementToString(lbsIter.next()));
        if (lbsIter.hasNext()) {
          result.append(", ");
        }
      }
      result.append("\n");
    }
    return result.toString();
  }

  protected String priorityElementToString(Object element) {
    return (element == null) ? "null" : element.toString();
  }

}