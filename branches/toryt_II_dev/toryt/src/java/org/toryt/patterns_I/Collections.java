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
  public static boolean noNull(Collection<?> c) {
    assert c != null;
    return ! c.contains(null);
  }

  /**
   * @pre c != null;
   * @pre a != null;
   * @return (forall _T_ o; c.contains(o); a.isTrueFor(o));
   */
  public static <_T_> boolean forAll(Collection<_T_> c, Assertion<_T_> a) {
    assert c != null;
    assert a != null;
    Iterator<_T_> iter = c.iterator();
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
   * @return (exists _T_ o; c.contains(o); a.isTrueFor(o));
   */
  public static <_T_> boolean exists(Collection<_T_> c, Assertion<_T_> a) {
    assert c != null;
    assert a != null;
    Iterator<_T_> iter = c.iterator();
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
   *
   * @deprecated Use generics instead
   */
  @Deprecated
  public static <_T_> boolean instanceOf(Collection<_T_> c, final Class<_T_> type) {
    assert type != null;
    return forAll(c, new Assertion<_T_>() {

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
   * @deprecated use c1.equals(c2)
   */
  @Deprecated
  public static boolean sameContents(final Collection<?> c1, final Collection<?> c2) {
    assert c1 != null;
    assert c2 != null;
    return c1.containsAll(c2) && c2.containsAll(c1);
  }

  /**
   * @pre m != null;
   * @result ! m.containsKey(null)
   * @result ! m.containsValue(null)
   */
  public static boolean noNull(Map<?, ?> m) {
    assert m != null;
    return (! m.containsKey(null)) && (! m.containsValue(null));
  }

  /**
   * @pre m != null;
   * @pre keyType != null;
   * @pre valueType != null;
   * @result (forall Object o, m.containsKey(o); keyType.isInstance(o));
   * @result (forall Object o, m.containsValue(o); valueType.isInstance(o));
   *
   * @deprecated Use generics instead
   */
  @Deprecated
  public static <_K_, _V_> boolean instanceOf(Map<_K_, _V_> m, final Class<_K_> keyType, final Class<_V_> valueType) {
    assert m != null;
    assert keyType != null;
    assert valueType != null;
    return forAll(m.entrySet(),
                  new Assertion<Map.Entry<_K_, _V_>>() {

                          public boolean isTrueFor(Map.Entry<_K_, _V_> o) {
                            return keyType.isInstance(o.getKey()) &&
                                   valueType.isInstance(o.getValue());
                          }

                       });
  }

  /**
   * @pre array != null;
   * @pre a != null;
   * @return (forall int i; (i >= 0) && (i < array.length);
   *            a.isTrueFor(array[i]));
   */
  public static <_T_> boolean forAll(_T_[] array, Assertion<_T_> a) {
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
  public static <_V_> boolean forAllValues(Map<?, _V_> map, Assertion<_V_> a) {
    assert map != null;
    assert a != null;
    Iterator<_V_> iter = map.values().iterator();
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
  public static <_T_> boolean exists(_T_[] array, Assertion<_T_> a) {
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
  public static <_T_> boolean noNull(_T_[] a) {
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
  public static <_T_> boolean containsAll(final _T_[] a, final _T_[] b) {
    assert a != null;
    assert b != null;
    return forAll(b, new Assertion<_T_>() {

                            public boolean isTrueFor(_T_ o) {
                              return ArrayUtils.contains(a, o);
                            }

                          });
  }

  /**
   * <code>a</code> contains all elements of <code>b</code>, in any order.
   *
   * @pre a != null;
   * @pre b != null;
   * @return containsAll(a, b) && containsAll(b, a);
   */
  public static <_T_> boolean sameContents(final _T_[] a, final _T_[] b) {
    assert a != null;
    assert b != null;
    return containsAll(a, b) && containsAll(b, a);
  }

}