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
public abstract class AbstractCollectionBackedCollection<_ElementType_>
    implements Collection<_ElementType_> {

  protected abstract <_BackingCollectionType_ extends Collection<_ElementType_>>
      _BackingCollectionType_ getBackingCollection();



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

  public final boolean containsAll(Collection<?> c) {
    return getBackingCollection().containsAll(c);
  }

  public final Object[] toArray() {
    return getBackingCollection().toArray();
  }

  public final <_ResultBaseType_> _ResultBaseType_[] toArray(_ResultBaseType_[] a) {
    return getBackingCollection().toArray(a);
  }

  @Override
  public final boolean equals(Object o) {
    return getBackingCollection().equals(o);
  }

  @Override
  public final int hashCode() {
    return getBackingCollection().hashCode();
  }

  /*</section>*/

}