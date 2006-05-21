package org.toryt.util_I.collections.lockable;


import java.util.Map;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>{@link Map} that becomes unmodifiable once it is
 *   locked. The map could be locked at construction.
 *   <code>null</code> keys are never allowed. If {@link #isNullAllowed()}
 *   is true, there can be no <code>null</code> values in the map.</p>
 *
 * @author Jan Dockx
 *
 * @invar (! isNullAllowed()) ? Collections.noNull(this.values());
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface LockableMap<_Key_, _Value_> extends Map<_Key_, _Value_>, Cloneable {

  /**
   * Clone method must be public.
   */
  Object clone();

  /**
   * Is <code>null</code> allowed as element in this collection?
   *
   * @basic
   */
  boolean isNullAllowed();

  /**
   * @basic
   * @init false;
   */
  boolean isLocked();



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  /**
   * @post   ((! isNullAllowed()) && (value == null)) ? false;
   * @post   isLocked() ? false;
   * @return get(value);
   * @throws NullPointerException
   *         (! isNullAllowed()) && (value == null);
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  public _Value_ put(_Key_ key, _Value_ value) throws UnsupportedOperationException;


  /**
   * @post   isLocked() ? false;
   * @return get(value);
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  public _Value_ remove(Object key) throws UnsupportedOperationException;

  /**
   * @post   m.containsKey(null) ? false;
   * @post   ((! isNullAllowed()) && m.containsValue(null)) ? false;
   * @post   isLocked() ? false;
   * @throws NullPointerException
   *         m.containsKey(null);
   * @throws NullPointerException
   *         (! isNullAllowed()) && m.containsValue(null);
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  public void putAll(Map<? extends _Key_, ? extends _Value_> m);

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  void clear() throws UnsupportedOperationException;

  /*</section>*/

}