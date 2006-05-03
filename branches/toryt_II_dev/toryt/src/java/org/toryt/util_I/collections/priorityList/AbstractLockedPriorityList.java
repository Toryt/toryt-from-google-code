package org.toryt.util_I.collections.priorityList;


import java.math.BigInteger;
import java.util.Iterator;
import java.util.ListIterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;
import org.toryt.util_I.collections.lockable.AbstractLockedList;


/**
 * <p>Implementation of some methods for a locked {@link PriorityList}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractLockedPriorityList<_PriorityElement_>
    extends AbstractLockedList<LockableBigSet<? extends _PriorityElement_>>
    implements PriorityList<_PriorityElement_> {

  /**
   * @pre cardinality != null;
   * @pre cardinality >= 0;
   */
  protected AbstractLockedPriorityList(BigInteger cardinality) {
    super(false);
    assert cardinality != null;
    assert cardinality.compareTo(BigInteger.ZERO) >= 0;
    $cardinality = cardinality;
  }



  /* <property name="cardinality"> */
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


  @Override
  public String toString() {
    StringBuffer result = new StringBuffer();
    ListIterator<LockableBigSet<? extends _PriorityElement_>> iter = listIterator();
    while (iter.hasNext()) {
      LockableBigSet<? extends _PriorityElement_> lbs = iter.next();
      result.append(iter.previousIndex());
      result.append(" (");
      result.append(lbs.getBigSize());
      result.append("): ");
      Iterator<? extends _PriorityElement_> lbsIter = lbs.iterator();
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

  protected String priorityElementToString(_PriorityElement_ element) {
    return (element == null) ? "null" : element.toString();
  }

}