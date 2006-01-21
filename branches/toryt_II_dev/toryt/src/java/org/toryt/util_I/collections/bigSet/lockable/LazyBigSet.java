package org.toryt.util_I.collections.bigSet.lockable;


import org.toryt.util_I.collections.bigSet.BigSet;
import org.toryt.util_I.collections.lockable.LazySet;


/**
 * <p>{@link BigSet} that becomes unmodifiable once it is
 *   locked. The set could be locked at construction.</p>
 *
 * @author Jan Dockx
 */
public interface LazyBigSet extends LockableBigSet, LazySet {

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