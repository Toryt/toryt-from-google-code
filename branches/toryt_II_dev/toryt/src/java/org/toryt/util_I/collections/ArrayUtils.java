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
  public static <_InBase_, _ResultBase_>
      _ResultBase_[] flatten2(_InBase_[] array,
                                      Class<_ResultBase_> resultComponentType) {
    assert resultComponentType != null;
    LinkedList<_ResultBase_> resultList = new LinkedList<_ResultBase_>();
    flattenHelper(array, resultList);
    @SuppressWarnings("unchecked") _ResultBase_[] result =
        (_ResultBase_[])Array.newInstance(resultComponentType, resultList.size());
    /* unchecked cast because Java API for array construction is not generic */
    resultList.toArray(result);
    return result;
  }

  /**
   * @pre resultComponentType != null;
   */
  public static <_ResultBase_> void flattenHelper(Object[] array,
                                                           LinkedList<_ResultBase_> acc) {
    for (Object firstLevel : array) {
      if ((firstLevel == null) || (! firstLevel.getClass().isArray())) {
        // firstLevel is the final level; it must me of tye _ResultComponentType_
        @SuppressWarnings("unchecked") _ResultBase_ simple = (_ResultBase_)firstLevel;
        // unchecked cast: it is ok, or pre's are violated
        acc.add(simple);
      }
      else {
        // firstLevel is an array; recursive
        flattenHelper((Object[])firstLevel, acc);
      }
    }
  }

}