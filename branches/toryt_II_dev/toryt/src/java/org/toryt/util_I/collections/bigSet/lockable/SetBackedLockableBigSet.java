package org.toryt.util_I.collections.bigSet.lockable;


import java.math.BigInteger;
import java.util.HashSet;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.BigSet;
import org.toryt.util_I.collections.lockable.SetBackedLockableSet;
import org.toryt.util_I.collections.typed.SetBackedTypedSet;


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
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class SetBackedLockableBigSet<_ElementType_>
    extends SetBackedLockableSet<_ElementType_>
    implements LockableBigSet<_ElementType_> {

  /**
   * Creates an instance backed by a {@link SetBackedTypedSet},
   * backed itself by a {@link HashSet}.
   */
  public SetBackedLockableBigSet(boolean nullAllowed) {
    super(nullAllowed);
  }

  public final BigInteger getBigSize() {
    return BigInteger.valueOf(size());
  }

}