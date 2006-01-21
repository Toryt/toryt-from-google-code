package org.toryt.util_I.collections.bigSet.lockable;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Iterator;

import org.toryt.util_I.collections.bigSet.BigSet;


/**
 * <p>A {@link BigSet} that contains exactly 1 element that is
 *   <code>null</code>.</p>
 * <p>Since <code>null</code> has no type, we need to provide a
 *   {@link #getElementType() element type} at construction. 
 *
 * @invar getBigSize() == 1;
 */
public class NullSingletonBigSet extends AbstractLockedBigSet {

  /* <section name="Meta Information"> */
  //------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /* </section> */


  
  public static final NullSingletonBigSet OBJECT_INSTANCE = new NullSingletonBigSet(Object.class); 
  
  
  /**
   * @pre backingSet != null;
   */
  public NullSingletonBigSet(Class elementType) {
    super(elementType, true, BigInteger.ONE);
  }
  
  public final boolean isEmpty() {
    return false;
  }

  public final boolean contains(Object o) {
    return (o == null);
  }
  
  public final Iterator iterator() {
    return new AbstractLockedCollectionIterator() {

                  private boolean $done = false;
                  
                  public final boolean hasNext() {
                    return ! $done;
                  }
            
                  public Object next() {
                    return null;
                  }
            
                };
  }

  public final Object[] toArray() {
    return new Object[] {null};
  }

  public final Object[] toArray(Object[] a) {
    Object[] result;
    if (a.length > 1) {
      result = a;
      result[1] = null;
    }
    else {
      result = (Object[])Array.newInstance(a.getClass().getComponentType(), 1);
    }
    result[0] = null;
    return result;
  }

}