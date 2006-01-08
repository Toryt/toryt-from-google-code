package org.toryt.util_I.collections;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.toryt.patterns_I.Collections;


/**
 * <p>Implementation of {@link TypedSet} and {@link Set},
 *   backed by another {@link Set}.</p>
 *
 * @author Jan Dockx
 */
public class SetBackedTypedSet extends SetBackedNoNullSet implements TypedSet {

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
   * @pre backingSet != null;
   * @pre elementType != null;
   */
  public SetBackedTypedSet(Class elementType, Set backingSet) {
    super(backingSet);
    assert elementType != null;
    $elementType = elementType;
  }

  /**
   * Create an instance backed by a fresh {@link HashSet}.
   *
   * @pre elementType != null;
   */
  public SetBackedTypedSet(Class elementType) {
    this(elementType, new HashSet());
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

  public boolean add(Object o) {
    if (! getElementType().isInstance(o)) {
      throw new ClassCastException("Only elements of type " +
                                    getElementType() +
                                    " allowed (" + o.getClass() + ").");
    }
    return super.add(o);
  }

  public boolean addAll(Collection c) {
    if ((c != null) && ! Collections.instanceOf(c, getElementType())) {
      throw new ClassCastException("Only elements of type " +
                                    getElementType() +
                                    " allowed.");
    }
    return super.addAll(c);
  }

  /*</section>*/

}