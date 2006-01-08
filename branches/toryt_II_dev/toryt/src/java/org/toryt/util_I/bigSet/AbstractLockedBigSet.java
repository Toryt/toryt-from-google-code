package org.toryt.util_I.bigSet;


import java.math.BigInteger;
import java.util.Collection;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.collections.AbstractLockedCollection;


/**
 * <p>Implementation of some methods for a locked {@link BigSet}.</p>
 */
public abstract class AbstractLockedBigSet extends AbstractLockedCollection
    implements LockBigSet {

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
   */
  public AbstractLockedBigSet(Class elementType) {
    assert elementType != null;
    $elementType = elementType;
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

  private final static BigInteger MAXINT = BigInteger.valueOf(Integer.MAX_VALUE);

  /**
   * @deprecated
   */
  public final int size() {
    return (getBigSize().compareTo(MAXINT) < 0) ?
             getBigSize().intValue() :
             Integer.MAX_VALUE;
  }

  /*</property>*/



  /**
   * @deprecated
   */
  public final boolean containsAll(Collection c) {
    return Collections.forAll(c,
                              new Assertion() {

                                    public boolean isTrueFor(Object o) {
                                      return contains(o);
                                    }

                                  });
  }

  /**
   * @deprecated
   */
  public final Object[] toArray() {
    return toArray(new Object[size()]);
  }

}