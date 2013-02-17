package org.toryt.util_I.collections.algebra;


import java.util.Iterator;

import org.toryt.patterns_I.Assertion;
import org.toryt.patterns_I.Collections;
import org.toryt.util_I.annotations.vcs.CvsInfo;
import org.toryt.util_I.collections.lockable.LockableSet;


/**
 * <p>A lazy set, that is the union of all component {@link LockableSet lockable sets}.</p>
 * <p>It is a precondition in the constructor for all
 *   component sets to be <em>disjunct</em>.</p>
 * <p>If one of the {@link #getComponents()} is <code>null</code>,
 *   it behaves as an empty set.</p>
 *
 * @author Jan Dockx
 *
 * @invar (forall int i; (i >= 0) && (i < getComponents().length);
 *          (forall int j; (j >= 0) && (j < getComponents().length) && (j != i);
 *            (forall Object o; getComponents()[i].contains(o);
 *              ! getComponents()[j].contains(o)));
 *        component sets are disjunct
 */
@CvsInfo(revision = "$Revision$",
         date     = "$Date$",
         state    = "$State$",
         tag      = "$Name$")
public class UnionSet<_Element_> extends AbstractComponentSet<_Element_, _Element_> {

  /**
   * @pre components != null;
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        (components[i] != null) ? components[i].isLocked());
   * @pre (! nullAllowed) ?
   *        (forall int i; (i >= 0) && (i < components.length);
   *          (components[i] != null) ? (! components[i].contains(null)));
   * @pre (forall int i; (i >= 0) && (i < components.length);
   *        (forall int j; (j >= 0) && (j < components.length) && (j != i);
   *          (components[i] != null) && (components[j] != null) ?
   *            (forall Object o; components[i].contains(o);
   *              ! components[j].contains(o)));
   *      component sets must be disjunct
   * @post Collections.containsAll(components, new.getComponents());
   */
  public UnionSet(boolean nullAllowed, LockableSet<? extends _Element_>... component) {
    super(nullAllowed, component);
  }

  // no need to override clone: there are no extra instance variables here

  public final int size() {
    int result = 0;
    for (LockableSet<? > c : $components) {
      if (c != null) {
        result += c.size();
      }
    }
    return result;
  }

  @Override
  public final boolean contains(final Object o) {
    return Collections.exists($components,
                              new Assertion<LockableSet<? extends _Element_>>() {

                                    public boolean isTrueFor(LockableSet<? extends _Element_> s) {
                                      return (s != null) && s.contains(o);
                                    }

                                  });
  }

  /**
   * @return (forall int i; (i > 0) && (i < getComponents().length);
   *            getComponents()[i].isEmpty());
   */
  public final boolean isEmpty() {
    return Collections.forAll($components,
                              new Assertion<LockableSet<? extends _Element_>>() {

                                    public boolean isTrueFor(LockableSet<? extends _Element_> o) {
                                      return (o == null) || o.isEmpty();
                                    }

                                  });
  }

  public Iterator<_Element_> iterator() {
    return new AbstractLockedCollectionIterator() {

      private int $componentIndex = -1;

      private void nextComponentIterator() {
        do {
          $componentIndex++;
        } while (($componentIndex < $components.length) &&
                 (($components[$componentIndex] == null) ||
                  $components[$componentIndex].isEmpty()));
        if ($componentIndex < $components.length) {
          assert ($components[$componentIndex] != null) &&
                 (! $components[$componentIndex].isEmpty());
          $componentIterator = $components[$componentIndex].iterator();
        }
        else {
          $componentIterator = null;
        }
      }

      private Iterator<? extends _Element_> $componentIterator;

      {
        nextComponentIterator();
      }

      public final boolean hasNext() {
        return $componentIterator != null;
      }

      public final _Element_ next() {
        _Element_ result = $componentIterator.next();
        if (! $componentIterator.hasNext()) {
          nextComponentIterator();
        }
        return result;
      }

    };
  }

}