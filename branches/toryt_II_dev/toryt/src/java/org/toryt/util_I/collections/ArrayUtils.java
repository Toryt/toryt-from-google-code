package org.toryt.util_I.collections;


import java.util.ArrayList;
import java.util.List;


/**
 * <p>General array utility methods.</p>
 *
 * @author Jan Dockx
 */
public abstract class ArrayUtils {

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


  public static Object[] flatten(Object[] array) {
    return flattenHelper(array).toArray(array);
  }

  private static List flattenHelper(Object e) {
    ArrayList result = new ArrayList();
    if ((e == null) || (! e.getClass().isArray())) {
      result.add(e);
    }
    else {
      Object array[] = (Object[])e;
      for (int i = 0; i < array.length; i++) {
        result.addAll(flattenHelper(array[i]));
      }
    }
    return result;
  }

}