package org.toryt.util_I.collections;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.toryt.patterns_I.Collections;


/**
 * <p>Implementation of {@link TypedList} and {@link List},
 *   backed by another {@link List}.</p>
 *
 * @author Jan Dockx
 */
public class ListBackedTypedList extends AbstractCollectionBackedTypedCollection
    implements TypedList {

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
   * @pre backingList != null;
   * @pre elementType != null;
   */
  public ListBackedTypedList(Class elementType, List backingList, boolean nullAllowed) {
    super(elementType, nullAllowed);
    assert backingList != null;
    $backingList = backingList;
  }

  /**
   * Create an instance backed by a fresh {@link ArrayList}.
   *
   * @pre elementType != null;
   */
  public ListBackedTypedList(Class elementType, boolean nullAllowed) {
    this(elementType, new ArrayList(), nullAllowed);
  }



  /* <property name="backing list"> */
  //------------------------------------------------------------------

  protected final List getBackingList() {
    return $backingList;
  }

  protected final Collection getBackingCollection() {
    return $backingList;
  }

  /**
   * @invar $backingList != null;
   */
  private List $backingList;

  /*</property>*/



  /* <section name="Inspectors"> */
  //------------------------------------------------------------------

  /**
   * @mudo not good enough: must be a TypedList too!
   */
  public final List subList(int fromIndex, int toIndex) {
    return $backingList.subList(fromIndex, toIndex);
  }

  public final Object get(int index) {
    return $backingList.get(index);
  }

  public final int indexOf(Object o) {
    return $backingList.indexOf(o);
  }

  public final int lastIndexOf(Object o) {
    return $backingList.lastIndexOf(o);
  }

  public final Iterator iterator() {
    return listIterator();
  }

  public final ListIterator listIterator() {
    return listIterator(0);
  }

  public final ListIterator listIterator(final int index) {
    return new ListIterator() {

      private ListIterator $iter = listIterator(index);

      public boolean hasNext() {
        return $iter.hasNext();
      }

      public Object next() {
        return $iter.next();
      }

      public boolean hasPrevious() {
        return $iter.hasPrevious();
      }

      public Object previous() {
        return $iter.previous();
      }

      public int nextIndex() {
        return $iter.nextIndex();
      }

      public int previousIndex() {
        return $iter.previousIndex();
      }

      public void remove() {
        $iter.remove();
      }

      public void set(Object o) {
        if ((! isNullAllowed()) && (o == null)) {
          throw new NullPointerException("Null is not allowed");
        }
        if (! getElementType().isInstance(o)) {
          throw new ClassCastException("Only elements of type " +
                                        getElementType() +
                                        " allowed (" + o.getClass() + ").");
        }
        $iter.set(o);
      }

      public void add(Object o) {
        if ((! isNullAllowed()) && (o == null)) {
          throw new NullPointerException("Null is not allowed");
        }
        if (! getElementType().isInstance(o)) {
          throw new ClassCastException("Only elements of type " +
                                        getElementType() +
                                        " allowed (" + o.getClass() + ").");
        }
        $iter.add(o);
      }

    };
  }

  /*</section>*/



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  public final void add(int index, Object o) throws NullPointerException, ClassCastException {
    if ((! isNullAllowed()) && (o == null)) {
      throw new NullPointerException("Null is not allowed");
    }
    if (! getElementType().isInstance(o)) {
      throw new ClassCastException("Only elements of type " +
                                    getElementType() +
                                    " allowed (" + o.getClass() + ").");
    }
    $backingList.add(index, o);
  }

  public final boolean addAll(int index, Collection c) throws NullPointerException, ClassCastException {
    if ((! isNullAllowed()) && (c != null) && c.contains(null)) {
      throw new NullPointerException("Null is not allowed");
    }
    if ((c != null) && ! Collections.instanceOf(c, getElementType())) {
      throw new ClassCastException("Only elements of type " +
                                    getElementType() +
                                    " allowed.");
    }
    return $backingList.addAll(index, c);
  }

  public final Object set(int index, Object o) throws NullPointerException, ClassCastException {
    if ((! isNullAllowed()) && (o == null)) {
      throw new NullPointerException("Null is not allowed");
    }
    if (! getElementType().isInstance(o)) {
      throw new ClassCastException("Only elements of type " +
                                    getElementType() +
                                    " allowed (" + o.getClass() + ").");
    }
    return $backingList.set(index, o);
  }

  public final Object remove(int index) {
    return $backingList.remove(index);
  }

  /*</section>*/

}