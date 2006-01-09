package org.toryt.util_I.collections;


import java.util.Collection;
import java.util.Iterator;


/**
 * <p>Generalization of common code for {@link SetBackedNoNullSet}
 *   and {@link ListBackedNoNullList}.</p>
 *
 * @author Jan Dockx
 */
public abstract class AbstractCollectionBackedNoNullCollection
    extends AbstractCollectionBackedCollection
    implements NoNullCollection {

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


  /* <section name="Inspectors"> */
  //------------------------------------------------------------------

  public final Iterator iterator() {
    return getBackingCollection().iterator();
  }
  
  /*</section>*/



  /* <section name="Modifying Operations"> */
  //------------------------------------------------------------------

  public boolean add(Object o) throws NullPointerException {
    if (o == null) {
      throw new NullPointerException("Null is not allowed");
    }
    return getBackingCollection().add(o);
  }

  public boolean addAll(Collection c) throws NullPointerException {
    if ((c != null) && c.contains(null)) {
      throw new NullPointerException("Null is not allowed");
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