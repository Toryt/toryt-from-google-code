package org.toryt.support.straightlist;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An empty {@link StraightList}.
 *
 * @author    Jan Dockx
 */
public final class EmptyStraightList extends AbstractAllValidStraightList {

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



  public final static EmptyStraightList INSTANCE =  new EmptyStraightList();

  public final boolean contains(Object o) {
    return false;
  }

  public final boolean containsAll(Collection c) {
    return false;
  }

  public final boolean isEmpty() {
    return true;
  }

  public final Iterator iterator() {
    return new AbstractUnmodifiableIterator() {

      public boolean hasNext() {
        return false;
      }

      public Object next() {
        throw new NoSuchElementException();
      }

    };
  }

  public final int size() {
    return 0;
  }

  public final Object[] toArray(Class clazz, int size) {
    return (Object[])Array.newInstance(clazz, 0);
  }

  public final BigInteger getBigSize() {
    return ZERO;
  }

}
