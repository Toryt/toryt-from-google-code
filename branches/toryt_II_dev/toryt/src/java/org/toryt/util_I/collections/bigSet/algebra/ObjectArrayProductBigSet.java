package org.toryt.util_I.collections.bigSet.algebra;


import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;


/**
 * <p>A {@link ArrayProductBigSet} that uses an ObjectArrayAggregatorFactory
 *   as {@link #getAggregatorFactory() aggregator factory}. It always
 *   returns {@code Object[]} as {@code _ResultElementType}.</p>
 *
 * @author Jan Dockx
 *
 * @invar getAggregatorFactory() instanceof ObjectArrayAggregatorFactory;
 *
 * @deprecated This is not a very nice class. Use {@link ProductBigSet} instead.
 */
@Deprecated
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class ObjectArrayProductBigSet extends ArrayProductBigSet<Object[]> {

  /**
   * @pre components != null;
   * @pre (forall int i; (i >= 0) && (i < component.length);
   *        (component[i] != null) ? component[i].isLocked());
   * @post ArrayUtils.isEquals(component, new.getComponents());
   */
  public ObjectArrayProductBigSet(LockableBigSet<?>... component) {
    super(new org.toryt.util_I.collections.algebra.ObjectArrayAggregatorFactory(component.length),
          component);
  }

}