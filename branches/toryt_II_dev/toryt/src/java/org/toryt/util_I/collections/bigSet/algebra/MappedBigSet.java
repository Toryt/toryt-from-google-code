package org.toryt.util_I.collections.bigSet.algebra;


import java.util.Collection;
import java.util.Iterator;

import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.algebra.Mapping;
import org.toryt.util_I.collections.bigSet.BigSet;
import org.toryt.util_I.collections.bigSet.lockable.AbstractLockedBigSet;
import org.toryt.util_I.collections.bigSet.lockable.LockableBigSet;


/**
 * <p>A lazy big set, wrapped around another {@link LockableBigSet},
 *   that presents the elements of the wrapped big set mapped using
 *   a {@link Mapping}.</p>
 *
 * @author Jan Dockx
 *
 * @invar getWrapped() != null;
 * @invar getWrapped().isLocked();
 * @invar getMapping() != null;
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class MappedBigSet<_WrappedElement_, _ResultElement_>
    extends AbstractLockedBigSet<_ResultElement_> {

  /**
   * @pre wrapped != null;
   * @pre wrapped.isLocked() != null;
   * @pre mapping != null;
   * @post new.getWrapped() == wrapped.
   */
  public MappedBigSet(LockableBigSet<? extends _WrappedElement_> wrapped, Mapping<_WrappedElement_, _ResultElement_> mapping) {
    super(nullAllowed, wrapped.getBigSize());
    assert wrapped != null;
    assert wrapped.isLocked();
    assert mapping != null;
    $wrapped = wrapped;
    $mapping = mapping;
  }

  public final LockableBigSet<? extends _WrappedElement_> getWrapped() {
    return $wrapped;
  }

  /**
   * @invar $wrapped != null;
   * @invar $wrapped.isLocked();
   */
  public final LockableBigSet<? extends _WrappedElement_> $wrapped;

  public final Mapping<_WrappedElement_, _ResultElement_> getMapping() {
    return $mapping;
  }

  /**
   * @invar $mapping != null;
   */
  public final Mapping<_WrappedElement_, _ResultElement_> $mapping;

  /**
   * If this method is used with
   * <code>! o instanceof MappedBigSet</code>,
   * it is possibly very expensive, as it uses
   * {@link #containsAll(Collection)} on this and <code>o</code>:
   * if {@link #containsAll(Collection)} is expensive, so is this
   * method if <code>! o instanceof AbstractComponentBigSet</code>.
   */
  @Override
  public final boolean equals(Object o) {
    if ((o == null) || (! (o instanceof BigSet<?>))) {
      return false;
    }
    else if (o instanceof MappedBigSet<?, ?>) {
      LockableBigSet<?> otherWrapped = ((MappedBigSet<?, ?>)o).getWrapped();
      return $wrapped.equals(otherWrapped);
    }
    else {
      // fallback; very possibly expensive, and stupid for a lazy set
      return super.equals(o);
    }
  }

  @Override
  public final int hashCode() {
    return getWrapped().hashCode();
  }

  public final boolean isEmpty() {
    return $wrapped.isEmpty();
  }

  public final Iterator<_ResultElement_> iterator() {
    return new LockIterator<_ResultElement_>() {

      /**
       * @invar $wrappedIterator != null;
       */
      private final Iterator<? extends _WrappedElement_> $wrappedIterator = $wrapped.iterator();

      public boolean hasNext() {
        return $wrappedIterator.hasNext();
      }

      public _ResultElement_ next() {
        return getMapping().map($wrappedIterator.next());
      }

      public final void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
      }

    };
  }

}