package org.toryt.util_I.collections.bigSet;


import java.math.BigInteger;
import java.util.Collection;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.collections.AbstractLockedCollection;


/**
 * <p>Implementation of some methods for a locked {@link BigSet}.</p>
 *
 * @author Jan Dockx
 */
public abstract class AbstractLockedBigSet extends AbstractLockedCollection
    implements LockableBigSet {

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
   * @pre elementType != null;
   * @pre bigSize != null;
   * @pre bigSize >= 0;
   */
  protected AbstractLockedBigSet(Class elementType, BigInteger bigSize) {
    assert elementType != null;
    assert bigSize != null;
    assert bigSize.compareTo(BigInteger.ZERO) >= 0;
    $elementType = elementType;
    $bigSize = bigSize;
  }



  /* <property name="element type"> */
  //------------------------------------------------------------------

  public final Class getElementType() {
    return $elementType;
  }

  /**
   * @invar $elementType != null;
   */
  private Class $elementType;

  /*</property>*/



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