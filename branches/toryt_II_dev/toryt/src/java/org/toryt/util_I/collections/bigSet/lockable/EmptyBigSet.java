package org.toryt.util_I.collections.bigSet.lockable;


import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.Iterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.bigSet.BigSet;


/**
 * <p>An empty {@link BigSet}.</p>
 *
 * @invar getBigSize() == 1;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class EmptyBigSet<_ElementType_> extends AbstractLockedBigSet<_ElementType_> {

  public EmptyBigSet() {
    super(false, BigInteger.ZERO);
  }

  public final boolean isEmpty() {
    return true;
  }

  @Override
  public final boolean contains(Object o) {
    return false;
  }

  public final Iterator<_ElementType_> iterator() {
    return new AbstractLockedCollectionIterator() {

                  public final boolean hasNext() {
                    return false;
                  }

                  /**
                   * @deprecated method not supported
                   */
                  @Deprecated
                  public _ElementType_ next() {
                    throw new IndexOutOfBoundsException();
                  }

                };
  }

  @Override
  public final Object[] toArray() {
    return new Object[] {};
  }

  @Override
  public final <_ComponentElementType_> _ComponentElementType_[] toArray(_ComponentElementType_[] a) {
    _ComponentElementType_[] result;
    if (a.length >= 1) {
      result = a;
      result[0] = null;
    }
    else {
      @SuppressWarnings("unchecked") _ComponentElementType_[] arrayResult =
          (_ComponentElementType_[])Array.newInstance(a.getClass().getComponentType(), 0);
      result = arrayResult;
    }
    return result;
  }

}