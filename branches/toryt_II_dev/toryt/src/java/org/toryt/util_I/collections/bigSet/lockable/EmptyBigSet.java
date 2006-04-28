package org.toryt.util_I.collections.bigSet.lockable;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Iterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.BigSet;


/**
 * <p>An empty {@link BigSet}.</p>
 * <p>We need to provide a
 *   {@link #getElementType() element type} at construction.
 *
 * @invar getBigSize() == 1;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class EmptyBigSet extends AbstractLockedBigSet {


  public static final EmptyBigSet OBJECT_INSTANCE = new EmptyBigSet(Object.class);


  /**
   * @pre backingSet != null;
   */
  public EmptyBigSet(Class elementType) {
    super(elementType, true, BigInteger.ZERO);
  }

  public final boolean isEmpty() {
    return true;
  }

  public final boolean contains(Object o) {
    return false;
  }

  public final Iterator iterator() {
    return new AbstractLockedCollectionIterator() {

                  public final boolean hasNext() {
                    return false;
                  }

                  public Object next() {
                    throw new IndexOutOfBoundsException();
                  }

                };
  }

  public final Object[] toArray() {
    return new Object[] {};
  }

  public final Object[] toArray(Object[] a) {
    Object[] result;
    if (a.length >= 1) {
      result = a;
      result[0] = null;
    }
    else {
      result = (Object[])Array.newInstance(a.getClass().getComponentType(), 0);
    }
    return result;
  }

}