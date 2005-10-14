package org.toryt.support.straightlist;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;


/**
 * Wrapper around a {@link StraightList} that adds a first element that is
 * <code>null</code>.
 *
 * @author    Jan Dockx
 */
public final class NullFirstStraightList extends AbstractStraightList {

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
   * @pre l != null;
   * @post containsAll(l);
   * @post l.containsAll(this) except null;
   * @post ; the order of <code>l</code> and <code>this</code> is the same
   */
  public NullFirstStraightList(StraightList l) {
    assert l != null;
    $l = l;
  }
  
  /**
   * @invar $l != null;
   */
  private StraightList $l;
  
  public final boolean contains(Object o) {
    return (o == null) || $l.contains(o);
  }

  /**
   * Returns true if this collection contains all of the elements in the
   * specified collection.
   *
   * @see     Collection
   * @return  (forAll Object obj;
   *             c.contains(obj);
   *             contains(obj));
   */
  public final boolean containsAll(Collection c) {
    Iterator iter = c.iterator();
    while (iter.hasNext()) {
      Object cObj = iter.next();
      if (!contains(cObj)) {
        return false;
      }
    }
    return true;
  }

  public final boolean isEmpty() {
    return false; // always at least 1 element
  }

  public final Iterator iterator() {
    return new AbstractUnmodifiableIterator() {
      
      Iterator $iter = $l.iterator();
      boolean $givenNull = false;
      
      public boolean hasNext() {
        if (! $givenNull) {
          return true;
        }
        else {
          return $iter.hasNext();
        }
      }

      public Object next() {
        if (! $givenNull) {
          $givenNull = true;
          return null;
        }
        else {
          return $iter.next();
        }
      }

      public BigInteger getSizeGuess() {
        return $l.getBigSize().add(ONE);
      }

    };
  }

  public final int size() {
    return ($l.size() < Integer.MAX_VALUE)
              ? $l.size() + 1
              : Integer.MAX_VALUE;
  }

  public Object[] toArray(Class clazz, int size) {
    Object[] array = (Object[])Array.newInstance(clazz, size);
    System.arraycopy($l.toArray(clazz,  size - 1), 0, array, 1, $l.size());
    return array;
  }

  public final BigInteger getBigSize() {
    return $l.getBigSize().add(ONE);
  }

  public final boolean isSizeFixed() {
    return $l.isSizeFixed();
  }

}
