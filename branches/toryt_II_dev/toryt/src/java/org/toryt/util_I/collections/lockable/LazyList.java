package org.toryt.util_I.collections.lockable;


import java.util.List;



/**
 * <p>{@link List} that generates its elements fresh while
 *   iterating over them.</p>
 *
 * @see LazyCollection

 * @author Jan Dockx
 */
public interface LazyList extends LazyCollection, LockableList {

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