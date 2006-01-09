package org.toryt.util_I.collections;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.toryt.patterns_I.Collections;


/**
 * <p>Implementation of {@link TypedList} and {@link List},
 *   backed by another {@link List}.</p>
 *
 * @author Jan Dockx
 */
public class ListBackedTypedList extends ListBackedNoNullList
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
  public ListBackedTypedList(Class elementType, List backingList) {
    super(backingList);
    assert elementType != null;
    $elementType = elementType;
  }

  /**
   * Create an instance backed by a fresh {@link ArrayList}.
   *
   * @pre elementType != null;
   */
  public ListBackedTypedList(Class elementType) {
    this(elementType, new ArrayList());
  }



  /* <property name="element type"> */
  //------------------------------------------------------------------

  public final Class getElementType() {
    return $elementType;
  }

  /**
   * @invar $elementType != null;
   */
  private Class $elementType;

  /*</property>*/



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  public boolean add(Object o) throws ClassCastException {
    if (! getElementType().isInstance(o)) {
      throw new ClassCastException("Only elements of type " +
                                    getElementType() +
                                    " allowed (" + o.getClass() + ").");
    }
    return super.add(o);
  }

  public boolean addAll(Collection c) throws ClassCastException {
    if ((c != null) && ! Collections.instanceOf(c, getElementType())) {
      throw new ClassCastException("Only elements of type " +
                                    getElementType() +
                                    " allowed.");
    }
    return super.addAll(c);
  }

  public final void add(int index, Object o) throws ClassCastException {
    if (! getElementType().isInstance(o)) {
      throw new ClassCastException("Only elements of type " +
                                    getElementType() +
                                    " allowed (" + o.getClass() + ").");
    }
    super.add(index, o);
  }

  public final boolean addAll(int index, Collection c) throws ClassCastException {
    if ((c != null) && ! Collections.instanceOf(c, getElementType())) {
      throw new ClassCastException("Only elements of type " +
                                    getElementType() +
                                    " allowed.");
    }
    return super.addAll(index, c);
  }

  public final Object set(int index, Object o) throws ClassCastException {
    if (! getElementType().isInstance(o)) {
      throw new ClassCastException("Only elements of type " +
                                    getElementType() +
                                    " allowed (" + o.getClass() + ").");
    }
    return super.set(index, o);
  }
  
  /*</section>*/

}