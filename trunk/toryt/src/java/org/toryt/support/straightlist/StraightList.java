package org.toryt.support.straightlist;


import java.math.BigInteger;
import java.util.Collection;
import java.util.List;


/**
 * A collection type very much like {@link List}, but simpler.
 * StraightLists can only be iterated from start to end, and are
 * immutable.
 * Entries in a StraightList can be valid or invalid. It is
 * possible that invalidity of elements is only noticed when
 * we try to visit an element the first time with an iterator.
 * Therefor, a StraightList is not really umodifiable: users
 * cannot add or remove elements after construction, but the size
 * may shrink while iterating over the StraightList, as invalid
 * elements are skipped. Ultimately, the exact number of valid elements
 * can be known if the StraightList is iterated over once.
 * {@link #isSizeFixed()} reports whether the size can shrink further.
 * Since there could be more than 1 simultaneous iteration over a given
 * StraightList, the iterator has the best guess of the actual number
 * of valid elements.
 *
 * @author    Jan Dockx
 * 
 * @invar iterator() != null;
 * @invar isSizeFixed() ==> getBigSize().equals('getBigSize());
 *        History constraint: once the size gets fixed, it doesn't change anymore
 * @invar getBigSize().compareTo('getBigSize()) <= 0;
 *        History constraint: the size can only shrink.
 */
public interface StraightList extends Collection {

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
   * @post false;
   * @throws UnsupportedOperationException
   *         true;
   */
  boolean add(Object o);

  /**
   * @post false;
   * @throws UnsupportedOperationException
   *         true;
   */
  boolean remove(Object o);


  /**
   * @post false;
   * @throws UnsupportedOperationException
   *         true;
   */
  boolean addAll(Collection c);

  /**
   * @post false;
   * @throws UnsupportedOperationException
   *         true;
   */
  boolean removeAll(Collection c);

  /**
   * @post false;
   * @throws UnsupportedOperationException
   *         true;
   */
  boolean retainAll(Collection c);

  /**
   * @post false;
   * @throws UnsupportedOperationException
   *         true;
   */
  void clear();

  /**
   * An array of type <code>clazz</code> and size <code>size</code>.
   * 
   * @mudo better contract
   */
  Object[] toArray(Class clazz, int size);

  /**
   * @basic
   */
  BigInteger getBigSize();
  
  /**
   * @return (getBigSize() < Integer.MAX_VALUE)
   *            ? getBigSize()
   *            : Integer.MAX_VALUE;
   */
  int size();
  

  /**
   * The {@link #getBigSize()} and {@link #size()} will not change anymore.
   * 
   * @basic
   */
  boolean isSizeFixed();

  public final static BigInteger ZERO = BigInteger.ZERO;
  public final static BigInteger ONE = BigInteger.ONE;
  
//  ValidityIterator validityIterator();
//  
//  /**
//   * Invalid elements in a {@link StraightList} will be skipped. As a result, the
//   * size of the list will shrink. The iterator has the best guess about the final
//   * size of the list.
//   * Since StraightLists are unmodifiable, the {@link #remove()} method
//   * always throws an {@link UnsupportedOperationException}.
//   * 
//   * @invar ! 'hasNext() ==> ! hasNext();
//   */
//  public interface ValidityIterator extends Iterator {
//
////    /**
////     * @basic
////     */
////    Object next();
////
////    /**
////     * The iterator size guess according to this iterator.
////     * 
////     * @basic
////     */
////    BigInteger getSizeGuess();
//    
//    /**
//     * @post false;
//     * @throws UnsupportedOperationException;
//     */
//    void remove() throws UnsupportedOperationException;
//        
//  }
  
}