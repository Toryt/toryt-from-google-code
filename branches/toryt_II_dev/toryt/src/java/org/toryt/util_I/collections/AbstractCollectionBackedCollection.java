package org.toryt.util_I.collections;


import java.util.Collection;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Generalization of common code for backed collections.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public abstract class AbstractCollectionBackedCollection
    implements Collection {

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

  public final boolean equals(Object o) {
    return getBackingCollection().equals(o);
  }

  public final int hashCode() {
    return getBackingCollection().hashCode();
  }

  /*</section>*/

}