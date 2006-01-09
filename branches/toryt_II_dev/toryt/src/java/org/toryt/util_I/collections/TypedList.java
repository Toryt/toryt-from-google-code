package org.toryt.util_I.collections;


import java.util.Collection;
import java.util.List;


/**
 * <p>{@link List} that only allows elements of type
 *   {@link #getElementType()}.</p>
 *
 * @author Jan Dockx
 *
 * @note When moving to Java 5, replace this with a generics.
 */
public interface TypedList extends TypedCollection, List {

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
   * @post   ! getElementType().isInstance(o) ? false;
   * @throws ClassCastException
   *         ! getElementType().isInstance(o);
   */
  void add(int index, Object o) throws ClassCastException;

  /**
   * @post   ((c != null) && ! cC:instanceOf(c, getElementType())) ? false;
   * @throws ClassCastException
   *         (c != null) && ! cC:instanceOf(this, getElementType());
   */
  boolean addAll(int index, Collection c) throws ClassCastException;

  /**
   * @post   ! getElementType().isInstance(o) ? false;
   * @throws ClassCastException
   *         ! getElementType().isInstance(o);
   */
  Object set(int index, Object o) throws ClassCastException;

}