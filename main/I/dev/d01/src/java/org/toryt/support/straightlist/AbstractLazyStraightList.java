package org.toryt.support.straightlist;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;


/**
 * Implementation of some methods for lazy {@link StraightList} instances.
 * These methods are very costly, since they actually generate all elements.
 *
 * @author    Jan Dockx
 * 
 * @invar ! contains(null);
 * @invar (forall Object o, contains(o), o instanceof Map);
 * 
 * @mudo better contract
 */
public abstract class AbstractLazyStraightList extends AbstractStraightList {

  /*<section name="Meta Information">*/
  //------------------------------------------------------------------

  /** {@value} */
  public static final String CVS_REVISION = "$Revision$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_DATE = "$Date$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_STATE = "$State$"; //$NON-NLS-1$
  /** {@value} */
  public static final String CVS_TAG = "$Name$"; //$NON-NLS-1$

  /*</section>*/


  /**
   * {@inheritDoc}
   * This method is very costly, since it actually generates the combinations
   * until a match failure is found.
   */
  public final boolean contains(Object o) {
    Iterator iter = iterator();
    while (iter.hasNext()) {
      Map entry = (Map)iter.next();
      if (! entry.equals(o)) {
        return false;
      }
    }
    return true;
  }

  public final boolean isEmpty() {
    return size() == 0;
  }

  /**
   * {@inheritDoc}
   * This method is very costly, since it actually generates the combinations.
   */
  public final Object[] toArray(Class clazz, int size) {
    if (size < size()) {
      throw new ArrayIndexOutOfBoundsException();
    }
    Object[] result = (Object[])Array.newInstance(clazz, size); 
    int i = 0;
    Iterator iter = iterator();
    while (iter.hasNext()) {
      Map entry = (Map)iter.next();
      result[i] = entry;
    }
    return result;
  }

  private final static BigInteger MAX_INTEGER
      = BigInteger.valueOf(Integer.MAX_VALUE);

  public final int size() {
    return (getBigSize().compareTo(MAX_INTEGER) < 0) 
            ? getBigSize().intValue()
            : Integer.MAX_VALUE;
  }

  public final BigInteger getBigSize() {
    return $sizeGuess;
  }

  /**
   * @pre ! $sizeFixed
   */
  protected void updateListSize(BigInteger sizeGuess, boolean stillMoreToCheck) {
    if (($sizeGuess == null) || ($sizeGuess.compareTo(sizeGuess) > 0)) {
      // the iterator has a tighther guess; copy it
      $sizeGuess = sizeGuess;
    }
    $sizeFixed |= (! stillMoreToCheck);
  }

  protected void setSizeGuess(BigInteger sizeGuess) {
    $sizeGuess = sizeGuess;
  }
  
  /**
   * The best guess of the size of this list. This can only
   * shrink.
   * 
   * @invar $sizeGuess <= '$sizeGuess;
   * @invar $sizeFixed ==> ($sizeGuess == '$sizeGuess);
   */
  private BigInteger $sizeGuess;
  
  public final boolean isSizeFixed() {
    return $sizeFixed;
  }

  private boolean $sizeFixed;

}
