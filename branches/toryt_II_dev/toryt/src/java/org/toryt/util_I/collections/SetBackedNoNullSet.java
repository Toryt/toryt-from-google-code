package org.toryt.util_I.collections;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * <p>Implementation of {@link NoNullCollection} and {@link Set}
 *   through a backing set.</p>
 *
 * @author Jan Dockx
 */
public class SetBackedNoNullSet
    extends AbstractCollectionBackedNoNullCollection
    implements NoNullSet {

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
   */
  public SetBackedNoNullSet(Set backingSet) {
    assert backingSet != null;
    $backingSet = backingSet;
  }

  /**
   * Create an instance backed by a fresh {@link HashSet}.
   */
  public SetBackedNoNullSet() {
    this(new HashSet());
  }

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

}