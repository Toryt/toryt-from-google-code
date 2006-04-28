package org.toryt.patterns_I;


import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class Collections {

  private Collections() {
    // NOP
  }

  /**
   * @pre c != null;
   * @return ! c.contains(null)
   */
  public static boolean noNull(Collection c) {
    assert c != null;
    return ! c.contains(null);
  }

  /**
   * @pre c != null;
   * @pre a != null;
   * @return (forall Object o; c.contains(o); a.isTrueFor(o));
   */
  public static boolean forAll(Collection c, Assertion a) {
    assert c != null;
    assert a != null;
    Iterator iter = c.iterator();
    while (iter.hasNext()) {
      if (! a.isTrueFor(iter.next())) {
        return false;
      }
    }
    return true;
  }

  /**
   * @pre c != null;
   * @pre a != null;
   * @return (exists Object o; c.contains(o); a.isTrueFor(o));
   */
  public static boolean exists(Collection c, Assertion a) {
    assert c != null;
    assert a != null;
    Iterator iter = c.iterator();
    while (iter.hasNext()) {
      if (a.isTrueFor(iter.next())) {
        return true;
      }
    }
    return false;
  }

  /**
   * @pre c != null;
   * @pre type != null;
   * @return (forall Object o, c.contains(o); type.isInstance(o));
   */
  public static boolean instanceOf(Collection c, final Class type) {
    assert type != null;
    return forAll(c, new Assertion() {

                            public boolean isTrueFor(Object o) {
                              return type.isInstance(o);
                            }

                         });
  }

  /**
   * <code>a</code> contains all elements of <code>b</code>.
   *
   * @pre a != null;
   * @pre b != null;
   * @return c1.containsAll(c2) && c2.containsAll(c1);
   */
  public static boolean sameContents(final Collection c1, final Collection c2) {
    assert c1 != null;
    assert c2 != null;
    return c1.containsAll(c2) && c2.containsAll(c1);
  }

  /**
   * @pre m != null;
   * @result ! m.containsKey(null)
   * @result ! m.containsValue(null)
   */
  public static boolean noNull(Map m) {
    assert m != null;
    return (! m.containsKey(null)) && (! m.containsValue(null));
  }

  /**
   * @pre m != null;
   * @pre keyType != null;
   * @pre valueType != null;
   * @result (forall Object o, m.containsKey(o); keyType.isInstance(o));
   * @result (forall Object o, m.containsValue(o); valueType.isInstance(o));
   */
  public static boolean instanceOf(Map m, final Class keyType, final Class valueType) {
    assert m != null;
    assert keyType != null;
    assert valueType != null;
    return forAll(m.entrySet(),
                  new Assertion() {

                          public boolean isTrueFor(Object o) {
                            Map.Entry entry = (Map.Entry)o;
                            return keyType.isInstance(entry.getKey()) &&
                                   valueType.isInstance(entry.getValue());
                          }

                       });
  }

  /**
   * @pre array != null;
   * @pre a != null;
   * @return (forall int i; (i >= 0) && (i < array.length);
   *            a.isTrueFor(array[i]));
   */
  public static boolean forAll(Object[] array, Assertion a) {
    assert array != null;
    assert a != null;
    for (int i = 0; i < array.length; i++) {
      if (! a.isTrueFor(array[i])) {
        return false;
      }
    }
    return true;
  }

  /**
   * @pre map != null;
   * @pre a != null;
   * @return (forall Object o; map.values().contains(o);
   *            a.isTrueFor(o));
   */
  public static boolean forAllValues(Map map, Assertion a) {
    assert map != null;
    assert a != null;
    Iterator iter = map.values().iterator();
    while (iter.hasNext()) {
      if (! a.isTrueFor(iter.next())) {
        return false;
      }
    }
    return true;
  }

  /**
   * @pre array != null;
   * @pre a != null;
   * @return (exists int i; (i >= 0) && (i < array.length);
   *            a.isTrueFor(array[i]));
   */
  public static boolean exists(Object[] array, Assertion a) {
    assert array != null;
    assert a != null;
    for (int i = 0; i < array.length; i++) {
      if (a.isTrueFor(array[i])) {
        return true;
      }
    }
    return false;
  }

  /**
   * @pre a != null;
   * @return (forall int i; (i >= 0) && (i < a.length); a[i] != null);
   */
  public static boolean noNull(Object[] a) {
    assert a != null;
    return ! ArrayUtils.contains(a, null);
  }

  /**
   * <code>a</code> contains all elements of <code>b</code>.
   *
   * @pre a != null;
   * @pre b != null;
   * @return (forall Object o; ArrayUtils.contains(b, o), ArrayUtils.contains(a, o));
   */
  public static boolean containsAll(final Object[] a, final Object[] b) {
    assert a != null;
    assert b != null;
    return forAll(b, new Assertion() {

                            public boolean isTrueFor(Object o) {
                              return ArrayUtils.contains(a, o);
                            }

                          });
  }

  /**
   * <code>a</code> contains all elements of <code>b</code>.
   *
   * @pre a != null;
   * @pre b != null;
   * @return containsAll(a, b) && containsAll(b, a);
   */
  public static boolean sameContents(final Object[] a, final Object[] b) {
    assert a != null;
    assert b != null;
    return containsAll(a, b) && containsAll(b, a);
  }

}