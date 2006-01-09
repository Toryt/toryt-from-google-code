package org.toryt.util_I.collections;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;


/**
 * <p>Implementation of {@link NoNullCollection} and {@link List}
 *   through a backing list.</p>
 *
 * @author Jan Dockx
 */
public class ListBackedNoNullList
    extends AbstractCollectionBackedNoNullCollection
    implements NoNullList {

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
   */
  public ListBackedNoNullList(List backingList) {
    assert backingList != null;
    $backingList = backingList;
  }

  /**
   * Create an instance backed by a fresh {@link ArrayList}.
   */
  public ListBackedNoNullList() {
    this(new ArrayList());
  }

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



  /* <section name="Inspectors"> */
  //------------------------------------------------------------------

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
  
  public final ListIterator listIterator() {
    return $backingList.listIterator();
  }
  
  public final ListIterator listIterator(int index) {
    return $backingList.listIterator(index);
  }


  /*</section>*/



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  public void add(int index, Object o) throws UnsupportedOperationException {
    if (o == null) {
      throw new NullPointerException("Null is not allowed");
    }
    $backingList.add(index, o);
  }

  public boolean addAll(int index, Collection c) throws UnsupportedOperationException {
    if ((c != null) && c.contains(null)) {
      throw new NullPointerException("Null is not allowed");
    }
    return $backingList.addAll(index, c);
  }

  public Object set(int index, Object o) throws UnsupportedOperationException {
    if (o == null) {
      throw new NullPointerException("Null is not allowed");
    }
    return $backingList.set(index, o);
  }

  public final Object remove(int index) throws UnsupportedOperationException {
    return $backingList.remove(index);
  }

  /*</section>*/

}