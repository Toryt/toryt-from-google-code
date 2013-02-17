/*<license>
Copyright 2006 - $Date$ by the authors mentioned below.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
</license>*/

package org.toryt.util_I.collections.lockable;


import java.util.Map;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Implementation of modifying methods for a {@link LockableMap}
 *   that is locked always.</p>
 *
 * @author Jan Dockx
 *
 * @invar isLocked();
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractLockedMap<_Key_, _Value_> implements LockableMap<_Key_, _Value_> {

  protected AbstractLockedMap(boolean nullAllowed) {
    $nullAllowed = nullAllowed;
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    }
    catch (CloneNotSupportedException cnsExc) {
      assert false : "CloneNotSupportedException should not happen: " + cnsExc;
      return null; // keep compiler happy
    }
  }



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
    return true;
  }

  /*</property>*/



  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * This implementation is very expensive for lazy maps.
   *
   * @protected
   * General implementation as high in the inheritance
   * hierarchy as possible. Overwrite when a more performant
   * implementation is possible.
   */
  public boolean containsKey(final Object o) {
    return Collections.exists(keySet(), new Assertion<_Key_>() {
              public boolean isTrueFor(_Key_ key) {
                return (((o == null) && (key == null)) ||
                        ((o != null) && (o.equals(key))));
              }
            });
  }

  /**
   * This implementation is very expensive for lazy maps.
   *
   * @protected
   * General implementation as high in the inheritance
   * hierarchy as possible. Overwrite when a more performant
   * implementation is possible.
   */
  public boolean containsValue(final Object o) {
    return Collections.exists(values(), new Assertion<_Value_>() {
              public boolean isTrueFor(_Value_ value) {
                return (((o == null) && (value == null)) ||
                        ((o != null) && (o.equals(value))));
              }
            });
  }



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final _Value_ put(_Key_ key, _Value_ value) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Map is locked");
  }


  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final _Value_ remove(Object key) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Map is locked");
  }

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final void putAll(Map<? extends _Key_, ? extends _Value_> m) {
    throw new UnsupportedOperationException("Map is locked");
  }

  /**
   * @post   isLocked() ? false;
   * @throws UnsupportedOperationException
   *         isLocked();
   */
  public final void clear() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Map is locked");
  }

  /*</section>*/

}