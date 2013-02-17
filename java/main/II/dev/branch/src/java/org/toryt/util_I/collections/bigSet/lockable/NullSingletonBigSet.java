package org.toryt.util_I.collections.bigSet.lockable;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Iterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.BigSet;


/**
 * <p>A {@link BigSet} that contains exactly 1 element that is
 *   <code>null</code>.</p>
 *
 * @invar getBigSize() == 1;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class NullSingletonBigSet<_Element_> extends AbstractLockedBigSet<_Element_> {

  public NullSingletonBigSet() {
    super(true, BigInteger.ONE);
  }

  // overriding clone is not necessary: there are no instance variable here

  public final boolean isEmpty() {
    return false;
  }

  @Override
  public final boolean contains(Object o) {
    return (o == null);
  }

  public final Iterator<_Element_> iterator() {
    return new AbstractLockedCollectionIterator() {

                  private boolean $done = false;

                  public final boolean hasNext() {
                    return ! $done;
                  }

                  public _Element_ next() {
                    return null;
                  }

                };
  }

  @Override
  public final Object[] toArray() {
    return new Object[] {null};
  }


  @Override
  public final <_ComponentElement_> _ComponentElement_[] toArray(_ComponentElement_[] a) {
    _ComponentElement_[] result;
    if (a.length > 1) {
      result = a;
      result[1] = null;
    }
    else {
      @SuppressWarnings("unchecked") _ComponentElement_[] arrayResult =
          (_ComponentElement_[])Array.newInstance(a.getClass().getComponentType(), 1);
      result = arrayResult;
    }
    result[0] = null;
    return result;
  }

}