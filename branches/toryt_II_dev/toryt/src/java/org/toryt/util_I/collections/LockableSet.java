package org.toryt.util_I.collections;


import java.util.Set;


/**
 * <p>{@link Set} that becomes unmodifiable once it is
 *   locked.</p>
 *
 * @see LockableCollection
 *
 * @author Jan Dockx
 */
public interface LockableSet extends LockableCollection, Set {

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

}