package org.toryt.util_I.collections.bigSet.lockable;


import java.math.BigInteger;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.BigSet;
import org.toryt.util_I.collections.lockable.AbstractLockedSet;


/**
 * <p>Implementation of some methods for a locked {@link BigSet}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractLockedBigSet<_Element_> extends AbstractLockedSet<_Element_>
    implements LockableBigSet<_Element_> {

  /**
   * @pre bigSize != null;
   * @pre bigSize >= 0;
   */
  protected AbstractLockedBigSet(boolean nullAllowed, BigInteger bigSize) {
    super(nullAllowed);
    assert bigSize != null;
    assert bigSize.compareTo(BigInteger.ZERO) >= 0;
    $bigSize = bigSize;
  }

  // no need to override clone: there are no extra instance variables here that are mutable


  /* <property name="size"> */
  //------------------------------------------------------------------

  /**
   * @basic
   */
  public final BigInteger getBigSize() {
    return $bigSize;
  }

  private final static BigInteger MAXINT = BigInteger.valueOf(Integer.MAX_VALUE);

  /**
   * @deprecated
   */
  @Deprecated
  public final int size() {
    return (getBigSize().compareTo(MAXINT) < 0) ?
             getBigSize().intValue() :
             Integer.MAX_VALUE;
  }

  /**
   * @invar $bigSize != null;
   */
  private BigInteger $bigSize;

  /*</property>*/


  /*
   this implementation is too expensive

  public boolean contains(final Object o) {
    Iterator<_ElementType_> iter = iterator();
    while (iter.hasNext()) {
      if (iter.next().equals(o)) {
        return true;
      }
    }
    return false;
  }
  */

  /**
   * Overridden to use {@link #getBigSize()} instead of <code>size()</code>.
   */
  @Override
  public boolean equals(Object o) {
    return (o != null) &&
           (o instanceof BigSet) &&
           getBigSize().equals(((BigSet<?>)o).getBigSize()) &&
           containsAll((BigSet<?>)o) &&
           ((BigSet<?>)o).containsAll(this);
  }

  // no need to override hashCode()

}