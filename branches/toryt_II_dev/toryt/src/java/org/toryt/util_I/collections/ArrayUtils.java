package org.toryt.util_I.collections;


import java.lang.reflect.Array;
import java.util.LinkedList;

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

  /**
   * @pre resultComponentType != null;
   */
  @SuppressWarnings("unchecked")
  public static <_InComponentType_, _ResultComponentType_>
      _ResultComponentType_[] flatten2(_InComponentType_[] array,
                                      Class<_ResultComponentType_> resultComponentType) {
    assert resultComponentType != null;
    LinkedList<_ResultComponentType_> resultList = new LinkedList<_ResultComponentType_>();
    flattenHelper(array, resultList);
    _ResultComponentType_[] result = (_ResultComponentType_[])Array.newInstance(resultComponentType, resultList.size());
    /* unchecked cast because Java API for array construction is not generic */
    resultList.toArray(result);
    return result;
  }

  /**
   * @pre resultComponentType != null;
   */
  @SuppressWarnings("unchecked")
  public static <_ResultComponentType_> void flattenHelper(Object[] array,
                                                           LinkedList<_ResultComponentType_> acc) {
    for (Object firstLevel : array) {
      if ((firstLevel == null) || (! firstLevel.getClass().isArray())) {
        // firstLevel is the final level; it must me of tye _ResultComponentType_
        acc.add((_ResultComponentType_)firstLevel);
        // unchecked cast: it is ok, or pre's are violated
      }
      else {
        // firstLevel is an array; recursive
        flattenHelper((Object[])firstLevel, acc);
      }
    }
  }

}