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
public abstract class AbstractCollectionBackedCollection<_Element_, _BackingCollection_ extends Collection<_Element_>>
    implements Collection<_Element_>, Cloneable {

  /*<construction>*/
  //------------------------------------------------------------------

  /**
   * The <code>backingCollection</code> should not be exposed to protect integrity.
   *
   * @pre backingCollection != null;
   * @post new.getBackingCollection() == backingCollection;
   */
  protected AbstractCollectionBackedCollection(_BackingCollection_ backingCollection) {
    assert backingCollection != null;
    $backingCollection = backingCollection;
  }

  /**
   * This method makes a deep copy of the backing collection.
   */
  @Override
  public Object clone() {
    try {
      @SuppressWarnings("unchecked") AbstractCollectionBackedCollection<_Element_, _BackingCollection_> result =
          (AbstractCollectionBackedCollection<_Element_, _BackingCollection_>)super.clone();
      result.$backingCollection = backingCollectionClone($backingCollection);
      return result;
    }
    catch (CloneNotSupportedException cnsExc) {
      assert false : "CloneNotSupportedException should not happen: " + cnsExc;
      return null; // keep compiler happy
    }
  }

  protected abstract _BackingCollection_ backingCollectionClone(_BackingCollection_ backingCollection);

  /*</construction>*/



  protected final _BackingCollection_ getBackingCollection() {
    return $backingCollection;
  }

  /**
   * @invar $backingCollection != null;
   */
  private _BackingCollection_ $backingCollection;



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

  public final <_Base_> _Base_[] toArray(_Base_[] a) {
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