package org.toryt.util_I.collections.lockable;


import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;



/**
 * <p>Implementation of modifying methods for a {@link LockableCollection}
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
public abstract class AbstractLockedCollection<_Element_>
    implements LockableCollection<_Element_> {

  protected AbstractLockedCollection(boolean nullAllowed) {
    $nullAllowed = nullAllowed;
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



  /**
   * This implementation is very expensive for lazy collections.
   *
   * @protected
   * General implementation as high in the inheritance
   * hierarchy as possible. Overwrite when a more performant
   * implementation is possible.
   */
  public boolean contains(final Object o) {
    return Collections.exists(this, new Assertion<_Element_>() {
              public boolean isTrueFor(_Element_ element) {
                return (((o == null) && (element == null)) ||
                        ((o != null) && (o.equals(element))));
              }
            });
  }

  /**
   * This implementation is very expensive for lazy collections.
   *
   * @protected
   * General implementation as high in the inheritance
   * hierarchy as possible.
   *
   * @throws NullPointerException
   *         c == null;
   */
  public final boolean containsAll(Collection<?> c) {
    return (size() >= c.size()) && // NullPointerException as expected
           Collections.forAll(c,
                              new Assertion<Object>() {
                                    public boolean isTrueFor(Object o) {
                                      return contains(o);
                                    }
                                  });
  }

  /**
   * This implementation is very expensive for lazy collections.
   *
   * @protected
   * General implementation as high in the inheritance
   * hierarchy as possible.
   */
  public Object[] toArray() {
    return toArray(new Object[size()]);
  }

  /**
   * This implementation is very expensive for lazy collections.
   *
   * @protected
   * General implementation as high in the inheritance
   * hierarchy as possible.
   */
  public <_Base_> _Base_[] toArray(_Base_[] a) {
    int size = size();
    _Base_[] result;
    if (a.length >= size) {
      result = a;
      result[size] = null;
    }
    else {
      @SuppressWarnings("unchecked") _Base_[] newArray =
          (_Base_[])Array.newInstance(a.getClass().getComponentType(), size);
      /* Java API is not generic here */
      result = newArray;
    }
    Iterator<_Element_> iter = iterator();
    int i = 0;
    try {
      while (iter.hasNext()) {
        @SuppressWarnings("unchecked") _Base_ next = (_Base_)iter.next();
        /* this is weird, yes. the reason is that we cannot say that
         * _ResultBaseType_ super _ElementType_ for some reason;
         * so, we might get a ClassCastException, which we will transform into
         * an ArrayStoreException */
        result[i] = next;
        i++;
      }
    }
    catch (ClassCastException ccExc) {
      throw new ArrayStoreException();
    }
    return result;
  }



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final boolean add(_Element_ o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final boolean remove(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final boolean addAll(Collection<? extends _Element_> c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final boolean retainAll(Collection<?> c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final boolean removeAll(Collection<?> c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /**
   * @deprecated Unsupported
   */
  @Deprecated
  public final void clear() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /*</section>*/


  public abstract class AbstractLockedCollectionIterator implements Iterator<_Element_> {

    /**
     * @deprecated Unsupported
     */
    @Deprecated
    public final void remove()  throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Set is locked");
    }

  }

}