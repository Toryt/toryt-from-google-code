package org.toryt.support.straightlist;


import java.util.Collection;
import java.util.List;


/**
 * A collection type very much like {@link List}, but simpler.
 * StraightLists can only be iterated from start to end, and are
 * immutable.
 *
 * @author    Jan Dockx
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

}
