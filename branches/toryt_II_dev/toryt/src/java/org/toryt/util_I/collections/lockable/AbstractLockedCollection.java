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
public abstract class AbstractLockedCollection implements LockableCollection {

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
    return Collections.exists(this, new Assertion() {
              public boolean isTrueFor(Object element) {
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
  public final boolean containsAll(Collection c) {
    return (size() >= c.size()) && // NullPointerException as expected
           Collections.forAll(c,
                              new Assertion() {
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
  public Object[] toArray(Object[] a) {
    int size = size();
    Object[] result;
    if (a.length >= size) {
      result = a;
      result[size] = null;
    }
    else {
      result = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
    }
    Iterator iter = iterator();
    int i = 0;
    while (iter.hasNext()) {
      result[i] = iter.next();
      i++;
    }
    return result;
  }



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  public final boolean add(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public final boolean remove(Object o) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public final boolean addAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public final boolean retainAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public final boolean removeAll(Collection c) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  public final void clear() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Set is locked");
  }

  /*</section>*/


  public abstract class AbstractLockedCollectionIterator implements Iterator {

    public final void remove()  throws UnsupportedOperationException {
      throw new UnsupportedOperationException("Set is locked");
    }

  }

}