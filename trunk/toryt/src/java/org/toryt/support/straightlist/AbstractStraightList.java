package org.toryt.support.straightlist;


import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;


/**
 * Implementation of a number of methods of {@link StraightList}
 *
 * @author    Jan Dockx
 */
public abstract class AbstractStraightList implements StraightList {

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



  public final boolean add(Object o) {
    throw new UnsupportedOperationException();
  }

  public final boolean remove(Object o) {
    throw new UnsupportedOperationException();
  } 

  public final boolean addAll(Collection c) {
    throw new UnsupportedOperationException();
  }

  public final boolean removeAll(Collection c) {
    throw new UnsupportedOperationException();
  }

  public final boolean retainAll(Collection c) {
    throw new UnsupportedOperationException();
  }

  public final void clear() {
    throw new UnsupportedOperationException();
  }
  
  public boolean containsAll(Collection c) {
    Iterator iter = c.iterator();
    while (iter.hasNext()) {
      if (! contains(iter.next())) {
        return false;
      }
    }
    return true;
  }

  public Object[] toArray() {
    return toArray(Object.class);
  }

  public Object[] toArray(Object[] a) {
    if (a.length >= size()) {
      System.arraycopy(toArray(a.getClass().getComponentType()), 0, a, 0, a.length);
      Arrays.fill(a, size(), a.length - 1, null);
      return a;
    }
    else {
      return toArray(a.getClass());
    }
  }

  public final Object[] toArray(Class clazz) {
    return toArray(clazz, size());
  }

  public final boolean equals(Object other) {
    return super.equals(other);
  }
  
  public final int hashCode() {
    return super.hashCode();
  }

  public final String toString() {
    StringBuffer result = new StringBuffer("[");
    Iterator iter = iterator();
    while (iter.hasNext()) {
      Object o = iter.next();
      result.append(o);
      if (iter.hasNext()) {
        result.append(", ");
      }
    }
    result.append("]");
    return result.toString();
  }

}
