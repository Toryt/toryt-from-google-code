package org.toryt.util_I.bigSet;


import java.math.BigInteger;
import java.util.HashSet;

import org.toryt.util_I.collections.LockableSet;
import org.toryt.util_I.collections.SetBackedLockableSet;
import org.toryt.util_I.collections.SetBackedTypedSet;
import org.toryt.util_I.collections.TypedSet;


/**
 * <p>A {@link BigSet} implemented as a {@link SetBackedLockableSet},
 *   and a backing {@link SetBackedTypedSet}. This is for small sets,
 *   where actually <code>{@link #getBigSize()} &lt;&lt; {@link Integer#MAX_VALUE}</code>
 *   (A {@link BigSet} <em>can</em> be big, but doesn't <em>have</em> to be).</p>
 *
 * @invar getBigSize() < Integer.MAX_VALUE;
 *
 * @protected-invar TypedSet.isAssignableFrom(getBackingSet());
 */
public class SetBackedLockableBigSet extends SetBackedLockableSet implements BigSet, LockableSet {

  /* <section name="Meta Information"> */
  //------------------------------------------------------------------
  /** {@value} */
  public static final String CVS_REVISION = "$Revision$";
  /** {@value} */
  public static final String CVS_DATE = "$Date$";
  /** {@value} */
  public static final String CVS_STATE = "$State$";
  /** {@value} */
  public static final String CVS_TAG = "$Name$";
  /* </section> */


  /**
   * @pre backingSet != null;
   */
  public SetBackedLockableBigSet(TypedSet backingSet) {
    super(backingSet);
  }

  /**
   * Creates an instance backed by a {@link SetBackedTypedSet},
   * backed itself by a {@link HashSet}.
   *
   * @pre elementType != null;
   */
  public SetBackedLockableBigSet(Class elementType) {
    super(new SetBackedTypedSet(elementType));
  }

  public final BigInteger getBigSize() {
    return BigInteger.valueOf(size());
  }

  public Class getElementType() {
    return ((TypedSet)getBackingSet()).getElementType();
  }

}