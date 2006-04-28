package org.toryt.util_I.collections.typed;


import java.util.Collection;

import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.AbstractCollectionBackedCollection;


/**
 * <p>Generalization of common code for {@link SetBackedTypedSet}
 *   and {@link ListBackedTypedList}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractCollectionBackedTypedCollection
    extends AbstractCollectionBackedCollection
    implements TypedCollection {

  public AbstractCollectionBackedTypedCollection(Class elementType, boolean nullAllowed) {
    assert elementType != null;
    $elementType = elementType;
    $nullAllowed = nullAllowed;
  }



  /* <property name="null allowed"> */
  //------------------------------------------------------------------

  public final boolean isNullAllowed() {
    return $nullAllowed;
  }

  private boolean $nullAllowed;

  /*</property>*/



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

  public final boolean add(Object o) throws NullPointerException, ClassCastException {
    if ((! isNullAllowed()) && (o == null)) {
      throw new NullPointerException("Null is not allowed");
    }
    if (! getElementType().isInstance(o)) {
      throw new ClassCastException("Only elements of type " +
                                    getElementType() +
                                    " allowed (" + o.getClass() + ").");
    }
    return getBackingCollection().add(o);
  }

  public boolean addAll(Collection c) throws NullPointerException, ClassCastException {
    if ((! isNullAllowed()) && (c != null) && c.contains(null)) {
      throw new NullPointerException("Null is not allowed");
    }
    if ((c != null) && ! Collections.instanceOf(c, getElementType())) {
      throw new ClassCastException("Only elements of type " +
                                    getElementType() +
                                    " allowed.");
    }
    return getBackingCollection().addAll(c);
  }

  public final boolean remove(Object o) {
    return getBackingCollection().remove(o);
  }

  public final boolean retainAll(Collection c) {
    return getBackingCollection().retainAll(c);
  }

  public final boolean removeAll(Collection c) {
    return getBackingCollection().removeAll(c);
  }

  public final void clear() {
    getBackingCollection().clear();
  }

  /*</section>*/

}