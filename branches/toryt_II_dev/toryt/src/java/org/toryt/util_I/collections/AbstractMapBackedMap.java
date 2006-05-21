package org.toryt.util_I.collections;


import java.util.HashMap;
import java.util.Map;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Generalization of common code for backed maps.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractMapBackedMap<_Key_, _Value_>
    implements Map<_Key_, _Value_>, Cloneable {

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * The <code>backingMap</code> should not be exposed to protect integrity.
   *
   * @pre backingMap != null;
   * @post new.getBackingMap() == backingMap;
   */
  protected AbstractMapBackedMap(Map<_Key_, _Value_> backingMap) {
    assert backingMap != null;
    $backingMap = backingMap;
  }

  /**
   * This method makes a deep copy of the backing map.
   */
  @Override
  public Object clone() {
    try {
      @SuppressWarnings("unchecked") AbstractMapBackedMap<_Key_, _Value_> result =
          (AbstractMapBackedMap<_Key_, _Value_>)super.clone();
      result.$backingMap = backingCollectionClone($backingMap);
      return result;
    }
    catch (CloneNotSupportedException cnsExc) {
      assert false : "CloneNotSupportedException should not happen: " + cnsExc;
      return null; // keep compiler happy
    }
  }

  protected Map<_Key_, _Value_> backingCollectionClone(Map<_Key_, _Value_> backingMap) {
    return new HashMap<_Key_, _Value_>(backingMap);
  }

  /*</construction>*/



  protected final Map<_Key_, _Value_> getBackingMap() {
    return $backingMap;
  }

  /**
   * @invar $backingMap != null;
   */
  private Map<_Key_, _Value_> $backingMap;



  /* <section name="Inspectors"> */
  //------------------------------------------------------------------

  public final int size() {
    return getBackingMap().size();
  }

  public final boolean isEmpty() {
    return getBackingMap().isEmpty();
  }

  public final boolean containsKey(Object o) {
    return getBackingMap().containsKey(o);
  }

  public final boolean containsValue(Object o) {
    return getBackingMap().containsValue(o);
  }

  @Override
  public final boolean equals(Object o) {
    return getBackingMap().equals(o);
  }

  @Override
  public final int hashCode() {
    return getBackingMap().hashCode();
  }

  public final _Value_ get(Object key) {
    return getBackingMap().get(key);
  }

  /*</section>*/

}