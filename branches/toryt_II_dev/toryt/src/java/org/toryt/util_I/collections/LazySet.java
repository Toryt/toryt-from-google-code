package org.toryt.util_I.collections;


import java.util.Set;


/**
 * <p>{@link Set} that generates its elements fresh while
 *   iterating over them.</p>
 *
 * @see LazyCollection
 */
public interface LazySet extends LazyCollection, LockSet {

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