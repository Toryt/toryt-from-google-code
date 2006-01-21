package org.toryt.util_I.collections.typed;


import java.util.Set;


/**
 * <p>Set that only allows elements of type
 *   {@link #getElementType()}.</p>
 *
 * @author Jan Dockx
 *
 * @note When moving to Java 5, replace this with a generics.
 */
public interface TypedSet extends TypedCollection, Set {

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