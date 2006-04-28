package org.toryt.util_I.collections.typed;


import java.util.Collection;

import org.toryt.util_I.annotations.vcs.CvsInfo;


/**
 * <p>Collection that only allows elements of type
 *   {@link #getElementType()}, and nothing else. Can
 *   be configured to allow <code>null</code> or not.</p>
 *
 * @author Jan Dockx
 *
 * @invar toryt:cC org.toryt.patterns_I.Collections;
 * @invar getElementType() !=  null;
 * @invar cC:instanceOf(this, getElementType());
 *
 * @note When moving to Java 5, replace this with a generics.
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public interface TypedCollection extends Collection {

  /**
   * The type of the elements in the set.
   * No other types of objects are allowed.
   *
   * @basic
   *
   */
  Class getElementType();

  /**
   * Is <code>null</code> allowed as element in this collection?
   *
   * @basic
   */
  boolean isNullAllowed();

  /**
   * @post   ((! isNullAllowed()) && (o == null)) ? false;
   * @post   ! getElementType().isInstance(o) ? false;
   * @throws NullPointerException
   *         (! isNullAllowed()) && (o == null);
   * @throws ClassCastException
   *         ! getElementType().isInstance(o);
   */
  boolean add(Object o) throws NullPointerException, ClassCastException;

  /**
   * @post   ((! isNullAllowed()) && c.contains(null)) ? false;
   * @post   ((c != null) && ! cC:instanceOf(c, getElementType())) ? false;
   * @throws NullPointerException
   *         (! isNullAllowed()) && c.contains(null);
   * @throws ClassCastException
   *         (c != null) && ! cC:instanceOf(this, getElementType());
   */
  boolean addAll(Collection c) throws NullPointerException, ClassCastException;

}