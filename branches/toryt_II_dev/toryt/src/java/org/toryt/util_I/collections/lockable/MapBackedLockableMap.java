package org.toryt.util_I.collections.lockable;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.AbstractMapBackedMap;


/**
 * <p>Implementation of {@link LockableMap} and {@link Map},
 *   backed by another {@link Map}. The backing map
 *   needs to be kept private, to ensure correct behavior when
 *   {@link #isLocked()}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class MapBackedLockableMap<_Key_, _Value_>
    extends AbstractMapBackedMap<_Key_, _Value_>
    implements LockableMap<_Key_, _Value_> {

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * The <code>backingMap</code> should not be exposed to protect integrity
   * when {@link #isLocked()}.
   *
   * @pre backingMap != null;
   * @post new.isNullAllowed() == nullAllowed;
   * @post new.getBackingMap() == backingMap;
   * @post ! new.isLocked();
   */
  protected MapBackedLockableMap(Map<_Key_, _Value_> backingMap, boolean nullAllowed) {
    super(backingMap);
    $nullAllowed = nullAllowed;
  }

  /**
   * @post new.isNullAllowed() == nullAllowed;
   * @post ! new.isLocked();
   */
  public MapBackedLockableMap(boolean nullAllowed) {
    this(new HashMap<_Key_, _Value_>(), nullAllowed);
  }

  /*</construction>*/



  /* <property name="null allowed"> */
  //------------------------------------------------------------------

  public final boolean isNullAllowed() {
    return $nullAllowed;
  }

  private boolean $nullAllowed;

  /*</property>*/



  /* <property name="locked"> */
  //------------------------------------------------------------------

  public final boolean isLocked() {
    return $locked;
  }

  public final void lock() {
    extraLock();
    $locked = true;
  }

  /**
   * Extra lock activity.
   * This implementation does nothing, but subclasses might do more.
   */
  protected void extraLock() {
    // NOP
  }

  private boolean $locked;

  /*</property>*/








  public final LockableSet<_Key_> keySet() {
    SetBackedLockableSet<_Key_> result = new SetBackedLockableSet<_Key_>(getBackingMap().keySet(), false);
    if (isLocked()) {
      result.lock();
    }
    return result;
  }


  public LockableCollection<_Value_> values() {
    CollectionBackedLockableCollection<_Value_, Collection<_Value_>> result =
        new CollectionBackedLockableCollection<_Value_, Collection<_Value_>>(getBackingMap().values(), false);
    if (isLocked()) {
      result.lock();
    }
    return result;
  }


  public final LockableSet<Entry<_Key_, _Value_>> entrySet() {
    SetBackedLockableSet<Map.Entry<_Key_, _Value_>> result =
        new SetBackedLockableSet<Map.Entry<_Key_, _Value_>>(getBackingMap().entrySet(), false);
    if (isLocked()) {
      result.lock();
    }
    return result;
  }




  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  /**
   * Not final, because locked subclasses want to override this.
   */
  public _Value_ put(_Key_ key, _Value_ value)
      throws NullPointerException, UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Map is locked");
    }
    if (key == null) {
      throw new NullPointerException("Null key is not allowed");
    }
    if ((! isNullAllowed()) && (value == null)) {
      throw new NullPointerException("Null value is not allowed");
    }
    return getBackingMap().put(key, value);
  }

  /**
   * Not final, because locked subclasses want to override this.
   */
  public void putAll(Map<? extends _Key_, ? extends _Value_> map)
      throws NullPointerException, UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Map is locked");
    }
    if (map.containsKey(null)) {
      throw new NullPointerException("Null key is not allowed");
    }
    if ((! isNullAllowed()) && (map.containsValue(null))) {
      throw new NullPointerException("Null value is not allowed");
    }
    getBackingMap().putAll(map);
  }

  /**
   * Not final, because locked subclasses want to override this.
   */
  public _Value_ remove(Object key) throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Map is locked");
    }
    return getBackingMap().remove(key);
  }

  /**
   * Not final, because locked subclasses want to override this.
   */
  public void clear() throws UnsupportedOperationException {
    if (isLocked()) {
      throw new UnsupportedOperationException("Map is locked");
    }
    getBackingMap().clear();
  }

  /*</section>*/

}