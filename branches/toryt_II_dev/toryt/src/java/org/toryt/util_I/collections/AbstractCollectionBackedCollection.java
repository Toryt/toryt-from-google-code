package org.toryt.util_I.collections;


import java.util.Collection;


/**
 * <p>Generalization of common code for backed collections.</p>
 *
 * @author Jan Dockx
 */
public abstract class AbstractCollectionBackedCollection
    implements Collection {

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


  protected abstract Collection getBackingCollection();



  /* <section name="Inspectors"> */
  //------------------------------------------------------------------

  public final int size() {
    return getBackingCollection().size();
  }

  public final boolean isEmpty() {
    return getBackingCollection().isEmpty();
  }

  public final boolean contains(Object o) {
    return getBackingCollection().contains(o);
  }

  public final boolean containsAll(Collection c) {
    return getBackingCollection().containsAll(c);
  }

  public final Object[] toArray() {
    return getBackingCollection().toArray();
  }

  public final Object[] toArray(Object[] a) {
    return getBackingCollection().toArray(a);
  }

  /*</section>*/

}