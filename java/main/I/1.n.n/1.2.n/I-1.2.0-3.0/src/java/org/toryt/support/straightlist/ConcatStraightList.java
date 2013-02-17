package org.toryt.support.straightlist;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * A (lazy) concatenation of other StraightLists. {@link #iterator()}
 * traverses the lists one after the other.
 *
 * @author    Jan Dockx
 */
public final class ConcatStraightList extends AbstractStraightList {

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
   * @pre ! Arrays.asList(l).contains(null);
   * @post (forall int i; (0 <= i) && (i < l.length); containsAll(l[i]));
   * @post (forall Object o; contains(o);
   *          (exists int i; (0 <= i) && (i < l.length); l[i].contains(o)));
   */
  public ConcatStraightList(StraightList[] l) {
    assert l != null;
    $l = l;
  }
  
  /**
   * @invar $l != null;
   */
  private StraightList[] $l;
  
  public final boolean contains(Object o) {
    for (int i = 0; i < $l.length; i++) {
      if ($l[i].contains(o)) {
        return true;
      }
    }
    return false;
  }

  public final boolean isEmpty() {
    for (int i = 0; i < $l.length; i++) {
      if (! $l[i].isEmpty()) {
        return false;
      }
    }
    return true;
  }

  public final Iterator iterator() {
    return new AbstractUnmodifiableIterator() {

      /**
       * The next iterator.
       * 
       * @invar $li < $l.length
       */
      private int $li = 0;
      
      /**
       * The iterator of $l[$li - 1];
       * $iter will always be ready for the next element.
       * If it is null, there are no more elements.
       * There will be no iterator for an empty list.
       */
      private Iterator $iter = null;
      
      {
        nextIter();
      }

      private void nextIter() {
        $iter = null;
        while (($li < $l.length) && ($iter == null)) {
          $iter = $l[$li].iterator();
          if (! $iter.hasNext()) {
            $iter = null;
          }
          $li++;
        }
      }
      
      public final Object next() throws NoSuchElementException {
        if ($iter == null) {
          throw new NoSuchElementException();
        }
        Object result = $iter.next();
        if (! $iter.hasNext()) {
          nextIter(); // ready for the next next() and for hasNext()
        }
        return result;
      }

      public final BigInteger getSizeGuess() {
        return getBigSize();
      }

      public final boolean hasNext() {
        return $iter != null;
      }
      
    };
  }
  
  public final int size() {
    return getBigSize().intValue();
  }

  public final Object[] toArray(Class clazz, int size) {
    if (size < size()) {
      throw new ArrayIndexOutOfBoundsException();
    }
    Object[] result = (Object[])Array.newInstance(clazz, size); 
    int start = 0;
    for (int i = 0; i < $l.length; i++) {
      int lSize = $l[i].size();
      System.arraycopy($l[i].toArray(), 0, result, start, lSize);
      start += lSize;
    }
    return result;
  }

  /**
   * Not null means fixed.
   */
  private BigInteger $bigSize = null;

  public final BigInteger getBigSize() {
    if ($bigSize == null) {
      BigInteger acc = ZERO;
      boolean fixed = true;
      for (int i = 0; i < $l.length; i++) {
        acc = acc.add($l[i].getBigSize());
        fixed &= $l[i].isSizeFixed();
      }
      if (fixed) {
        $bigSize = acc;
      }
      return acc;
    }
    else {
      return $bigSize;
    }
  }
  
  public boolean isSizeFixed() {
    getBigSize();
    return ($bigSize != null);
  }

}
