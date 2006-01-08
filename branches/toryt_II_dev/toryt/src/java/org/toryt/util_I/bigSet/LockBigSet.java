package org.toryt.util_I.bigSet;


import org.toryt.util_I.collections.LockSet;


/**
 * <p>{@link BigSet} that becomes unmodifiable once it is
 *   locked. The set could be locked at construction.</p>
 */
public interface LockBigSet extends BigSet, LockSet {

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