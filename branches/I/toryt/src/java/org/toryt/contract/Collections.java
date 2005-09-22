package org.toryt.contract;


import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;


/**
 * @author Jan Dockx
 */
public class Collections {

  /*<section name="Meta Information">*/
  //  ------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /*</section>*/

  private Collections() {
    // NOP
  }

  /**
   * @return ! c.contains(null)
   */
  public static boolean noNull(Collection c) {
    return ! c.contains(null);
  }

  /**
   * @return (forall Object o, c.contains(o); type.isInstance(o));
   */
  public static boolean instanceOf(Collection c, Class type) {
    Iterator iter = c.iterator();
    while (iter.hasNext()) {
      if (! type.isInstance(iter.next())) {
        return false;
      }
    }
    return true;
  }

  /**
   * @result ! m.containsKey(null)
   * @result ! m.containsValue(null)
   */
  public static boolean noNull(Map m) {
    return (! m.containsKey(null)) && (! m.containsValue(null));
  }

  /**
   * @result (forall Object o, m.containsKey(o); keyType.isInstance(o));
   * @result (forall Object o, m.containsValue(o); valueType.isInstance(o));
   */
  public static boolean instanceOf(Map m, Class keyType, Class valueType) {
    Iterator iter = m.entrySet().iterator();
    while (iter.hasNext()) {
      Map.Entry entry = (Map.Entry)iter.next();
      if ((! keyType.isInstance(entry.getKey()))
          || (! valueType.isInstance(entry.getValue()))) {
        return false;
      }
    }
    return true;
  }

  /**
   * @return c1.containsAll(c2) && c2.containsAll(c1);
   */
  public static boolean hasSameContents(Collection c1, Collection c2) {
    return c1.containsAll(c2) && c2.containsAll(c1);
  }

  /**
   * The elements in <code>collection</code> are presented by its
   * {@link Collection#iterator()} in the order defined by
   * <code>comparator</code>.
   *
   * @pre collection != null;
   * @pre comparator != null;
   */
  public static boolean isSorted(Collection collection, Comparator comparator) {
    assert collection != null;
    assert comparator != null;
    if (collection.isEmpty()) {
      return true;
    }
    Iterator iter = collection.iterator();
    Object previous = iter.next(); // there is at least 1 element
    while (iter.hasNext()) {
      if ((previous == null) || (comparator.compare(previous, iter.next()) > 0)) {
        return false;
      }
    }
    return true;
  }

}