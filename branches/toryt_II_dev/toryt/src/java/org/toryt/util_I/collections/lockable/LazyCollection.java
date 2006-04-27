package org.toryt.util_I.collections.lockable;


import java.util.Collection;



/**
 * <p>{@link Collection} that generates its elements fresh while
 *   iterating over them. Since each new {@link #iterator()}
 *   will generate fresh objects, these objects <strong>must</strong>
 *   override {@link Object#equals(Object)} for semantics to
 *   be correct (an implementation as <code>this == other</code>,
 *   i.e., reference semantics, is not allowed for elements).</p>
 * <p>Since elements are created lazily, {@link #toArray()} and
 *   {@link #toArray(Object[])} are
 *   especially expensive and <em>shouldn't be called</em>. To warn
 *   the developer about this, this method is declared depracated.</p>
 * <p>Lazy collections are unmodifiable. They are configured using
 *   constructor arguments. If these arguments are
 *   {@link Collection Collections} themselves, they should be of type
 *   {@link LockableCollection}, and {@link LockableCollection#isLocked() locked}
 *   before they are used.</p>
 *
 * @author Jan Dockx
 *
 * @invar isLocked();
 */
public interface LazyCollection extends LockableCollection {

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
   * This operation is very expensive. Don't call this method.
   *
   * @deprecated
   */
  Object[] toArray();

  /**
   * This operation is very expensive. Don't call this method.
   *
   * @deprecated
   */
  Object[] toArray(Object[] o);

}