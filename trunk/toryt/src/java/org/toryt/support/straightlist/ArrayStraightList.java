package org.toryt.support.straightlist;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * Wrapper around an array to make it a {@link StraightList}.
 *
 * @author    Jan Dockx
 */
public class ArrayStraightList extends AbstractStraightList {

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
   * @pre a != null;
   * @post containsAll(Arrays.asList(a));
   * @post Arrays.asList(a).containsAll(this);
   * @post ; the order of <code>l</code> and <code>this</code> is the same
   */
  public ArrayStraightList(Object[] a) {
    assert a != null : "array cannot be null";
    $a = a;
    $l = Arrays.asList($a);
  }
  
  /**
   * @invar $a != null;
   */
  private Object[] $a;
  
  /**
   * @invar $l != null;
   */
  private List $l;
  
  public final boolean contains(Object o) {
    return $l.contains(o);
  }

  public final boolean containsAll(Collection c) {
    return $l.containsAll(c);
  }

  public final boolean isEmpty() {
    return $a.length == 0;
  }

  public final Iterator iterator() {
    return $l.iterator();
  }

  public final int size() {
    return $a.length;
  }

  public final Object[] toArray(Class clazz, int size) {
    if (size < size()) {
      throw new ArrayIndexOutOfBoundsException();
    }
    Object[] result = (Object[])Array.newInstance(clazz, size); 
    System.arraycopy($a, 0, result, 0, $a.length);
    return result;
  }

  public BigInteger getBigSize() {
    return BigInteger.valueOf($a.length); 
  }

}
