package org.toryt.support.straightlist;


import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;


/**
 * Implementation of some methods for lazy {@link StraightList} instances.
 * These methods are very costly, since they actually generate all elements.
 *
 * @author    Jan Dockx
 * 
 * @invar ! contains(null);
 * @invar (forall Object o, contains(o), o instanceof Map);
 * 
 * @mudo better contract
 */
public abstract class AbstractLazyStraightList extends AbstractStraightList {

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
   * {@inheritDoc}
   * This method is very costly, since it actually generates the combinations
   * until a match failure is found.
   */
  public final boolean contains(Object o) {
    Iterator iter = iterator();
    while (iter.hasNext()) {
      Map entry = (Map)iter.next();
      if (! entry.equals(o)) {
        return false;
      }
    }
    return true;
  }

  public final boolean isEmpty() {
    return size() == 0;
  }

  /**
   * {@inheritDoc}
   * This method is very costly, since it actually generates the combinations.
   */
  public final Object[] toArray(Class clazz, int size) {
    if (size < size()) {
      throw new ArrayIndexOutOfBoundsException();
    }
    Object[] result = (Object[])Array.newInstance(clazz, size); 
    int i = 0;
    Iterator iter = iterator();
    while (iter.hasNext()) {
      Map entry = (Map)iter.next();
      result[i] = entry;
    }
    return result;
  }

}
