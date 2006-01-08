package org.toryt.util_I.collections;


import java.util.Collection;


/**
 * <p>Collection that only allows elements of type
 *   {@link #getElementType()}.</p>
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getElementType() !=  null;
 * @invar cC:instanceOf(this, getElementType());
 *
 * @note When moving to Java 5, replace this with a generics.
 */
public interface TypedCollection extends NoNullCollection {

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
   * The type of the elements in the set.
   * No other types of objects are allowed.
   *
   * @basic
   *
   */
  Class getElementType();

  /**
   * @post   ! getElementType().isInstance(o) ? false;
   * @throws ClassCastException
   *         ! getElementType().isInstance(o);
   */
  boolean add(Object o) throws ClassCastException;

  /**
   * @post   ((c != null) && ! cC:instanceOf(c, getElementType())) ? false;
   * @throws ClassCastException
   *         (c != null) && ! cC:instanceOf(this, getElementType());
   */
  boolean addAll(Collection c) throws ClassCastException;

}