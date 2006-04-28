package org.toryt.util_I.collections;


import java.util.ArrayList;
import java.util.List;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>General array utility methods.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class ArrayUtils {

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