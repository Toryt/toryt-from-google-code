package org.toryt.support.straightlist;


import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * Wrapper around a {@link List} to make it a {@link StraightList}.
 * All non-modifying methods delegate to the wrapped list. Other methods
 * throw an {@link UnsupportedOperationException}.
 *
 * @author    Jan Dockx
 */
public class ListWrapperStraightList extends AbstractStraightList {

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
   * @post l.containsAll(this);
   * @post ; the order of <code>l</code> and <code>this</code> is the same
   */
  public ListWrapperStraightList(List l) {
    assert l != null;
    $l = l;
  }
  
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
    return $l.isEmpty();
  }

  public final Iterator iterator() {
    return $l.iterator();
  }

  public final int size() {
    return $l.size();
  }

  public final Object[] toArray() {
    return $l.toArray();
  }

  public final Object[] toArray(Object[] a) {
    return $l.toArray(a);
  }

  public Object[] toArray(Class clazz, int size) {
    Object[] array = (Object[])Array.newInstance(clazz, size);
    return toArray(array);
  }

}
