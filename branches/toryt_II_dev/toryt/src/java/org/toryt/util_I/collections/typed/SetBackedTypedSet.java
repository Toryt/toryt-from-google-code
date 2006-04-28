package org.toryt.util_I.collections.typed;


import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.toryt.util_I.annotations.vcs.CvsInfo;



/**
 * <p>Implementation of {@link TypedSet} and {@link Set},
 *   backed by another {@link Set}.</p>
 *
 * @author Jan Dockx
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class SetBackedTypedSet extends AbstractCollectionBackedTypedCollection
    implements TypedSet {

  /**
   * @pre backingSet != null;
   * @pre elementType != null;
   */
  public SetBackedTypedSet(Class elementType, Set backingSet, boolean nullAllowed) {
    super(elementType, nullAllowed);
    assert backingSet != null;
    $backingSet = backingSet;
  }

  /**
   * Create an instance backed by a fresh {@link HashSet}.
   *
   * @pre elementType != null;
   */
  public SetBackedTypedSet(Class elementType, boolean nullAllowed) {
    this(elementType, new HashSet(), nullAllowed);
  }



  /* <property name="backing set"> */
  //------------------------------------------------------------------

  protected final Set getBackingSet() {
    return $backingSet;
  }

  protected final Collection getBackingCollection() {
    return $backingSet;
  }

  /**
   * @invar $backingSet != null;
   */
  private Set $backingSet;

  /*</property>*/



  public final Iterator iterator() {
    return getBackingCollection().iterator();
  }

}