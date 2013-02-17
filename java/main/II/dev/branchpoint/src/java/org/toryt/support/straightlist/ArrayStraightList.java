package org.toryt.support.straightlist;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Wrapper around an array to make it a {@link StraightList}.
 *
 * @author    Jan Dockx
 */
public class ArrayStraightList extends AbstractAllValidStraightList {

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
  }
  
  /**
   * @invar $a != null;
   */
  private Object[] $a;
  
  private List $l;
  
  public final boolean contains(Object o) {
    if ($l == null) {
      $l = Arrays.asList($a);
    }
    return $l.contains(o);
  }

  public final boolean containsAll(Collection c) {
    if ($l == null) {
      $l = Arrays.asList($a);
    }
    return $l.containsAll(c);
  }

  public final boolean isEmpty() {
    return $a.length == 0;
  }

  /**
   * All elements are valid.
   */
  public final Iterator iterator() {
    return new AbstractUnmodifiableIterator() {

      int $i = 0;

      public Object next() {
        try {
          Object result = $a[$i];
          $i++;
          return result;
        }
        catch (ArrayIndexOutOfBoundsException aioobExc) {
          throw new NoSuchElementException();
        }
      }

      public boolean hasNext() {
        return $i < $a.length;
      }

    };
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

  public final BigInteger getBigSize() {
    return BigInteger.valueOf($a.length); 
  }

}
